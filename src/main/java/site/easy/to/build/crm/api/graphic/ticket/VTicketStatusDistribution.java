package site.easy.to.build.crm.api.graphic.ticket;

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
@Table(name = "v_ticket_status_distribution")
public class VTicketStatusDistribution {
    @Id
    @Size(max = 25)
    @NotNull
    @Column(name = "status", nullable = false, length = 25)
    private String status;

    @NotNull
    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "percentage", precision = 26, scale = 2)
    private BigDecimal percentage;

}