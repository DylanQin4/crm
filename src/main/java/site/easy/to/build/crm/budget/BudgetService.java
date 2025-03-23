package site.easy.to.build.crm.budget;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Transactional
    public void createBudget(Budget budget) {
        if (budget.getRemainingBudget() == null) {
            budget.setRemainingBudget(budget.getBudget());
        }
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

    public List<Budget> getBudgetsByCustomerId(Integer customerId) {
        return budgetRepository.findByCustomerId(customerId);
    }
}
