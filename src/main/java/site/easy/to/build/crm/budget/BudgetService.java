package site.easy.to.build.crm.budget;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.expense.Expense;
import site.easy.to.build.crm.expense.ExpenseRepository;
import site.easy.to.build.crm.expense.ExpenseService;
import site.easy.to.build.crm.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetCustomerRepository budgetCustomerRepository;
    private final AlertRateRepository alertRateRepository;
    private final CustomerRepository customerRepository;
    private final ExpenseRepository expenseRepository;

    public BudgetService(BudgetRepository budgetRepository, BudgetCustomerRepository budgetCustomerRepository, AlertRateRepository alertRateRepository, CustomerRepository customerRepository, ExpenseRepository expenseRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetCustomerRepository = budgetCustomerRepository;
        this.alertRateRepository = alertRateRepository;
        this.customerRepository = customerRepository;
        this.expenseRepository = expenseRepository;
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

    public BigDecimal getTotalBudgetCustomerById(Integer customerId) {
        List<BudgetCustomer> budgets = budgetCustomerRepository.findByCustomerId(customerId);
        return budgets.stream()
                .map(BudgetCustomer::getBudget)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<String> getAlertMessages() {
        List<String> alertMessages = new ArrayList<>();
        BigDecimal alertRate = alertRateRepository.findLatestRate();

        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            BigDecimal totalBudgetCustomer = getTotalBudgetCustomerById(customer.getCustomerId());
            BigDecimal totalExpenses = getTotalExpensesByCustomerId(customer.getCustomerId());

            System.out.println("======================================");
            System.out.println(customer.getCustomerId());
            System.out.println(alertRate);
            System.out.println(totalBudgetCustomer);
            System.out.println(totalExpenses);
            System.out.println(totalExpenses.compareTo(totalBudgetCustomer.multiply(alertRate)));

            if (totalExpenses.compareTo(totalBudgetCustomer.multiply(alertRate)) >= 0) {
                alertMessages.add("Alert: Customer " + customer.getName() + " has reached the alert rate of their budget.");
            }
        }
        return alertMessages;
    }

    public BigDecimal getTotalExpensesByCustomerId(Integer customerId) {
        List<Expense> expenses = expenseRepository.findByCustomerId(customerId);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
