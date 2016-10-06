package Model;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataReport {
	/**
	 * all instance data in StringProperty to connect to the view
	 */
	private StringProperty _waterType = new SimpleStringProperty();
	private StringProperty _waterCondition = new SimpleStringProperty();
	private String name;
	private Date date;

	public DataReport(String waterType, String waterCondition, String name,
			Date date) {
		this._waterType.set(waterType);
		this._waterCondition.set(waterCondition);
		this.name = name;
		this.date = date;
	}

	public DataReport() {
		this("", "", "", null);
	}

	public StringProperty getWaterType() {
		return _waterType;
	}

	public void setWaterType(StringProperty waterType) {
		this._waterType = waterType;
	}

	public StringProperty getWaterCondition() {
		return _waterCondition;
	}

	public void setWaterCondition(StringProperty waterCondition) {
		this._waterCondition = waterCondition;
	}

	@Override
	public String toString() {
		return "DataReport [_waterType=" + _waterType + ", _waterCondition="
				+ _waterCondition + "]";
	}

}
