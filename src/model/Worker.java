package model;
/**
 * Worker Class
 * @author Dong Son Trinh
 */
public class Worker extends User {

    /**
     * empty constructor
     */
    public Worker() {
        super();
    }
    /**
     * Contructor for a Worker authorized user
     * @param username username
     * @param password password
     */
    public Worker(String username, String password) {
        super(username, password);
    }
    /**
     * @param username username of worker
     * @param password password of worker
     * @param name name of worker
     * @param title title of worker
     * @param email email of worker
     * @param address address of worker
     * @param accountLocked accountLocked
     * @param userBanned userBanned
     */
    public Worker(String username, String password, String name,
                  String title, String email, String address, int accountLocked, int userBanned) {
        super(username, password, name, title, email, address, accountLocked, userBanned);
    }
    /**
     * @param username username of worker
     * @param password password of worker
     * @param profile profile of worker
     */
    public Worker(String username, String password, Profile profile) {
        super(username, password, profile);
    }
}