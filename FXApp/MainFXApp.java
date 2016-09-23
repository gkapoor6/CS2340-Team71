package FXApp;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import Controller.ApplicationScreenController;
import Controller.LoginScreenController;
import Controller.RegistrationScreenController;
import Controller.WelcomeScreenController;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainFXApp extends Application {
	
	private HashMap<String, User> users = new HashMap<>();
	
	private static final Logger LOGGER = Logger.getLogger("MainFXApp");
	
	private Stage mainScreen;
	
	private VBox layout;
	
	public HashMap<String, User> getUsers() {
		return users;
	}
	
	@Override
	public void start(Stage primaryStage) {
		mainScreen = primaryStage;
		User u = new User("Irtiza", "relaxyo");
		User u2 = new User("user", "pass");
		users.put(u.getName(), u);
		users.put(u2.getName(), u2);
		initLayout(mainScreen);
		
	}
	
	public Stage getMaindScreen() { return mainScreen; }
	
	private void initLayout(Stage mainScreen) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/WelcomeScreen.fxml"));
			layout = loader.load();
			
			WelcomeScreenController controller = loader.getController();
			controller.setMainApp(this);
			
			Scene scene = new Scene(layout);
			mainScreen.setScene(scene);
			mainScreen.show();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for WelcomeScreen");
			e.printStackTrace();
		}
	}
	
	public void showMainScreen() {
		initLayout(mainScreen);
	}
	
	public void showLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/LoginScreen.fxml"));
			
			Pane pane = loader.load();
			
			Scene scene = new Scene(pane);
			mainScreen.setScene(scene);
			
			LoginScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for LoginScreen");
			e.printStackTrace();
		}
	}
	
	public void showRegistration() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/RegistrationScreen.fxml"));
			
			AnchorPane pane = loader.load();
			
			Scene scene = new Scene(pane);
			mainScreen.setScene(scene);
			
			RegistrationScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for RegistrationScreen");
			e.printStackTrace();
		}
	}
	
	public void showApplication() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainFXApp.class.getResource("../View/ApplicationScreen.fxml"));
			
			AnchorPane pane = loader.load();
			
			Scene scene = new Scene(pane);
			mainScreen.setScene(scene);
			
			ApplicationScreenController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to find the fxml file for RegistrationScreen");
			e.printStackTrace();
		}
	}
	
	@FXML
    private void handleCloseMenu() {
        System.exit(0);

    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
