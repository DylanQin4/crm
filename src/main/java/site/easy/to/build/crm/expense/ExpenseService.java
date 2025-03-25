package site.easy.to.build.crm.expense;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.easy.to.build.crm.budget.Budget;
import site.easy.to.build.crm.budget.BudgetService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;

    public ExpenseService(ExpenseRepository expenseRepository, BudgetService budgetService) {
        this.expenseRepository = expenseRepository;
        this.budgetService = budgetService;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByBudgetId(Integer budgetId) {
        return expenseRepository.findByBudgetId(budgetId);
    }

    public void createExpense(Expense expense) {
        // Check if this is an existing expense being updated
        boolean isUpdate = expense.getId() != null;

        // Validate unique ticket constraint
        if (expense.getTicketId() != null) {
            List<Expense> existingExpenses = expenseRepository.findByTicket(expense.getTicketId());

            if (!existingExpenses.isEmpty() &&
                    (!isUpdate || !existingExpenses.get(0).getId().equals(expense.getId()))) {
                throw new DuplicateExpenseException("An expense record already exists for this ticket");
            }
        }

        // Validate unique lead constraint
        if (expense.getLeadId() != null) {
            List<Expense> existingExpenses = expenseRepository.findByLeadId(expense.getLeadId());

            if (!existingExpenses.isEmpty() &&
                    (!isUpdate || !existingExpenses.get(0).getId().equals(expense.getId()))) {
                throw new DuplicateExpenseException("An expense record already exists for this lead");
            }
        }

        // Check if budget limit would be exceeded
        Budget budget = expense.getBudget();
        if (budget != null) {
            BigDecimal currentExpenses = expenseRepository.getTotalExpensesByBudget(budget.getId());
            BigDecimal newExpenseAmount = expense.getAmount();

            // For updates, only count the difference
            if (isUpdate) {
                Expense oldExpense = expenseRepository.findById(expense.getId()).orElse(null);
                if (oldExpense != null) {
                    newExpenseAmount = newExpenseAmount.subtract(oldExpense.getAmount());
                }
            }

            BigDecimal totalAfterNewExpense = currentExpenses.add(newExpenseAmount);
            if (totalAfterNewExpense.compareTo(budget.getBudget()) > 0) {
                throw new RuntimeException("Budget limit exceeded");
            }
        }

        expenseRepository.save(expense);
    }

    @Transactional
    public void updateExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    @Transactional
    public void deleteExpense(Integer id) {
        expenseRepository.deleteById(id);
    }

    public Expense getExpenseById(Integer id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public BigDecimal getTotalExpensesByBudget(Integer budgetId) {
        List<Expense> expenses = expenseRepository.findByBudgetId(budgetId);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getRemainingBudget(Integer budgetId) {
        Budget budget = budgetService.getBudgetById(budgetId);
        if (budget == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalExpense = getTotalExpensesByBudget(budgetId);
        return budget.getBudget().subtract(totalExpense);
    }

    public Expense findExpenseById(Integer id) {
        return expenseRepository.findById(id).orElse(null);
    }
}