package controller;
import java.net.URL;
import java.util.ResourceBundle;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.AuthorizedUser;
import model.ReportDBAccess;
import model.WaterSourceReport;
import netscape.javascript.JSObject;
/**
 * Controller for view of all reports submitted by a user
 * @author Dong Son Trinh
 *
 */
public class AllReportViewController
    implements Initializable, MapComponentInitializedListener {
    /**
     * reference to mainApp
     */
    private MainFXApp mainApp;
    /**
     * references to widgets in FXML files
     */
    @FXML
    private GoogleMapView mapView;
    /**
     * instance date required to show the reports submitted by a user
     */
    private GoogleMap map;
    private AuthorizedUser user;
    private InfoWindow window;
    /**
     * Setup the main application link so we can call methods there
     * @param mainApp reference to the FXApp instance
     */
    public void setMain(MainFXApp mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * Setup a certain user's interface of application
     * @param user user
     */
    public void setUser(AuthorizedUser user) {
        this.user = user;
    }
    /**
     * Runs on load of the controller
     * Sets the Table to data in the database and
     * Adds a listener to the selected entry to show the its location
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }
    /**
     * Uses GoogleMapView to mark all locations of reports
     */
    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(33.7756178, -84.3984737))
            .mapType(MapTypeIdEnum.TERRAIN)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .zoom(9);
        map = mapView.createMap(mapOptions);
        window = new InfoWindow();
        for (WaterSourceReport w: ReportDBAccess.getReportList(
                user.getProfile().getNameProperty().get())) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong location = new LatLong(w.getLatitudeProperty().get(),
                    w.getLongitudeProperty().get());
            Marker marker = new Marker(markerOptions.position(location)
                    .visible(true)
                    .title(w.getNameProperty().get() + " submitted at "
                            + w.getDateTimeProperty().get()));
            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                    InfoWindowOptions windowOptions = new
                            InfoWindowOptions().content(w.toString());
                    window.setOptions(windowOptions);
                    window.open(map, marker);
                });
            map.addMarker(marker);
        }
    }
    /**
     * Button event handler to go back to the application
     */
    @FXML
    public void handleBackToApplication() {
        mainApp.showApplication(user);
    }
}