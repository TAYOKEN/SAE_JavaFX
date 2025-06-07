package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ParcourirPageController {

    @FXML
    private ListView<String> itemList;

    @FXML
    public void initialize() {
        itemList.getItems().addAll("Article 1", "Article 2", "Article 3");
    }
}