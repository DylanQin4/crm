package site.easy.to.build.crm.service.importCSV;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.entity.Role;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import jakarta.validation.ConstraintViolation;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.repository.RoleRepository;

@Service
public class ImportCsvService {
    private final LocalValidatorFactoryBean validator;
    private final RoleRepository roleRepository;

    public ImportCsvService(LocalValidatorFactoryBean validator, RoleRepository roleRepository) {
        this.validator = validator;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void importRolesFromCSV(MultipartFile file) throws IOException {
        List<Role> roleToSave = new ArrayList<>();
        try (Reader reader = new InputStreamReader(file.getInputStream())){
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    //.withDelimiter(',')
                    .withFirstRecordAsHeader()
                    .parse(reader);
            int lineNumber= 1;
            for (CSVRecord record : records) {
                Role role = new Role();
                role.setName(record.get("name"));

                // validation manuelle
                Set<ConstraintViolation<Role>> violations = validator.validate(role);
                if (!violations.isEmpty()) {
                    // s'il y a des violations
                    StringBuilder errorMsg = new StringBuilder("Erreur a la ligne " + lineNumber + " : ");
                    for (ConstraintViolation<Role> violation : violations) {
                        errorMsg.append(violation.getMessage()).append(" ");
                    }
                    throw new IOException(errorMsg.toString());
                } else {
                    roleToSave.add(role);
                }
                lineNumber++;
            }
        }

        try {
            for (Role role : roleToSave) {
                roleRepository.save(role);
            }
            System.out.println("import OK");
        } catch (Exception e) {
            throw new IOException("Erreur lors de l'enregistrement des donnees : " + e.getMessage());
        }
    }
}
