package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataReport {
	/**
	 * all instance data in StringProperty to connect to the view
	 */
	private StringProperty _waterType = new SimpleStringProperty();
	private StringProperty _waterCondition = new SimpleStringProperty();

	public DataReport(String waterType, String waterCondition) {
		_waterCondition.set(waterCondition);
		_waterType.set(waterType);
	}

	public DataReport() {
		this("", "");
	}

	public StringProperty getWaterType() {
		return _waterType;
	}

	public void setWaterType(StringProperty _waterType) {
		this._waterType = _waterType;
	}

	public StringProperty getWaterCondition() {
		return _waterCondition;
	}

	public void setWaterCondition(StringProperty _waterCondition) {
		this._waterCondition = _waterCondition;
	}

	@Override
	public String toString() {
		return "DataReport [_waterType=" + _waterType + ", _waterCondition="
				+ _waterCondition + "]";
	}

}
