package Controller;
import java.util.ArrayList;

import FXApp.MainFXApp;
import Model.ReportDBAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
/**
 * Registration screen controller
 *
 */
public class RegistrationScreenController {
    /**
     * reference to main application
     */
    private MainFXApp mainApp;
    /**
     * references to widgets in the fxml file
     */
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private ComboBox<String> accountType;
    /**
     * called automatically after load
     */
    @FXML
    private void initialize() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("User");
        arrayList.add("Worker");
        arrayList.add("Manager");
        arrayList.add("Admin");
        ObservableList<String> list =
                FXCollections.observableArrayList(arrayList);
        accountType.setItems(list);
    }
    /**
     * Setup the main application link so we can call methods there
     * @param main reference to main application
     */
    public void setMainApp(MainFXApp main) {
        mainApp = main;
    }
    /**
     * Event handler for cancel button
     */
    @FXML
    private void handleCancelPressed() {
        mainApp.showMainScreen();
    }
    /**
     * Event handler for registration button
     */
    @FXML
    private void handleRegisterPressed() {
        String userName = userNameField.getText();
        String pass = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String type = accountType.getValue();
        if (userName == null || pass == null
                || confirm == null || type == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not enough information");
            alert.setContentText("Please complete all required information");
            alert.showAndWait();
        } else {
            if (pass.equals(confirm)) {
                if (!ReportDBAccess.insertUser(userName, pass, type)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Could not create User");
                    alert.setContentText("A user with this username"
                            + " already exists");
                    alert.showAndWait();
                } else {
                    mainApp.showMainScreen();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Input passwords do not match");
                alert.showAndWait();
            }
        }
    }
}