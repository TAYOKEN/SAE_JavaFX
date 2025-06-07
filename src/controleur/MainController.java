package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        loadCenter("/vue/Menu.fxml");
    }

    public void handleAccueil() {
        loadCenter("/vue/Menu.fxml");
    }

    public void handleParcourir() {
        loadCenter("/vue/ParcourirPage.fxml");
    }

    public void handleLogout() {
        loadCenter("/vue/LoginPage.fxml");
    }

    private void loadCenter(String fxmlPath) {
        try {
            Node center = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainBorderPane.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}