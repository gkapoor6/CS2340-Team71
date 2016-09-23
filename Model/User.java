package Model;

public class User {
	
	private String name;
	private String password;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getPwd() { return password; }
	public void setPwd(String pwd) { password = pwd; }
	
	public User(String name, String pwd) {
		this.name = name ;
		password = pwd;
	}
	
	public String toString() {
		return name;
	}
}