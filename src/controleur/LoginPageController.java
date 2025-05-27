package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import model.Utilisateur;

public class LoginPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Utilisateur utilisateur = new Utilisateur("admin","admin@admin.fr", "1234");

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (utilisateur.verifierIdentifiants(username, password)) {
            errorLabel.setText("Connexion réussie !");
            // Redirection à implémenter
        } else {
            errorLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }
}