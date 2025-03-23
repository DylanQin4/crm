package site.easy.to.build.crm.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import site.easy.to.build.crm.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findByCustomerId(Integer customerId);

    @Query("SELECT b.budget FROM Budget b WHERE b.id = :budgetId")
    BigDecimal findBudgetAmountById(@Param("budgetId") Integer budgetId);
}
