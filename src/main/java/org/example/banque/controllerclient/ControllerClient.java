package org.example.banque.controllerclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.example.banque.classes.Client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControllerClient {

    @FXML private TableView<Client> clientsTable;
    @FXML private TableColumn<Client, Integer> clientIdColumn;
    @FXML private TableColumn<Client, String> clientNomColumn;
    @FXML private TableColumn<Client, String> clientPrenomColumn;
    @FXML private TableColumn<Client, String> clientEmailColumn;

    @FXML private TextField searchField;
    @FXML private Label searchResultLabel;

    @FXML private TextField clientIdField;
    @FXML private TextField clientNomField;
    @FXML private TextField clientPrenomField;
    @FXML private TextField clientEmailField;

    @FXML private TextField idField;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;

    public void initialize() {
        // Initialisation des colonnes de la TableView
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));  // Utilisez "clientId"
        clientPrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom")); // Utilisez "prenom"
        clientNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));       // Utilisez "nom"
        clientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));   // Utilisez "email"
    }

    // Afficher la liste des clients
    public void listClients() {
        try {
            List<Client> clients = Client.getClients();
            clientsTable.getItems().setAll(clients);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rechercher un client par ID
    @FXML
    public void searchClientById() {
        int clientId;
        try {
            clientId = Integer.parseInt(searchField.getText());
            Client client = Client.getClientById(clientId);
            if (client != null) {
                searchResultLabel.setText("Found: " + client.getNom() + " " + client.getPrenom() + " - " + client.getEmail());
            } else {
                searchResultLabel.setText("No client found with ID " + clientId);
            }
        } catch (NumberFormatException e) {
            searchResultLabel.setText("Invalid ID format.");
        } catch (SQLException e) {
            searchResultLabel.setText("Database error: " + e.getMessage());
        }
    }

    // Ajouter un client
    @FXML
    private void showAddClientForm() {
        String idString = idField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();

        if (idString.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erreur", "L'ID doit être un nombre valide !");
            return;
        }

        Client client = new Client(id, nom, prenom, email);
        client.insertClientIntoDatabase();

        showAlert(AlertType.INFORMATION, "Succès", "Client ajouté avec succès !");
        idField.clear();
        nomField.clear();
        prenomField.clear();
        emailField.clear();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void openSearchView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/banque/client/SearchClientView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Rechercher un client par ID");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openAddClientView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/banque/client/AddClientView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ajouter un client");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            // Définir la taille de la fenêtre (optionnel)
            stage.setWidth(600);  // largeur
            stage.setHeight(400); // hauteur
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }}
        public void Openviewtransaction() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/banque/transactionView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Ajouter un client");
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);

                // Définir la taille de la fenêtre (optionnel)
                stage.setWidth(600);  // largeurss
                stage.setHeight(400); // hauteur
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

