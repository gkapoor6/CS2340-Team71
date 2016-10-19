package Model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class representing a water purity report
 * @author Geetika Kapoor
 *
 */
public class WaterPurityReport {

    private IntegerProperty _reportID = new SimpleIntegerProperty();
    private StringProperty _name = new SimpleStringProperty();
    private IntegerProperty _virusPPM = new SimpleIntegerProperty();
    private IntegerProperty _contaminantPPM = new SimpleIntegerProperty();
    private StringProperty _overallCondition = new SimpleStringProperty();
    private DoubleProperty _latitude = new SimpleDoubleProperty();
    private DoubleProperty _longitude = new SimpleDoubleProperty();
    private StringProperty _dateTime = new SimpleStringProperty();

    /**
     * Water Purity Report Constructor
     * @param reportID ID of the report
     * @param name Name of the submitter
     * @param virusPPM virus PPM
     * @param contaminantPPM contaminant PPM
     * @param overallCondition overall condition of water
     * @param dateTime date and time of submission
     * @param latitude latitude of location
     * @param longitude longitude of location
     */
    public WaterPurityReport(int reportID, String name, int virusPPM,
                             int contaminantPPM, String overallCondition,
                             String dateTime, double latitude,
                             double longitude) {

        _reportID.set(reportID);
        _name.set(name);
        _contaminantPPM.set(contaminantPPM);
        _virusPPM.set(virusPPM);
        _overallCondition.set(overallCondition);
        _dateTime.set(dateTime);
        _latitude.set(latitude);
        _longitude.set(longitude);
    }

    public IntegerProperty getReportIDProperty() {
        return _reportID;
    }

    public StringProperty getNameProperty() {
        return _name;
    }

    public IntegerProperty getVirusPPMProperty() {
        return _virusPPM;
    }

    public IntegerProperty getContaminantPPMProperty() {
        return _contaminantPPM;
    }

    public StringProperty getWaterConditionProperty() {
        return _overallCondition;
    }

    public DoubleProperty getLatitudeProperty() {
        return _latitude;
    }

    public DoubleProperty getLongitudeProperty() {
        return _longitude;
    }

    public StringProperty getDateTimeProperty() {
        return _dateTime;
    }

    @Override
    public String toString() {
        return "Water Purity Report [Report ID = " + _reportID.get()
                + ", Name = " + _name.get()
                + ", Virus PPM = " + _virusPPM.get()
                + ", Contaminant PPM = " + _contaminantPPM.get()
                + ", Latitude = "
                + _latitude.get() + ", Longitude = " + _longitude.get()
                + ", Date and Time = " + _dateTime.get() + "]";
    }

}
