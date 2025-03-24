package site.easy.to.build.crm.api.graphic.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VTicketStatusDistributionServiceImpl implements VTicketStatusDistributionService {

    private final VTicketStatusDistributionRepository repository;

    @Autowired
    public VTicketStatusDistributionServiceImpl(VTicketStatusDistributionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VTicketStatusDistribution> getAllTicketStatusDistribution() {
        return repository.findAll();
    }

    @Override
    public List<VTicketStatusDistribution> getAllTicketStatusDistributionOrderByCount() {
        return repository.findAllByOrderByCountDesc();
    }

    @Override
    public List<VTicketStatusDistribution> getAllTicketStatusDistributionOrderByPercentage() {
        return repository.findAllByOrderByPercentageDesc();
    }
}
