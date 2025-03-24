package site.easy.to.build.crm.api.graphic.ticket;

import java.util.List;

public interface VTicketStatusDistributionService {
    List<VTicketStatusDistribution> getAllTicketStatusDistribution();
    List<VTicketStatusDistribution> getAllTicketStatusDistributionOrderByCount();
    List<VTicketStatusDistribution> getAllTicketStatusDistributionOrderByPercentage();
}