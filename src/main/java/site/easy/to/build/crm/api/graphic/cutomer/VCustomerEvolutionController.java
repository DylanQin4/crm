package site.easy.to.build.crm.api.graphic.cutomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customer/evolution")
public class VCustomerEvolutionController {

    private final VCustomerEvolutionService customerEvolutionService;

    @Autowired
    public VCustomerEvolutionController(VCustomerEvolutionService customerEvolutionService) {
        this.customerEvolutionService = customerEvolutionService;
    }

    @GetMapping
    public ResponseEntity<List<VCustomerEvolution>> getAllCustomerEvolution() {
        List<VCustomerEvolution> evolutionData = customerEvolutionService.getAllCustomerEvolution();
        return ResponseEntity.ok(evolutionData);
    }

    @GetMapping("/year-range")
    public ResponseEntity<List<VCustomerEvolution>> getCustomerEvolutionByYearRange(
            @RequestParam Integer startYear,
            @RequestParam Integer endYear) {
        List<VCustomerEvolution> evolutionData =
                customerEvolutionService.getCustomerEvolutionByYearRange(startYear, endYear);
        return ResponseEntity.ok(evolutionData);
    }

    @GetMapping("/period-range")
    public ResponseEntity<List<VCustomerEvolution>> getCustomerEvolutionByPeriodRange(
            @RequestParam String startPeriod,
            @RequestParam String endPeriod) {
        List<VCustomerEvolution> evolutionData =
                customerEvolutionService.getCustomerEvolutionByPeriodRange(startPeriod, endPeriod);
        return ResponseEntity.ok(evolutionData);
    }
}
