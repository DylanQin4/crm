package site.easy.to.build.crm.api.graphic.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VTicketStatusDistributionRepository extends JpaRepository<VTicketStatusDistribution, String> {
    List<VTicketStatusDistribution> findAllByOrderByCountDesc();
    List<VTicketStatusDistribution> findAllByOrderByPercentageDesc();
}