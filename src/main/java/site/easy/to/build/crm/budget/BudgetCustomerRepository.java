package site.easy.to.build.crm.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetCustomerRepository extends JpaRepository<BudgetCustomer, Integer> {
    List<BudgetCustomer> findByCustomerId(Integer customerId);
}
