package Controller;

import FXApp.MainFXApp;
import Model.AuthorizedUser;
import javafx.fxml.FXML;

/**
 * Controller for application window when logged in
 * @author Dong Son Trinh
 *
 */
public class ApplicationScreenController {
	
	/**
	 * reference to mainApp
	 */
	private MainFXApp mainApp;
	
	/**
	 * User whose application interface is shown
	 */
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
	
	/**
	 * Show reports submitted by this {@code user}
	 */
	@FXML
	public void handleActualReportsPressed() {
		mainApp.showReports(user);
	}
	
	/**
	 * Submit a water source report
	 */
	@FXML
	public void handleReportDataPressed() {
		mainApp.showReportData(user);
	}
	
	/**
	 * Show all reports purely on a map without a table
	 */
	@FXML
	public void handleViewAllReports() {
		mainApp.showAllReports(user);
	}
}
