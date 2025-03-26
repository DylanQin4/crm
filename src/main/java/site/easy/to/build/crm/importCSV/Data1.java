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
public class Data1 {
    private String customerEmail;
    private String customerName;

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

    public void setCustomerName(String name, List<String> erreurs, int ligne) {
        // Suppression des espaces et débogage de la valeur du nom
        String trimmedName = name != null ? name.trim() : "";
        System.out.println("Valeur de 'name' pour la ligne " + ligne + " après trim: '" + trimmedName + "'");

        if (trimmedName.isEmpty()) {  // Si le nom est vide après suppression des espaces
            erreurs.add("Erreur ligne " + ligne + ": Nom du client ne doit pas être vide.");
        } else {
            this.customerName = trimmedName;
        }
    }

    public static List<Data1> lireEtVerifierCsv(List<String> erreurs, File fichier) {
        List<Data1> donnees = new ArrayList<>();
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

                if (valeurs.size() < 2) {
                    erreurs.add("Erreur ligne " + numeroLigne + ": Format invalide, colonnes manquantes");
                    continue;
                }

                Data1 data = new Data1();
                data.setCustomerEmail(valeurs.get(0), erreurs, numeroLigne);
                data.setCustomerName(valeurs.get(1), erreurs, numeroLigne);
                donnees.add(data);
            }
        } catch (IOException ioex) {
            erreurs.add("Erreur lors de la lecture du fichier : " + ioex.getMessage());
        }

        return donnees;
    }
}
