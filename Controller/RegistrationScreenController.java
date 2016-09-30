package Controller;

import FXApp.MainFXApp;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

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
	 * references to widgets in the fxml file
	 */
    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;
    
    @FXML
    private TextField confirmPasswordField;

    @FXML
	private ComboBox<String> accountType;


    /**
     * called automatically after load
     */
    @FXML
    private void initialize() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("User");
        arrayList.add("Worker");
        arrayList.add("Manager");
        arrayList.add("Admin");
        ObservableList<String> list = FXCollections.observableArrayList(arrayList);
        accountType.setItems(list);
    }


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

	/**
	 * Event handler for registration button
	 */
	@FXML
	private void handleRegisterPressed() {
	    String userName = userNameField.getText();
        String pass = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String type = accountType.getValue();
        
        AuthorizedUser user;
        if (userName.equals("")|| pass.equals("") || confirm.equals("") || type == null) {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("Not enough information");
        	alert.setContentText("Please complete all required information");
        	alert.showAndWait();
        } else {
        	if (pass.equals(confirm)) {
		        if (type.equals("User")) {
		            user = new User(userName, pass);
		        } else if (type.equals("Worker")) {
		            user = new Worker(userName, pass);
		        } else if (type.equals("Manager")) {
		            user = new Manager(userName, pass);
		        } else {
		            user = new Admin(userName, pass);
		        }
		        Model.addAuthorizedUser(user);
		        mainApp.showMainScreen();
	        } else {
	        	Alert alert = new Alert(Alert.AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setContentText("Input passwords do not match");
	        	alert.showAndWait();
	        }
        }
    }
}
