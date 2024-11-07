package org.example.banque.classes;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Banque {
    private int banque_id;
    private String nom;
    private String pays;

    // Constructor to initialize and save Banque to the database
    public Banque(int banque_id, String nom, String pays) {
        this.banque_id = banque_id;
        this.nom = nom;
        this.pays = pays;

        // Call the method to save the Banque in the database
        saveToDatabase();
    }

    // Method to save the bank (Banque) to the database
    private void saveToDatabase() {
        String insertSQL = "INSERT INTO banque (banque_id, nom, pays) VALUES (?, ?, ?)";

        // Get a connection to the database
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Set the values for the prepared statement
            preparedStatement.setInt(1, this.banque_id);
            preparedStatement.setString(2, this.nom);
            preparedStatement.setString(3, this.pays);

            // Execute the insertion query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Banque successfully saved in the database.");
            } else {
                System.out.println("Failed to save the Banque in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public int getBanque_id() {
        return banque_id;
    }

    public void setBanque_id(int banque_id) {
        this.banque_id = banque_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
    public static void main(String[] args) {

        // Creating a new Compte instance with valid client and banque IDs
        Banque Banque1 = new Banque(1, "cih", "maroc"); // Ensure both 12 and 1 exist

        // Output to confirm creation
        System.out.println("Banque created successfully.");
    }

}

