package FXApp;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import Controller.ApplicationScreenController;
//import Controller.GoogleMapController;
import Controller.InitialStageController;
import Controller.LoginScreenController;
import Controller.ProfileScreenController;
import Controller.RegistrationScreenController;
import Controller.WaterReportViewController;
import Controller.WelcomeScreenController;
import Model.AuthorizedUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Logger;

//import com.lynden.gmapsfx.GoogleMapView;

/**
 * Main application class
 * Handles all scene switching to reuse main stage 
 *
 */
public class MainFXApp extends Application {
	/**
	 * Holder of Users
	 */
	private HashMap<String, AuthorizedUser> users = new HashMap<>();
	
	/**
	 * java logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger("MainFXApp");
	
	/**
	 * main container for the app window
	 */
	private Stage mainScreen;
	
	/**
	 * layout for the welcome screen
	 */
	private BorderPane layout;
	
	/*
	 * Getter of Users
	 */
	public HashMap<String, AuthorizedUser> getUsers() {
		return users;
	}
	
	@Override
	public void start(Stage primaryStage) {
		mainScreen = primaryStage;
		initLayout(mainScreen);
		showWelcomeScreen();
		
	}
	
	/**
	 * Getter of reference to main window stage
	 * @return reference to main stage
	 */
	public Stage getMaindScreen() { return mainScreen; }
	
	
	
	
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
	 * Initialize first and welcome screen
	 * @param mainScreen main Stage window
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
			
			AnchorPane applicationScreen = loader.load();

			layout.setCenter(applicationScreen);
			layout.setRight(null);
			
			ApplicationScreenController controller = loader.getController();
			controller.setUser(user);
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for RegistrationScreen");
			e.printStackTrace();
		}
	}
	
	public void showProfile(AuthorizedUser user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/ProfileScreen.fxml"));
			
			VBox profileScreen = loader.load();
			
			layout.setCenter(profileScreen);
			
			ProfileScreenController controller = loader.getController();
			controller.setUser(user);
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for ProfileScreen");
			e.printStackTrace();
		}
	}
	
	public void showReports(AuthorizedUser user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/WaterReportView.fxml"));
			
			AnchorPane reportView = loader.load();
			
			layout.setRight(reportView);
			
			WaterReportViewController controller = loader.getController();			
			controller.setUser(user);
			controller.setMain(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for WaterReportView");
			e.printStackTrace();
		}
	}
	
	/*public void showMap() {
		try {
            // Load course overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApp.class.getResource("../View/GoogleMap.fxml"));
            AnchorPane mapScreen = loader.load();

            layout.setCenter(mapScreen);

            Google controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for Google Map");
            e.printStackTrace();
        }
	}*/
	
	public static void main(String[] args) {
		launch(args);
	}
}
