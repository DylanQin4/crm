package site.easy.to.build.crm.api.totals.ticket;

import java.math.BigDecimal;
import java.util.List;

public interface VTicketExpensesDetailService {
    List<VTicketExpensesDetail> getAllTicketExpenses();
    BigDecimal calculateTotalTicketExpenses();
}