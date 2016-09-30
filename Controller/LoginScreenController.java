package Controller;

import FXApp.MainFXApp;
import Model.AuthorizedUser;
import Model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for login screen
 *
 */
public class LoginScreenController {
	
	/**
	 * reference to mainApp
	 */
	private MainFXApp mainApp;
	
	/**
	 * references to windgets in the fxml file
	 */
	@FXML
	private TextField userField;
	
	@FXML
	private PasswordField passwordField;
	
	/**
	 * Setup the main application link so we can call methods there
	 * @param main reference back to main class
	 */
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	/**
	 * Cancel button event handler
	 */
	@FXML
	private void handleCancelPressed() {
		mainApp.showMainScreen();
	}
	
	/**
	 * Login attempt event handler
	 */
	@FXML
	private void handleLoginPressed() {
		AuthorizedUser user = Model.getUser(userField.getText());
		if (user != null) {
			if (user.getPassword().equals(passwordField.getText())) {
				mainApp.showApplication();
			} else {
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Login Attempt");
	            alert.setHeaderText("Attempt Failed");
	            alert.setContentText("Combination of username and password not found");

	            alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Attempt");
            alert.setHeaderText("No Such user exists");
            alert.setContentText("Combination of username and password not found");

            alert.showAndWait();
		}
	}
}
