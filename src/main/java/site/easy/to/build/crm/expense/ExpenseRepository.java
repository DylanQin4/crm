package site.easy.to.build.crm.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("SELECT e FROM Expense e WHERE e.ticketId = :ticketId")
    List<Expense> findByTicket(Integer ticketId);
    @Query("SELECT e FROM Expense e WHERE e.leadId = :leadId")
    List<Expense> findByLeadId(Integer leadId);

    boolean existsByTicketId(Integer ticketId);
    boolean existsByLeadId(Integer leadId);
    Expense findByTicketId(Integer ticketId);

    @Query("SELECT e FROM Expense e " +
            "WHERE e.ticketId IN (SELECT t.ticketId FROM Ticket t WHERE t.customer.customerId = :customerId) OR " +
            "e.leadId IN (SELECT l.leadId FROM Lead l WHERE l.customer.customerId = :customerId)")
    List<Expense> findByCustomerId(@Param("customerId") Integer customerId);
}

