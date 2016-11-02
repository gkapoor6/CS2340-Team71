package Controller;

import FXApp.MainFXApp;
import Model.AuthorizedUser;
import Model.ReportDBAccess;
import Model.WaterPurityReport;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

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



/**
 * Created by swatimardia on 10/28/16.
 */
public class ViewWaterQualityHistoryGraph implements Initializable, MapComponentInitializedListener{

    /**
     * reference to mainApp
     */
    private MainFXApp mainApp;

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
    private StringProperty address = new SimpleStringProperty();


    /**
     * Setup the main application link so we can call methods there
     * @param main reference back to main class
     */
    public void setMainApp(MainFXApp main) {
        mainApp = main;
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

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Virus PPM");
        arrayList.add("Contaminant PPM");
        ObservableList<String> list = FXCollections.observableArrayList(arrayList);
        typePicked.setItems(list);
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
     * Goes to a location on the map using the text printed into the search field
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
            } else if (results.length > 1 ) {
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
        LineChart<String, Number> lineChart = new LineChart<String, Number>(
                xAxis, yAxis);
        //XYChart.Series series2 = new XYChart.Series();

        lineChart.setTitle("Purity History Graph");
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        if (year == null | location == null | type == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not enough information");
            alert.setContentText("Please complete all required information");
            alert.showAndWait();
        } else {
            ObservableList<WaterPurityReport> waterPurityReportList = ReportDBAccess.getWaterPurityReportList();

            //String month;
            double virusJan = 0.0, virusFeb = 0.0, virusMar = 0.0, virusApr = 0.0,
                    virusMay = 0.0, virusJun = 0.0, virusJul = 0.0, virusAug = 0.0,
                    virusSept = 0.0, virusOct = 0.0, virusNov = 0.0, virusDec = 0.0;
            int virusJanCount = 0, virusFebCount = 0,
                    virusMarCount = 0, virusAprCount = 0, virusMayCount = 0,
                    virusJunCount = 0, virusJulCount = 0, virusAugCount = 0,
                    virusSeptCount = 0, virusOctCount = 0, virusNovCount = 0, virusDecCount = 0;


            for (WaterPurityReport report : waterPurityReportList) {
                //System.out.print(location.getLatitude() instanceof IntegerProperty)
               // System.out.println(report.getDateTimeProperty().getValue());
                String yearRecorded = report.getDateTimeProperty().getValue().substring(0, 4);
                if ((Math.round(report.getLongitudeProperty().get()) == Math.round(location.getLongitude()))
                        && Math.round(report.getLatitudeProperty().get()) == Math.round(location.getLatitude())
                        && yearRecorded.equals(year)) {


                    if (report.getDateTimeProperty().getValue().substring(6, 8).equals("01")) {

                        //month = "January";

                        if (type.equals("Virus PPM")) {
                            virusJan = virusJan + report.getVirusPPMProperty().getValue();
                            virusJanCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusJan = virusJan + report.getContaminantPPMProperty().getValue();
                            virusJanCount++;
                        }


                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("02")) {
                        //month = "February";
                        if (type.equals("Virus PPM")) {
                            virusFeb = virusFeb + report.getVirusPPMProperty().getValue();
                            virusFebCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusFeb = virusFeb + report.getContaminantPPMProperty().getValue();
                            virusFebCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("03")) {
                        //month = "March";
                        if (type.equals("Virus PPM")) {
                            virusMar = virusMar + report.getVirusPPMProperty().getValue();
                            virusMarCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusMar = virusMar + report.getContaminantPPMProperty().getValue();
                            virusMarCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("04")) {
                        //month = "April";
                        if (type.equals("Virus PPM")) {
                            virusApr = virusApr + report.getVirusPPMProperty().getValue();
                            virusAprCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusApr = virusApr + report.getContaminantPPMProperty().getValue();
                            virusAprCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("05")) {
                        //month = "May";
                        if (type.equals("Virus PPM")) {
                            virusMay = virusMay + report.getVirusPPMProperty().getValue();
                            virusMayCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusMay = virusMay + report.getContaminantPPMProperty().getValue();
                            virusMayCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("06")) {
                        //month = "June";
                        if (type.equals("Virus PPM")) {
                            virusJun = virusJun + report.getVirusPPMProperty().getValue();
                            virusJunCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusJun = virusJun + report.getContaminantPPMProperty().getValue();
                            virusJunCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("07")) {
                        //month = "July";
                        if (type.equals("Virus PPM")) {
                            virusJul = virusJul + report.getVirusPPMProperty().getValue();
                            virusJulCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusJul = virusJul + report.getContaminantPPMProperty().getValue();
                            virusJulCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("08")) {
                        //month = "August";
                        if (type.equals("Virus PPM")) {
                            virusAug = virusAug + report.getVirusPPMProperty().getValue();
                            virusAugCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusAug = virusAug + report.getContaminantPPMProperty().getValue();
                            virusAugCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("09")) {
                        //month = "September";
                        if (type.equals("Virus PPM")) {
                            virusSept = virusSept + report.getVirusPPMProperty().getValue();
                            virusSeptCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusSept = virusSept + report.getContaminantPPMProperty().getValue();
                            virusSeptCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("10")) {
                        //month = "October";
                        if (type.equals("Virus PPM")) {
                            virusOct = virusOct + report.getVirusPPMProperty().getValue();
                            virusOctCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusOct = virusOct + report.getContaminantPPMProperty().getValue();
                            virusOctCount++;
                        }
                    } else if (report.getDateTimeProperty().getValue().substring(5, 7).equals("11")) {
                        //month = "November";
                        if (type.equals("Virus PPM")) {
                            virusNov = virusNov + report.getVirusPPMProperty().getValue();
                            virusNovCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusNov = virusNov + report.getContaminantPPMProperty().getValue();
                            virusNovCount++;
                        }
                    } else {
                        //month = "December";
                        if (type.equals("Virus PPM")) {
                            virusDec = virusDec + report.getVirusPPMProperty().getValue();
                            virusDecCount++;
                        } else if (type.equals("Contaminant PPM")) {
                            virusDec = virusDec + report.getContaminantPPMProperty().getValue();
                            virusDecCount++;
                        }
                    }

                }
            }
            if (virusJanCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("January", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("January", (virusJan / virusJanCount)));

            }
            if (virusFebCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("February", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("February", (virusFeb / virusFebCount)));
            }
            if (virusMarCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("March", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("March", (virusMar / virusMarCount)));
            }
            if (virusAprCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("April", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("April", (virusApr / virusAprCount)));

            }
            if (virusMayCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("May", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("May", (virusMay / virusMayCount)));
            }
            if (virusJunCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("June", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("June", (virusJun / virusJunCount)));
            }
            if (virusJulCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("July", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("July", (virusJul / virusJulCount)));
            }
            if (virusAugCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("August", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("August", (virusAug / virusAugCount)));
            }
            if (virusSeptCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("September", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("September", (virusSept / virusSeptCount)));
            }
            if (virusOctCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("October", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("October", (virusOct / virusOctCount)));
            }
            if (virusNovCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("November", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("November", (virusNov / virusNovCount)));
            }
            if (virusDecCount == 0) {
                series.getData().add(new XYChart.Data<String, Number>("December", 0));
            } else {
                series.getData().add(new XYChart.Data<String, Number>("December", (virusDec / virusDecCount)));
            }

            lineChart.getData().add(series);
            Scene scene = new Scene(lineChart, 800, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
}
