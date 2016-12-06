package model;

/**
 * Created by swatimardia on 11/27/16.
 */


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;


public class MyLogger  {


    static private FileHandler fileTxt;
    static private MyFormatter formatterTxt;
    //or should I use simple formatter


    static public void setup(Logger logger) {

        // get the global logger to configure it
       // Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        /*Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }*/

        try {
            logger.setUseParentHandlers(false);

            logger.setLevel(Level.INFO);
            //if file doesnt already exist, create a new file
            if (fileTxt == null) {
                fileTxt = new FileHandler("../CS2340-Team71/SecurityLog.log", true);
            }

            // create a TXT formatter
            formatterTxt = new MyFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}

