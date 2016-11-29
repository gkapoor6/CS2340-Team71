package controller;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.AuthorizedUser;

import java.util.logging.Logger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by swatimardia on 11/29/16.
 */
public class ViewSecurityLogController implements Initializable {

    private static Logger LOGGER = Logger.getLogger(ViewSecurityLogController.class.getName());


    private MainFXApp mainApp;

    private AuthorizedUser user;

    @FXML
    private TextArea textArea;

    /**
     * Setup the main application link so we can call methods there
     * @param main reference to main application
     */
    public void setMainApp(MainFXApp main) {

        mainApp = main;
        model.MyLogger.setup(LOGGER);
        LOGGER.info(user.getUsername() + " has viewed Security Log.");
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
    private void handleBackToApplicationButton() {
        mainApp.showApplication(user);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Scanner s = new Scanner(new File("SecurityLog.log")).useDelimiter("\\n");
            while (s.hasNextLine()) {

                String line = s.nextLine();

                if ("I".equals(line.substring(0, 1)) || "S".equals(line.substring(0, 1))) {
                    Scanner s2 = new Scanner(line).useDelimiter("\\s+");

                    while (s2.hasNext()) {
                        if (s2.hasNextInt()) { // check if next token is an int
                            textArea.appendText(s2.nextInt() + " "); // display the found integer
                        } else {
                            textArea.appendText(s2.next() + " "); // else read the next token
                        }
                    }

                    textArea.appendText("\n");
                    s2.close();
                }

            }
            s.close();
        } catch (FileNotFoundException ex) {
            LOGGER.severe("SecurityLog.log file not found");
            System.err.println(ex);
        }
    }
}
