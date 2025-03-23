package site.easy.to.build.crm.expense;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.easy.to.build.crm.budget.Budget;
import site.easy.to.build.crm.budget.BudgetService;

import java.math.BigDecimal;
import java.util.List;

@Service
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

    @Transactional
    public void createExpense(Expense expense) {
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