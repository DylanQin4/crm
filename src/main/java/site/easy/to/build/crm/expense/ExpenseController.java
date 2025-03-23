package site.easy.to.build.crm.expense;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.budget.BudgetService;
import site.easy.to.build.crm.repository.CustomerRepository;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/employee/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final BudgetService budgetService;
    private final CustomerRepository customerRepository;
    private final TicketService ticketService;
    private final LeadService leadService;
    private final AuthenticationUtils authenticationUtils;

    public ExpenseController(ExpenseService expenseService,
                             BudgetService budgetService,
                             CustomerRepository customerRepository, TicketService ticketService, LeadService leadService, AuthenticationUtils authenticationUtils) {
        this.expenseService = expenseService;
        this.budgetService = budgetService;
        this.customerRepository = customerRepository;
        this.ticketService = ticketService;
        this.leadService = leadService;
        this.authenticationUtils = authenticationUtils;
    }

    @GetMapping
    public String listExpenses(@RequestParam(value = "budgetId", required = false) Integer budgetId,
                                   Model model) {
        List<Expense> expenses;

        if (budgetId != null) {
            expenses = expenseService.getExpensesByBudgetId(budgetId);
            model.addAttribute("budget", budgetService.getBudgetById(budgetId));
            model.addAttribute("budgetId", budgetId);
        } else {
            expenses = expenseService.getAllExpenses();
        }

        model.addAttribute("expenses", expenses);
        model.addAttribute("budgets", budgetService.getAllBudgets());
        return "expenses/all-expenses";
    }

    @GetMapping("/new")
    public String showCreateForm(@RequestParam(value = "budgetId", required = false) Integer budgetId,
                                 Model model,
                                 Authentication authentication) {
        int userId = authenticationUtils.getLoggedInUserId(authentication);
        if(userId == -1) {
            return "error/not-found";
        }
        Expense expense = new Expense();


        if (budgetId != null) {
            expense.setBudget(budgetService.getBudgetById(budgetId));
        }

        model.addAttribute("expense", expense);
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("tickets", ticketService.findEmployeeTickets(userId));
        model.addAttribute("leads", leadService.findAssignedLeads(userId));
        return "expenses/create-expense";
    }

    @PostMapping("/save")
    public String saveExpense(@Valid @ModelAttribute("expense") Expense expense,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("budgets", budgetService.getAllBudgets());
            model.addAttribute("customers", customerRepository.findAll());
            return "expenses/create-expense";
        }

        try {
            expenseService.createExpense(expense);
            return "redirect:/employee/expenses?budgetId=" + expense.getBudget().getId();
        } catch (Exception e) {
            bindingResult.rejectValue("amount", "error.expense", "Budget limit exceeded");
            model.addAttribute("budgets", budgetService.getAllBudgets());
            model.addAttribute("customers", customerRepository.findAll());
            return "expenses/create-expense";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id,
                               Model model,
                               Authentication authentication) {
        int userId = authenticationUtils.getLoggedInUserId(authentication);
        if(userId == -1) {
            return "error/not-found";
        }
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return "error/not-found";
        }

        model.addAttribute("expense", expense);
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("tickets", ticketService.findEmployeeTickets(userId));
        model.addAttribute("leads", leadService.findAssignedLeads(userId));
        return "expenses/update-expense";
    }

    @PostMapping("/update")
    public String updateExpense(@Valid @ModelAttribute("expense") Expense expense,
                                    BindingResult bindingResult,
                                    Model model,
                                    Authentication authentication) {
        int userId = authenticationUtils.getLoggedInUserId(authentication);
        if(userId == -1) {
            return "error/not-found";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("budgets", budgetService.getAllBudgets());
            model.addAttribute("tickets", ticketService.findEmployeeTickets(userId));
            model.addAttribute("leads", leadService.findAssignedLeads(userId));
            return "expenses/update-expense";
        }

        try {
            expenseService.updateExpense(expense);
            return "redirect:/employee/expenses?budgetId=" + expense.getBudget().getId();
        } catch (Exception e) {
            bindingResult.rejectValue("amount", "error.expense", "Budget limit exceeded");
            model.addAttribute("budgets", budgetService.getAllBudgets());
            model.addAttribute("tickets", ticketService.findEmployeeTickets(userId));
            model.addAttribute("leads", leadService.findAssignedLeads(userId));
            return "expenses/update-expense";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable("id") Integer id,
                                    @RequestParam(required = false) Integer budgetId) {
        Expense expense = expenseService.getExpenseById(id);
        Integer redirectBudgetId = budgetId;

        if (expense != null && redirectBudgetId == null) {
            redirectBudgetId = expense.getBudget().getId();
        }

        expenseService.deleteExpense(id);
        return "redirect:/employee/expenses" + (redirectBudgetId != null ? "?budgetId=" + redirectBudgetId : "");
    }
}