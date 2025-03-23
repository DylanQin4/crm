package site.easy.to.build.crm.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import site.easy.to.build.crm.entity.Customer;

import javax.annotation.RegEx;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "budget", nullable = false, precision = 18, scale = 2)
    private BigDecimal budget;

    @Column(name = "remaining_budget", nullable = false, precision = 18, scale = 2)
    private BigDecimal remainingBudget;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_min")
    private LocalDate dateMin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_max")
    private LocalDate dateMax;

    @Size(max = 250)
    @Column(name = "designation", length = 250)
    private String designation;

    @ColumnDefault("'ACTIVE'")
    @Lob
    @Column(name = "status")
    @Pattern(regexp = "^(ACTIVE|INACTIVE|SOLD_OUT)$", message = "Invalid status")
    private String status;

    @NotNull
    @Column(name = "customer_id")
    private Integer customerId;
}