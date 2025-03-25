package site.easy.to.build.crm.api.totals.lead;

import java.math.BigDecimal;
import java.util.List;

public interface VLeadExpensesDetailService {
    List<VLeadExpensesDetail> getAllLeadExpenseDetails();
    BigDecimal calculateTotalLeadExpenses();
}