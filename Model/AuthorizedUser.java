package Model;

/**
 * AuthorizedUser Class which is an ancestor class of User, Manager,
 * Worker and Admin
 * @author DongSon
 */
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
	
	/**
	 * Return user's profile object
	 * @return profile object
	 */
	public Profile getProfile() { return profile; }
	
	/**
	 * Constructor for an Authorized User
	 * @param name name
	 * @param pwd password
	 */
	
	public AuthorizedUser(String name, String password) {
		this.name = name ;
		this.password = password;
		profile = new Profile();
	}
	
	/**
	 * Copies the current object
	 * @return a copy of {@code this}
	 */
	public abstract AuthorizedUser copy();
	
	
	@Override
	public String toString() {
		return name + " " + password;
	}
}
