package site.easy.to.build.crm.webservice;

import lombok.Data;
import java.util.List;

@Data
public class DataTransfertObject {
    int statusCode;
    String message;
    List<String> erreur;
}