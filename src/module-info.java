module prog_TP4_Exo1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	opens vue to javafx.graphics, javafx.fxml;
	opens controleur to javafx.fxml;
	//opens model to javafx.fxml;
}
