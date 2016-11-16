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
import model.DBInterfacer;
import netscape.javascript.JSObject;
import FXApp.MainFXApp;
/**
 * Water Purity Report Controller
 * Created by Geetika Kapoor
 */
public class ReportWaterPurityController implements Initializable,
        MapComponentInitializedListener {

    private MainFXApp mainApp;
    /**
     * FXML file widgets
     */
    @FXML
    private TextField addressField;

    @FXML
    private GoogleMapView mapView;

    @FXML
    private ComboBox<String> ConditionComboBox;

    @FXML
    private TextField VirusPPMField;

    @FXML
    private TextField ContaminantPPMField;

    /**
     * Instance Data
     */
    private AuthorizedUser user;

    private GoogleMap map;

    private GeocodingService geocodingService;

    private LatLong location;

    private Marker marker;

    private int locationNum = 0;
    private String locationSearch = "";
    private final StringProperty address = new SimpleStringProperty();
    private ObservableList<String> ConditionList;

    /**
     * Set up the main application link so we can call methods there
     *
     * @param mainApp reference to the FXApp instance
     */
    public void setMainApp(MainFXApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Set up a certain user's interface of application
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
        // Water Condition
        ArrayList<String> condition = new ArrayList<>();
        condition.add("Safe");
        condition.add("Treatable");
        condition.add("Unsafe");
        ConditionList = FXCollections.observableArrayList(condition);
        ConditionComboBox.setItems(ConditionList);
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
        marker = new Marker(new MarkerOptions().
                    position(new LatLong(33.7756178, -84.3984737))
                    .visible(false));
        map.addMarker(marker);

        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
                location = new LatLong((JSObject) obj.getMember("latLng"));
                addressField.setText(location.toString());
                marker.setOptions(new MarkerOptions().position(location)
                    .visible(true));
                int currentZoom = map.getZoom();
                map.setZoom(currentZoom - 1);
                map.setZoom(currentZoom);
            });
    }
    /**
     * Goes to a location on the map using the text printed
     * into the search field
     * Upon several presses of ENTER, goes to next possible location
     * matching the text in the search field
     */
    @FXML
    public void addressTextFieldAction() {
        geocodingService.geocode(address.get(), (GeocodingResult[] results,
                                                 GeocoderStatus status) -> {
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
                            .getGeometry().getLocation().getLongitude());
                    locationNum = (locationNum + 1) % results.length;
                } else {
                    location = new LatLong(results[0].getGeometry()
                            .getLocation().getLatitude(), results[0]
                            .getGeometry().getLocation().getLongitude());
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
        if (location == null || ConditionComboBox.getValue() == null
                || VirusPPMField.getText() == null
                || ContaminantPPMField.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Not Enough Information Input");
            alert.setContentText("Please Choose Overall Water "
                    + "Condition, Virus PPM, and Contaminant PPM");
            alert.showAndWait();
        } else if (user.getProfile().getNameProperty().get() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Name");
            alert.setHeaderText("Invalid Profile Name ");
            alert.setContentText("Please Update Your Profile Name");
            alert.showAndWait();
        } else {
            try {
                DBInterfacer.insertPurityReport(user.getProfile().
                        getNameProperty().get(),
                        Double.parseDouble(VirusPPMField.getText()),
                        Double.parseDouble(ContaminantPPMField.getText()),
                        ConditionComboBox.getValue(),
                        location.getLatitude(),
                        location.getLongitude());
                location = null;
                mainApp.showApplication(user);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid number");
                alert.setContentText("Please input numbers into PPM fields");
                alert.showAndWait();
            }
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

