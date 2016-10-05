package Controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class InitialStageController {

	private MainFXApp mainApp;
	
	public void setMainApp(MainFXApp mainApp) {
		this.mainApp = mainApp;
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
