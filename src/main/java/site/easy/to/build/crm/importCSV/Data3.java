package site.easy.to.build.crm.importCSV;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Data3 {
   private String customerEmail;
   private String subjectOrName;
   private String type;
   private String status;
   private double expense;
   public void setCustomerEmail(String email, List<String> erreurs, int ligne) {
      if (email == null || email.trim().isEmpty()) {
         erreurs.add("Erreur ligne " + ligne + ": Email ne doit pas être vide.");
         return;
      }

       if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
         erreurs.add("Erreur ligne " + ligne + ": Email invalide -> " + email);
      } else {
         this.customerEmail = email;
      }
   }

   public void setType(String type, List<String> erreurs, int ligne) {
      if (type.equalsIgnoreCase("lead") || type.equalsIgnoreCase("ticket")) {
         this.type = type.toLowerCase();
      } else {
         erreurs.add("Erreur ligne " + ligne + ": Type invalide (doit être 'lead' ou 'ticket') -> " + type);
      }
   }

   public void setStatus(String status, List<String> erreurs, int ligne) {
      if (this.type == null) {
         erreurs.add("Erreur ligne " + ligne + ": Type doit être défini avant le statut.");
         return;
      }

      // Default value
       if (this.type.equals("lead")) {
           status = "meeting-to-schedule";
       } else if (this.type.equals("ticket")) {
           status = "open";
       }

      if (this.type.equals("lead") && !status.matches("^(meeting-to-schedule|scheduled|archived|success|assign-to-sales)$")) {
         erreurs.add("Erreur ligne " + ligne + ": Status Lead invalide -> " + status);
      } else if (this.type.equals("ticket") && !status.matches("^(open|assigned|on-hold|in-progress|resolved|closed|reopened|pending-customer-response|escalated|archived)$")) {
         erreurs.add("Erreur ligne " + ligne + ": Status Ticket invalide -> " + status);
      } else {
         this.status = status;
      }
   }

   public void setExpense(String expense, List<String> erreurs, int ligne) {
      try {
          // delete double quotes

          String sanitizedExpense = expense.replace(",", ".");
          System.out.println("STRING :"+expense);
          System.out.println("DOUBLE :"+sanitizedExpense);
         this.expense = Double.parseDouble(sanitizedExpense);
         if (this.expense <= 0) {
            erreurs.add("Erreur ligne " + ligne + ": Expense doit être strictement positif -> " + expense);
         }
      } catch (NumberFormatException ex) {
         erreurs.add("Erreur ligne " + ligne + ": Expense invalide -> " + expense);
      }
   }

    public static List<Data3> lireEtVerifierCsv(List<String> erreurs, File fichier) {
        List<Data3> donnees = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?:\"([^\"]*)\")|([^,]+)");

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int numeroLigne = 0;

            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                if (numeroLigne == 1) { // Ignorer l'en-tête
                    continue;
                }

                List<String> valeurs = new ArrayList<>();
                Matcher matcher = pattern.matcher(ligne);
                while (matcher.find()) {
                    valeurs.add(matcher.group(1) != null ? matcher.group(1) : matcher.group(2));
                }

                if (valeurs.size() < 5) {
                    erreurs.add("Erreur ligne " + numeroLigne + ": Format invalide, colonnes manquantes");
                    continue;
                }

                Data3 data = new Data3();

                data.setCustomerEmail(valeurs.get(0));
                data.setSubjectOrName(valeurs.get(1));
                data.setType(valeurs.get(2), erreurs, numeroLigne);
                data.setStatus("open", erreurs, numeroLigne);
                data.setExpense(valeurs.get(4), erreurs, numeroLigne);
                donnees.add(data);
            }
        } catch (IOException ioex) {
            erreurs.add("Erreur lors de la lecture du fichier : " + ioex.getMessage());
        }

        return donnees;
    }
}
