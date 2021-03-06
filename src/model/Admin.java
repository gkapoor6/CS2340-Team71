package model;

/**
 * Admin Class
 * @author Dong Son Trinh
 */
public class Admin extends AuthorizedUser {

    /**
     * empty constructor
     */
    public Admin() {
        super();
    }
    /**
     * Constructor for Admin class
     * @param username username
     * @param password password
     */
    public Admin(String username, String password) {
        super(username, password);
    }
    /**
     * Constructor for Admin class
     * @param username username
     * @param password password
     * @param name name
     * @param title title
     * @param email email
     * @param address address
     * @param accountLocked accountLocked
     * @param userBanned userBanned
     */
    public Admin(String username, String password, String name, String title,
                 String email, String address, int accountLocked, int userBanned) {
        super(username, password, name, title, email, address, accountLocked, userBanned);
    }
    /**
     * Constructor for Admin class
     * @param username username
     * @param password password
     * @param profile profile
     */
    public Admin(String username, String password, Profile profile) {
        super(username, password, profile);
    }
}