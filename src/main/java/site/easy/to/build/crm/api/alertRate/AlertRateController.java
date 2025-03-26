package site.easy.to.build.crm.api.alertRate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.budget.AlertRate;
import site.easy.to.build.crm.budget.AlertRateRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/alert-rate")
public class AlertRateController {

    @Autowired
    private AlertRateRepository alertRateRepository;

    @GetMapping("/latest")
    public ResponseEntity<Map<String, BigDecimal>> getLatestRate() {
        BigDecimal latestRate = alertRateRepository.findLatestRate();
        Map<String, BigDecimal> response = Map.of("rate", latestRate);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<AlertRate> createAlertRate(@RequestBody String rate) {
        BigDecimal r;
        System.out.println(rate);
        try {
            rate = rate.replace("\"", "");
            r = new BigDecimal(rate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        AlertRate alertRate = new AlertRate();
        alertRate.setCreatedAt(Instant.now());
        alertRate.setRate(r);
        AlertRate savedAlertRate = alertRateRepository.save(alertRate);
        return ResponseEntity.ok(savedAlertRate);
    }
}