package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Utilisateur;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private TextField identifiantField;

    @FXML
    private PasswordField motDePasseField;

    @FXML
    private Button connexionButton;

    @FXML
    private Label pasDeCompteLabel;

    @FXML
    private Label errorLabel;
    

    // Utilisateur de test avec le modèle Utilisateur
    private Utilisateur utilisateur = new Utilisateur("admin", "admin@admin.fr", "1234");

    /**
     * Méthode appelée lors du clic sur le bouton "Connexion"
     */
    @FXML
    private void handleConnexion(ActionEvent event) {
        handleLogin();
    }


    @FXML
    private void handleLogin() {
        String username = identifiantField.getText();
        String password = motDePasseField.getText();
        
        if (utilisateur.verifierIdentifiants(username, password)) {
            errorLabel.setText("Connexion réussie !");
            errorLabel.setStyle("-fx-text-fill: green;");
            
            // Redirection vers la page principale après un délai
            loadMainPage();
            
        } else {
            errorLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Méthode appelée lors du clic sur "Pas de compte ?"
     */
    @FXML
    private void handlePasDeCompte(MouseEvent event) {
        try {
            // Charger la page d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/RegisterPage.fxml"));
            Parent root = loader.load();
            
            // Obtenir la fenêtre actuelle
            Stage stage = (Stage) pasDeCompteLabel.getScene().getWindow();
            
            // Changer de scène
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inscription");
            
        } catch (IOException e) {
            errorLabel.setText("Impossible de charger la page d'inscription.");
            errorLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    /**
     * Vérifie si les identifiants sont valides (méthode conservée pour compatibilité)
     */
    private boolean isValidLogin(String username, String password) {
        return utilisateur.verifierIdentifiants(username, password);
    }

    /**
     * Affiche une boîte de dialogue d'alerte
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Méthode pour charger la page principale après connexion
     * (À personnaliser selon votre application)
     */
    private void loadMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/MainApp.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) connexionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Erreur lors du chargement du menu.");
            errorLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }


    /**
     * Méthode d'initialisation (optionnelle)
     * Appelée automatiquement après le chargement du FXML
     */
    @FXML
    private void initialize() {
        // Configuration supplémentaire si nécessaire
        // Par exemple, ajouter des listeners sur les champs de texte
        
        // Permettre la connexion avec la touche Entrée
        motDePasseField.setOnAction(this::handleConnexion);
    }
}