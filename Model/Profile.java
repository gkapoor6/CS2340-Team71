package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Geetika Kapoor on 9/28/2016.
 */
public class Profile {
    private StringProperty _name = new SimpleStringProperty();
    private StringProperty _email = new SimpleStringProperty();
    // title or type
    private StringProperty _title = new SimpleStringProperty();
    private StringProperty _home = new SimpleStringProperty();

    public Profile(String name, String email, String title, String home) {
        _name.set(name);
        _email.set(email);
        _title.set(title);
        _home.set(home);
    }

    public Profile() {
    	this("", "", "", "");
    }

    public void setName(String name) { _name.set(name); }
    public void setEmail(String email) { _email.set(email); }
    public void setTitle(String title) { _title.set(title); }
    public void setHome(String home) { _home.set(home); }
    
}
