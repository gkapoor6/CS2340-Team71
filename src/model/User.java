package model;

/**
 * User Class
 * @author Dong Son Trinh
 */
public class User extends AuthorizedUser {

    /**
     * empty constructor
     */
    public User() {
        super();
    }
    /**
     * Contructor for a User authorized user
     * @param username username
     * @param password password
     */
    public User(String username, String password) {
        super(username, password);
    }
    /**
     * @param username username of user
     * @param password password of user
     * @param name name of user
     * @param title title of user
     * @param email email of user
     * @param address address of user
     * @param accountLocked accountLocked
     * @param userBanned userBanned
     */
    public User(String username, String password, String name,
                String title, String email, String address, int accountLocked, int userBanned) {
        super(username, password, name, title, email, address, accountLocked, userBanned);
    }
    /**
     * @param username username of user
     * @param password password of user
     * @param profile profile of user
     */
    public User(String username, String password, Profile profile) {
        super(username, password, profile);
    }
}