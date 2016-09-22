package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;

public class WelcomeScreenController {
	
	private MainFXApp mainApp;

	
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	@FXML
	private void handleCloseMenu() {
		System.exit(0);
	}
	
	@FXML
	public void loginPressed() {

	}

}
