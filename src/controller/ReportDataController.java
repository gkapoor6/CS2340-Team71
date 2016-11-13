package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

import FXApp.MainFXApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.AuthorizedUser;
import model.ReportDBAccess;
import netscape.javascript.JSObject;
/**
 * Controller for view of water source report submission
 * @author Dong Son Trinh
 *
 */
public class ReportDataController
    implements Initializable, MapComponentInitializedListener {
    /**
     * reference to mainApp
     */
    private MainFXApp mainApp;
    /**
     * References to all widgets from FXML file
     */
    @FXML
    private TextField addressField;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private ComboBox<String> WaterTypeCombox;
    @FXML
    private ComboBox<String> WaterConditionCombox;
    /**
     * All instance data required to submit a report
     */
    private AuthorizedUser user;
    private GoogleMap map;
    private GeocodingService geocodingService;
    private LatLong location;
    private Marker marker;
    private int locationNum = 0;
    private String locationSearch = "";
    private StringProperty address = new SimpleStringProperty();
    private ObservableList<String> WaterTypeList;
    private ObservableList<String> WaterConditionList;
    /**
     * Setup the main application link so we can call methods there
     * @param mainApp reference to the FXApp instance
     */
    public void setMainApp(MainFXApp mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * Setup a certain user's interface of application
     * @param user user
     */
    public void setUser(AuthorizedUser user) {
        this.user = user;
    }
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        address.bind(addressField.textProperty());
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
        WaterConditionList = FXCollections.observableArrayList(arrayList2);
        WaterConditionCombox.setItems(WaterConditionList);
    }
    /**
     * Also called right after loading
     * Uses GoogleMapView to set the location of the water source report
     */
    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(33.7756178, -84.3984737))
        .mapType(MapTypeIdEnum.ROADMAP)
        .overviewMapControl(false)
        .panControl(false)
        .rotateControl(false)
        .scaleControl(false)
        .streetViewControl(false)
        .zoomControl(false)
            .zoom(12);
        map = mapView.createMap(mapOptions);
        marker = new Marker(new MarkerOptions()
                .position(new LatLong(33.7756178, -84.3984737))
                .visible(false));
        map.addMarker(marker);
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
                location = new LatLong((JSObject) obj.getMember("latLng"));
                // System.out.println("LatLong: lat: " + location.getLatitude()
                // + " lng: " + location.getLongitude());
                addressField.setText(location.toString());
                marker.setOptions(new MarkerOptions().position(location)
                        .visible(true));
                int currentZoom = map.getZoom();
                map.setZoom(currentZoom - 1);
                map.setZoom(currentZoom);
            });
    }
    /**
     * Goes to a location on the map using the text
     * printed into the search field
     * Upon several presses of ENTER,
     * goes to next possible location matching the text in the search field
     */
    @FXML
    public void addressTextFieldAction() {
        geocodingService.geocode(address.get(),
                (GeocodingResult[] results, GeocoderStatus status) -> {
                if (!address.get().equals(locationSearch)) {
                    locationNum = 0;
                    locationSearch = address.get();
                }
                if (status == GeocoderStatus.ZERO_RESULTS) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "No matching address found");
                    alert.show();
                    return;
                } else if (results.length > 1) {
                    location = new LatLong(results[locationNum].getGeometry()
                            .getLocation().getLatitude(), results[locationNum]
                                    .getGeometry().getLocation()
                                    .getLongitude());
                    locationNum = (locationNum + 1) % results.length;
                } else {
                    location = new LatLong(results[0].getGeometry()
                            .getLocation().getLatitude(),
                            results[0].getGeometry().getLocation()
                            .getLongitude());
                }
                marker.setOptions(new MarkerOptions().position(location)
                        .visible(true));
                map.setCenter(location);
            });
    }
    /**
     * Submission button event handler
     * Upon successful submission adds the report to the database
     */
    @FXML
    private void handleSubmitPressed() {
        if (location == null || WaterTypeCombox.getValue() == null
                || WaterConditionCombox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Not enough information input");
            alert.setContentText("Please choose water type and water condition"
                    + " and choose the location");
            alert.showAndWait();
        } else if (user.getProfile().getNameProperty().get() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Name");
            alert.setHeaderText("Name in the Profile invalid");
            alert.setContentText("Please update your Profile name");
            alert.showAndWait();
        } else {
            ReportDBAccess.insertReport(
                    user.getProfile().getNameProperty().get(),
                    WaterTypeCombox.getValue(), WaterConditionCombox.getValue(),
                    location.getLatitude(), location.getLongitude());
            location = null;
            mainApp.showApplication(user);
        }
    }
    /**
     * Cancel button event handler
     */
    @FXML
    private void handleCancelPressed() {
        location = null;
        mainApp.showApplication(user);
    }
}