package Model;

public class Admin extends AuthorizedUser{
	public Admin(String name, String password) {
		super(name, password);
	}
	
	public AuthorizedUser copy() {
		return new Admin(this.getName(), this.getPassword());
	}
}
