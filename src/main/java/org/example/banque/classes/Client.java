package org.example.banque.classes;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class Client {
    private int clientId;
    private String nom;
    private String prenom;
    private String email;

    // Getters and Setters
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Constructor
    public Client(int clientId, String nom, String prenom, String email) {
        this.clientId = clientId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Client(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // Insert client into the database
    public void insertClientIntoDatabase() {
        String query = "INSERT INTO Client (nom, prenom, email) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, this.nom);
            preparedStatement.setString(2, this.prenom);
            preparedStatement.setString(3, this.email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.clientId = generatedKeys.getInt(1); // Set the generated client ID
                    }
                }
            }
            System.out.println("Client registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error registering client: " + e.getMessage());
        }
    }

    public static Client getClientById(int clientId) throws SQLException {
        String query = "SELECT client_id, nom, prenom, email FROM Client WHERE client_id = ?";
        Client client = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, clientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("client_id");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String email = resultSet.getString("email");

                    client = new Client(id, nom, prenom, email);
                }
            }
        }

        return client;
    }

    public static List<Client> getClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT client_id, nom, prenom, email FROM Client";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("client_id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");

                clients.add(new Client(id, nom, prenom, email));
            }
        }

        return clients;
    }
        public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }

        // Convert JSON to Client
        public static Client fromJson(String json) {
            Gson gson = new Gson();
            return gson.fromJson(json, Client.class);
        }
}
