package site.easy.to.build.crm.api.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.service.ticket.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class APITicketController {

    private final TicketService ticketService;

    @Autowired
    public APITicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Integer id) {
        Ticket ticket = ticketService.findByTicketId(id);
        if (ticket == null) {
            return ResponseEntity.notFound().build();
        }
        ticketService.delete(ticket);
        return ResponseEntity.ok().build();
    }
}