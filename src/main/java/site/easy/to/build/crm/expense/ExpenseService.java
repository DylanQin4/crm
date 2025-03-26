package site.easy.to.build.crm.expense;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.easy.to.build.crm.budget.AlertRateRepository;
import site.easy.to.build.crm.budget.Budget;
import site.easy.to.build.crm.budget.BudgetService;
import site.easy.to.build.crm.repository.LeadRepository;
import site.easy.to.build.crm.repository.TicketRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;
    private final AlertRateRepository alertRateRepository;
    private final TicketRepository ticketRepository;
    private final LeadRepository leadRepository;

    public ExpenseService(ExpenseRepository expenseRepository, BudgetService budgetService, AlertRateRepository alertRateRepository, TicketRepository ticketRepository, LeadRepository leadRepository) {
        this.expenseRepository = expenseRepository;
        this.budgetService = budgetService;
        this.alertRateRepository = alertRateRepository;
        this.ticketRepository = ticketRepository;
        this.leadRepository = leadRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void updateExpenseAmount(Integer id, BigDecimal amount) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setAmount(amount);
            expenseRepository.save(expense);
        } else {
            throw new RuntimeException("Expense not found");
        }
    }

    public void createExpense(Expense expense, boolean isConfirmed) {
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

        // Check if the expense amount exceeds the budget limit
        BigDecimal alertRate = alertRateRepository.findLatestRate();
        Integer customerId = expense.getTicketId() != null
                ? ticketRepository.findByTicketId(expense.getTicketId()).getCustomer().getCustomerId()
                : leadRepository.findByLeadId(expense.getLeadId()).getCustomer().getCustomerId();
        BigDecimal totalBudgetCustomer = budgetService.getTotalBudgetCustomerById(customerId);
        BigDecimal totalExpenses = getTotalExpensesByCustomerId(customerId);

        System.out.println(customerId);
        System.out.println(alertRate);
        System.out.println(totalBudgetCustomer);
        System.out.println(totalExpenses);
        System.out.println(totalExpenses.add(expense.getAmount()).compareTo(totalBudgetCustomer));

        if (!isConfirmed && totalExpenses.add(expense.getAmount()).compareTo(totalBudgetCustomer) > 0) {
            throw new BudgetAlertException("The expense amount exceeds the customer's total budget. Please confirm to proceed.");
        }

        expenseRepository.save(expense);
    }

    public BigDecimal getTotalExpensesByCustomerId(Integer customerId) {
        List<Expense> expenses = expenseRepository.findByCustomerId(customerId);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public Expense findExpenseById(Integer id) {
        return expenseRepository.findById(id).orElse(null);
    }
}