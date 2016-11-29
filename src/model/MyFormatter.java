package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;
import java.util.GregorianCalendar;
import java.util.Calendar;


/**
 * Created by swatimardia on 11/27/16.
 */
public class MyFormatter extends SimpleFormatter {




    public String format(LogRecord record) {

        SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(record.getMillis());
        return record.getLevel()
                + logTime.format(cal.getTime())
                + " || "
                + record.getSourceClassName().substring(
                record.getSourceClassName().lastIndexOf(".")+1,
                record.getSourceClassName().length())
                + "."
                + record.getSourceMethodName()
                + "() : "
                + record.getMessage() + "\n";


    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }


    public String getHead(Handler h) {
        return "<!DOCTYPE html>\n<head>\n<style>\n"
                + "table { width: 100% }\n"
                + "th { font:bold 10pt Tahoma; }\n"
                + "td { font:normal 10pt Tahoma; }\n"
                + "h1 {font:normal 11pt Tahoma;}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>" + (new Date()) + "</h1>\n"
                + "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n"
                + "<tr align=\"left\">\n"
                + "\t<th style=\"width:10%\">Loglevel</th>\n"
                + "\t<th style=\"width:15%\">Time</th>\n"
                + "\t<th style=\"width:75%\">Log Message</th>\n"
                + "</tr>\n";
    }

    public String getTail(Handler h) {
        return "</table>\n</body>\n</html>";
    }



}
