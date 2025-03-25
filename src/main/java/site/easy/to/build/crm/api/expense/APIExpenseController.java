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
    public ResponseEntity<Void> updateExpenseAmount(@PathVariable("id_expense") Integer idExpense, @RequestBody String amount) {
        BigDecimal amountNumeric = BigDecimal.ZERO;
        System.out.println("amount: " + amount);
        try {
            amount = amount.replace("\"", "");
            amountNumeric = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        expenseService.updateExpenseAmount(idExpense, amountNumeric);
        return ResponseEntity.ok().build();
    }
}