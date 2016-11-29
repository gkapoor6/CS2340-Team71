package controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.AuthorizedUser;
import model.MyLogger;
import model.Profile;
import model.DBInterfacer;
import java.util.ArrayList;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
/**
 * Controller for profile edit window
 * @author Hairong Ke
 */
public class ProfileScreenController {


    private static Logger LOGGER =
            Logger.getLogger(ProfileScreenController.class.getName());

    /**
     * reference to mainApp
     */
    private MainFXApp mainApp;
    /**
     * references to widgets in the fxml file
     */
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> titleComboBox;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    /**
     * instance data needed for updating the profile
     */
    private AuthorizedUser user;

    private Profile profile;

    /**
     ** called automatically after load
     */
    @FXML
   private void initialize() {
        Collection<String> arrayList = new ArrayList<>();
        arrayList.add("Mr.");
        arrayList.add("Mrs.");
        arrayList.add("Ms.");

        ObservableList<String> list
                = FXCollections.observableArrayList(arrayList);
        titleComboBox.setItems(list);
    }

    /**
     * Setup the main application link so we can call methods there
     * @param main reference to the FXApp instance
     */
    public void setMainApp(MainFXApp main) {

        mainApp = main;
        model.MyLogger.setup(LOGGER);
    }
    /**
     * Setup a certain user's interface of application
     * @param user user
     */
    public void setUser(AuthorizedUser user) {
        this.user = user;
        profile = user.getProfile();
        titleComboBox.setValue(profile.getTitleProperty().get());
        nameField.setText(profile.getNameProperty().get());
        emailField.setText(profile.getEmailProperty().get());
        addressField.setText(profile.getHomeProperty().get());
    }
    /**
     * Update button event handler
     */
    @FXML
    private void handleUpdatePressed() {
        // validate the profile info
        if (isInputValid()) {
            profile.setName(nameField.getText());
            profile.setTitle(titleComboBox.getValue());
            profile.setEmail(emailField.getText());
            profile.setHome(addressField.getText());
            DBInterfacer.updateProfile(nameField.getText(),
                     emailField.getText(), titleComboBox.getValue(),
                    addressField.getText(), user.getUsername());
            LOGGER.info(user.getUsername() + " has updated his profile.");
            mainApp.showApplication(user);
        }
    }
    /**
     * Cancel button event handler
     */
    @FXML
    private void handleCancelPressed() {
        LOGGER.info(user.getUsername() + " has cancelled from profile screen.");
        mainApp.showApplication(user);
    }
    /**
     * Validate the text fields
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if ((nameField.getText() == null)
                || (nameField.getText().isEmpty())) {
            errorMessage += "Please enter a valid name.\n";
        }
        if ((emailField.getText() == null)
                || (emailField.getText().isEmpty())) {
            errorMessage += "Please enter a valid name.\n";
        }
        if ((addressField.getText() == null)
                || (addressField.getText().isEmpty())) {
            errorMessage += "Please enter a valid address.\n";
        }
        // no error message means success / good input
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show the error message if bad data
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}