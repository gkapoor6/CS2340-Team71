package Model;

/**
 * Admin Class
 * @author Dong Son Trinh
 */
public class Admin extends AuthorizedUser{
	/**
	 * Contructor for an Admin authorized user
	 * @param username username
	 * @param password password
	 */
	public Admin(String username, String password) {
		super(username, password);
	}
	
	/**
	 * @param username username of admin
	 * @param password password of admin
	 * @param name name of admin
	 * @param title title of admin
	 * @param email email of admin
	 * @param address address of admin
	 */
	public Admin(String username, String password, String name, String title, String email, String address) {
		super(username, password, name, title, email, address);
	}
	
	/**
	 * @param username username of admin
	 * @param password password of admin
	 * @param profile profile of admin
	 */
	public Admin(String username, String password, Profile profile) {
		super(username, password, profile);
	}
}
