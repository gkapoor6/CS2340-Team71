package Model;

/**
 * Worker Class
 * @author Dong Son Trinh
 */
public class Worker extends User{
	/**
	 * Contructor for a Worker authorized user
	 * @param username username
	 * @param password password
	 */
	public Worker(String username, String password) {
		super(username, password);
	}

	/**
	 * @param username username of worker
	 * @param password password of worker
	 * @param name name of worker
	 * @param title title of worker
	 * @param email email of worker
	 * @param address address of worker
	 */
	public Worker(String username, String password, String name, String title, String email, String address) {
		super(username, password, name, title, email, address);
	}
	
	/**
	 * @param username username of worker
	 * @param password password of worker
	 * @param profile profile of worker
	 */
	public Worker(String username, String password, Profile profile) {
		super(username, password, profile);
	}
}
