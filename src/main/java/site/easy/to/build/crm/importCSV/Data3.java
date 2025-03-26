package site.easy.to.build.crm.importCSV;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

      if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(gmail\\.com|hotmail\\.com|yahoo\\.com|outlook\\.com|icloud\\.com|orange\\.fr|free\\.fr)$")) {
         erreurs.add("Erreur ligne " + ligne + ": Email invalide (doit se terminer par @gmail.com, @hotmail.com, etc.) -> " + email);
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
         this.expense = Double.parseDouble(expense);
         if (this.expense <= 0) {
            erreurs.add("Erreur ligne " + ligne + ": Expense doit être strictement positif -> " + expense);
         }
      } catch (NumberFormatException ex) {
         erreurs.add("Erreur ligne " + ligne + ": Expense invalide -> " + expense);
      }
   }

   public static List<Data3> lireEtVerifierCsv(List<String> erreurs, File fichier) {
      List<Data3> donnees = new ArrayList<>();

      try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
         String ligne;
         int numeroLigne = 0;

         while ((ligne = br.readLine()) != null) {
            numeroLigne++;
            if (numeroLigne == 1) { // Ignorer l'en-tête
               continue;
            }

            String[] valeurs = ligne.split(",");
            if (valeurs.length < 5) {
               erreurs.add("Erreur ligne " + numeroLigne + ": Format invalide, colonnes manquantes");
               continue;
            }

            Data3 data = new Data3();
            data.setCustomerEmail(valeurs[0]);
            data.setSubjectOrName(valeurs[1]);
            data.setType(valeurs[2], erreurs, numeroLigne);
            data.setStatus(valeurs[3], erreurs, numeroLigne);
            data.setExpense(valeurs[4], erreurs, numeroLigne);

            donnees.add(data);
         }
      } catch (IOException ioex) {
         ioex.printStackTrace();
      }

      return donnees;
   }
}
