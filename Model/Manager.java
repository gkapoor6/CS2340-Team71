package Model;

/**
 * Manager Class
 * @author DongSon
 */
public class Manager extends Worker{
	/**
	 * Contructor for a Manager authorized user
	 * @param name name
	 * @param password password
	 */
	public Manager(String name, String password) {
		super(name, password);
	}
	
	@Override
	public AuthorizedUser copy() {
		return new Manager(this.getName(), this.getPassword());
	}
}
