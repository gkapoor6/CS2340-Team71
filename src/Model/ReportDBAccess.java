package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Higher level connector to sqlite database implementing it with respect to columns needed for the application
 * @author Dong Son Trinh
 *
 */
public class ReportDBAccess {
	
	
	/**
	 * Creates a table
	 */
	public static void createTable() {
		try {
			DBUtilizer.dbCreate();
		} catch (SQLException e) {
			System.out.println("failed to create the table");
			e.printStackTrace();
		}
	}

	/**
	 * Looks up the name in the database and gets all reports submitted by him
	 * @param name name of user
	 * @return an observable list of water source reports submitted by user
	 */
	public static ObservableList<WaterSourceReport> getReportList(String name) {
		ObservableList<WaterSourceReport> list = FXCollections.observableArrayList();
		String stmt = "SELECT * FROM WaterSourceReportTable WHERE Name = '" + name + "'";
		try {
			ResultSet rs = DBUtilizer.dbExecuteQuery(stmt);
			while (rs.next()) {
				WaterSourceReport report = new WaterSourceReport(rs.getInt("ReportID"), rs.getString("Name"),
						rs.getString("WaterType"), rs.getString("WaterCondition"), rs.getString("DateTime"),
						rs.getDouble("Latitude"), rs.getDouble("Longitude"));
				list.add(report);
			}
		} catch (SQLException e) {
			System.out.println("SQL select failed");
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Add a report to the database
	 * @param name name of submitter
	 * @param waterType water type
	 * @param waterCondition condition of water
	 * @param latitude latitude of location
	 * @param longtitude longitude of location
	 */
	public static void insertReport(String name, String waterType, String waterCondition,
			double latitude, double longtitude) {
		try {
			ResultSet rs = DBUtilizer.dbExecuteQuery("SELECT MAX(ReportID) from WaterSourceReportTable");
			int ID;
			if (rs.next()) {
				ID = rs.getInt("MAX(ReportID)") + 1; 
			} else {
				ID = 1;
			}
			String stmt = String.format(Locale.US, "INSERT INTO WaterSourceReportTable"
					+ " (ReportID, Name, WaterType, WaterCondition, Latitude, Longitude, DateTime) "
					+ "VALUES (%d, '%s', '%s', '%s', %f, %f, '%s');",
					ID, name, waterType, waterCondition, latitude, longtitude, getTime());

			DBUtilizer.dbExecuteUpdate(stmt);
		} catch (SQLException e) {
			System.out.println("SQL insert failed");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gets current time in GMT-4 Timezone or EST
	 * @return
	 */
	private static String getTime() {
		ZoneId zone = ZoneId.of("GMT-4");
		ZonedDateTime zonedt = ZonedDateTime.now(zone);
		return zonedt.format(DateTimeFormatter.ISO_LOCAL_DATE) + " "
				+ zonedt.format(DateTimeFormatter.ISO_LOCAL_TIME).split("[.]")[0];
	}
}
