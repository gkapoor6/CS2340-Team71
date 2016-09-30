package Model;

/**
 * Singleton Model that holds all
 * @author DongSon
 */
import java.util.HashMap;

public class Model {
	/**
	 * Singly instance of the class
	 */
	private static final Model instance = new Model();
	
	/**
	 * Getter for the single instance of class
	 * @return instance of class
	 */
	public static Model getInstance() { return instance; }
	
	/**
	 * User data holder
	 */
	private static HashMap<String, AuthorizedUser> users = new HashMap<>();
	
	/**
	 * Return a copy of the user stored in the database
	 * @param name name of user to search
	 * @return copy of the user object
	 */
	public static AuthorizedUser getUser(String name) {
		AuthorizedUser user = users.get(name);
		if (user == null) {
			return null;
		} else {
			return user.copy();
		}
	}
	
	/**
	 * Add a user to the database
	 * @param user user
	 * @return the outcome of attempt to add a user
	 */
	public static boolean addAuthorizedUser(AuthorizedUser user) {
		if (users.get(user.getName()) == null) {
			users.put(user.getName(), user);
			Model.getInstance();
			return true;
		} else {
			return false;
		}
	}
}
