package controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.AuthorizedUser;
import model.DBInterfacer;
import model.MyLogger;

import java.util.logging.Logger;

/**
 * Created by swatimardia on 11/26/16.
 */
public class BanUserScreenController {

    private static Logger LOGGER = Logger.getLogger(BanUserScreenController.class.getName());

    //private static Logger LOGGER;
    /*public void setLOGGER(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }*/

    private MainFXApp mainApp;

    private AuthorizedUser user;

    @FXML
    private TextField username;

    /**
     * Setup the main application link so we can call methods there
     * @param main reference to main application
     */
    public void setMainApp(MainFXApp main) {

        mainApp = main;
        model.MyLogger.setup(LOGGER);
    }


    /**
     * Set the user
     * @param user the user
     */
    public void setUser(AuthorizedUser user) {
        this.user = user;
    }

    /**
     * Event handler for cancel button
     */
    @FXML
    private void handleCancelButtonClicked() {
        mainApp.showApplication(user);
    }

    /**
     * Event handler for ban user button clicked
     */
    @FXML
    private void handleBanUserClicked() {
        String userBan = username.getText();
        if (userBan == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not enough information");
            alert.setContentText("Please complete all required information");
            alert.showAndWait();
        } else {
            AuthorizedUser userBanned = DBInterfacer.getUser(userBan);
            if (userBanned == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Does Not Exist");
                alert.setContentText("A user with this username"
                        + " does not exist.");
                alert.showAndWait();
            } else {
                userBanned.setUserBanned(true);
                DBInterfacer.updateUser(userBanned);
                LOGGER.info(user.getUsername() + " has banned " + userBan + " from submitting water reports.");
                mainApp.showApplication(user);
            }
        }
    }
}
