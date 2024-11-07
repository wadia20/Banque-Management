package org.example.banque;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.example.banque.classes.Transaction;

public class ControllerTransaction {

    @FXML private TextField transactionIdField;
    @FXML private TextField montantField;
    @FXML private TextField compteEmetteurField;
    @FXML private TextField compteRecepteurField;
    @FXML private Button validerTransactionButton;

    // Méthode qui valide et effectue la transaction
    @FXML
    public void effectuerTransaction() {
        String transactionIdText = transactionIdField.getText();
        String montantText = montantField.getText();
        String compteEmetteurText = compteEmetteurField.getText();
        String compteRecepteurText = compteRecepteurField.getText();

        // Vérifier que tous les champs sont remplis
        if (transactionIdText.isEmpty() || montantText.isEmpty() || compteEmetteurText.isEmpty() || compteRecepteurText.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        int transactionId;
        double montant;
        int compteEmetteur;
        int compteRecepteur;

        // Vérification des types des champs
        try {
            transactionId = Integer.parseInt(transactionIdText);
            montant = Double.parseDouble(montantText);
            compteEmetteur = Integer.parseInt(compteEmetteurText);
            compteRecepteur = Integer.parseInt(compteRecepteurText);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erreur", "Les valeurs saisies doivent être des nombres valides !");
            return;
        }

        // Créer la transaction
        try {
            Transaction transaction = new Transaction(transactionId, montant, compteEmetteur, compteRecepteur);
            showAlert(AlertType.INFORMATION, "Succès", "Transaction effectuée avec succès !");
        } catch (IllegalStateException e) {
            showAlert(AlertType.ERROR, "Erreur", e.getMessage());
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Erreur", e.getMessage());
        }
    }

    // Méthode pour afficher les alertes
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Optionnel: ajouter des comportements d'initialisation si nécessaire
    }
}

