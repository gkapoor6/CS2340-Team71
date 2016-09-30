package Model;

/**
 * Admin Class
 * @author DongSon
 */
public class Admin extends AuthorizedUser{
	/**
	 * Contructor for an Admin authorized user
	 * @param name name
	 * @param password password
	 */
	public Admin(String name, String password) {
		super(name, password);
	}
	
	@Override
	public AuthorizedUser copy() {
		return new Admin(this.getName(), this.getPassword());
	}
}
