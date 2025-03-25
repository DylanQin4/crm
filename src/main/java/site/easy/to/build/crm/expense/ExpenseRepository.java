package site.easy.to.build.crm.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByBudgetId(Integer budgetId);
    @Query("SELECT e FROM Expense e WHERE e.ticketId = :ticketId")
    List<Expense> findByTicket(Integer ticketId);
    @Query("SELECT e FROM Expense e WHERE e.leadId = :leadId")
    List<Expense> findByLeadId(Integer leadId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.budget.id = :budgetId")
    BigDecimal getTotalExpensesByBudget(@Param("budgetId") Integer budgetId);

    boolean existsByTicketId(Integer ticketId);
    boolean existsByLeadId(Integer leadId);
    Expense findByTicketId(Integer ticketId);
}

