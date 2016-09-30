package Model;

/**
 * User Class
 */
public class User extends AuthorizedUser{
	public User(String name, String pwd) {
		super(name, pwd);
	}
	
	public AuthorizedUser copy() {
		return new User(this.getName(), this.getPassword());
	}
}