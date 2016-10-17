package Model;

/**
 * AuthorizedUser Class which is an ancestor class of User, Manager,
 * Worker and Admin
 * @author Dong Son Trinh
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
	 * @param username username
	 * @param password password
	 */
	
	public AuthorizedUser(String username, String password) {
		this(username, password, null, null, null, null);
	}
	
	/**
	 * @param username username of user
	 * @param password password of user
	 * @param name name of user
	 * @param title title of user
	 * @param email email of user
	 * @param address address of user
	 */
	public AuthorizedUser(String username, String password, String name, String title, String email, String address) {
		this(username, password, new Profile(name, title, email, address));
	}
	
	/**
	 * @param username username of user
	 * @param password password of user
	 * @param profile profile of user
	 */
	public AuthorizedUser(String username, String password, Profile profile) {
		this.name = username;
		this.password = password;
		this.profile = profile;
	}
	
	@Override
	public String toString() {
		return name + " " + password;
	}
}