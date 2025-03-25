package site.easy.to.build.crm.api.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.expense.ExpenseService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/expense")
public class APIExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public APIExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PutMapping("/{id_expense}")
    public ResponseEntity<Void> updateExpenseAmount(@PathVariable("id_expense") Integer idExpense, @RequestBody BigDecimal amount) {
        expenseService.updateExpenseAmount(idExpense, amount);
        return ResponseEntity.ok().build();
    }
}