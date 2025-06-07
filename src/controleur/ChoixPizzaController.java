package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;

public class ChoixPizzaController {

    @FXML
    private ChoiceBox<String> pizzaChoiceBox;

    @FXML
    private Label confirmationLabel;

    @FXML
    public void initialize() {
        pizzaChoiceBox.getItems().addAll("Margherita", "Pepperoni", "Reine", "4 Fromages");
    }

    @FXML
    private void handleChoix() {
        String choix = pizzaChoiceBox.getValue();
        confirmationLabel.setText("Vous avez choisi : " + choix);
    }
}