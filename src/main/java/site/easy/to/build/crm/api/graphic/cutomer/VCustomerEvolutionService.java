package site.easy.to.build.crm.api.graphic.cutomer;

import java.util.List;

public interface VCustomerEvolutionService {
    List<VCustomerEvolution> getAllCustomerEvolution();
    List<VCustomerEvolution> getCustomerEvolutionByYearRange(Integer startYear, Integer endYear);
    List<VCustomerEvolution> getCustomerEvolutionByPeriodRange(String startPeriod, String endPeriod);
}
