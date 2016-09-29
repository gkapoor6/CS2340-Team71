package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;

/**
 * Controller for application window when logged in
 *
 */
public class ApplicationScreenController {
	
	/**
	 * reference to mainApp
	 */
	private MainFXApp mainApp;
	
	/**
	 * Setup the main application link so we can call methods there
	 * @param main	reference to the FXApp instance
	 */
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	/**
	 * Logout button event handler
	 */
	@FXML
	public void handleLogoutPressed() {
		mainApp.showMainScreen();
	}
}
