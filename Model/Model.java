package Model;

import java.util.HashMap;

public class Model {
	private static final Model instance = new Model();
	public static Model getInstance() { return instance; }
	private static HashMap<String, AuthorizedUser> users = new HashMap<>();

	public static boolean addAuthorizedUser(AuthorizedUser user) {
		for (String name: users.keySet()) {
			if (name.equals(user.getName())) {
				return false;
			}
		}
		users.put(user.getName(), user);
		return true;
	}
}
