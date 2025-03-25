package site.easy.to.build.crm.api.totals.lead;

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
@Table(name = "v_lead_expenses_detail")
public class VLeadExpensesDetail {
    @Id
    @Column(name = "lead_id", columnDefinition = "int UNSIGNED not null")
    private Integer leadId;

    @Size(max = 255)
    @Column(name = "lead_name")
    private String leadName;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Size(max = 255)
    @Column(name = "customer_name")
    private String customerName;

    @NotNull
    @Column(name = "expense_id", nullable = false)
    private Integer expenseId;

    @NotNull
    @Column(name = "expense_count", nullable = false)
    private Integer expenseCount;

    @NotNull
    @Column(name = "total_expense_amount", nullable = false, precision = 40, scale = 2)
    private BigDecimal totalExpenseAmount;

}