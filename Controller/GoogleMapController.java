package Controller;

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
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

import FXApp.MainFXApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class GoogleMapController implements Initializable, MapComponentInitializedListener {

	private MainFXApp mainApp;
	
	@FXML
	private TextField addressField;
	
	@FXML
	private GoogleMapView mapView;
	
	private GoogleMap map;

	private GeocodingService geocodingService;
	
	private int locationNum = 0;
	private String locationSearch = "";
	private StringProperty address = new SimpleStringProperty();
	
	public void setMain(MainFXApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mapView.addMapInializedListener(this);
		address.bind(addressField.textProperty());
	}
	
	@Override
	public void mapInitialized() {
		
		geocodingService = new GeocodingService();
        
        
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(47.6097, -122.3331))
        	.mapType(MapTypeIdEnum.ROADMAP)
	        .overviewMapControl(false)
	        .panControl(false)
	        .rotateControl(false)
	        .scaleControl(false)
	        .streetViewControl(false)
	        .zoomControl(false)
	        .zoom(12);
        
		map = mapView.createMap(mapOptions);
	}
	
	@FXML
    public void addressTextFieldAction(ActionEvent event) {
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {
        	if (!address.get().equals(locationSearch)) {
        		locationNum = 0;
        		locationSearch = address.get();
        	}
            LatLong latLong = null;
            
            if (status == GeocoderStatus.ZERO_RESULTS) {
            	Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                alert.show();
                return;
            } else if (results.length > 1 ) {
                latLong = new LatLong(results[locationNum].getGeometry().getLocation().getLatitude(), results[locationNum].getGeometry().getLocation().getLongitude());
                locationNum = (locationNum + 1) % results.length;
                System.out.println(locationNum);
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
                System.out.println(results.length);
            }
            
            map.setCenter(latLong);

        });
    }
}
