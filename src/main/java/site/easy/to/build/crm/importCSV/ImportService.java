package site.easy.to.build.crm.importCSV;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.budget.Budget;
import site.easy.to.build.crm.budget.BudgetService;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.expense.Expense;
import site.easy.to.build.crm.expense.ExpenseService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImportService {
    @Autowired
    CustomerService customerService;
    @Autowired
    BudgetService budgetService;
    @Autowired
    TicketService ticketService;
    @Autowired
    ExpenseService depenseService;
    @Autowired
    LeadService leadService;
    public Customer saveCustomer(Data1 data1){
        Customer r=customerService.findByEmail(data1.getCustomerEmail());
        if(r==null){
            Customer customer=new Customer();
            customer.setEmail(data1.getCustomerEmail());
            customer.setName(data1.getCustomerName());
            customer.setCountry("madagascar");
            customer.setCreatedAt(LocalDateTime.now());
            r=customerService.save(customer);
        }
        return r;
    }
    public Customer insertOrRechercheCustomerParEmail(String email){
        Customer r=customerService.findByEmail(email);
        if(r==null){
            System.out.println("null"+email);
            Customer customer=new Customer();
            customer.setEmail(email);
            customer.setName(email);
            customer.setCreatedAt(LocalDateTime.now());
            r=customerService.save(customer);
        }
        return r;
    }
    public BigDecimal parseDoubleToBigDecimal(double valeur){
        return new BigDecimal(valeur);
    }
    public Budget saveBudgetCustomer(Data2 data2){
        Budget budget=new Budget();
        budget.setCustomerId(insertOrRechercheCustomerParEmail(data2.getCustomerEmail()).getCustomerId());
        budget.setBudget(parseDoubleToBigDecimal(data2.getBudget()));
        budget.setDesignation("budget de client"+customerService.findByCustomerId(budget.getCustomerId()).getEmail());
        return budgetService.createBudget(budget);
    }
    public Ticket insertTicket(Data3 data3, User user){
        Ticket t=new Ticket();
        t.setSubject(data3.getSubjectOrName());
        t.setStatus(data3.getStatus());
        t.setCustomer(insertOrRechercheCustomerParEmail(data3.getCustomerEmail()));
        t.setPriority("low");
        t.setCreatedAt(LocalDateTime.now());
        t.setManager(user);
        t.setEmployee(user);
        return ticketService.save(t);
    }
    public Expense insertExpenseTicket(Data3 data3, User user){
        Ticket t=insertTicket(data3,user);
        Expense d=new Expense();
        d.setTicketId(t.getTicketId());
        d.setLabel("depense ticket"+d.getLabel());
        d.setAmount(parseDoubleToBigDecimal(data3.getExpense()));
        return depenseService.createExpense(d, true);

    }
    public Lead insertLead(Data3 data3,User user){
        Lead l=new Lead();
        l.setManager(user);
        l.setEmployee(user);
        l.setName(data3.getSubjectOrName());
        l.setStatus(data3.getStatus());
        l.setCustomer(insertOrRechercheCustomerParEmail(data3.getCustomerEmail()));
        l.setCreatedAt(LocalDateTime.now());
        return leadService.save(l);
    }
    public Expense insertExpenseLead(Data3 data3,User user){
        Lead l=insertLead(data3,user);
        Expense d=new Expense();
        d.setLeadId(l.getLeadId());
        d.setLabel("depense lead "+d.getLabel());
        d.setAmount(parseDoubleToBigDecimal(data3.getExpense()));
        return depenseService.createExpense(d, true);
    }
    @Transactional
    public List<String> saveImport(List<Data1> data1, List<Data2> data2, List<Data3> data3,User user) {
        List<String> erreurs = new ArrayList<>();
        try {
            for (Data1 donne1 : data1) {
                saveCustomer(donne1);
            }
        } catch (Exception ex) {
            erreurs.add("Erreur de Customer: " + ex.getMessage());
            ex.printStackTrace();
            // Vous pouvez ici décider de continuer ou d'abandonner la transaction
        }

        try {
            for (Data2 donne2 : data2) {
                saveBudgetCustomer(donne2);
            }
        } catch (Exception ex) {
            erreurs.add("Erreur de Budget Customer: " + ex.getMessage());
            ex.printStackTrace();
            // Idem ici
        }

        try {
            for (Data3 donne3 : data3) {
                if (donne3.getType().equals("lead")) {
                    insertExpenseLead(donne3,user);
                } else if (donne3.getType().equals("ticket")) {
                    insertExpenseTicket(donne3,user);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            erreurs.add("Erreur de Expense: " + ex.getMessage());
        }
        return erreurs;
    }

}
