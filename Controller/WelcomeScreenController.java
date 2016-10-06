package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * Controller for main/welcome screen
 *
 */
public class WelcomeScreenController {
	
	/**
	 * reference to main application
	 */
	private MainFXApp mainApp;

	/**
	 * Setup the main application link so we can call methods there
	 * @param main reference to main class
	 */
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	/**
	 * Event handler for login button
	 */
	@FXML
	public void loginPressed() {
		mainApp.showLogin();
	}
	
	/**
	 * Event handler for register button
	 */
	@FXML
	public void registerPressed() {
		mainApp.showRegistration();
	}
}
