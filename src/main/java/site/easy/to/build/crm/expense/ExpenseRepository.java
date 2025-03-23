package site.easy.to.build.crm.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByBudgetId(Integer budgetId);
    @Query("SELECT e FROM Expense e WHERE e.ticketId = :ticketId")
    List<Expense> findByTicket(Integer ticketId);
    @Query("SELECT e FROM Expense e WHERE e.leadId = :leadId")
    List<Expense> findByLeadId(Integer leadId);
}

