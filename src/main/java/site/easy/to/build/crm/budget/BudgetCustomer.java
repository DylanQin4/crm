package site.easy.to.build.crm.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "budget_customer")
public class BudgetCustomer {
    @Id
    @NotNull
    @Column(name = "budget_id", nullable = false)
    private Integer budgetId;

    @Size(max = 250)
    @Column(name = "designation", length = 250)
    private String designation;

    @Column(name = "date_min")
    private LocalDate dateMin;

    @Column(name = "date_max")
    private LocalDate dateMax;

    @Lob
    @Column(name = "status")
    private String status;

    @Column(name = "customer_id", columnDefinition = "int UNSIGNED not null")
    private Integer customerId;

    @Size(max = 255)
    @Column(name = "customer_name")
    private String customerName;

    @NotNull
    @Column(name = "budget", nullable = false, precision = 18, scale = 2)
    private BigDecimal budget;

    @NotNull
    @Column(name = "remaining_budget", nullable = false, precision = 41, scale = 2)
    private BigDecimal remainingBudget;
}