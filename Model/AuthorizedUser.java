package Model;

public abstract class AuthorizedUser {
	/**
	 * Instance data of an Authorized User
	 */
	private String name;
	private String password;
	private Profile profile;
	
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
	
	public Profile getProfile() { return profile; }
	
	/**
	 * Constructor
	 * @param name name
	 * @param pwd password
	 */
	
	public AuthorizedUser(String name, String password) {
		this.name = name ;
		this.password = password;
		profile = new Profile();
	}
	
	public abstract AuthorizedUser copy();
	
	
	@Override
	public String toString() {
		return name + " " + password;
	}
}
