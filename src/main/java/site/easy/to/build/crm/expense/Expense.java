package site.easy.to.build.crm.expense;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import site.easy.to.build.crm.budget.Budget;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "expenses")
@AtLeastOneNotNull(fields = {"ticketId", "leadId"}, message = "Either a ticket or a lead must be specified")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Size(max = 250)
    @Column(name = "label", length = 250)
    private String label;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_expense")
    private LocalDate dateExpense = LocalDate.now();

    @Column(name = "ticket_id")
    private Integer ticketId;

    @Column(name = "lead_id")
    private Integer leadId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;
}