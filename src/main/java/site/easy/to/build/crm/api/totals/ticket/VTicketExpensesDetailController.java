package site.easy.to.build.crm.api.totals.ticket;

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
@RequestMapping("/api/totals/ticket")
public class VTicketExpensesDetailController {

    private final VTicketExpensesDetailService ticketExpensesDetailService;

    @Autowired
    public VTicketExpensesDetailController(VTicketExpensesDetailService ticketExpensesDetailService) {
        this.ticketExpensesDetailService = ticketExpensesDetailService;
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getTotalTicketExpenses() {
        BigDecimal total = ticketExpensesDetailService.calculateTotalTicketExpenses();
        Map<String, BigDecimal> response = new HashMap<>();
        response.put("totalAmount", total);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details")
    public ResponseEntity<List<VTicketExpensesDetail>> getAllTicketExpensesDetails() {
        List<VTicketExpensesDetail> details = ticketExpensesDetailService.getAllTicketExpenses();
        return ResponseEntity.ok(details);
    }
}