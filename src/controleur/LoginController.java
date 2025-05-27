package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import model.Utilisateur;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Utilisateur utilisateur = new Utilisateur("admin", "1234", "admin@example.com");

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!Utilisateur.verifierFormatEmail(email)) {
            errorLabel.setText("Email invalide.");
            return;
        }

        if (utilisateur.getEmail().equals(email) && utilisateur.getMotDePasse().equals(password)) {
            errorLabel.setText("Connexion réussie !");
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
        }
    }
}