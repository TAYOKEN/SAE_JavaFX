package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import model.Utilisateur;

public class RegisterPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Tous les champs sont requis.");
        } else if (!Utilisateur.verifierFormatEmail(email)) {
            statusLabel.setText("Format d'email invalide.");
        } else {
            Utilisateur nouveau = new Utilisateur(username, password, email);
            statusLabel.setText("Inscription réussie !");
        }
    }
}