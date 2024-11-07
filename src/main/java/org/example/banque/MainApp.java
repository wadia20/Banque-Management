package org.example.banque;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        VBox root = FXMLLoader.load(getClass().getResource("client/client_view.fxml"));

        // Créer la scène avec la racine VBox
        Scene scene = new Scene(root, 600, 400);

        // Définir la scène et afficher la fenêtre principale
        primaryStage.setTitle("Gestion des Transactions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}