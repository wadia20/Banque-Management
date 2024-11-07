package org.example.banque.controllerclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.banque.classes.Client;

import java.sql.SQLException;

public class ControllerSearchClient {

    @FXML private TextField searchIdField;
    @FXML private Label searchResultLabel;

    // Méthode pour effectuer la recherche
    @FXML
    public void searchClientById() {
        int clientId;
        try {
            clientId = Integer.parseInt(searchIdField.getText());
            Client client = Client.getClientById(clientId);
            if (client != null) {
                searchResultLabel.setText("Client trouvé: " + client.getNom() + " " + client.getPrenom() + " - " + client.getEmail());
            } else {
                searchResultLabel.setText("Aucun client trouvé avec l'ID " + clientId);
            }
        } catch (NumberFormatException e) {
            searchResultLabel.setText("Format d'ID invalide.");
        } catch (SQLException e) {
            searchResultLabel.setText("Erreur base de données: " + e.getMessage());
        }
    }
}

