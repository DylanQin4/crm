package site.easy.to.build.crm.api.totals.lead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VLeadExpensesDetailServiceImpl implements VLeadExpensesDetailService {

    private final VLeadExpensesDetailRepository repository;

    @Autowired
    public VLeadExpensesDetailServiceImpl(VLeadExpensesDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VLeadExpensesDetail> getAllLeadExpenseDetails() {
        return repository.findAllByOrderByTotalExpenseAmountDesc();
    }

    @Override
    public BigDecimal calculateTotalLeadExpenses() {
        BigDecimal total = repository.calculateTotalExpenseAmount();
        return total != null ? total : BigDecimal.ZERO;
    }
}