package site.easy.to.build.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.easy.to.build.crm.service.db.DatabaseResetService;

@Controller
@RequestMapping("/manager")
public class DatabaseResetController {
    private final DatabaseResetService databaseResetService;

    public DatabaseResetController(DatabaseResetService databaseResetService) {
        this.databaseResetService = databaseResetService;
    }

    @PostMapping("/reset-db")
    public String resetDatabase(Model model) {
        try {
            databaseResetService.resetDatabase();
            model.addAttribute("message", "Base de données réinitialisée avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur : " + e.getMessage());
        }
        return "reset-db";
    }
}
