package Model;

import java.util.HashMap;

public class Model {
	private static final Model instance = new Model();
	public static Model getInstance() { return instance; }
	private HashMap<String, User> users = new HashMap<>();

	
	private Model() {
		users.put("Irtiza",new User("Irtiza", "relaxyo"));
	}
	public boolean addUser(User user) {
		for (String name: users.keySet()) {
			if (name.equals(user.getName())) {
				return false;
			}
		}
		users.put(user.getName(), user);
		return true;
	}
}
