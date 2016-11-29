package controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AuthorizedUser;
import model.DBInterfacer;
import model.MyLogger;
import model.WaterPurityReport;
import netscape.javascript.JSObject;

/**
 * class to view water quality history
 */
public class ViewWaterQualityHistoryGraph
        implements Initializable, MapComponentInitializedListener {

    private static Logger LOGGER =
            Logger.getLogger(ViewWaterQualityHistoryGraph.class.getName());
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
     * references to widgets in the fxml file
     */
    @FXML
    private GoogleMapView mapView;
    @FXML
    private TextField locationEntered;
    @FXML
    private TextField yearEntered;
    @FXML
    private ComboBox<String> typePicked;
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
    /**
     * Setup the main application link so we can call methods there
     * @param main reference back to main class
     */
    public void setMainApp(MainFXApp main) {

        mainApp = main;
        model.MyLogger.setup(LOGGER);
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
        address.bind(locationEntered.textProperty());
        Collection<String> list = new ArrayList<>();
        list.add("Virus PPM");
        list.add("Contaminant PPM");
        ObservableList<String> observableList =
                FXCollections.observableArrayList(list);
        typePicked.setItems(observableList);
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
        marker = new Marker(new MarkerOptions().
                position(new LatLong(INITIAL_LATITUDE, INITIAL_LONGITUDE))
                .visible(false));
        map.addMarker(marker);

        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            location = new LatLong((JSObject) obj.getMember("latLng"));
            //System.out.println("LatLong: lat: " +
            // location.getLatitude() + " lng: " + location.getLongitude());
            locationEntered.setText(location.toString());
            marker.setOptions(new MarkerOptions().position(location)
                    .visible(true));
            int currentZoom = map.getZoom();
            map.setZoom(currentZoom - 1);
            map.setZoom(currentZoom);
        });
    }

    /**
     * Goes to a location on the map using the
     * text printed into the search field
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
     * Cancel button event handler
     */
    @FXML
    private void handleCancelledPressed() {
        location = null;
        mainApp.showApplication(user);
    }

    /**
     * Submit button event handler
     */
    @FXML
    private void handleSubmitPressed() {
        String year = yearEntered.getText();
        String type = typePicked.getValue();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel(type);
        yAxis.autoRangingProperty();
        LineChart<String, Number> lineChart = new LineChart<>(
                xAxis, yAxis);
        //XYChart.Series series2 = new XYChart.Series();

        lineChart.setTitle("Purity History Graph");
        XYChart.Series<String, Number> series = new XYChart.Series<>();


        if ((year == null) | (location == null) | (type == null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not enough information");
            alert.setContentText("Please complete all required information");
            alert.showAndWait();
        } else {

            //String month;
            series = helperSubmit(series, type, year);

            int scenePositionX = 800;
            int scenePositionY = 600;
            lineChart.getData().add(series);
            Scene scene = new Scene(lineChart, scenePositionX, scenePositionY);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            LOGGER.info(user.getUsername() + " has viewed the water quality history graph in "
                    + type + " for the year " + year + " in latitude "
                    + location.getLatitude() + " and longitude " + location.getLongitude() + ".");
        }
    }

    /**
     * helper method for submit
     * @param series series
     * @param type type
     * @param year year
     * @return series
     */
    private XYChart.Series<String, Number>
        helperSubmit(XYChart.Series<String,
            Number> series, String type, String year) {
        ObservableList<WaterPurityReport> waterPurityReportList =
                DBInterfacer.getPurityReportList();
        double virusJan = 0.0;
        double virusFeb = 0.0;
        double virusMar = 0.0;
        double virusApr = 0.0;
        double virusMay = 0.0;
        double virusJun = 0.0;
        double virusJul = 0.0;
        double virusAug = 0.0;
        double virusSept = 0.0;
        double virusOct = 0.0;
        double virusNov = 0.0;
        double virusDec = 0.0;
        int virusJanCount = 0;
        int virusFebCount = 0;
        int virusMarCount = 0;
        int virusAprCount = 0;
        int virusMayCount = 0;
        int virusJunCount = 0;
        int virusJulCount = 0;
        int virusAugCount = 0;
        int virusSeptCount = 0;
        int virusOctCount = 0;
        int virusNovCount = 0;
        int virusDecCount = 0;
        for (WaterPurityReport report : waterPurityReportList) {
            String yearRecorded = report.getDateTimeProperty()
                    .getValue().substring(0, 4);
            if (((Math.round(report.getLongitudeProperty().get())
                    == Math.round(location.getLongitude())))
                    && (Math.round(report.getLatitudeProperty().get())
                    == Math.round(location.getLatitude()))
                    && (yearRecorded.equals(year))) {
                if ("01".equals(report.getDateTimeProperty()
                        .getValue().substring(6, 8))) {
                    if ("Virus PPM".equals(type)) {
                        virusJan = virusJan
                                + report.getVirusPPMProperty().getValue();
                        virusJanCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusJan = virusJan
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusJanCount++;
                    }
                } else if ("02".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusFeb = virusFeb + report
                                .getVirusPPMProperty()
                                .getValue();
                        virusFebCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusFeb = virusFeb + report
                                .getContaminantPPMProperty()
                                .getValue();
                        virusFebCount++;
                    }
                } else if ("03"
                        .equals(report.getDateTimeProperty()
                                .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusMar = virusMar
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusMarCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusMar = virusMar
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusMarCount++;
                    }
                } else if ("04".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusApr = virusApr
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusAprCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusApr = virusApr
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusAprCount++;
                    }
                } else if ("05".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {

                    if ("Virus PPM".equals(type)) {
                        virusMay = virusMay
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusMayCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusMay = virusMay
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusMayCount++;
                    }
                } else if ("06".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusJun = virusJun
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusJunCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusJun = virusJun
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusJunCount++;
                    }
                } else if ("07".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusJul = virusJul
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusJulCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusJul = virusJul
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusJulCount++;
                    }
                } else if ("08".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusAug = virusAug
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusAugCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusAug = virusAug
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusAugCount++;
                    }
                } else if ("09".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusSept = virusSept
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusSeptCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusSept = virusSept
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusSeptCount++;
                    }
                } else if ("10".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusOct = virusOct
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusOctCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusOct = virusOct
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusOctCount++;
                    }
                } else if ("11".equals(report.getDateTimeProperty()
                        .getValue().substring(5, 7))) {
                    if ("Virus PPM".equals(type)) {
                        virusNov = virusNov
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusNovCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusNov = virusNov
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusNovCount++;
                    }
                } else {
                    if ("Virus PPM".equals(type)) {
                        virusDec = virusDec
                                + report.getVirusPPMProperty()
                                .getValue();
                        virusDecCount++;
                    } else if ("Contaminant PPM".equals(type)) {
                        virusDec = virusDec
                                + report.getContaminantPPMProperty()
                                .getValue();
                        virusDecCount++;
                    }
                }
            }
        }
        if (virusJanCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("January", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("January",
                            (virusJan / virusJanCount)));
        }
        if (virusFebCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("February", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("February",
                            (virusFeb / virusFebCount)));
        }
        if (virusMarCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("March", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("March",
                            (virusMar / virusMarCount)));
        }
        if (virusAprCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("April", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("April",
                            (virusApr / virusAprCount)));
        }
        if (virusMayCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("May", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("May",
                            (virusMay / virusMayCount)));
        }
        if (virusJunCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("June", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("June",
                            (virusJun / virusJunCount)));
        }
        if (virusJulCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("July", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("July",
                            (virusJul / virusJulCount)));
        }
        if (virusAugCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("August", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("August",
                            (virusAug / virusAugCount)));
        }
        if (virusSeptCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("September", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("September",
                            (virusSept / virusSeptCount)));
        }
        if (virusOctCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("October", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("October",
                            (virusOct / virusOctCount)));
        }
        if (virusNovCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("November", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("November",
                            (virusNov / virusNovCount)));
        }
        if (virusDecCount == 0) {
            series.getData()
                    .add(new XYChart.Data<>("December", 0));
        } else {
            series.getData()
                    .add(new XYChart.Data<>("December",
                            (virusDec / virusDecCount)));
        }

        return series;
    }
}
