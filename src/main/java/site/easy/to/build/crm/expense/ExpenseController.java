package site.easy.to.build.crm.expense;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.budget.Budget;
import site.easy.to.build.crm.budget.BudgetService;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.repository.CustomerRepository;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String listExpenses(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();

        model.addAttribute("expenses", expenses);
        return "expenses/all-expenses";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model, Authentication authentication) {
        int userId = authenticationUtils.getLoggedInUserId(authentication);
        if(userId == -1) {
            return "error/not-found";
        }
        Expense expense = new Expense();

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"));

        List<Ticket> tickets = isManager ? ticketService.getAllTickets() : ticketService.findEmployeeTickets(userId);
        List<Lead> leads = isManager ? leadService.getAllLeads() : leadService.findAssignedLeads(userId);

        model.addAttribute("expense", expense);
        model.addAttribute("tickets", tickets);
        model.addAttribute("leads", leads);
        return "expenses/create-expense";
    }

    @PostMapping("/save")
    public String saveExpense(@Valid @ModelAttribute("expense") Expense expense,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam(value = "confirm", required = false) Boolean confirm,
                              Authentication authentication) {

        int userId = authenticationUtils.getLoggedInUserId(authentication);
        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"));
        List<Ticket> tickets = isManager ? ticketService.getAllTickets() : ticketService.findEmployeeTickets(userId);
        List<Lead> leads = isManager ? leadService.getAllLeads() : leadService.findAssignedLeads(userId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("tickets", tickets);
            model.addAttribute("leads", leads);
            return "expenses/create-expense";
        }

        try {
            expenseService.createExpense(expense, Boolean.TRUE.equals(confirm));
            return "redirect:/employee/expenses";
        } catch (DuplicateExpenseException e) {
            if (expense.getTicketId() != null) {
                bindingResult.rejectValue("ticketId", "error.expense.duplicate",
                        "An expense already exists for this ticket");
            } else if (expense.getLeadId() != null) {
                bindingResult.rejectValue("leadId", "error.expense.duplicate",
                        "An expense already exists for this lead");
            }
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("tickets", tickets);
            model.addAttribute("leads", leads);
            return "expenses/create-expense";
        } catch (BudgetAlertException e) {
            model.addAttribute("confirmMessage", e.getMessage());
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("tickets", tickets);
            model.addAttribute("leads", leads);
            return "expenses/create-expense";
        } catch (Exception e) {
            bindingResult.rejectValue("amount", "error.expense", e.getMessage());
            model.addAttribute("budgets", budgetService.getAllBudgets());
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("tickets", tickets);
            model.addAttribute("leads", leads);
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

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"));

        List<Ticket> tickets = isManager ? ticketService.getAllTickets() : ticketService.findEmployeeTickets(userId);
        List<Lead> leads = isManager ? leadService.getAllLeads() : leadService.findAssignedLeads(userId);

        model.addAttribute("expense", expense);
        model.addAttribute("tickets", tickets);
        model.addAttribute("leads", leads);
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

        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"));

        List<Ticket> tickets = isManager ? ticketService.getAllTickets() : ticketService.findEmployeeTickets(userId);
        List<Lead> leads = isManager ? leadService.getAllLeads() : leadService.findAssignedLeads(userId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("tickets", tickets);
            model.addAttribute("leads", leads);
            return "expenses/update-expense";
        }

        try {
            expenseService.updateExpense(expense);
            return "redirect:/employee/expenses";
        } catch (Exception e) {
            bindingResult.rejectValue("amount", "error.expense", "Budget limit exceeded");
            model.addAttribute("budgets", budgetService.getAllBudgets());
            model.addAttribute("tickets", tickets);
            model.addAttribute("leads", leads);
            return "expenses/update-expense";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable("id") Integer id) {
        Expense expense = expenseService.getExpenseById(id);

        expenseService.deleteExpense(id);
        return "redirect:/employee/expenses";
    }
}