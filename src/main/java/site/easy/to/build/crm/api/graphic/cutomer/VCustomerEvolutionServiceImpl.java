package site.easy.to.build.crm.api.graphic.cutomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VCustomerEvolutionServiceImpl implements VCustomerEvolutionService {

    private final VCustomerEvolutionRepository customerEvolutionRepository;

    @Autowired
    public VCustomerEvolutionServiceImpl(VCustomerEvolutionRepository customerEvolutionRepository) {
        this.customerEvolutionRepository = customerEvolutionRepository;
    }

    @Override
    public List<VCustomerEvolution> getAllCustomerEvolution() {
        return customerEvolutionRepository.findAllByOrderByYearAscMonthAsc();
    }

    @Override
    public List<VCustomerEvolution> getCustomerEvolutionByYearRange(Integer startYear, Integer endYear) {
        return customerEvolutionRepository.findByYearBetweenOrderByYearAscMonthAsc(startYear, endYear);
    }

    @Override
    public List<VCustomerEvolution> getCustomerEvolutionByPeriodRange(String startPeriod, String endPeriod) {
        return customerEvolutionRepository.findByPeriodBetweenOrderByPeriodAsc(startPeriod, endPeriod);
    }
}
