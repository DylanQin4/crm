package site.easy.to.build.crm.importCSV;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Data2 {
    private String customerEmail;
    private double budget;

    public void setBudget(String budget, List<String> erreurs, int ligne) {
        try {
            this.budget = Double.parseDouble(budget);
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

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(gmail\\.com|hotmail\\.com|yahoo\\.com|outlook\\.com|icloud\\.com|orange\\.fr|free\\.fr)$")) {
            erreurs.add("Erreur ligne " + ligne + ": Email invalide (doit se terminer par @gmail.com, @hotmail.com, etc.) -> " + email);
        } else {
            this.customerEmail = email;
        }
    }

    public static List<Data2> lireEtVerifierCsv(List<String> erreurs, File fichier) {
        List<Data2> donnees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int numeroLigne = 0;

            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                if (numeroLigne == 1) { // Ignorer l'en-tête
                    continue;
                }

                String[] valeurs = ligne.split(",");
                if (valeurs.length < 2) {
                    erreurs.add("Erreur ligne " + numeroLigne + ": Format invalide, colonnes manquantes");
                    continue;
                }

                Data2 data = new Data2();
                data.setCustomerEmail(valeurs[0]);
                data.setBudget(valeurs[1], erreurs, numeroLigne);
                donnees.add(data);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        return donnees;
    }
}
