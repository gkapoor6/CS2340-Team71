package model;

/**
 * Manager Class
 * @author Dong Son Trinh
 */
public class Manager extends Worker {

    /**
     * empty constructor
     */
    public Manager() {
        super();
    }
    /**
     * Contructor for a Manager authorized user
     * @param username username
     * @param password password
     */
    public Manager(String username, String password) {
        super(username, password);
    }
    /**
     * @param username username of manager
     * @param password password of manager
     * @param name name of manager
     * @param title title of manager
     * @param email email of manager
     * @param address address of manager
     * @param accountLocked accountLocked
     * @param userBanned userBanned
     */
    public Manager(String username, String password, String name, String title,
                   String email, String address, int accountLocked, int userBanned) {
        super(username, password, name, title, email, address, accountLocked, userBanned);
    }
    /**
     * @param username username of manager
     * @param password password of manager
     * @param profile profile of manager
     */
    public Manager(String username, String password, Profile profile) {
        super(username, password, profile);
    }
}