package org.example.banque.controllerclient;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import org.example.banque.classes.Client;

public class ControllerAddClient {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;

    // Méthode pour ajouter un client
    @FXML
    public void addClient() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();

        // Vérification des champs
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        // Création d'un nouveau client et ajout à la base de données
        Client client = new Client(nom, prenom, email);
        client.insertClientIntoDatabase();

        // Message de confirmation
        showAlert(AlertType.INFORMATION, "Succès", "Client ajouté avec succès !");

        // Réinitialiser les champs
        nomField.clear();
        prenomField.clear();
        emailField.clear();
    }

    // Méthode pour afficher les alertes
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
