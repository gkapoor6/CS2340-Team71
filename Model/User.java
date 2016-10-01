package Model;

/**
 * User Class
 * @author DongSon
 */
public class User extends AuthorizedUser{
	/**
	 * Contructor for a User authorized user
	 * @param name name
	 * @param password password
	 */
	public User(String name, String pwd) {
		super(name, pwd);
	}
}