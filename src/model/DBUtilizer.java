package model;

import java.sql.*;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Connector to the sqlite database
 * @author Dong Son Trinh
 *
 */

class DBUtilizer {
    private static final String jdbcDriver = "org.sqlite.JDBC";
    private static final String dbName = "jdbc:sqlite:ProjectDatabase.db";
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
            assert stmt != null;
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
     * Create UsersTable Table
     */
    public static void dbCreateUsers() {
        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("database error");
            e.printStackTrace();
        }

        try {
            assert stmt != null;
            //stmt.execute("DROP TABLE UsersTable");
            stmt.execute("CREATE TABLE UsersTable"
                    + "(Username TEXT NOT NULL,"
                    + " Password TEXT NOT NULL,"
                    + " Type TEXT NOT NULL,"
                    + " Name TEXT,"
                    + " Title TEXT,"
                    + " Email TEXT,"
                    + " Address TEXT,"
                    + " AccountBlocked INTEGER NOT NULL,"
                    + " BanUser INTEGER NOT NULL"
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
            assert stmt != null;
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
    private static void dbConnect() {
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
    private static void dbDisconnect() {
        try {
            if ((conn != null) && (!conn.isClosed())) {
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
    public static ResultSet dbExecuteQuery(String queryStmt)
            throws SQLException {
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
        //return rst;
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