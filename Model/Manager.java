package Model;

public class Manager extends Worker{
	public Manager(String name, String password) {
		super(name, password);
	}
	public AuthorizedUser copy() {
		return new Manager(this.getName(), this.getPassword());
	}
}
