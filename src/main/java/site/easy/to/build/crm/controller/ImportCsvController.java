package site.easy.to.build.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.service.importCSV.ImportCsvService;

import java.io.IOException;

@Controller
public class ImportCsvController {
    private final ImportCsvService importCsvService;

    public ImportCsvController(ImportCsvService importCsvService) {
        this.importCsvService = importCsvService;
    }

    @GetMapping("/import-csv")
    public String importCsv() {
        return "import-csv/import-csv";
    }

    @PostMapping("/import-csv")
    public String importCsv(@RequestParam("file") MultipartFile file, Model model) {
        try {
            importCsvService.importRolesFromCSV(file);
            model.addAttribute("success", "Importation réussie !");
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "import-csv/import-csv";
    }

}
