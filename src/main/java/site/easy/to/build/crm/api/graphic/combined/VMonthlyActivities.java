package site.easy.to.build.crm.api.graphic.combined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "v_monthly_activities_evolution")
public class VMonthlyActivities {
    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Id
    @Size(max = 7)
    @Column(name = "period", length = 7)
    private String period;

    @Size(max = 21)
    @Column(name = "month_year_label", length = 21)
    private String monthYearLabel;

    @Column(name = "ticket_count")
    private Long ticketCount;

    @Column(name = "lead_count")
    private Long leadCount;

    @Column(name = "total_expenses", precision = 40, scale = 2)
    private BigDecimal totalExpenses;

}