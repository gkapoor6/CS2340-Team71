package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class WelcomeScreenController {
	
	private MainFXApp mainApp;

	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	public void loginPressed() {
		mainApp.showLoginDialog();
	}
	
	public void registerPressed() {
		mainApp.showRegistrationDialog();
	}
	
	@FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
	
	@FXML
	private void handleAboutMenu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("M4 Project");
        alert.setHeaderText("About");
        alert.setContentText("Simple Implementation of User login and register");

        alert.showAndWait();

    }
}
