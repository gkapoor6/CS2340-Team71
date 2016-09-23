package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;

public class LoginScreenController {
	private MainFXApp mainApp;

	
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	@FXML
	private void handleCancelPressed() {
		mainApp.showMainScreen();
	}
}
