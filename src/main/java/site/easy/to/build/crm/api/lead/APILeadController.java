package site.easy.to.build.crm.api.lead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.service.lead.LeadService;

@RestController
@RequestMapping("/api/lead")
public class APILeadController {

    private final LeadService leadService;

    @Autowired
    public APILeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable("id") Integer id) {
        Lead lead = leadService.findByLeadId(id);
        if (lead == null) {
            return ResponseEntity.notFound().build();
        }
        leadService.delete(lead);
        return ResponseEntity.ok().build();
    }
}