package site.easy.to.build.crm.importCSV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.AuthenticationUtils;
import site.easy.to.build.crm.util.ImportUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ImportController {
    @Autowired
    ImportService importService;
    @Autowired
    AuthenticationUtils authenticationUtils;

    @Autowired
    UserService userService;
    @GetMapping("/import")
    public String importer(){
        return "import/import";
    }
    @PostMapping("/importer")
    public String importer(@RequestParam("data1") MultipartFile fichierData1, @RequestParam("data2") MultipartFile fichierData2, @RequestParam("data3") MultipartFile fichierData3, Model model, Authentication authentication){
        try {
            int userId = authenticationUtils.getLoggedInUserId(authentication);
            User user = userService.findById(userId);

            File file1= ImportUtil.convertMultipartFileToFile(fichierData1);
            File file2= ImportUtil.convertMultipartFileToFile(fichierData2);
            File file3= ImportUtil.convertMultipartFileToFile(fichierData3);
            List<String> erreurs1=new ArrayList<>();
            List<String> erreurs2=new ArrayList<>();
            List<String> erreurs3=new ArrayList<>();
            List<Data1> data1=Data1.lireEtVerifierCsv(erreurs1,file1);
            List<Data2> data2= Data2.lireEtVerifierCsv(erreurs2,file2);
            List<Data3> data3=Data3.lireEtVerifierCsv(erreurs3,file3);
            if (!erreurs1.isEmpty() || !erreurs2.isEmpty() || !erreurs3.isEmpty()){
                model.addAttribute("erreurs1",erreurs1);
                model.addAttribute("erreurs2",erreurs2);
                model.addAttribute("erreurs3",erreurs3);
                return "import/import";
            }
            List<String> erreursInsert=importService.saveImport(data1,data2,data3,user);
            if (erreursInsert.isEmpty()){
                model.addAttribute("message","Import reussi avec succes");
            }else{
                model.addAttribute("erreursInsert",erreursInsert);
            }
            return "import/import";
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return "pages/import/TableauImport";
    }

}

