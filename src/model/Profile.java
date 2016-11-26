package model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * profile class for user profile
 */

public class Profile {
    /**
     * all instance data in StringProperty to connect to the view
     */
    private final StringProperty _name = new SimpleStringProperty();
    private final StringProperty _email = new SimpleStringProperty();
    private final StringProperty _title = new SimpleStringProperty();
    private final StringProperty _home = new SimpleStringProperty();
    /**
     * Constructor
     * @param name name
     * @param email email
     * @param title title
     * @param home home address
     */
    public Profile(String name, String email, String title, String home) {
        this._name.set(name);
        this._email.set(email);
        this._title.set(title);
        this._home.set(home);
    }
    /**
     * Constructor for profile of a newly registered user
     */
    public Profile() {
    }

    /**
     * Setter for name
     * @param name name
     */
    public void setName(String name) {
        this._name.set(name);
    }

    /**
     *Setter for email
     * @param email email
     */
    public void setEmail(String email) {
        this._email.set(email);
    }

    /**
     *Setter for title
     * @param title title
     */
    public void setTitle(String title) {
        this._title.set(title);
    }

    /**
     *Setter for home
     * @param home home
     */
    public void setHome(String home) {
        this._home.set(home);
    }

    /**
     * Getter for name
     * @return name
     */
    public StringProperty getNameProperty() {
        return this._name;
    }

    /**
     *Getter for email
     * @return email
     */
    public StringProperty getEmailProperty() {
        return this._email;
    }

    /**
     *Getter for title
     * @return title
     */
    public StringProperty getTitleProperty() {
        return this._title;
    }

    /**
     *Getter for home
     * @return home
     */
    public StringProperty getHomeProperty() {
        return this._home;
    }

    @Override
    public String toString() {
        return "Profile [name=" + this._name.get()
                + ", email=" + this._email.get()
                + ", title=" + this._title.get()
                + ", home=" + this._home.get() + "]";
    }
}