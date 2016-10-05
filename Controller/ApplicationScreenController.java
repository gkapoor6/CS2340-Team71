package Controller;

import FXApp.MainFXApp;
import Model.AuthorizedUser;
import javafx.fxml.FXML;

/**
 * Controller for application window when logged in
 *
 */
public class ApplicationScreenController {
	
	/**
	 * reference to mainApp
	 */
	private MainFXApp mainApp;
	
	private AuthorizedUser user;
	/**
	 * Setup the main application link so we can call methods there
	 * @param main	reference to the FXApp instance
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
	}
	
	/**
	 * Logout button event handler
	 */
	@FXML
	public void handleLogoutPressed() {
		mainApp.showMainScreen();
	}
	
	/**
	 * Edit Profile button event handler
	 */
	@FXML
	public void handleEditProfilePressed() {
		mainApp.showProfile(user);
	}
	
	@FXML
	public void handleReportsPressed() {
		mainApp.showMap();
	}
}
