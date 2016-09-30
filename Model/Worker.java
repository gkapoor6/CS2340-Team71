package Model;

public class Worker extends User{ 
	public Worker(String name, String password) {
		super(name, password);
	}
	
	public AuthorizedUser copy() {
		return new Worker(this.getName(), this.getPassword());
	}
}
