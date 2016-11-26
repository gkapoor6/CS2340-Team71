package model;

/**
 * AuthorizedUser Class which is an ancestor class of User, Manager,
 * Worker and Admin
 * @author Dong Son Trinh
 */
@SuppressWarnings("ClassWithTooManyDependents")
public abstract class AuthorizedUser {
    private String username;
    private String password;
    private Profile profile;

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * password setter
     * @param password password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    /**
     * Returns username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * Returns the password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * Return user's profile object
     * @return profile object
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * empty constructor
     */
    AuthorizedUser() {

    }
    /**
     * Constructor for an Authorized User
     * @param username username
     * @param password password
     */
    AuthorizedUser(String username, String password) {
        this(username, password, null, null, null, null);
    }
    /**
     * @param username username of user
     * @param password password of user
     * @param name name of user
     * @param title title of user
     * @param email email of user
     * @param address address of user
     */
    AuthorizedUser(String username, String password, String name,
                   String title, String email, String address) {
        this(username, password,
                new Profile(name, title, email, address));
    }
    /**
     * @param username username of user
     * @param password password of user
     * @param profile profile of user
     */
    AuthorizedUser(String username, String password, Profile profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
    @Override
    public String toString() {
        return this.username + " " + this.password;
    }
}