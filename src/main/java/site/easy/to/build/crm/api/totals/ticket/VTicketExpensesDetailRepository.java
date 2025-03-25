package site.easy.to.build.crm.api.totals.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VTicketExpensesDetailRepository extends JpaRepository<VTicketExpensesDetail, Long> {

    List<VTicketExpensesDetail> findAllByOrderByTotalExpenseAmountDesc();

    @Query("SELECT SUM(v.totalExpenseAmount) FROM VTicketExpensesDetail v")
    BigDecimal calculateTotalExpenseAmount();
}