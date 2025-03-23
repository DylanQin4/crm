package site.easy.to.build.crm.budget;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.easy.to.build.crm.expense.ExpenseRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetCustomerRepository budgetCustomerRepository;
    private final ExpenseRepository expenseRepository;
    private final AlertRateRepository alertRateRepository;

    public BudgetService(BudgetRepository budgetRepository, BudgetCustomerRepository budgetCustomerRepository, ExpenseRepository expenseRepository, AlertRateRepository alertRateRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetCustomerRepository = budgetCustomerRepository;
        this.expenseRepository = expenseRepository;
        this.alertRateRepository = alertRateRepository;
    }

    public boolean isAlertReached(Integer budgetId) {
        BigDecimal totalExpenses = expenseRepository.getTotalExpensesByBudget(budgetId);
        BigDecimal budgetAmount = budgetRepository.findBudgetAmountById(budgetId);
        BigDecimal alertRate = alertRateRepository.findLatestRate();

        if (totalExpenses != null && budgetAmount != null) {
            BigDecimal alertThreshold = budgetAmount.multiply(alertRate);
            return totalExpenses.compareTo(alertThreshold) >= 0;
        }
        return false;
    }

    public boolean isBudgetExceeded(Integer budgetId) {
        BigDecimal totalExpenses = expenseRepository.getTotalExpensesByBudget(budgetId);
        BigDecimal budgetAmount = budgetRepository.findBudgetAmountById(budgetId);

        if (totalExpenses != null && budgetAmount != null) {
            return totalExpenses.compareTo(budgetAmount) > 0;
        }
        return false;
    }

    public List<BudgetCustomer> getAllBudgets() {
        return budgetCustomerRepository.findAll();
    }

    @Transactional
    public void createBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    @Transactional
    public void updateBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    @Transactional
    public void deleteBudget(Integer id) {
        budgetRepository.deleteById(id);
    }

    public Budget getBudgetById(Integer id) {
        return budgetRepository.findById(id).orElse(null);
    }

    public List<BudgetCustomer> getBudgetsByCustomerId(Integer customerId) {
        return budgetCustomerRepository.findByCustomerId(customerId);
    }

    public Budget findBudgetById(Integer id) {
        return budgetRepository.findById(id).orElse(null);
    }

    public BigDecimal getRemainingBudget(Integer budgetId) {
        Budget budget = getBudgetById(budgetId);
        if (budget == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalExpense = expenseRepository.getTotalExpensesByBudget(budgetId);
        return budget.getBudget().subtract(totalExpense);
    }
}
