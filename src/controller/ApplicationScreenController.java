package controller;
import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.AuthorizedUser;
import model.Manager;
import model.Worker;
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
        mainApp.showSubmittedSourceReports(user);
    }
    /**
     * Submit a water source report
     */
    @FXML
    public void handleReportDataPressed() {
        mainApp.showReportSource(user);
    }
    /**
     * Show all reports purely on a map without a table
     */
    @FXML
    public void handleViewAllReports() {
        mainApp.showAllSourceReports(user);
    }
    /**
     * Show all reports purely on a map without a table
     */
    @FXML
    public void handlePurityReports() {
        //noinspection InstanceofConcreteClass
        if (user instanceof Manager) {
            mainApp.showSubmittedPurityReports(user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Restriction");
            alert.setContentText("You are not a Manager, you are not allowed"
                    + " to access all the Purity reports");
            alert.showAndWait();
        }
    }
    /**
     * Submit a water purity report
     */
    @FXML
    public void handleReportPurity() {
        //noinspection InstanceofConcreteClass
        if (user instanceof Worker) {
            mainApp.showReportPurity(user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Restriction");
            alert.setContentText("Your access level is below Worker type of"
                    + " user, you are not allowed to submit purity reports");
            alert.showAndWait();
        }
    }
    /**
     * View Water Quality History Graph
     */
    @FXML
    public void handleWaterQualityHistoryGraph() {
        //noinspection InstanceofConcreteClass
        if (user instanceof Manager) {
            mainApp.showWaterQualityHistoryGraph(user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Restriction");
            alert.setContentText("Your access level is below Manager type of"
                    + " user, you are not allowed to"
                    + " view water quality history graphs");
            alert.showAndWait();
        }
    }
}