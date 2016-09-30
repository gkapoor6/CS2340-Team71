package Controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import FXApp.MainFXApp;
import Model.Profile;

public class ProfileScreenController {
	private MainFXApp mainApp;

	@FXML
	private TextField nameField;

	@FXML
	private ComboBox<String> titleComboBox;

	@FXML
	private TextField emailField;

	@FXML
	private TextField addressField;

	/** the window for this dialog */
	private Stage dialogStage;

	/** the student whose data is being manipulated */
	private Profile profile;

	/** flag to signal whether dialog was closed normally */
	private boolean updateClicked = false;
	
	@FXML
    private void initialize() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Mr.");
        arrayList.add("Mrs.");
        ObservableList<String> list = FXCollections.observableArrayList(arrayList);
        titleComboBox.setItems(list);
    }


	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	
	

	/**
	 * Returns true if the update button is clicked.
	 *
	 * @return true if the update button clicked.
	 */
	public boolean isUpdateClicked() {
		return updateClicked;
	}

	@FXML
	private void handleUpdatePressed() {
		// validate the profile info
		if (isInputValid()) {
			profile.setEmail(emailField.getText());
			profile.setName(nameField.getText());
			profile.setHome(addressField.getText());

			// Signal success and close the profile dialog window.
			updateClicked = true;
			mainApp.showApplication();
		}
	}

	@FXML
	private void handleCancelPressed() {
		mainApp.showApplication();
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
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}

	}
}
