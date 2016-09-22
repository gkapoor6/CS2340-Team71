package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class WelcomeScreenController {
	
	private MainFXApp mainApp;

	@FXML
	private void initialize() {
		
	}
	public void setMainApp(MainFXApp main) {
		System.out.println("ASDF");
		mainApp = main;
	}
	
	public void loginPressed() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initOwner(mainApp.getMaindScreen());
		
	}
	

}
