package Controller;

import FXApp.MainFXApp;
import Model.AuthorizedUser;

public class WaterReportViewController {
	
	private MainFXApp mainApp;
	
	private AuthorizedUser user;
	
	public void setMain(MainFXApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setUser(AuthorizedUser user) {
		this.user = user;
	}
	
	public void handleCancelPressed() {
		mainApp.showApplication(user);
	}

}
