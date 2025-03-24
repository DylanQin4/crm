package site.easy.to.build.crm.api.graphic.combined;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VMonthlyActivitiesRepository extends JpaRepository<VMonthlyActivities, String> {
    List<VMonthlyActivities> findAllByOrderByYearAscMonthAsc();
    List<VMonthlyActivities> findByYearOrderByMonth(Integer year);
}