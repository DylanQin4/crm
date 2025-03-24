package site.easy.to.build.crm.api.graphic.combined;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VMonthlyActivitiesServiceImpl implements VMonthlyActivitiesService {

    private final VMonthlyActivitiesRepository repository;

    @Autowired
    public VMonthlyActivitiesServiceImpl(VMonthlyActivitiesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VMonthlyActivities> getAllMonthlyActivities() {
        return repository.findAll();
    }

    @Override
    public List<VMonthlyActivities> getAllMonthlyActivitiesByChronologicalOrder() {
        return repository.findAllByOrderByYearAscMonthAsc();
    }

    @Override
    public List<VMonthlyActivities> getMonthlyActivitiesByYear(Integer year) {
        return repository.findByYearOrderByMonth(year);
    }
}