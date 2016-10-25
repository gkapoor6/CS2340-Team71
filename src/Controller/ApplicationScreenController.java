package Controller;

import FXApp.MainFXApp;
import Model.AuthorizedUser;
import javafx.fxml.FXML;
import Model.Worker;
import Model.Manager;
import javafx.scene.control.Label;

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
     * label for displaying messages
     */
    @FXML
    private Label myLabel;
	/**
	 * Setup the main application link so we can call methods there
	 * @param main	reference to the FXApp instance
	 */
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}
	
	/**
	 * Setup a certain user's interface of application
	 * @param user user
	 */
	public void setUser(AuthorizedUser user) {
		this.user = user;
	}
	
	/**
	 * Logout button event handler
	 */
	@FXML
	public void handleLogoutPressed() {
        myLabel.setVisible(false);
		mainApp.showMainScreen();
	}
	
	/**
	 * Edit Profile button event handler
	 */
	@FXML
	public void handleEditProfilePressed() {
        myLabel.setVisible(false);
		mainApp.showProfile(user);
	}
	
	/**
	 * Show reports submitted by this {@code user}
	 */
	@FXML
	public void handleActualReportsPressed() {
        myLabel.setVisible(false);
		mainApp.showReports(user);
	}
	
	/**
	 * Submit a water source report
	 */
	@FXML
	public void handleReportDataPressed() {
        myLabel.setVisible(false);
		mainApp.showReportData(user);
	}
	
	/**
	 * Show all reports purely on a map without a table
	 */
	@FXML
	public void handleViewAllReports() {
        myLabel.setVisible(false);
		mainApp.showAllReports(user);
	}

	/**
	 * Show all reports purely on a map without a table
	 */
	@FXML
	public void handlePurityReports() {
	    myLabel.setVisible(false);
        if (user instanceof Manager) {
            mainApp.showPurityReports(user);
        } else {
            myLabel.setText("ACCESS PRIVILEGES NOT GRANTED");
            myLabel.setVisible(true);
        }
	}

    /**
     * Submit a water purity report
     */
    @FXML
    public void handleReportPurity() {
        myLabel.setVisible(false);
    	if (user instanceof Worker) {
            mainApp.showReportPurity(user);
        } else {
            myLabel.setText("ACCESS PRIVILEGES NOT GRANTED");
            myLabel.setVisible(true);
        }
    }
}
