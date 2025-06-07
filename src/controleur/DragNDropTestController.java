package controleur;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.control.Label;

public class DragNDropTestController {

    @FXML
    private Label dropTarget;

    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != dropTarget && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void handleDrop(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            dropTarget.setText("Déposé : " + db.getString());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
}