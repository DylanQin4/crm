package site.easy.to.build.crm.api.totals.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VTicketExpensesDetailServiceImpl implements VTicketExpensesDetailService {

    private final VTicketExpensesDetailRepository ticketExpensesDetailRepository;

    @Autowired
    public VTicketExpensesDetailServiceImpl(VTicketExpensesDetailRepository ticketExpensesDetailRepository) {
        this.ticketExpensesDetailRepository = ticketExpensesDetailRepository;
    }

    @Override
    public List<VTicketExpensesDetail> getAllTicketExpenses() {
        return ticketExpensesDetailRepository.findAllByOrderByTotalExpenseAmountDesc();
    }

    @Override
    public BigDecimal calculateTotalTicketExpenses() {
        BigDecimal total = ticketExpensesDetailRepository.calculateTotalExpenseAmount();
        return total != null ? total : BigDecimal.ZERO;
    }
}