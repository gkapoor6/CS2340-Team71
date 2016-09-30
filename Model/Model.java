package Model;

import java.util.HashMap;

public class Model {
	private static final Model instance = new Model();
	public static Model getInstance() { return instance; }
	private static HashMap<String, AuthorizedUser> users = new HashMap<>();

	public static AuthorizedUser getUser(String name) {
		AuthorizedUser user = users.get(name);
		if (user == null) {
			return null;
		} else {
			return user.copy();
		}
	}
	
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
