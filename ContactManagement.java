package controller;

import java.sql.SQLException;

public class ContactManagementApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserController usercontroller = new UserController();
		usercontroller.startProcess();
	}

}
