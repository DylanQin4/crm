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
public class Data2 {
    private String customerEmail;
    private double budget;

    public void setBudget(String budget, List<String> erreurs, int ligne) {
        try {

            String sanitizedExpense = budget.replace(",", ".");
            System.out.println("STRING :"+budget);
            System.out.println("DOUBLE :"+sanitizedExpense);
            this.budget = Double.parseDouble(sanitizedExpense);
            if (this.budget < 0) {
                erreurs.add("Erreur ligne " + ligne + ": Budget ne doit pas être négatif -> " + budget);
            }
        } catch (NumberFormatException ex) {
            erreurs.add("Erreur ligne " + ligne + ": Budget invalide -> " + budget);
        }
    }
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

    public static List<Data2> lireEtVerifierCsv(List<String> erreurs, File fichier) {
        List<Data2> donnees = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?:\"([^\"]*)\")|([^,]+)"); // Gérer les valeurs entre guillemets

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

                if (valeurs.size() < 2) {
                    erreurs.add("Erreur ligne " + numeroLigne + ": Format invalide, colonnes manquantes");
                    continue;
                }

                Data2 data = new Data2();
                data.setCustomerEmail(valeurs.get(0));
                data.setBudget(valeurs.get(1), erreurs, numeroLigne);
                donnees.add(data);
            }
        } catch (IOException ioex) {
            erreurs.add("Erreur lors de la lecture du fichier : " + ioex.getMessage());
        }

        return donnees;
    }

}
