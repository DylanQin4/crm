package site.easy.to.build.crm.api.totals.ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "v_ticket_expenses_detail")
public class VTicketExpensesDetail {
    @Id
    @Column(name = "ticket_id", columnDefinition = "int UNSIGNED not null")
    private Long ticketId;

    @Size(max = 255)
    @Column(name = "subject")
    private String subject;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Size(max = 50)
    @Column(name = "priority", length = 50)
    private String priority;

    @Size(max = 255)
    @Column(name = "customer_name")
    private String customerName;

    @NotNull
    @Column(name = "expense_id", nullable = false)
    private Integer expenseId;

    @NotNull
    @Column(name = "expense_count", nullable = false)
    private Long expenseCount;

    @NotNull
    @Column(name = "total_expense_amount", nullable = false, precision = 40, scale = 2)
    private BigDecimal totalExpenseAmount;

}