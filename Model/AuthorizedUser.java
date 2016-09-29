package Model;

public abstract class AuthorizedUser {
	/**
	 * Instance data of an Authorized User
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
	public String getPassword() { return password; }
	
	/**
	 * Constructor
	 * @param name name
	 * @param pwd password
	 */
	public AuthorizedUser(String name, String pwd) {
		this.name = name ;
		password = pwd;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
