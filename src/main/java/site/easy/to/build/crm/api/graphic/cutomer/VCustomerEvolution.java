package site.easy.to.build.crm.api.graphic.cutomer;

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


@Getter
@Setter
@Entity
@Immutable
@Table(name = "v_customer_evolution_stats")
public class VCustomerEvolution {
    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Id
    @Size(max = 7)
    @Column(name = "period", length = 7)
    private String period;

    @NotNull
    @Column(name = "new_customers", nullable = false)
    private Long newCustomers;

    @Column(name = "cumulative_customers", precision = 42)
    private BigDecimal cumulativeCustomers;
}