package Controller;

import FXApp.MainFXApp;
import Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

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

    @FXML
    private TextField textField_UserName;

    @FXML
    private TextField textField_Pass;

    @FXML
	private ComboBox<String> comboBox_Type;



    @FXML
    private void initialize() {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("User");
        arrayList.add("Worker");
        arrayList.add("Manager");
        arrayList.add("Admin");
        ObservableList<String> list = FXCollections.observableArrayList(arrayList);
        comboBox_Type.setItems(list);
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

	@FXML
	private void handleRegisterPressed() {
	    String userName = textField_UserName.getText();
        String pass = textField_Pass.getText();
        String type = comboBox_Type.getValue();
        AuthorizedUser user;

        if (type == "User") {
            user = new User(userName, pass);
        } else if (type == "Worker") {
            user = new Worker(userName, pass);
        } else if (type == "Manager") {
            user = new Manager(userName, pass);
        } else if (type == "Admin") {
            user = new Admin(userName, pass);
        }

        Model.addAuthorizedUser(user);

    }
}
