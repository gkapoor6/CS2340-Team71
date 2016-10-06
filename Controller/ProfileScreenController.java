package Controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import FXApp.MainFXApp;
import Model.AuthorizedUser;
import Model.Profile;

/**
 * Controller for profile edit window
 */
public class ProfileScreenController {
	/**
	 * reference to mainApp
	 */
	private MainFXApp mainApp;


	/**
	 * references to widgets in the fxml file
	 */
	@FXML
	private TextField nameField;

	@FXML
	private ComboBox<String> titleComboBox;

	@FXML
	private TextField emailField;

	@FXML
	private TextField addressField;

	/**
	 * instance data needed for updating the profile
	 */
	private AuthorizedUser user;

	private ObservableList<String> list;
	
	private Profile profile;

	/** flag to signal whether dialog was closed normally */
	private boolean updateClicked = false;
	
	/**
	 * called automatically after load
	 */
	@FXML
    private void initialize() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Mr.");
        arrayList.add("Mrs.");
        arrayList.add("Ms.");
        list = FXCollections.observableArrayList(arrayList);
        titleComboBox.setItems(list);
    }

	/**
	 * Setup the main application link so we can call methods there
	 * @param main reference to the FXApp instance
	 */
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	
	/**
	 * Setup a certain user's interface of application
	 * @param user
	 */
	public void setUser(AuthorizedUser user) {
		
		this.user = user;
		profile = user.getProfile();
        titleComboBox.setValue(profile.getTitle().get());
		nameField.setText(profile.getName().get());
        emailField.setText(profile.getEmail().get());
        addressField.setText(profile.getHome().get());
	}

	/**
	 * Returns true if the update button is clicked.
	 *
	 * @return true if the update button clicked.
	 */
	public boolean isUpdateClicked() {
		return updateClicked;
	}

	/**
	 * Update button event handler
	 */
	@FXML
	private void handleUpdatePressed() {
		// validate the profile info
		if (isInputValid()) {
			profile.setEmail(emailField.getText());
			profile.setName(nameField.getText());
			profile.setHome(addressField.getText());
			profile.setTitle(titleComboBox.getValue());
			// Signal success and close the profile dialog window.
			updateClicked = true;
			mainApp.showApplication(user);
		}
	}

	/**
	 * Cancel button event handler
	 */
	@FXML
	private void handleCancelPressed() {
		mainApp.showApplication(user);
	}

	/**
	 * Validate the text fields
	 * 
	 * @return true if the input is valid
	 */

	private boolean isInputValid() {
		String errorMessage = "";
		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "Please enter a valid name.\n";
		}
		if (emailField.getText() == null || emailField.getText().length() == 0) {
			errorMessage += "Please enter a valid name.\n";
		}
		if (addressField.getText() == null
				|| addressField.getText().length() == 0) {
			errorMessage += "Please enter a valid address.\n";
		}

		// no error message means success / good input
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message if bad data
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}

	}
}
