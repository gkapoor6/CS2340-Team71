package model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Class representing a water source report
 * @author Dong Son Trinh
 *
 */
public class WaterSourceReport {
    /**
     * all instance data in StringProperty to connect to the view
     */
    private IntegerProperty _reportID = new SimpleIntegerProperty();
    private StringProperty _name = new SimpleStringProperty();
    private StringProperty _waterType = new SimpleStringProperty();
    private StringProperty _waterCondition = new SimpleStringProperty();
    private DoubleProperty _latitude = new SimpleDoubleProperty();
    private DoubleProperty _longitude = new SimpleDoubleProperty();
    private StringProperty _dateTime = new SimpleStringProperty();
    /**
     * Constructor for the water source report
     * @param reportID ID of the report
     * @param name Name of the submitter
     * @param waterType water type
     * @param waterCondition condition of water
     * @param dateTime date and time of submission
     * @param latitude latitude of location
     * @param longitude longitude of location
     */
    public WaterSourceReport(int reportID, String name, String waterType,
            String waterCondition, String dateTime, double latitude,
            double longitude) {
        _reportID.set(reportID);
        _name.set(name);
        _waterCondition.set(waterCondition);
        _waterType.set(waterType);
        _dateTime.set(dateTime);
        _latitude.set(latitude);
        _longitude.set(longitude);
    }
    /**
     * Default constructor
     */
    public WaterSourceReport() {
    }
    /**
     * Getters for all the properties
     */
    public IntegerProperty getReportIDProperty() {
        return _reportID;
    }
    public StringProperty getNameProperty() {
        return _name;
    }
    public StringProperty getWaterTypeProperty() {
        return _waterType;
    }
    public StringProperty getWaterConditionProperty() {
        return _waterCondition;
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
        return "WaterSourceReport [reportID=" + _reportID.get()
                + ", name=" + _name.get() + ", waterType=" + _waterType.get()
                + ", waterCondition=" + _waterCondition.get()
                + ", latitude=" + _latitude.get() + ", longitude="
                + _longitude.get() + ", dateTime=" + _dateTime.get() + "]";
    }
}