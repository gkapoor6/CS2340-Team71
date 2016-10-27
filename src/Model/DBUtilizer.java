package Model;

import java.sql.*;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Connector to the sqlite database
 * @author Dong Son Trinh
 *
 */
/**
 * @author Dong Son Trinh
 *
 */
/**
 * @author Dong Son Trinh
 *
 */
/**
 * @author Dong Son Trinh
 *
 */
public class DBUtilizer {
	
	private static final String jdbcDriver = "org.sqlite.JDBC";
	
	private static final String dbName = "jdbc:sqlite:reports.db";
	
	private static Connection conn = null;
	
	/**
	 * Create Water Source Report Table
	 */
	public static void dbCreateSourceReports() {
		Statement stmt = null;
		try {
			dbConnect();

			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Database error occured");
			e.printStackTrace();
		}

		try {
			stmt.execute("CREATE TABLE WaterSourceReportTable"
					+ "(ReportID INT PRIMARY KEY NOT NULL,"
					+ " Name TEXT NOT NULL,"
					+ " WaterType TEXT NOT NULL,"
					+ " WaterCondition TEXT NOT NULL,"
					+ " Latitude REAL,"
					+ " Longitude REAL,"
					+ " DateTime TEXT NOT NULL"
					+ " )");
		} catch (SQLException e) {
			System.out.println("WaterSourceReport Table Already exists.");
		} finally {
			if (stmt != null) {
				try {

					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			dbDisconnect();
		}
	}
	
	
	/**
	 *	Create Users Table
	 */
	public static void dbCreateUsers() {
		Statement stmt = null;
		try {
			dbConnect();
			
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("database error");
		}
		
		try {
			stmt.execute("CREATE TABLE Users"
					+ "(Username TEXT NOT NULL,"
					+ " Password TEXT NOT NULL,"
					+ " Type TEXT NOT NULL,"
					+ " Name TEXT,"
					+ " Title TEXT,"
					+ " Email TEXT,"
					+ " Address TEXT"
					+ " )");
		} catch (SQLException e) {
			System.out.println("All good. User base already exists");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			dbDisconnect();
		}
	}

	/**
	 * Create Water Purity Report Table
	 */
	public static void dbCreatePurityReports() {
		Statement stmt = null;
		try {
			dbConnect();

			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Database error occurred");
			e.printStackTrace();
		}

		try {
			stmt.execute("CREATE TABLE WaterPurityReportTable"
					+ "(ReportID INT PRIMARY KEY NOT NULL,"
					+ " Name TEXT NOT NULL,"
					+ " VirusPPM REAL NOT NULL,"
                    + " ContaminantPPM REAL NOT NULL,"
					+ " OverallCondition TEXT NOT NULL,"
					+ " Latitude REAL,"
					+ " Longitude REAL,"
					+ " DateTime TEXT NOT NULL"
					+ " )");
		} catch (SQLException e) {
			System.out.println("WaterPurityReport Table already exists.");
		} finally {
			if (stmt != null) {
				try {

					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			dbDisconnect();
		}
	}
	/**
	 * Connects to the database
	 */
	public static void dbConnect() {
		try {
			Class.forName(jdbcDriver);
			
			conn = DriverManager.getConnection(dbName);
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the jdbc driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Failed to connect to the database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Disconnects from the database
	 */
	public static void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Could not disconnect");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gives back a result set from the query
	 * @param queryStmt query statement/string
	 * @return result of the query
	 * @throws SQLException thrown if failed to execute the query/disconnect
	 */
	public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
		Statement stmt = null;
		ResultSet rst = null;
		CachedRowSetImpl crs = null;
		try {
			dbConnect();
			
			stmt = conn.createStatement();
			rst = stmt.executeQuery(queryStmt);
			
			crs = new CachedRowSetImpl();
			crs.populate(rst);
			
		} catch (SQLException e) {
			System.out.println("Failed to execute the query");
			throw e;
		} finally {
			if (rst != null) {
				try {
					rst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			dbDisconnect();
		}
		return crs;
	}
	
	/**
	 * Update the database
	 * @param updateStmt update statement/String
	 * @throws SQLException thrown if failed to execute the update/disconnect
	 */
	public static void dbExecuteUpdate(String updateStmt) throws SQLException {
		Statement stmt = null;
		try {
			dbConnect();
			stmt = conn.createStatement();
			
			stmt.executeUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.println("Failed to execute the update");
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbDisconnect();
		}
	}
}
