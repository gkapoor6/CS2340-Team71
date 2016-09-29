package Controller;

import FXApp.MainFXApp;
import Model.*;
import javafx.fxml.FXML;

import java.util.HashMap;
import java.util.HashSet;
import java.awt.TextField;

/**
 * Registration screen controller
 *
 */
public class RegistrationScreenController {

	/**
	 * reference to main application
	 */
	private MainFXApp mainApp;
    private TextField textField_UserName;
    private TextField textField_Pass;
	
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

	@FXML
	private void handleRegisterPressed() {
	    String userName = textField_UserName.getText();
        String pass = textField_Pass.getText();
	    // check which option selected from drop down list
        // accordingly instantiate class - user, Manager, Admin
        

    }
}
