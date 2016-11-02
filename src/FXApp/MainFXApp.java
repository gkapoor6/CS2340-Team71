package FXApp;

import java.io.IOException;
import java.util.logging.Level;

import Controller.*;
import Model.AuthorizedUser;
import Model.ReportDBAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		ReportDBAccess.createSourceReportTable();
		ReportDBAccess.createUserTable();
		ReportDBAccess.createPurityTable();
		initLayout(mainScreen);
		showWelcomeScreen();
		
	}
	
	/**
	 * Getter of reference to main window stage
	 * @return reference to main stage
	 */
	public Stage getMaindScreen() { return mainScreen; }
	
	/**
	 * Initial layout
	 * @param mainScreen mainstage
	 */
	private void initLayout(Stage mainScreen) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/InitialStage.fxml"));
			layout = loader.load();
			
			InitialStageController controller = loader.getController();
			controller.setMainApp(this);
			
			Scene scene = new Scene(layout);
			mainScreen.setScene(scene);
			mainScreen.show();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the initial stage fxml file");
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize welcome screen
	 */
	private void showWelcomeScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/WelcomeScreen.fxml"));
			VBox welcomeScreen = loader.load();
			
			layout.setCenter(welcomeScreen);
			
			WelcomeScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for WelcomeScreen");
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
			loader.setLocation(MainFXApp.class.getResource("../View/LoginScreen.fxml"));
			
			Pane loginScreen = loader.load();
			
			layout.setCenter(loginScreen);
			
			LoginScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for LoginScreen");
			e.printStackTrace();
		}
	}
	
	/**
	 * Switches to registration view
	 */
	public void showRegistration() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/RegistrationScreen.fxml"));
			
			AnchorPane registrationScreen = loader.load();
			
			layout.setCenter(registrationScreen);
			
			RegistrationScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for RegistrationScreen");
			e.printStackTrace();
		}
	}
	
	/**
	 * Proceeds to the application view once logged in successfully
	 */
	public void showApplication(AuthorizedUser user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/ApplicationScreen.fxml"));
			
			BorderPane applicationScreen = loader.load();

			layout.setCenter(applicationScreen);
			layout.setBottom(null);
			
			ApplicationScreenController controller = loader.getController();
			controller.setUser(user);
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for RegistrationScreen");
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
			loader.setLocation(MainFXApp.class.getResource("../View/ProfileScreen.fxml"));
			
			AnchorPane profileScreen = loader.load();
			
			layout.setCenter(profileScreen);
			
			ProfileScreenController controller = loader.getController();
			controller.setUser(user);
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for ProfileScreen");
			e.printStackTrace();
		}
	}
	
	/**
	 * Goes from application screen to view of all submitted reports by the user
	 * @param user user whose reports are submitted in the next screen
	 */
	public void showReports(AuthorizedUser user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/ReportView.fxml"));
			
			SplitPane reportView = loader.load();
			
			layout.setCenter(reportView);
			
			ReportViewController controller = loader.getController();			
			controller.setUser(user);
			controller.setMain(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for viewing reports");
			e.printStackTrace();
		}
	}
	
	/**
	 * Goes from application screen to a reporting data to a water source report screen
	 * @param user user who is submitting the report
	 */
	public void showReportData(AuthorizedUser user) {
		try {
            // Load course overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource("../View/ReportData.fxml"));
            SplitPane reportScreen = loader.load();

            layout.setCenter(reportScreen);

            ReportDataController controller = loader.getController();
            controller.setUser(user);
            controller.setMainApp(this);

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for reporting data");
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
                    getResource("../View/ReportWaterPurity.fxml"));
            VBox reportScreen = loader.load();

            layout.setCenter(reportScreen);

            ReportWaterPurityController controller = loader.getController();
            controller.setUser(user);
            controller.setMainApp(this);

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml" +
                    " file for reporting data");
            e.printStackTrace();
        }

    }
	
	/**
	 * Go to screen with all reports submitted by a user marker on a map 
	 * @param user the user
	 */
	public void showAllReports(AuthorizedUser user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.
                    getResource("../View/AllReportView.fxml"));
			GoogleMapView mapScreen = loader.load();
			
			layout.setCenter(mapScreen);
			
			AllReportViewController controller = loader.getController();
			controller.setUser(user);
			controller.setMain(this);
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for viewing all reports on a map");
			e.printStackTrace();
		}
	}

	/**
	 * Go to screen with purity reports submitted
	 * @param user the user
	 */
	public void showPurityReports(AuthorizedUser user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.
                    getResource("../View/ReportPurityView.fxml"));
			VBox mapScreen = loader.load();

			layout.setCenter(mapScreen);

			ReportPurityViewController controller = loader.getController();
			controller.setUser(user);
			controller.setMain(this);

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file" +
                    " for viewing all reports on a map");
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
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for waterQualityHistoryGraphScreen");
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
