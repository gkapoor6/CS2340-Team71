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
public class DeleteAccountScreenController {

    private static Logger LOGGER = Logger.getLogger(DeleteAccountScreenController.class.getName());


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
    private void handleDeleteAccountClicked() {
        String usernameDelete = username.getText();
        if (usernameDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not enough information");
            alert.setContentText("Please complete all required information");
            alert.showAndWait();
        } else {
            if (DBInterfacer.getUser(usernameDelete) == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Does Not Exist");
                alert.setContentText("A user with this username"
                        + " does not exist.");
                alert.showAndWait();
            } else {
                DBInterfacer.deleteUser(usernameDelete);
                LOGGER.info(user.getUsername() + " has deleted the account of " + usernameDelete + ".");
                mainApp.showApplication(user);
            }
        }

    }

}
