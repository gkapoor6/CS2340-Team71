package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Geetika Kapoor on 9/28/2016.
 */
public class Profile {
	/**
	 * all instance data in StringProperty to connect to the view
	 */
	private StringProperty _name = new SimpleStringProperty();
	private StringProperty _email = new SimpleStringProperty();
	private StringProperty _title = new SimpleStringProperty();
	private StringProperty _home = new SimpleStringProperty();

	/**
	 * Constructor
	 * @param name name
	 * @param email email
	 * @param title title
	 * @param home home address
	 */
	public Profile(String name, String email, String title, String home) {
		_name.set(name);
		_email.set(email);
		_title.set(title);
		_home.set(home);
	}

	/**
	 * Constructor for profile of a newly registered user
	 */
	public Profile() {
		this("", "", "", "");
	}

	/**
	 * Setters for the instance data
	 */
	public void setName(String name) {
		_name.set(name);
	}

	public void setEmail(String email) {
		_email.set(email);
	}

	public void setTitle(String title) {
		_title.set(title);
	}

	public void setHome(String home) {
		_home.set(home);
	}

	/**
	 * Getters for the instance data
	 */
	public StringProperty getName() {
		return _name;
	}

	public StringProperty getEmail() {
		return _email;
	}

	public StringProperty getTitle() {
		return _title;
	}

	public StringProperty getHome() {
		return _home;
	}

	@Override
	public String toString() {
		return "Profile [_name=" + _name + ", _email=" + _email + ", _title="
				+ _title + ", _home=" + _home + "]";
	}

}
