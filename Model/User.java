package Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	private final StringProperty _name = new SimpleStringProperty();
	private String password;
	
	public String getName() { return _name.get(); }
	public void setName(String name) { _name.set(name); }
	
	public String getPwd() { return password; }
	public void setPwd(String pwd) { password = pwd; }
	
	public User(String name, String pwd) {
		_name.set(name);
		password = pwd;
	}
	
	public String toString() {
		return _name.get();
	}
}