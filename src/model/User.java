package model;

/**
 * User Class
 * @author Dong Son Trinh
 */
public class User extends AuthorizedUser {
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
     */
    public User(String username, String password, String name,
            String title, String email, String address) {
        super(username, password, name, title, email, address);
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