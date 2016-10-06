package Controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import FXApp.MainFXApp;

public class ReportDataController {
	/**
	 * reference to mainApp
	 */
	private MainFXApp mainApp;

	@FXML
	private ComboBox<String> WaterTypeCombox;

	@FXML
	private ComboBox<String> WaterConditionCombox;

	private ObservableList<String> WaterTypeList;
	private ObservableList<String> WaterConditionList;

	/**
	 * called automatically after load
	 */
	@FXML
	private void initialize() {
		ArrayList<String> arrayList1 = new ArrayList<>();
		arrayList1.add("Bottled");
		arrayList1.add("Well");
		arrayList1.add("Stream");
		arrayList1.add("Lake");
		arrayList1.add("Spring");
		arrayList1.add("Other");
		WaterTypeList = FXCollections.observableArrayList(arrayList1);
		WaterTypeCombox.setItems(WaterTypeList);

		ArrayList<String> arrayList2 = new ArrayList<>();
		arrayList2.add("Bottled");
		arrayList2.add("Waste");
		arrayList2.add("Treatable-Clear");
		arrayList2.add("Treatable-Muddy");
		arrayList2.add("Potable");
		WaterTypeList = FXCollections.observableArrayList(arrayList2);
		WaterTypeCombox.setItems(WaterTypeList);

	}

	/**
	 * Setup the main application link so we can call methods there
	 * 
	 * @param main
	 *            reference to the FXApp instance
	 */
	public void setMainApp(MainFXApp main) {
		mainApp = main;
	}

}
