package controller;

import java.net.URL;
import java.util.ResourceBundle;

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
import model.WaterPurityReport;
/**
 * Controller for view all purity reports
 * @author Geetika Kapoor
 *
 */
public class ShowPurityReportsController implements Initializable,
        MapComponentInitializedListener {

    /**
     * reference to mainApp
     */
    private MainFXApp mainApp;


    /**
     * references to widgets in FXML files
     */
    @FXML
    private GoogleMapView mapView;

    @FXML
    private TableView<WaterPurityReport> reportTable;

    @FXML
    private TableColumn<WaterPurityReport, Integer> ReportIDColumn;

    @FXML
    private TableColumn<WaterPurityReport, String> NameColumn;

    @FXML
    private TableColumn<WaterPurityReport, Double> VirusPPMColumn;

    @FXML
    private TableColumn<WaterPurityReport, Double> ContaminantPPMColumn;

    @FXML
    private TableColumn<WaterPurityReport, String> OverallConditionColumn;

    @FXML
    private TableColumn<WaterPurityReport, String> DateTimeColumn;


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
        reportTable.setItems(DBInterfacer.
                getPurityReportList());


        ReportIDColumn.setCellValueFactory(cellData -> cellData.getValue().
                getReportIDProperty().asObject());
        NameColumn.setCellValueFactory(cellData -> cellData.getValue().
                getNameProperty());
        VirusPPMColumn.setCellValueFactory(cellData ->
            cellData.getValue().getVirusPPMProperty().asObject());
        ContaminantPPMColumn.setCellValueFactory(cellData ->
            cellData.getValue().getContaminantPPMProperty().asObject());
        OverallConditionColumn.setCellValueFactory(cellData ->
            cellData.getValue().getOverallConditionProperty());
        DateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().
                getDateTimeProperty());

        reportTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) ->
                    showReportLocation(newvalue));


        mapView.addMapInializedListener(this);
    }


    /**
     * Uses GoogleMapView to show location of selected water purity report
     * in tableview
     */
    @Override
    public void mapInitialized() {

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
        marker = new Marker(new MarkerOptions().position(
                new LatLong(33.7756178, -84.3984737))
                .visible(false));
        map.addMarker(marker);

    }


    /**
     * Shows the location of a particular water purity report
     * @param r water purity report
     */
    private void showReportLocation(WaterPurityReport r) {
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

