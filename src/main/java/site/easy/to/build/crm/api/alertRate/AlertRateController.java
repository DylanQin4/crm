package site.easy.to.build.crm.alertRate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.budget.AlertRate;
import site.easy.to.build.crm.budget.AlertRateRepository;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/alert-rate")
public class AlertRateController {

    @Autowired
    private AlertRateRepository alertRateRepository;

    @GetMapping("/latest")
    public ResponseEntity<BigDecimal> getLatestRate() {
        BigDecimal latestRate = alertRateRepository.findLatestRate();
        return ResponseEntity.ok(latestRate);
    }

    @PostMapping("/create")
    public ResponseEntity<AlertRate> createAlertRate(@RequestBody String rate) {
        BigDecimal r;
        try {
            rate = rate.replace("\"", "");
            r = new BigDecimal(rate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        AlertRate alertRate = new AlertRate();
        alertRate.setRate(r);
        AlertRate savedAlertRate = alertRateRepository.save(alertRate);
        return ResponseEntity.ok(savedAlertRate);
    }
}