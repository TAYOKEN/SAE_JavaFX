package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuController {

    @FXML
    private Label menuLabel;

    @FXML
    public void initialize() {
        menuLabel.setText("Bienvenue dans le Menu Principal");
    }
}