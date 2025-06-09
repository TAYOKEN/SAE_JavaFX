package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController {

    @FXML
    private Label menuLabel;

    @FXML
    private Button newbutton;

    @FXML
    public void initialize() {
        // Vous pouvez supprimer cette ligne si menuLabel n'existe pas dans le FXML
        // menuLabel.setText("Bienvenue dans le Menu Principal");
        
        // Test pour vérifier que le bouton est bien injecté
        if (newbutton != null) {
            System.out.println("Bouton correctement injecté !");
        } else {
            System.out.println("ERREUR: Bouton non injecté !");
        }
    }

    @FXML
    private void handleNewButtonClick() {
        System.out.println("Bouton cliqué !"); // Pour tester
        
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/dragndroptest.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle
            Stage stage = (Stage) newbutton.getScene().getWindow();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Changer la scène
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de dragndroptest.fxml: " + e.getMessage());
        }
    }
}