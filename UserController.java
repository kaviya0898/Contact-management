package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.UserDetails;
import dao.ContactManagementDAO;
import validation.Validation;

public class UserController {
	ContactManagementDAO contactDAO = new ContactManagementDAO();

	public void startProcess() throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		int userChoice;
		System.out.println("\t\t MY CONTACTS \t\t");
		System.out.println("1 : Add Contacts \n2 : Update Contacts \n3 : Delete Contacts \n4 : Search Contacts \n5 : View All Contacts \n6 : Exit");
		System.out.println("Enter Your Choice");
		userChoice = scanner.nextInt();
		switch(userChoice) {
		case 1:{
			addContacts();
			break;
		}
		case 2:{
			updateContacts();
			break;
		}
		case 3:{
			deleteContacts();
			break;
		}
		case 4:{
			searchContacts();
			break;
		}
		case 5:{
			viewAllContacts();
			break;
		}
		case 6:{
			System.out.println("Application Closed");
			System.exit(0);
			break;
		}
		default :{
			System.out.println("Invalid Choice !.. Try Again");
			startProcess();
		}
		}
	}
	
	private void viewAllContacts() throws ClassNotFoundException, SQLException {
		System.out.println();
		ArrayList<UserDetails> userList = contactDAO.viewUserDetails();
		System.out.println("-----------------------------------------------------------------");
		System.out.printf("| %10s | %10s | %10s | %22s |","FIRST_NAME","LAST_NAME","MOBILE_NO","EMAIL_ID");
		System.out.println();
		System.out.println("-----------------------------------------------------------------");
		for(int index=0;index<userList.size();index++) {
			System.out.printf("| %10s | %10s | %10s | %22s |",userList.get(index).getFirstName(),userList.get(index).getLastName(),
					userList.get(index).getMobileNumber(),userList.get(index).getEmailId());
			System.out.println();
			System.out.println("-----------------------------------------------------------------");
		}
		System.out.println();
		startProcess();
	}
	

	private void searchContacts() throws ClassNotFoundException, SQLException {
		System.out.println();
		UserDetails user = new UserDetails();
		Scanner scanner = new Scanner(System.in);
		System.out.println("1 : Search By First Name \n2 : Search By First Character \n3 : Exit");
		System.out.println("Enter Your Choice");
		int choice = scanner.nextInt();
		switch(choice) {
		case 1:{
			System.out.println("Enter the First Name To Be Search");
			user.setFirstName(scanner.next());
			ArrayList<UserDetails> searchList = contactDAO.searchUsingFirstName(user);
			System.out.println("-----------------------------------------------------------------");
			System.out.printf("| %10s | %10s | %10s | %22s |","FIRST_NAME","LAST_NAME","MOBILE_NO","EMAIL_ID");
			System.out.println();
			System.out.println("-----------------------------------------------------------------");
			for(int index=0;index<searchList.size();index++) {
				System.out.printf("| %10s | %10s | %10s | %22s |",searchList.get(index).getFirstName(),searchList.get(index).getLastName(),
						searchList.get(index).getMobileNumber(),searchList.get(index).getEmailId());
				System.out.println();
				System.out.println("-----------------------------------------------------------------");
			}
			System.out.println();
			break;
			}
		case 2:{
			System.out.println("Enter the First Character To Be Search");
			String search = scanner.next()+"%";
			ArrayList<UserDetails> searchValues = contactDAO.searchUsingFirstCharacter(search);
			System.out.println("-----------------------------------------------------------------");
			System.out.printf("| %10s | %10s | %10s | %22s |","FIRST_NAME","LAST_NAME","MOBILE_NO","EMAIL_ID");
			System.out.println();
			System.out.println("-----------------------------------------------------------------");
			for(int index=0;index<searchValues.size();index++) {
				System.out.printf("| %10s | %10s | %10s | %22s |",searchValues.get(index).getFirstName(),searchValues.get(index).getLastName(),
						searchValues.get(index).getMobileNumber(),searchValues.get(index).getEmailId());
				System.out.println();
				System.out.println("-----------------------------------------------------------------");
			}
			System.out.println();
			break;
		}
		case 3:{
			break;
		}
		default :{
			System.out.println("Invalid Choice :( Enter a Valid Choice");
			searchContacts();
		}
		}
		startProcess();
	}

	private void deleteContacts() throws ClassNotFoundException, SQLException {
		System.out.println();
		UserDetails user = new UserDetails();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the First Name To Be Deleted");
		user.setFirstName(scanner.next());

		System.out.println("Enter the Last Name To Be Deleted");
		user.setLastName(scanner.next());
		Integer id =contactDAO.fetchTheUserId(user);
		if(id!=null) {
			int result = contactDAO.deleteUserDetails(id);
			if(result == 1) {
				System.out.println("Contact Deleted Successfully");
				System.out.println();
				startProcess();
			}
		}else {
			System.out.println("No Contacts Found");
			System.out.println();
			deleteContacts();
		}
	}

	private void updateContacts() throws ClassNotFoundException, SQLException {
		System.out.println();
		UserDetails user = new UserDetails();
		Validation valid = new Validation();
		Scanner scanner = new Scanner(System.in);
		boolean boolValue = false;
		boolean boolValue1 = false;
		System.out.println("Enter the First Name To Be Updated");
		user.setFirstName(scanner.next());
		System.out.println("Enter the Last Name To Be Updated");
		user.setLastName(scanner.next());
		Integer id =contactDAO.fetchTheUserId(user);
		if(id!=null) {
			user.setUserId(id);
			System.out.println();
			System.out.println("1 : Update First Name \n2 : Update Last Name \n3 : Update Mobile Number \n4 : Update Mail ID \n5 : Exit");
			System.out.println("Enter Your Choice");
			int choice = scanner.nextInt();
			switch(choice) {
			case 1 :{
				System.out.println("Enter the First Name To Update");
				user.setFirstName(scanner.next());
				contactDAO.updateUserFirstName(user);
				System.out.println("Contact Updated Successfully");
				System.out.println();
				break;
			}
			case 2 :{
				System.out.println("Enter the Last Name To Update");
				user.setLastName(scanner.next());
				contactDAO.updateUserLastName(user);
				System.out.println("Contact Updated Successfully");
				System.out.println();
				break;
			}
			case 3:{
				while(!boolValue) {
					System.out.println("Enter the Mobile Number To Update");
					String mobileNumber = scanner.next();
					if(valid.mobileNumberValidation(mobileNumber)) {
						user.setMobileNumber(mobileNumber);
						contactDAO.updateUserMobileNumber(user);
						System.out.println("Contact Updated Successfully");
						System.out.println();
						boolValue=true;
						break;
					}else {
						System.out.println("Invalid Mobile Number :( Enter an Valid Mobile Number");
						break;
					}
				}
				break;
			}
			case 4:{
				while(!boolValue1) {
					System.out.println("Enter the Mail ID To Update");
					String mailId = scanner.next();
					if(valid.emailIdValidation(mailId)) {
						user.setEmailId(mailId);
						contactDAO.updateUserEmailId(user);
						System.out.println("Contact Updated Successfully");
						System.out.println();
						boolValue1=true;
						break;
			        }else {
						System.out.println("Invalid Email Address :( Enter an Valid Email Id");
						break;
					}
				}
				break;
			}
			case 5:{
				break;
			}
			default :{
				System.out.println("Invalid Choice !.. Try Again");
				updateContacts();
			}
			}
			startProcess();
		}else {
			System.out.println("No Contacts Found");
			System.out.println();
			updateContacts();
		}
	}

	private void addContacts() throws ClassNotFoundException, SQLException {
		System.out.println();
		UserDetails user = new UserDetails();
		Validation valid = new Validation();
		Scanner scanner = new Scanner(System.in);
		boolean boolValue = false;
		boolean boolValue1 = false;
		System.out.println("Enter the First Name");
		user.setFirstName(scanner.next());
		System.out.println("Enter the Last Name");
		user.setLastName(scanner.next());
		
		while(!boolValue) {
		System.out.println("Enter the Mobile Number");
		String mobileNumber = scanner.next();
		if(valid.mobileNumberValidation(mobileNumber)) {
			user.setMobileNumber(mobileNumber);
			boolValue=true;
		}else {
			System.out.println("Invalid Mobile Number :( Enter an Correct Mobile Number");
		    }
		}
		
		while(!boolValue1) {
			System.out.println("Enter the Mail ID");
			String mailId = scanner.next();
			if(valid.emailIdValidation(mailId)) {
				user.setEmailId(mailId);
				boolValue1 = true;
			}else {
				System.out.println("Invalid Email Address :( Enter an Valid Email Id");
			}
		}
		contactDAO.addUserDetails(user);
	    user.setUserId(contactDAO.fetchTheUserId(user));
		System.out.println("Contacts Added Successfully");
		System.out.println();
		startProcess();
	}
	
}
