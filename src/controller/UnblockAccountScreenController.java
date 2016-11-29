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
public class UnblockAccountScreenController {

    private static Logger LOGGER =
            Logger.getLogger(UnblockAccountScreenController.class.getName());

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
    private void handleCancelClicked() {
        mainApp.showApplication(user);
    }

    /**
     * Event handler for delete account button clicked
     */
    @FXML
    private void handleUnblockAccountClicked() {
        String userUnblock = username.getText();
        if (username == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not enough information");
            alert.setContentText("Please complete all required information");
            alert.showAndWait();
        } else {
            AuthorizedUser userUnblocked = DBInterfacer.getUser(userUnblock);
            if (userUnblocked == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Does Not Exist");
                alert.setContentText("A user with this username"
                        + " does not exist.");
                alert.showAndWait();
            } else {
                userUnblocked.setAccountLocked(false);
                DBInterfacer.updateUser(userUnblocked);
                LOGGER.info(user.getUsername() + " has unblocked the account of " + userUnblock + ".");
                mainApp.showApplication(user);
            }
        }

    }
}
