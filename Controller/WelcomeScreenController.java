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
	public void loginPressed() {
		mainApp.showLogin();
	}
	
	/**
	 * Event handler for register button
	 */
	public void registerPressed() {
		mainApp.showRegistration();
	}
	
	/**
	 * Close menu item event handler
	 */
	@FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
	
	/**
	 * About menu item event handler
	 */
	@FXML
	private void handleAboutMenu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("M4 Project");
        alert.setHeaderText("About");
        alert.setContentText("Simple Implementation of User login and register");

        alert.showAndWait();

    }
}
