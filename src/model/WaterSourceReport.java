package model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableDoubleValue;

/**
 * Class representing a water source report
 * @author Dong Son Trinh
 *
 */
public class WaterSourceReport {
    /**
     * all instance data in StringProperty to connect to the view
     */
    private final IntegerProperty _reportID = new SimpleIntegerProperty();
    private final StringProperty _name = new SimpleStringProperty();
    private final StringProperty _waterType = new SimpleStringProperty();
    private final StringProperty _waterCondition = new SimpleStringProperty();
    private final DoubleProperty _latitude = new SimpleDoubleProperty();
    private final DoubleProperty _longitude = new SimpleDoubleProperty();
    private final StringProperty _dateTime = new SimpleStringProperty();
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
        this._reportID.set(reportID);
        this._name.set(name);
        this._waterCondition.set(waterCondition);
        this._waterType.set(waterType);
        this._dateTime.set(dateTime);
        this._latitude.set(latitude);
        this._longitude.set(longitude);
    }

    /**
     * Default constructor
     */
    public WaterSourceReport() {
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
     * Getter for water type
     * @return water type
     */
    public StringProperty getWaterTypeProperty() {
        return this._waterType;
    }

    /**
     * Getter for water condition
     * @return water condition
     */
    public StringProperty getWaterConditionProperty() {
        return this._waterCondition;
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
        return "WaterSourceReport [reportID=" + this._reportID.get()
                + ", name=" + this._name.get() + ", waterType="
                + this._waterType.get()
                + ", waterCondition=" + this._waterCondition.get()
                + ", latitude=" + this._latitude.get() + ", longitude="
                + this._longitude.get() + ", dateTime="
                + this._dateTime.get() + "]";
    }
}