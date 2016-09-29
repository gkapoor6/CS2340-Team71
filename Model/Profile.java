package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Geetika Kapoor on 9/28/2016.
 */
public class Profile {
    private StringProperty _name = new SimpleStringProperty();
    private StringProperty _email = new SimpleStringProperty();
    private StringProperty _title = new SimpleStringProperty();
    private StringProperty _home = new SimpleStringProperty();

    public Profile(String name, String email, String title, String home) {
        _name.set(name);
        _email.set(email);
        _title.set(title);
        _home.set(home);
    }
}
