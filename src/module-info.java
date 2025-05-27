module prog_SAE_JavaFX {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	opens vue to javafx.graphics, javafx.fxml;
	opens controleur to javafx.fxml;
	opens model to javafx.fxml;
}
