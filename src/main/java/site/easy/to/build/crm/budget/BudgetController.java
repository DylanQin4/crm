package site.easy.to.build.crm.budget;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.repository.CustomerRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final CustomerRepository customerRepository;

    public BudgetController(BudgetService budgetService, CustomerRepository customerRepository) {
        this.budgetService = budgetService;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public String listBudgets(@RequestParam(value = "customerId", required = false) Integer customerId, Model model) {
        List<BudgetCustomer> budgets = new ArrayList<>();
        if (customerId == null) {
            budgets = budgetService.getAllBudgets();
        } else {
            Customer customer = customerRepository.findByCustomerId(customerId);
            if (customer != null) {
                budgets = budgetService.getBudgetsByCustomerId(customerId);
            }
        }
        List<String> alertMessages = budgetService.getAlertMessages();
        model.addAttribute("budgets", budgets);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("customerId", customerId);
        model.addAttribute("alertMessages", alertMessages);
        return "budgets/all-budgets";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Budget budget = new Budget();
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("budget", budget);
        return "budgets/create-budget";
    }

    @PostMapping("/save")
    public String saveBudget(@Valid @ModelAttribute("budget") Budget budget, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "budgets/create-budget";
        }
        budgetService.createBudget(budget);
        return "redirect:/manager/budgets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Budget budget = budgetService.getBudgetById(id);
        if (budget == null) {
            return "error/not-found";
        }
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("budgetx", budget);
        return "budgets/update-budget";
    }

    @PostMapping("/update")
    public String updateBudget(@Valid @ModelAttribute("budget") Budget budget, BindingResult bindingResult) {
        System.out.println("Budget amount: " + budget.getBudget());
        if (bindingResult.hasErrors()) {
            return "budgets/update-budget";
        }
        budgetService.updateBudget(budget);
        return "redirect:/manager/budgets";
    }

    @GetMapping("/delete/{id}")
    public String deleteBudget(@PathVariable("id") Integer id) {
        budgetService.deleteBudget(id);
        return "redirect:/manager/budgets";
    }
}
