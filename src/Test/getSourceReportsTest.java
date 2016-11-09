package Test;

import org.junit.Before;
import org.junit.Test;

import Model.DBUtilizer;
import Model.ReportDBAccess;
import Model.WaterSourceReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Test class for {@link Model.ReportDBAccess#getReportList(String)()}.
 * @author Dong Son Trinh
 * @version 1.1
 */

public class getSourceReportsTest {
    
    /**
     * Test data
     */
    private ObservableList<WaterSourceReport> setupObsList;
    private ObservableList<WaterSourceReport> testList;
    private ArrayList<WaterSourceReport> setupList;
    private static final int INITIAL_CAPACITY = 7;
    private static final double DELTA = 1e-15;

    private static final String ONE_NAME = "MyName";
    private static final String WRONG_NAME = "NotMyName";

    /**
     * Initialize a list containing 7 reports "in" the database
     */
    private void initializeSetupList() {
        WaterSourceReport r;
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            r = new WaterSourceReport();
            r.getLatitudeProperty().set(i);
            r.getLongitudeProperty().set(i);
            r.getNameProperty().set(ONE_NAME);
            r.getWaterConditionProperty().set(String.format("Condition%d", i));
            r.getWaterTypeProperty().set(String.format("Type%d", i));
            setupList.add(r);
        }
    }

    /**
     * Set up the data for testing
     * @throws SQLException
     */
    @Before
    public void setUp() throws SQLException {
        setupList = new ArrayList<>();
        initializeSetupList();
        setupObsList = FXCollections.observableArrayList(setupList);
        DBUtilizer.dbCreateSourceReports();
        DBUtilizer.dbExecuteUpdate("DELETE FROM WaterSourceReportTable");
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            WaterSourceReport r = setupList.get(i);
            ReportDBAccess.insertReport(r.getNameProperty().get(), r.getWaterTypeProperty().get(),
                    r.getWaterConditionProperty().get(), r.getLatitudeProperty().get(), r.getLongitudeProperty().get());
        }
    }

    /**
     * Test that the size of Report List is correct before and after adding
     * A report
     */
    @Test
    public void testSize() {
        testList = ReportDBAccess.getReportList(ONE_NAME);
        assertEquals("Wrong size", testList.size(), setupObsList.size());
        WaterSourceReport r = new WaterSourceReport();
        r.getLatitudeProperty().set(INITIAL_CAPACITY);
        r.getLatitudeProperty().set(INITIAL_CAPACITY);
        r.getNameProperty().set(ONE_NAME);
        r.getWaterConditionProperty().set(String.format("Condition%d", INITIAL_CAPACITY));
        r.getWaterTypeProperty().set(String.format("Type%d", INITIAL_CAPACITY));
        ReportDBAccess.insertReport(r.getNameProperty().get(), r.getWaterTypeProperty().get(),
                r.getWaterConditionProperty().get(), r.getLatitudeProperty().get(), r.getLongitudeProperty().get());
        setupObsList.add(r);

        testList = ReportDBAccess.getReportList(ONE_NAME);
        assertEquals("Wrong size after adding", testList.size(), setupObsList.size());
        
        testList = ReportDBAccess.getReportList(WRONG_NAME);
        assertEquals("Size should be 0", testList.size(), 0);
    }

    /**
     * Test that reports are same without adding any additional ones
     */
    @Test
    public void testInitReports() {
        testList = ReportDBAccess.getReportList(ONE_NAME);
        WaterSourceReport init;
        WaterSourceReport dbInit;
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            init = setupObsList.get(i);
            dbInit = testList.get(i);

            compare2Reports(init, dbInit);
        }
    }

    /**
     * Test that an added report is recorded and is retrieved correctly
     */
    @Test
    public void testAddReport() {
        WaterSourceReport r = new WaterSourceReport();
        r.getLatitudeProperty().set(INITIAL_CAPACITY);
        r.getLatitudeProperty().set(INITIAL_CAPACITY);
        r.getNameProperty().set(ONE_NAME);
        r.getWaterConditionProperty().set(String.format("Condition%d", INITIAL_CAPACITY));
        r.getWaterTypeProperty().set(String.format("Type%d", INITIAL_CAPACITY));
        
        testList = ReportDBAccess.getReportList(ONE_NAME);
        assertEquals("A report that should not exist is detected", testList.indexOf(r), -1);
        
        ReportDBAccess.insertReport(r.getNameProperty().get(), r.getWaterTypeProperty().get(),
                r.getWaterConditionProperty().get(), r.getLatitudeProperty().get(), r.getLongitudeProperty().get());
        setupObsList.add(r);

        testList = ReportDBAccess.getReportList(ONE_NAME);
        
        compare2Reports(testList.get(INITIAL_CAPACITY), r);
    }

    /**
     * Compare 2 water source reports
     * Do not compare time because it is auto-generated
     * @param first first report
     * @param second second report
     */
    private void compare2Reports(WaterSourceReport first, WaterSourceReport second) {
        assertEquals("Different Latitudes", first.getLatitudeProperty().get(), second.getLatitudeProperty().get(),
                DELTA);
        assertEquals("Different Longitudes", first.getLongitudeProperty().get(), second.getLongitudeProperty().get(),
                DELTA);
        assertEquals("Different Water Conditions", first.getWaterConditionProperty().get(),
                second.getWaterConditionProperty().get());
        assertEquals("Different Water Types", first.getWaterTypeProperty().get(), second.getWaterTypeProperty().get());
        assertEquals("Different Names", first.getNameProperty().get(), second.getNameProperty().get());
    }
}
