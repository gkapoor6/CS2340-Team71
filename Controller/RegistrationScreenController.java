package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;

/**
 * Registration screen controller
 *
 */
public class RegistrationScreenController {

	/**
	 * reference to main application
	 */
	private MainFXApp mainApp;
	
	
	/**
	 * Setup the main application link so we can call methods there
	 * @param main reference to main application
	 */
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	/**
	 * Event handler for cancel button
	 */
	@FXML
	private void handleCancelPressed() {
		mainApp.showMainScreen();
	}
}
