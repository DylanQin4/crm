package site.easy.to.build.crm.budget;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetCustomerRepository budgetCustomerRepository;

    public BudgetService(BudgetRepository budgetRepository, BudgetCustomerRepository budgetCustomerRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetCustomerRepository = budgetCustomerRepository;
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
}
