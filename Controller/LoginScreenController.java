package Controller;

import FXApp.MainFXApp;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScreenController {
	private MainFXApp mainApp;
	
	@FXML
	private TextField userField;
	
	@FXML
	private PasswordField passwordField;
	
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	@FXML
	private void handleCancelPressed() {
		mainApp.showMainScreen();
	}
	
	@FXML
	private void handleLoginPressed() {
		User user = mainApp.getUsers().get(userField.getText());
		if (user != null) {
			if (user.getPwd().equals(passwordField.getText())) {
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
            alert.setHeaderText("Wrong");
            alert.setContentText("Combination of username and password not found");

            alert.showAndWait();
		}
	}
}
