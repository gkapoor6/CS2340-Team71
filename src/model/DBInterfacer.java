package model;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Higher level connector to sqlite database implementing it with respect
 * to columns needed for the application
 * @author Dong Son Trinh
 *
 */
public class DBInterfacer {
    public static void createSourceReportTable() {
        DBUtilizer.dbCreateSourceReports();
    }
    public static void createUserTable() {
        DBUtilizer.dbCreateUsers();
    }
    public static void createPurityTable() {
        DBUtilizer.dbCreatePurityReports();
    }
    /**
     * Looks up the name in the database and gets all reports submitted by him
     * @param name name of user
     * @return an observable list of water source reports submitted by user
     */
    public static ObservableList<WaterSourceReport> getSourceReportList(String name) {
        ObservableList<WaterSourceReport> list =
                FXCollections.observableArrayList();
        ResultSet rs = null;
        try {
            String finduser = "SELECT * FROM WaterSourceReportTable"
                    + " WHERE Name = '" + name + "'";
            rs = DBUtilizer.dbExecuteQuery(finduser);
            WaterSourceReport report;
            while (rs.next()) {
                report = new WaterSourceReport(
                        rs.getInt("ReportID"),
                        rs.getString("Name"),
                        rs.getString("WaterType"),
                        rs.getString("WaterCondition"),
                        rs.getString("DateTime"),
                        rs.getDouble("Latitude"),
                        rs.getDouble("Longitude"));
                list.add(report);
            }
        } catch (SQLException e) {
            System.out.println("Could not get reports with the Name");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    /**
     * Gets all the water purity reports submitted by all users
     * @return an observable list of water purity reports submitted by user
     */
    public static ObservableList<WaterPurityReport>
            getPurityReportList() {
        ObservableList<WaterPurityReport> list =
                FXCollections.observableArrayList();
        ResultSet rs = null;
        try {
            String reports = "SELECT * FROM WaterPurityReportTable";
            rs = DBUtilizer.dbExecuteQuery(reports);
            WaterPurityReport report;
            while (rs.next()) {
                report = new WaterPurityReport(
                        rs.getInt("ReportID"),
                        rs.getString("Name"),
                        rs.getDouble("VirusPPM"),
                        rs.getDouble("ContaminantPPM"),
                        rs.getString("OverallCondition"),
                        rs.getString("DateTime"),
                        rs.getDouble("Latitude"),
                        rs.getDouble("Longitude"));
                list.add(report);
            }
        } catch (SQLException e) {
            System.out.println("Could not get any reports "
                    + "corresponding to the name");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    /**
     * Add a purity report to the database
     * @param name name of the submitter
     * @param virusPPM contaminantPPM
     * @param contaminantPPM contaminantPPM
     * @param overallCondition condition of water
     * @param latitude latitude of location
     * @param longitude longitude of location
     */
    public static void insertPurityReport(String name,
                                          double virusPPM,
                                          double contaminantPPM,
                                          String overallCondition,
                                          double latitude,
                                          double longitude) {
        ResultSet rs = null;
        try {
            rs = DBUtilizer.dbExecuteQuery("SELECT MAX(ReportID) from "
        + "WaterPurityReportTable");
            int ID;
            if (rs.next()) {
                ID = rs.getInt("MAX(ReportID)") + 1;
            } else {
                ID = 1;
            }
            String insertReport = String.format(
                    Locale.US, "INSERT INTO WaterPurityReportTable"
                            + " (ReportID, Name, VirusPPM, ContaminantPPM,"
                            + " OverallCondition, Latitude, "
                            + "Longitude, DateTime) "
                            + "VALUES (%d, '%s', '%f', '%f', '%s', %f,"
                            + " %f, '%s');",
                            ID, name, virusPPM, contaminantPPM,
                            overallCondition, latitude,
                            longitude, getTime());
            DBUtilizer.dbExecuteUpdate(insertReport);
        } catch (SQLException e) {
            System.out.println("Failed to insert report");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Add a purity report to the database
     * @param name name of the submitter
     * @param waterType water type
     * @param waterCondition condition of water
     * @param latitude latitude of location
     * @param longtitude longitude of location
     */
    public static boolean insertSourceReport(String name, String waterType,
                                    String waterCondition,
                                    double latitude, double longtitude) {
        ResultSet rs = null;
        try {
            rs = DBUtilizer.dbExecuteQuery("SELECT MAX(ReportID)"
                    + " from WaterSourceReportTable");
            int ID;
            if (rs.next()) {
                ID = rs.getInt("MAX(ReportID)") + 1;
            } else {
                ID = 1;
            }
            String insertreport = String.format(
                    Locale.US, "INSERT INTO WaterSourceReportTable"
                            + " (ReportID, Name, WaterType, WaterCondition,"
                            + " Latitude, Longitude, DateTime) "
                            + "VALUES (%d, '%s', '%s', '%s', %f, %f, '%s');",
                            ID, name, waterType, waterCondition, latitude,
                            longtitude, getTime());
            DBUtilizer.dbExecuteUpdate(insertreport);
        } catch (SQLException e) {
            System.out.println("failed to insert report");
            e.printStackTrace();
            return false;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /**
     * Insert a user
     * @param username username of user
     * @param password password of user
     * @param usertype user type
     * @return result of insertion
     */
    public static boolean insertUser(String username,
            String password, String usertype) {
        ResultSet rs = null;
        try {
            rs = DBUtilizer.dbExecuteQuery(String.format(Locale.US,
                    "SELECT * from Users WHERE Username = '%s'", username));
            if (!rs.next()) {
                String insertuser = String.format(Locale.US, "INSERT INTO Users"
                        + " (Username, Password, Type)"
                        + " VALUES ('%s', '%s', '%s')",
                        username, password, usertype);
                DBUtilizer.dbExecuteUpdate(insertuser);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Failed to insert User");
            e.printStackTrace();
            return false;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Delete a user
     * @param username user's username
     */
    public static void deleteUser(String username) {
        try {
            String deleteuser = String.format(
                    Locale.US, "DELETE FROM Users WHERE "
                            + "Username = '%s'", username);
            DBUtilizer.dbExecuteUpdate(deleteuser);
        } catch (SQLException e) {
            System.out.println("Failed to delete the user");
            e.printStackTrace();
        }
    }
    /**
     * Update profile of a user
     * @param name user's names
     * @param email user's email
     * @param title user's title
     * @param address user's address
     * @param username username of user who profile is updated
     */
    public static boolean updateProfile(String name, String email,
            String title, String address, String username) {
        try {
            String updateprofile = String.format(
                    Locale.US, "UPDATE Users set Name = '%s',"
                            + " Email = '%s',"
                            + " Title = '%s',"
                            + " Address = '%s'"
                            + " WHERE Username = '%s'",
                            name, email, title, address, username);
            DBUtilizer.dbExecuteUpdate(updateprofile);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to update profile");
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Get a user from database
     * @param username user's username
     * @param password user's password
     * @return user
     */
    public static AuthorizedUser getUser(String username, String password) {
        AuthorizedUser user = null;
        ResultSet rs = null;
        try {
            String getuser = String.format(
                    Locale.US, "SELECT * FROM Users WHERE Username = '%s'"
                            + " and Password = '%s'", username, password);
            rs = DBUtilizer.dbExecuteQuery(getuser);
            String type;
            String name;
            String title;
            String email;
            String address;
            if (rs.next()) {
                type = rs.getString("Type");
                name = rs.getString("Name");
                title = rs.getString("Title");
                email = rs.getString("Email");
                address = rs.getString("Address");
            } else {
                return user;
            }
            if (type != null) {
                user = (AuthorizedUser) Class.
                        forName(String.format("model.%s", type))
                        .getConstructor(String.class, String.class,
                                String.class,
                        String.class, String.class, String.class).newInstance(
                        username, password, name, email, title, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }
    /**
     * Gets current time in GMT-4 Timezone or EST
     * @return current time as a String
     */
    private static String getTime() {
        ZoneId zone = ZoneId.of("GMT-4");
        ZonedDateTime zonedt = ZonedDateTime.now(zone);
        return zonedt.format(DateTimeFormatter.ISO_LOCAL_DATE) + " "
            + zonedt.format(DateTimeFormatter.ISO_LOCAL_TIME)
        .split("[.]")[0];
    }
}