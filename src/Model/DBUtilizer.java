package Model;

import java.sql.*;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Connector to the sqlite database
 * @author Dong Son Trinh
 *
 */
public class DBUtilizer {
	
	private static final String jdbcDriver = "org.sqlite.JDBC";
	
	private static final String dbName = "jdbc:sqlite:reports.db";
	
	private static Connection conn = null;
	
	/**
	 * Delete existing WaterSourceReport and/or create a table WaterSourceReportTable 
	 * @throws SQLException if failed to create a table
	 */
	public static void dbCreate() throws SQLException {
		Statement stmt = null;
		try {
			dbConnect();
			
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("failed to connect");
			e.printStackTrace();
		}
		
		try {
			String delete = "DROP TABLE WaterSourceReportTable";
			
			stmt.execute(delete);
		} catch (SQLException e) {
			
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
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
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
		} catch (Exception e) {
			System.out.println("Could not disconnect");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gives back a result set from the query
	 * @param queryStmt query statement/string
	 * @return result of the query
	 * @throws SQLException thrown if failed to execute the query/disconnect from database/close the resultset
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
			e.printStackTrace();
		} finally {
			if (rst != null) {
				rst.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			dbDisconnect();
		}
		return crs;
	}
	
	/**
	 * Update the database
	 * @param updateStmt update statement/String
	 * @throws SQLException thrown if failed to execute the update/disconnect from database/close the statement
	 */
	public static void dbExecuteUpdate(String updateStmt) throws SQLException {
		Statement stmt = null;
		try {
			dbConnect();
			stmt = conn.createStatement();
			
			stmt.executeUpdate(updateStmt);
		} catch (SQLException e) {
			System.out.println("Failed to execute the update");
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			dbDisconnect();
		}
	}
}
