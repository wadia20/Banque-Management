package org.example.banque.classes;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Compte {
    private int compteId;
    private double solde;
    private String dateCreation;
    private String dateUpdate;
    private int clientId;  // Foreign key reference to Client
    private int banqueId;  // Foreign key reference to Banque

    // Constructor
    public Compte(double solde, int clientId, int banqueId) {
        this.solde = solde;
        this.dateCreation = getCurrentDate(); // Set dateCreation to the current date
        this.dateUpdate = this.dateCreation; // Set dateUpdate to the current date
        this.clientId = clientId;
        this.banqueId = banqueId;

        // Insert the compte into the database
        insertCompteIntoDatabase();
    }

    // Getters and Setters
    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getBanqueId() {
        return banqueId;
    }

    public void setBanqueId(int banqueId) {
        this.banqueId = banqueId;
    }

    // Method to get the current date as a formatted string
    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now(); // Get current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format
        return currentDate.format(formatter); // Return formatted date as String
    }

    // Method to insert compte into the database
    private void insertCompteIntoDatabase() {
        String query = "INSERT INTO Compte (solde, datecreation, dateupdate, client_id, banque_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, this.solde);
            preparedStatement.setString(2, this.dateCreation);
            preparedStatement.setString(3, this.dateUpdate);
            preparedStatement.setInt(4, this.clientId);
            preparedStatement.setInt(5, this.banqueId);

            preparedStatement.executeUpdate();
            System.out.println("Compte registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error registering compte: " + e.getMessage());
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Compte fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Compte.class);
    }
    public static void main(String[] args) {
        // Creating a new Compte instance
        Compte compte1 = new Compte(1000.00, 12, 1); // Removed compteId, added direct values

        // Output to confirm creation
        System.out.println("Compte created successfully.");
    }
}


