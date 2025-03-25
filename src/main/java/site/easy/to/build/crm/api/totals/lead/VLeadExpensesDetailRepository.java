package site.easy.to.build.crm.api.totals.lead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VLeadExpensesDetailRepository extends JpaRepository<VLeadExpensesDetail, Integer> {

    List<VLeadExpensesDetail> findAllByOrderByTotalExpenseAmountDesc();

    @Query("SELECT SUM(v.totalExpenseAmount) FROM VLeadExpensesDetail v")
    BigDecimal calculateTotalExpenseAmount();
}