package FXApp;

import java.io.IOException;
import java.util.logging.Level;

import controller.AllSourceReportsViewController;
import controller.ViewWaterQualityHistoryGraph;
import controller.ApplicationScreenController;
import controller.InitialStageController;
import controller.LoginScreenController;
import controller.ProfileScreenController;
import controller.RegistrationScreenController;
import controller.ReportWaterPurityController;
import controller.ReportWaterSourceController;
import controller.ShowPurityReportsController;
import controller.ShowSourceReportsController;
import controller.WelcomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AuthorizedUser;
import model.DBInterfacer;

import java.util.logging.Logger;

import com.lynden.gmapsfx.GoogleMapView;


/**
 * Main application class
 * Handles all scene switching to reuse main stage
 *
 */

public class MainFXApp extends Application {

    /**
     * java logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger("MainFXApp");

    /**
     * main container for the app window
     */
    private Stage mainScreen;

    /**
     * layout for the initial layout
     */
    private BorderPane layout;

    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        DBInterfacer.createSourceReportTable();
        DBInterfacer.createUserTable();
        DBInterfacer.createPurityTable();
        initLayout(mainScreen);
        showWelcomeScreen();

    }

    /**
     * Initial layout
     * @param mainScreen mainstage
     */
    private void initLayout(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/InitialStage.fxml"));
            layout = loader.load();

            InitialStageController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(layout);
            mainScreen.setScene(scene);
            mainScreen.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to find the initial stage fxml file");
            e.printStackTrace();
        }
    }

    /**
     * Initialize welcome screen
     */
    private void showWelcomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/WelcomeScreen.fxml"));
            VBox welcomeScreen = loader.load();

            layout.setCenter(welcomeScreen);

            WelcomeScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to "
                    + "find the fxml file for WelcomeScreen");
            e.printStackTrace();
        }
    }

    /**
     * For use to return to main window
     */
    public void showMainScreen() {
        showWelcomeScreen();
    }

    /**
     * Switches to login view
     */
    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/LoginScreen.fxml"));

            Pane loginScreen = loader.load();

            layout.setCenter(loginScreen);

            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to find the fxml file for LoginScreen");
            e.printStackTrace();
        }
    }

    /**
     * Switches to registration view
     */
    public void showRegistration() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/RegistrationScreen.fxml"));

            AnchorPane registrationScreen = loader.load();

            layout.setCenter(registrationScreen);

            RegistrationScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to find the fxml file for RegistrationScreen");
            e.printStackTrace();
        }
    }


    /**
     * Proceeds to the application view once logged in successfully
     * @param user the user
     */
    public void showApplication(AuthorizedUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/ApplicationScreen.fxml"));

            BorderPane applicationScreen = loader.load();

            layout.setCenter(applicationScreen);
            layout.setBottom(null);

            ApplicationScreenController controller = loader.getController();
            controller.setUser(user);
            controller.setMainApp(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to find the fxml file for RegistrationScreen");
            e.printStackTrace();
        }
    }

    /**
     * Goes from application screen to edit profile screen
     * @param user user whose profile will be edited
     */
    public void showProfile(AuthorizedUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/ProfileScreen.fxml"));

            AnchorPane profileScreen = loader.load();

            layout.setCenter(profileScreen);

            ProfileScreenController controller = loader.getController();
            controller.setUser(user);
            controller.setMainApp(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to find the fxml file for ProfileScreen");
            e.printStackTrace();
        }
    }

    /**
     * Goes from application screen to view of all submitted reports by the user
     * @param user user whose reports are submitted in the next screen
     */
    public void showSubmittedSourceReports(AuthorizedUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/ShowSourceReports.fxml"));

            SplitPane reportView = loader.load();

            layout.setCenter(reportView);

            ShowSourceReportsController controller = loader.getController();
            controller.setUser(user);
            controller.setMain(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to find "
                    + "the fxml file for viewing reports");
            e.printStackTrace();
        }
    }

    /**
     * Goes from application screen to a
     * reporting data to a water source report screen
     * @param user user who is submitting the report
     */
    public void showReportSource(AuthorizedUser user) {
        try {
            // Load course overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource(
                    "../View/ReportWaterSource.fxml"));
            SplitPane reportScreen = loader.load();

            layout.setCenter(reportScreen);

            ReportWaterSourceController controller = loader.getController();
            controller.setUser(user);
            controller.setMainApp(this);

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE,
                    "Failed to find the fxml file for reporting data");
            e.printStackTrace();
        }

    }

    /**
     * Goes from application screen to a reporting data to a water
     * purity report screen
     * @param user user who is submitting the report
     */
    public void showReportPurity(AuthorizedUser user) {
        try {
            // Load course overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.
                    getResource(
                            "../View/ReportWaterPurity.fxml"));
            SplitPane reportScreen = loader.load();

            layout.setCenter(reportScreen);

            ReportWaterPurityController controller = loader.getController();
            controller.setUser(user);
            controller.setMainApp(this);

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml"
                    + " file for reporting data");
            e.printStackTrace();
        }

    }

    /**
     * Go to screen with all reports submitted by a user marker on a map
     * @param user the user
     */
    public void showAllSourceReports(AuthorizedUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.
                    getResource("../View/AllSourceReportsView.fxml"));
            GoogleMapView mapScreen = loader.load();

            layout.setCenter(mapScreen);

            AllSourceReportsViewController controller = loader.getController();
            controller.setUser(user);
            controller.setMain(this);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to find the "
                    + "fxml file for viewing all reports on a map");
            e.printStackTrace();
        }
    }

    /**
     * Go to screen with purity reports submitted
     * @param user the user
     */
    public void showSubmittedPurityReports(AuthorizedUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.
                    getResource("../View/ShowPurityReports.fxml"));
            SplitPane mapScreen = loader.load();

            layout.setCenter(mapScreen);

            ShowPurityReportsController controller = loader.getController();
            controller.setUser(user);
            controller.setMain(this);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file"
                    + " for viewing all reports on a map");
            e.printStackTrace();
        }
    }

    /**
     * Go to show water quality history graph screen
     * @param user the user
     */
    public void showWaterQualityHistoryGraph(AuthorizedUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.
                    getResource("../View/ViewWaterQualityHistoryGraph.fxml"));
            SplitPane waterQualityHistoryGraphScreen = loader.load();

            layout.setCenter(waterQualityHistoryGraphScreen);

            ViewWaterQualityHistoryGraph controller = loader.getController();
            controller.setUser(user);
            controller.setMainApp(this);


        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to "
                    + "find the fxml file for waterQualityHistoryGraphScreen");
            e.printStackTrace();
        }
    }

    /**
     * launch the program
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
