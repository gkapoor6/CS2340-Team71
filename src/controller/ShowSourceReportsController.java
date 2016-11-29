package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import FXApp.MainFXApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.AuthorizedUser;
import model.DBInterfacer;
import model.MyLogger;
import model.WaterSourceReport;

/**
 * Controller for view of all source reports submitted by a user
 * @author Dong Son Trinh
 *
 */
public class ShowSourceReportsController
        implements Initializable, MapComponentInitializedListener {

    private static Logger LOGGER =
            Logger.getLogger(ShowSourceReportsController.class.getName());

    /**
     * reference to mainApp
     */
    private MainFXApp mainApp;

    /**
     *Creating a constant for the latitude to initialize in
     */
    private static final double INITIAL_LATITUDE = 33.7756178;

    /**
     * Creating a constant for the longitude to initialize in
     */
    private static final double INITIAL_LONGITUDE = -84.3984737;

    /**
     * Constant for zoom
     */
    private static final int ZOOM = 12;


    /**
     * references to widgets in FXML files
     */
    @FXML
    private GoogleMapView mapView;

    @FXML
    private TableView<WaterSourceReport> reportTable;

    @FXML
    private TableColumn<WaterSourceReport, Integer> ReportIDColumn;

    @FXML
    private TableColumn<WaterSourceReport, String> NameColumn;

    @FXML
    private TableColumn<WaterSourceReport, String> WaterTypeColumn;

    @FXML
    private TableColumn<WaterSourceReport, String> WaterConditionColumn;

    @FXML
    private TableColumn<WaterSourceReport, String> DateTimeColumn;


    /**
     * instance date required to show the reports submitted by a user
     */
    private GoogleMap map;

    private Marker marker;

    private AuthorizedUser user;

    /**
     * Setup the main application link so we can call methods there
     * @param mainApp reference to the FXApp instance
     */
    public void setMain(MainFXApp mainApp) {
        this.mainApp = mainApp;
        reportTable.setItems(DBInterfacer.getSourceReportList(
                user.getProfile().getNameProperty().get()));
        model.MyLogger.setup(LOGGER);
        LOGGER.info(user.getUsername() + " has viewed water source reports.");

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


        ReportIDColumn.setCellValueFactory(cellData ->
            cellData.getValue().getReportIDProperty().asObject());
        NameColumn.setCellValueFactory(cellData ->
            cellData.getValue().getNameProperty());
        WaterTypeColumn.setCellValueFactory(cellData ->
            cellData.getValue().getWaterTypeProperty());
        WaterConditionColumn.setCellValueFactory(cellData ->
            cellData.getValue().getWaterConditionProperty());
        DateTimeColumn.setCellValueFactory(cellData ->
            cellData.getValue().getDateTimeProperty());

        reportTable.getSelectionModel().selectedItemProperty().addListener((
                observable,
                oldvalue, newvalue) -> showReportLocation(newvalue));


        mapView.addMapInializedListener(this);
    }


    /**
     * Uses GoogleMapView to show location of selected
     * water source report in tableview
     */
    @Override
    public void mapInitialized() {

        //Set the initial properties of the map.


        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(INITIAL_LATITUDE, INITIAL_LONGITUDE))
            .mapType(MapTypeIdEnum.ROADMAP)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .zoom(ZOOM);

        map = mapView.createMap(mapOptions);
        marker = new Marker(new MarkerOptions().position(
                new LatLong(INITIAL_LATITUDE, INITIAL_LONGITUDE))
                .visible(false));
        map.addMarker(marker);

    }


    /**
     * Shows the location of a particular water source report
     * @param r water source report
     */
    private void showReportLocation(WaterSourceReport r) {
        LatLong reportLocation = new LatLong(r.getLatitudeProperty().get(),
                r.getLongitudeProperty().get());
        marker.setOptions(new MarkerOptions().position(reportLocation)
                .visible(true));
        int currentZoom = map.getZoom();
        map.setZoom(currentZoom - 1);
        map.setZoom(currentZoom);
        map.setCenter(reportLocation);
    }

    /**
     * Button event handler to go back to the application
     */
    @FXML
    public void handleBackToApplication() {
        mainApp.showApplication(user);
    }
}
