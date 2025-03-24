package site.easy.to.build.crm.api.graphic.combined;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-activities")
public class VMonthlyActivitiesController {

    private final VMonthlyActivitiesService service;

    @Autowired
    public VMonthlyActivitiesController(VMonthlyActivitiesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VMonthlyActivities>> getMonthlyActivities(
            @RequestParam(name = "year", required = false) Integer year) {

        List<VMonthlyActivities> result;

        if (year != null) {
            result = service.getMonthlyActivitiesByYear(year);
        } else {
            result = service.getAllMonthlyActivitiesByChronologicalOrder();
        }

        return ResponseEntity.ok(result);
    }
}