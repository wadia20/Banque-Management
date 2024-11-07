package org.example.banque.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private double montant; // Total amount to be transferred
    private String typeTransaction; // Type of transaction (e.g., VIRINI, VIRMULTA)
    private java.util.Date dateTransaction;
    private int compteIdEmetteur; // Foreign key reference to the sender's account
    private int compteIdRecepteur; // Foreign key reference to the receiver's account

    // Constructor
    public Transaction(int transactionId, double montant, int compteIdEmetteur, int compteIdRecepteur) {
        this.transactionId = transactionId;
        this.montant = montant;
        this.dateTransaction = getCurrentDate(); // Set the current date
        this.compteIdEmetteur = compteIdEmetteur;
        this.compteIdRecepteur = compteIdRecepteur;

        // Determine the type of transaction
        this.typeTransaction = determineTransactionType();
        processTransaction();
        // Insert the transaction into the database
        insertTransactionIntoDatabase();
    }
    private void processTransaction() {
        if (updateBalance(compteIdEmetteur, -montant)) {
            if (!updateBalance(compteIdRecepteur, montant)) {
                // If crediting the receiver fails, revert the sender's balance
                updateBalance(compteIdEmetteur, montant);
                throw new IllegalStateException("Failed to credit the receiver's account. Transaction reverted.");
            }
        } else {
            throw new IllegalStateException("Failed to debit the sender's account.");
        }
    }

    // Method to update the balance of a given compteId
    private boolean updateBalance(int compteId, double amount) {
        String query = "UPDATE Compte SET solde = solde + ? WHERE compte_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, compteId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Getters and Setters (not shown for brevity)

    // Method to get the current date as a formatted string
    // Method to get the current date
    private java.util.Date getCurrentDate() {
        return new java.util.Date(); // Returns the current date and time
    }


    // Method to determine the type of transaction based on sender and receiver accounts
    private String determineTransactionType() {
        String bankEmetteur = getBankByCompteId(this.compteIdEmetteur);
        String bankRecepteur = getBankByCompteId(this.compteIdRecepteur);
        String countryEmetteur = getCountryByCompteId(this.compteIdEmetteur);
        String countryRecepteur = getCountryByCompteId(this.compteIdRecepteur);

        // Ajoutez des messages pour vérifier les valeurs récupérées
        System.out.println("Banque émetteur: " + bankEmetteur);
        System.out.println("Banque récepteur: " + bankRecepteur);
        System.out.println("Pays émetteur: " + countryEmetteur);
        System.out.println("Pays récepteur: " + countryRecepteur);

        // Check for VIRINI
        if (bankEmetteur.equals(bankRecepteur)) {
            return "VIRINI";
        }
        // Check for VIRMULTA
        else if (countryEmetteur.equals(countryRecepteur) && !bankEmetteur.equals(bankRecepteur)) {
            return "VIRMULTA";
        }
        // Otherwise, it's an invalid transaction type
        else {
            throw new IllegalArgumentException("Invalid transaction: Accounts are not compatible.");
        }
    }


    // Method to retrieve the bank associated with a given compteId
    private String getBankByCompteId(int compteId) {
        String query = "SELECT banque_id FROM Compte WHERE compte_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, compteId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int banqueId = resultSet.getInt("banque_id");
                return getBankNameById(banqueId);
            } else {
                throw new IllegalArgumentException("Compte ID " + compteId + " n'a pas de banque associée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Method to retrieve the country associated with a given compteId
    private String getCountryByCompteId(int compteId) {
        String query = "SELECT banque_id FROM Compte WHERE compte_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, compteId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int banqueId = resultSet.getInt("banque_id");
                return getCountryByBankId(banqueId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve the bank name by its ID
    private String getBankNameById(int banqueId) {
        String query = "SELECT nom FROM Banque WHERE banque_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, banqueId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve the country by bank ID (assuming you have a field for country in Banque table)
    private String getCountryByBankId(int banqueId) {
        String query = "SELECT pays FROM Banque WHERE banque_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, banqueId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("pays");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to insert the transaction into the database
    private void insertTransactionIntoDatabase() {
        String query = "INSERT INTO Transaction (transaction_id, montant, type_transaction, date_transaction, " +
                "compte_id_emetteur, compte_id_recepteur) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, this.transactionId);
            preparedStatement.setDouble(2, this.montant);
            preparedStatement.setString(3, this.typeTransaction);

            // Convert java.util.Date to java.sql.Date for database insertion
            java.sql.Date sqlDate = new java.sql.Date(this.dateTransaction.getTime());
            preparedStatement.setDate(4, sqlDate);

            preparedStatement.setInt(5, this.compteIdEmetteur);
            preparedStatement.setInt(6, this.compteIdRecepteur);

            preparedStatement.executeUpdate();
            System.out.println("Transaction registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error registering transaction: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        // Example of creating a transaction
        Transaction transaction = new Transaction(20, 500.00, 2, 3); // Assuming compteId 1 and 2 exist
        System.out.println("Transaction created successfully: " );
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public int getCompteIdEmetteur() {
        return compteIdEmetteur;
    }

    public void setCompteIdEmetteur(int compteIdEmetteur) {
        this.compteIdEmetteur = compteIdEmetteur;
    }

    public int getCompteIdRecepteur() {
        return compteIdRecepteur;
    }

    public void setCompteIdRecepteur(int compteIdRecepteur) {
        this.compteIdRecepteur = compteIdRecepteur;
    }
}


