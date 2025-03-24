package site.easy.to.build.crm.api.graphic.cutomer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VCustomerEvolutionRepository extends JpaRepository<VCustomerEvolution, String> {
    List<VCustomerEvolution> findAllByOrderByYearAscMonthAsc();
    List<VCustomerEvolution> findByYearBetweenOrderByYearAscMonthAsc(Integer startYear, Integer endYear);
    List<VCustomerEvolution> findByPeriodBetweenOrderByPeriodAsc(String startPeriod, String endPeriod);
}
