package model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Created by Geetika Kapoor on 9/28/2016.
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
     * Setters for the instance data
     */
    public void setName(String name) {
        this._name.set(name);
    }
    public void setEmail(String email) {
        this._email.set(email);
    }
    public void setTitle(String title) {
        this._title.set(title);
    }
    public void setHome(String home) {
        this._home.set(home);
    }
    /**
     * Getters for the instance data
     */
    public StringProperty getNameProperty() {
        return this._name;
    }
    public StringProperty getEmailProperty() {
        return this._email;
    }
    public StringProperty getTitleProperty() {
        return this._title;
    }
    public StringProperty getHomeProperty() {
        return this._home;
    }
    @Override
    public String toString() {
        return "Profile [name=" + this._name.get() + ", email=" + this._email.get()
            + ", title=" + this._title.get() + ", home=" + this._home.get() + "]";
    }
}