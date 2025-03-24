package site.easy.to.build.crm.api.graphic.combined;

import java.util.List;

public interface VMonthlyActivitiesService {
    List<VMonthlyActivities> getAllMonthlyActivities();
    List<VMonthlyActivities> getAllMonthlyActivitiesByChronologicalOrder();
    List<VMonthlyActivities> getMonthlyActivitiesByYear(Integer year);
}