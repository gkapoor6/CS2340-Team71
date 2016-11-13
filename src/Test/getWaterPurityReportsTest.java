package Test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DBUtilizer;
import model.ReportDBAccess;
import model.WaterPurityReport;

/**
 * Test class for {@link model.ReportDBAccess#getWaterPurityReportList()}.
 * @author Md Irtiza Hafiz
 * @version 1.0
 */

public class getWaterPurityReportsTest {
    /**
     * Test Data
     */
    private ObservableList<WaterPurityReport> setupObsList;
    private ObservableList<WaterPurityReport> testList;
    private ArrayList<WaterPurityReport> setupList;
    private static final int INITIAL_CAPACITY = 5;
    private static final double DELTA = 1e-15;
    
    private static final String ONE_NAME = "MyName";
    
   
    /**
     * Initialize a list containing 5 water purity reports "in" the database
     */
    private void initializeSetupList() {
        WaterPurityReport p;
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            p = new WaterPurityReport(0, null, 0, 0, null, null, 0, 0);
            p.getNameProperty().set(ONE_NAME);
            p.getVirusPPMProperty().set(i);
            p.getContaminantPPMProperty().set(i);
            p.getOverallConditionProperty().set(String.format("Condition%d", i));
            p.getLatitudeProperty().set(i);
            p.getLongitudeProperty().set(i);
            setupList.add(p);
        }
    }
    
    /**
     * Set up the data before testing
     * @throws SQLException
     */
    @Before
    public void setUp() throws SQLException {
        setupList = new ArrayList<>();
        initializeSetupList();
        setupObsList = FXCollections.observableArrayList(setupList);
        DBUtilizer.dbCreatePurityReports();
        DBUtilizer.dbExecuteUpdate("DELETE FROM WaterPurityReportTable");
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            WaterPurityReport p = setupList.get(i);
            ReportDBAccess.insertPurityReport(p.getNameProperty().get(), p.getVirusPPMProperty().get(),
                    p.getContaminantPPMProperty().get(), p.getOverallConditionProperty().get(),
                    p.getLatitudeProperty().get(), p.getLongitudeProperty().get());
        }
    }
    
    /**
     * Test that the size of Water Purity Report List is correct before and after adding purity report
     */
    @Test
    public void testSize() {
        testList = ReportDBAccess.getWaterPurityReportList();
        WaterPurityReport p = new WaterPurityReport(0, null, 0, 0, null, null, 0, 0);
        p.getNameProperty().set(ONE_NAME);
        p.getVirusPPMProperty().set(INITIAL_CAPACITY);
        p.getContaminantPPMProperty().set(INITIAL_CAPACITY);
        p.getOverallConditionProperty().set(String.format("Condition%d", INITIAL_CAPACITY));
        p.getLatitudeProperty().set(INITIAL_CAPACITY);
        p.getLongitudeProperty().set(INITIAL_CAPACITY);
        ReportDBAccess.insertPurityReport(p.getNameProperty().get(), p.getVirusPPMProperty().get(),
                p.getContaminantPPMProperty().get(), p.getOverallConditionProperty().get(),
                p.getLatitudeProperty().get(), p.getLongitudeProperty().get());
        setupObsList.add(p);
        
        testList = ReportDBAccess.getWaterPurityReportList();
        assertEquals("Wrong size after adding", testList.size(), setupObsList.size());
    }
    
    /**
     * Test that purity reports are the same without any additional ones
     */
    @Test
    public void testInitReports() {
        testList = ReportDBAccess.getWaterPurityReportList();
        WaterPurityReport init;
        WaterPurityReport dbInit;
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            init = setupObsList.get(i);
            dbInit = testList.get(i);
            compare2Reports(init, dbInit);
        }
    }
    
    /**
     * Test that an added report is stored and obtained correctly
     */
    @Test
    public void testAddReport() {
        WaterPurityReport p = new WaterPurityReport(0, null, 0, 0, null, null, 0, 0);
        p.getLatitudeProperty().set(INITIAL_CAPACITY);
        p.getLongitudeProperty().set(INITIAL_CAPACITY);
        p.getNameProperty().set(ONE_NAME);
        p.getContaminantPPMProperty().set(INITIAL_CAPACITY);
        p.getVirusPPMProperty().set(INITIAL_CAPACITY);
        p.getOverallConditionProperty().set(String.format("Condition%d", INITIAL_CAPACITY));
        
        testList = ReportDBAccess.getWaterPurityReportList();
        assertEquals("A report that should not exist is detected", testList.indexOf(p), -1);
        
        ReportDBAccess.insertPurityReport(p.getNameProperty().get(), p.getVirusPPMProperty().get(),
                p.getContaminantPPMProperty().get(), p.getOverallConditionProperty().get(),
                p.getLatitudeProperty().get(), p.getLongitudeProperty().get());
        setupObsList.add(p);
        
        testList = ReportDBAccess.getWaterPurityReportList();
        
        compare2Reports(testList.get(INITIAL_CAPACITY), p);
    }
 
    /**
     * Compare 2 water purity reports
     * Do not compare date-time because it is auto-generated
     * @param first First water purity report
     * @param second Second water purity report
     */
    private void compare2Reports(WaterPurityReport first, WaterPurityReport second) {
        assertEquals("Different Latitudes", first.getLatitudeProperty().get(), second.getLatitudeProperty().get(), DELTA);
        assertEquals("Different Longitudes", first.getLongitudeProperty().get(), second.getLongitudeProperty().get(), DELTA);
        assertEquals("Different Overall Conditions", first.getOverallConditionProperty().get(), second.getOverallConditionProperty().get());
        assertEquals("Different Contaminant PPM", first.getContaminantPPMProperty().get(), second.getContaminantPPMProperty().get(), DELTA);
        assertEquals("Different Virus PPM", first.getVirusPPMProperty().get(), second.getVirusPPMProperty().get(), DELTA);
        assertEquals("Different Names", first.getNameProperty().get(), second.getNameProperty().get());
    }
}