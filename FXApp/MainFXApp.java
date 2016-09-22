package FXApp;

import java.io.IOException;
import java.util.logging.Level;

import Controller.WelcomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFXApp extends Application {
	
	private static final Logger LOGGER = Logger.getLogger("MainFXApp");
	
	private Stage mainScreen;
	
	private VBox layout;
	
	@Override
	public void start(Stage primaryStage) {
		mainScreen = primaryStage;
		initLayout(mainScreen);
		
	}
	
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
