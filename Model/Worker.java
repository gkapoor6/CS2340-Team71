package Model;

/**
 * Worker Class
 * @author DongSon
 */
public class Worker extends User{
	/**
	 * Contructor for a Worker authorized user
	 * @param name name
	 * @param password password
	 */
	public Worker(String name, String password) {
		super(name, password);
	}
	
	@Override
	public AuthorizedUser copy() {
		return new Worker(this.getName(), this.getPassword());
	}
}
