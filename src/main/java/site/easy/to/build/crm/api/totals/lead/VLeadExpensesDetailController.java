package site.easy.to.build.crm.api.totals.lead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/totals/lead")
public class VLeadExpensesDetailController {

    private final VLeadExpensesDetailService service;

    @Autowired
    public VLeadExpensesDetailController(VLeadExpensesDetailService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getTotalLeadExpenses() {
        BigDecimal total = service.calculateTotalLeadExpenses();
        Map<String, BigDecimal> response = new HashMap<>();
        response.put("totalAmount", total);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details")
    public ResponseEntity<List<VLeadExpensesDetail>> getAllLeadExpenseDetails() {
        List<VLeadExpensesDetail> details = service.getAllLeadExpenseDetails();
        return ResponseEntity.ok(details);
    }
}