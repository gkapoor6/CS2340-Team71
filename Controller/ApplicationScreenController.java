package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;

public class ApplicationScreenController {
	
	private MainFXApp mainApp;
	
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	@FXML
	public void handleLogoutPressed() {
		mainApp.showMainScreen();
	}
}
