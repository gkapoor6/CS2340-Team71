package Model;

/**
 * User Class
 */
public class User {
	
	/**
	 * Instance data of a User
	 */
	private String name;
	private String password;
	
	/**
	 * Returns name
	 * @return name
	 */
	public String getName() { return name; }
	
	/**
	 * Returns the password
	 * @return password
	 */
	public String getPwd() { return password; }
	
	/**
	 * Constructor
	 * @param name name
	 * @param pwd password
	 */
	public User(String name, String pwd) {
		this.name = name ;
		password = pwd;
	}
	
	@Override
	public String toString() {
		return name;
	}
}