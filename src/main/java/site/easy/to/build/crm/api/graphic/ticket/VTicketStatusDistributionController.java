package site.easy.to.build.crm.api.graphic.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ticket/status-distribution")
public class VTicketStatusDistributionController {

    private final VTicketStatusDistributionService service;

    @Autowired
    public VTicketStatusDistributionController(VTicketStatusDistributionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VTicketStatusDistribution>> getTicketStatusDistribution(
            @RequestParam(name = "sort", required = false, defaultValue = "count") String sortBy) {

        List<VTicketStatusDistribution> result;

        switch(sortBy.toLowerCase()) {
            case "percentage":
                result = service.getAllTicketStatusDistributionOrderByPercentage();
                break;
            case "count":
            default:
                result = service.getAllTicketStatusDistributionOrderByCount();
                break;
        }

        return ResponseEntity.ok(result);
    }
}