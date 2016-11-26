package model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableDoubleValue;

/**
 * Water Purity Report Class
 * @author Geetika Kapoor
 */
public class WaterPurityReport {
    private final IntegerProperty _reportID = new SimpleIntegerProperty();
    private final StringProperty _name = new SimpleStringProperty();
    private final DoubleProperty _virusPPM = new SimpleDoubleProperty();
    private final DoubleProperty _contaminantPPM = new SimpleDoubleProperty();
    private final StringProperty _overallCondition = new SimpleStringProperty();
    private final DoubleProperty _latitude = new SimpleDoubleProperty();
    private final DoubleProperty _longitude = new SimpleDoubleProperty();
    private final StringProperty _dateTime = new SimpleStringProperty();


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
    public WaterPurityReport(int reportID, String name, double virusPPM,
                             double contaminantPPM, String overallCondition,
                             String dateTime, double latitude,
                             double longitude) {
        this._reportID.set(reportID);
        this._name.set(name);
        this._contaminantPPM.set(contaminantPPM);
        this._virusPPM.set(virusPPM);
        this._overallCondition.set(overallCondition);
        this._dateTime.set(dateTime);
        this._latitude.set(latitude);
        this._longitude.set(longitude);
    }

    /**
     * Getter for report ID
     * @return report ID
     */
    public IntegerProperty getReportIDProperty() {
        return this._reportID;
    }

    /**
     * Getter for name
     * @return name
     */
    public StringProperty getNameProperty() {
        return this._name;
    }

    /**
     * Getter for virus ppm
     * @return virus ppm
     */
    public DoubleProperty getVirusPPMProperty() {
        return this._virusPPM;
    }

    /**
     * Getter for contaminant ppm
     * @return contaminant ppm
     */
    public DoubleProperty getContaminantPPMProperty() {
        return this._contaminantPPM;
    }

    /**
     * Getter for overall condition
     * @return overall condition
     */
    public StringProperty getOverallConditionProperty() {
        return this._overallCondition;
    }

    /**
     * Getter for latitude
     * @return latitude
     */
    public ObservableDoubleValue getLatitudeProperty() {
        return this._latitude;
    }

    /**
     * Getter for longitude
     * @return longitude
     */
    public ObservableDoubleValue getLongitudeProperty() {
        return this._longitude;
    }

    /**
     * Getter for date and time
     * @return date and time
     */
    public StringProperty getDateTimeProperty() {
        return this._dateTime;
    }
    @Override
    public String toString() {
        return "Water Purity Report [Report ID = " + this._reportID.get()
                + ", Name = " + this._name.get()
                + ", Virus PPM = " + this._virusPPM.get()
                + ", Contaminant PPM = " + this._contaminantPPM.get()
                + ", Overall Condition = " + this._overallCondition.get()
                + ", Latitude = " + this._latitude.get()
                + ", Longitude = " + this._longitude.get()
                + ", Date and Time = " + this._dateTime.get() + "]";
    }
}