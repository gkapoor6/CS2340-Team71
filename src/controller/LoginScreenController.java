package controller;
import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.AuthorizedUser;
import model.DBInterfacer;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MyLogger;


import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * Controller for login screen
 *
 */
public class LoginScreenController {


    private static Logger LOGGER = Logger.getLogger(LoginScreenController.class.getName());


    //private static Logger LOGGER;
    //public void setLOGGER(Logger LOGGER) {
        //this.LOGGER = LOGGER;
    //}


    /**
     * reference to mainApp
     */
    private MainFXApp mainApp;

    /**
     * counts the number of times user has tried to login
     */
    private int count = 0;
    /**
     * references to widgets in the fxml file
     */
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    /**
     * Setup the main application link so we can call methods there
     * @param main reference back to main class
     */
    public void setMainApp(MainFXApp main) {
        mainApp = main;
        model.MyLogger.setup(LOGGER);
    }
    /**
     * Cancel button event handler
     */
    @FXML
    private void handleCancelPressed() {
        mainApp.showMainScreen();
    }
    /**
     * Login attempt event handler
     */
    @FXML
    private void handleLoginPressed() {

        AuthorizedUser user = DBInterfacer.getUser(userField.getText());
        if (user != null) {
            if (!user.getAccountLocked()
                    && user.getPassword().equals(passwordField.getText())) {
                LOGGER.info(user.getUsername() + " has successfully logged in.");
                mainApp.showApplication(user);
            } else {

                if (user.getAccountLocked()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Attempt");
                    alert.setHeaderText("Attempt Failed");
                    alert.setContentText("User Account is locked. Admin can unlock it.");
                    alert.showAndWait();
                } else if (count < 3) {
                    count++;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Attempt");
                    alert.setHeaderText("Attempt Failed");
                    alert.setContentText("Combination of username "
                            + "and password not found");
                    alert.showAndWait();
                } else {
                    //make account locked
                    user.setAccountLocked(true);
                    //update the database
                    model.DBInterfacer.updateUser(user);
                    LOGGER.info(user.getUsername() + " account has been locked.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Attempt");
                    alert.setHeaderText("Attempt Failed");
                    alert.setContentText("User Account is locked. Admin can unlock it.");
                    alert.showAndWait();
                }

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Attempt");
            alert.setHeaderText("No Such user exists");
            alert.setContentText("Combination of username"
                    + " and password not found");
            alert.showAndWait();
        }
    }

    /**
     * Button for emailing password
     */
    @FXML
    public void handleEmailPasswordButtonClicked() {
        AuthorizedUser user = DBInterfacer.getUser(userField.getText());
        String emailTo = user.getProfile().getEmailProperty().toString();
        emailTo = emailTo.substring(23, emailTo.length() - 1);
        String emailFrom = "csteamlul@gmail.com";
        String password = "teamlul2016";

        String body = String.format("Crowd-sourced Water Reporting Application\nUsername: %s\nPassword: %s",
                user.getUsername(), user.getPassword());

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailFrom, password);
                    }
                });


        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(emailFrom));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));

            // Set Subject: header field
            message.setSubject("Team LUL: Password Recovery Service");

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            LOGGER.info("Password recovery email has been sent to " + emailTo);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Sent");
            alert.setHeaderText("Email has been successfully sent");
            alert.setContentText("Please check your inbox for an email with the password.");
            alert.showAndWait();
        } catch (MessagingException mex) {
            mex.printStackTrace();
            LOGGER.severe("Password recovery failed for " + user.getUsername() + ".\nWrong email address: "
                    + emailTo + ", or could not connect to localhost.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Recovery Unsuccessful");
            alert.setHeaderText("Email could not be sent due to incorrect email address.");
            alert.setContentText("Please wait for Admin to unblock your account.");
            alert.showAndWait();
        }


    }


}