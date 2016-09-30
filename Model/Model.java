package Model;

import java.util.HashMap;

public class Model {
	private static final Model instance = new Model();
	public static Model getInstance() { return instance; }
	private static HashMap<String, AuthorizedUser> users = new HashMap<>();

	public static AuthorizedUser getUser(String name) {
		return users.get(name).copy();
	}
	
	public static boolean addAuthorizedUser(AuthorizedUser user) {
		if (users.get(user.getName()) == null) {
			users.put(user.getName(), user);
			Model.getInstance();
			System.out.println(Model.users.get(user.getName()));
			return true;
		} else {
			return false;
		}
	}
}
