import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.module.FindException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * version: V4.0
 * author: Zejian Li
 * className: Bank_Control
 * packageName: default  
 * description: This class is used to offer several functions for user login and employee login. 
 * It also includes functions of employee services.
 * data: 2019-05-12 20:00
 **/
public class Bank_Control {

	/* User - related information when reading user files*/
	String readName = null;
	String readAddress = null;
	String readDateOfBirth = null;

	/* Account related information when reading user files */
	String readAccNo;
	String readAccType;
	String readPin;
	String readBalance;

	String readUnClearedFunds;
	String readIsSuspended;
	String readIsActive;
	String readNoticeNeeded;

	boolean creditStatus = true;
	String strCreditStatus = String.valueOf(creditStatus);
	// strCreditStatus:The credit status value when writing to and reading a file

	public static boolean readCreditStatus;
	// readCreditStatus:The result of converting the credit status value to a Boolean type after reading the user file

	static Scanner sc = new Scanner(System.in);
	static ArrayList<String> readInfoArray = new ArrayList<>();
	Deposit newDeposit = new Deposit();

	Account newAccountStartUI;
	Date withdrawalAllowableDate;
	String strWithdrawalAllowableDate;
	Customer newCustomerLogin;

	Boolean employeeLoginAccSuccess = false;

	public Bank_Control() {

	}

	public Bank_Control(Customer newCustomer) {
		// There is a parameter constructor, the parameter is Customer object
	}
	
    /**
    * author:  Zejian Li
    * methodsName: startUI
    * description: the first interface of the system and more instructions for the following operations.
    * param:  newCustomer
    * return: String
    */
	public void startUI(Customer newCustomer) {

		System.out.println("Welcom to the Banking System. Please choose your role£º");
		System.out.println("(Note£ºEnter the number to select the appropriate service)");

		System.out.println("1. Normal Customer");
		System.out.println("2. Bank Employee");

		int choice = Integer.valueOf(sc.nextLine());
		if ((choice == 1) || (choice == 2)) {

			if (choice == 1) {

				System.out.println("1. Register the user of the BANKING SYSTEM");
				System.out.println("2. Login in the BANKING SYSTEM");
				int choice2 =  Integer.valueOf(sc.nextLine());
				if ((choice2 == 1) || (choice2 == 2)) {
					if (choice2 == 1) {
						addCustomer(newCustomer);
					}
					if (choice2 == 2) {
						loginCustomer(newCustomer);
					}
				} else {
					System.out.println("---The input is wrong, please start again---" + "\n" + "\n");
					startUI(newCustomer);
				}

			}
			if (choice == 2) {
				employeeService(newCustomer);
			}
		} else {
			System.out.println("---The input is wrong, please start again---" + "\n" + "\n");
			startUI(newCustomer);
		}
		sc.close();
	}
	
    /**
    * author:  Zejian Li
    * methodsName: employeeService
    * description: the interface of the system for the employee/management service.
    * param:  newCustomer
    * return: String
    */
	public void employeeService(Customer newCustomer) {
		System.out.println("Enter the number to select the appropriate service");
		System.out.println("1. Check/Update customer credit status");
		System.out.println("2. Reinstate the account of customer");
		System.out.println("3. Clear Funds");
		System.out.println("4. Login in the customer to operate following services:" + "\n" + "   * Open Account" + "\n"
				+ "   * Inquery the balance" + "\n" + "   * Deposit" + "\n" + "   * Withdrawal" + "\n"
				+ "   * Suspend the account" + "\n" + "   * Close the account");
		System.out.println("5. Exit the system");
		int choice2 =  Integer.valueOf(sc.nextLine());
		if ((choice2 == 1) || (choice2 == 2) || (choice2 == 3) || (choice2 == 4)) {
			if (choice2 == 1) {
				CheckOrUpdateCD(newCustomer);
			}

			if (choice2 == 2) {
				employeeLoginAccount(newCustomer);
				reinstate(newAccountStartUI);
				startUI(newCustomer);
			}
			if (choice2 == 3) {
				employeeLoginAccount(newCustomer);
				clearFunds(newAccountStartUI);
				startUI(newCustomer);
			}
			if (choice2 == 4) {
				loginCustomer(newCustomer);
			}
			
			if (choice2 == 5) {
		      	   Bank_Control newBK = new Bank_Control();
		      	   newBK.startUI(newCustomer);
			} 
		} else {
			System.out.println("---The input is wrong, please start again---" + "\n" + "\n");
			startUI(newCustomer);
		}
	}
	
    /**
    * author:  Zejian Li
    * methodsName: CheckOrUpdateCD
    * description: the function of checking or updating the credit status of customer.
    * param:  newCustomer
    * return: String
    */
	public void CheckOrUpdateCD(Customer newCustomer) {
		System.out.println("\n" + "Please input the personal information£º");
		System.out.println("(Note£ºPress click button on keyboard to input 'end' to indicate completion)");
		System.out.println("1. name:");
		System.out.println("2. address");
		System.out.println("3. date of birth:");

		try {
			ArrayList<String> strArray = new ArrayList<>();
			do {
				String str = sc.nextLine();
				if (str.equals("end")) {
					break;
				}
				strArray.add(str);
			} while (true);
			String arrayName = strArray.get(0);
			String arrayAdd = strArray.get(1);
			String arrayDob = strArray.get(2);

			Customer newCustomerAccordingToUserInput = new Customer(arrayName, arrayAdd, arrayDob);

			readCustomerInfo(newCustomerAccordingToUserInput);

			if ((readName.equals(arrayName)) && (readAddress.equals(arrayAdd)) && (readDateOfBirth.equals(arrayDob))) {
				C_U_B(newCustomerAccordingToUserInput);// The bank employee chooses C/D/B
			} else {
				System.out.println("information doea not match, enter again");
				CheckOrUpdateCD(newCustomer);
			}

		} catch (Exception e) {
			System.out.println("There is error in your input, enter again");
			CheckOrUpdateCD(newCustomer);
		}
		sc.close();
	}
	
	
	public void C_U_B(Customer newCustomer) {
		System.out.println("1. Check the credit status of the customer");
		System.out.println("2. Update the credit status of the customer");
		System.out.println("3. Back");
		int choice2 =  Integer.valueOf(sc.nextLine());
		if ((choice2 == 1) || (choice2 == 2) || (choice2 == 3)) {
			if (choice2 == 1) {// Check the credit status of the customer
				if (readCreditStatus == true) {
					System.out.println("The customer's credit status: good.");
					C_U_B(newCustomer);
				} else {
					System.out.println("The customer's credit status: Not good.");
					C_U_B(newCustomer);
				}
			} else if (choice2 == 2) {// Update the credit status of the customer

				System.out.println("Please enter the updated credit status: (input the number of option)");
				System.out.println("1. good");
				System.out.println("2. Not good");
				int choice3 = Integer.valueOf(sc.nextLine());
				int lineCD = 4;
				Transaction newTR5 = new Transaction();
				if (choice3 == 1) {
					newTR5.replaceTxtByLineNo("D:\\" + newCustomer.getName() + ".txt", lineCD, "true");
					System.out.println("The credit status of the customer has changed from Not good to good");
					employeeService(newCustomer);
				} else if (choice3 == 2) {
					newTR5.replaceTxtByLineNo("D:\\" + newCustomer.getName() + ".txt", lineCD, "false");			
					System.out.println("The credit status of the customer has changed from good to Not good");
					employeeService(newCustomer);
				} else {
					System.out.println("Sorry, the input is wrong, please input again.");
					employeeService(newCustomer);
				}

			} else {
				startUI(newCustomer);
			}
		} else {
			System.out.println("\n" + ">_< please check the input and enter again");
			C_U_B(newCustomer);
		}
	}

	
    /**
    * author:  Zejian Li
    * methodsName: employeeLoginAccount
    * description: The bank employee enters the account information of the user for confirmation before 
    * performing the management operation
    * param:  newCustomer
    * return: String
    */
	public void employeeLoginAccount(Customer newCustomer) {
		System.out.println("\n" + "Please input the personal information£º");
		System.out.println("(Note£ºPress Enter button on keyboard to input 'end' to indicate completion)");
		System.out.println("1. name:");
		System.out.println("2. address");
		System.out.println("3. date of birth:");

		try {
			ArrayList<String> strArray66 = new ArrayList<>();
			do {
				String str = sc.nextLine();
				if (str.equals("end")) {
					break;
				}
				strArray66.add(str);
			} while (true);
			String arrayName66 = strArray66.get(0);
			String arrayAdd66 = strArray66.get(1);
			String arrayDob66 = strArray66.get(2);

			Customer newCustomerLogin = new Customer(arrayName66, arrayAdd66, arrayDob66);

			readCustomerInfo(newCustomerLogin);
			if ((readName.equals(arrayName66)) && (readAddress.equals(arrayAdd66))
					&& (readDateOfBirth.equals(arrayDob66))) {
				System.out.println("The customer exists, the customer name:" + arrayName66);

				File newFile = new File("D:\\" + arrayName66 + ".txt");
				if (newFile.exists()) {
					System.out.println("Customer File exists");
				} else {
					System.out.println("Customer File does not exists");
				}

				// Log in£ºthe start
				System.out.println("\n" + "Please input the following information to log into your account£º");
				System.out.println("(Note£ºPress Enter button on keyboard to input 'end' to indicate completion)");
				System.out.println("1. Account number:");
				System.out.println("1. PIN:");
				ArrayList<String> strArray3 = new ArrayList<>();
				do {
					String str = sc.nextLine();
					if (str.equals("end")) {
						break;
					}
					strArray3.add(str);
				} while (true);
				String array3AccNo = strArray3.get(0);
				String array3PIN = strArray3.get(1);
				// Create a new Account class here for the actions inside the startUI method
				newAccountStartUI = new Account(Integer.valueOf(array3AccNo), newCustomerLogin);

				String str;
				BufferedReader bre = null;
				int lines1 = 0, lines2 = 0;

				bre = new BufferedReader(new FileReader(newFile));
				int lines = -1;
				while ((str = bre.readLine()) != null) 
				{
					lines++;
					if (str.equals(array3AccNo)) {
						String str1 = str;
						lines1 = lines;
						System.out.println(lines1 + "\t" + str1);
					}
					if (str.equals(array3PIN)) {
						String str1 = str;
						lines2 = lines;
						System.out.println(lines2 + "\t" + str1);
					}
					if ((lines2 - lines1) == 2) { // If the account password is 2 more lines than the account number
						employeeLoginAccSuccess = true;
					}
				}
				bre.close();

				//The entered account number and PIN are in the user file, indicating that they have been registered
				if (employeeLoginAccSuccess == true) {
					System.out.println("Begin to enter into if (employeeLoginAccSuccess == true) ");
					System.out.println("The account exists.");
					
				} else {//The account number and PIN entered by the user do not exist in the user file
					System.out.println("\n"
							+ ">_< Login failed, please confirm you have registered and check the information you input");
					employeeLoginAccount(newCustomer);
				}

				// Log in£ºthe end of segment
			} else {
				System.out.println("Sorry, the customer is not in the system");
				CheckOrUpdateCD(newCustomer);
			}

		} catch (Exception e) {
			System.out.println("Error in employeeLoginAccount(Customer newCustomer), enter again");
			employeeService(newCustomer);
		}
	}

	
    /**
    * author:  Zejian Li
    * methodsName: addCustomer
    * description: The function of registering new customer
    * param:  newCustomer
    * return: String
    */
	public void addCustomer(Customer newCustomer) {

		Scanner sc = new Scanner(System.in);
		System.out.println("\n" + "Please input your personal information to complete the register");
		System.out.println("(Note£ºPress Enter button on keyboard to input 'end' to indicate completion)");
		System.out.println("1. name:");
		System.out.println("2. address");
		System.out.println("3. date of birth:");
		
		ArrayList<String> strArray = new ArrayList<>();
		do {
			String str = sc.nextLine();
			if (str.equals("end")) {
				break;
			}
			strArray.add(str);
		} while (true);
		String arrayName = strArray.get(0);
		String arrayAdd = strArray.get(1);
		String arrayDob = strArray.get(2);

		File file = new File("D:/" + arrayName + ".txt");
		
		SimpleDateFormat sft0 = new SimpleDateFormat("yyyy-MM-dd");
        String sftBirth = arrayDob;
        Date dateBirth0 = null;
        try{
            dateBirth0 = sft0.parse(sftBirth);
        }catch(Exception e){
            e.printStackTrace();
        }
        int age0 = Customer.getAgeByBirth(dateBirth0);
        System.out.println("Your age:" + age0);
		
		
		if(file.exists()) {
			System.out.println("Sorry,The name you entered already exists, please input again");
			addCustomer(newCustomer);
		}
		else if (age0<0){
			 System.out.println("The birthday is after Now,It's unbelievable,please check and enter again");		 
			 addCustomer(newCustomer);
		}else {
			FileWriter fw;
			try {
				fw = new FileWriter(file.getAbsoluteFile(), false);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(arrayName);
				bw.newLine();
				bw.write(arrayAdd);
				bw.newLine();
				bw.write(arrayDob);
				bw.newLine();
				bw.write(strCreditStatus);
				bw.newLine();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("\n" + "You have register successfully");
			System.out.println("(Note£ºEnter the number to select the appropriate service)");
			System.out.println("1. Login:");
			System.out.println("2. Back");
			int choice =  Integer.valueOf(sc.nextLine());
			if ((choice == 1) || (choice == 2)) {
				if (choice == 1) {
					loginCustomer(newCustomer);
				}
				if (choice == 2) {
					startUI(newCustomer);
				}
			} else {
				System.out.println("---The input is wrong, please start again---" + "\n" + "\n");
				startUI(newCustomer);
			}
			sc.close();
		}
		
	}

    /**
    * author:  Zejian Li
    * methodsName: addCustomer
    * description: The function of reading customer's related information
    * param:  newCustomer
    * return: String
    */
	public void readCustomerInfo(Customer newCustomer) {// Read the user's name, address, dob, cresitStatus in the user file
		String pathName = "D:\\" + newCustomer.getName() + ".txt";
		try (FileReader fr = new FileReader(pathName); BufferedReader bf = new BufferedReader(fr)) {

			readName = bf.readLine();
			readAddress = bf.readLine();
			readDateOfBirth = bf.readLine();
			strCreditStatus = bf.readLine();
			readCreditStatus = Boolean.parseBoolean(strCreditStatus);
			fr.close();
			bf.close();
		} catch (IOException e) {
			System.out.println("read Customer Info wrong");
		}
	}

    /**
    * author:  Zejian Li
    * methodsName: readAccountInfo
    * description: Read 7 pieces of relevant information under the specified account number in the user file
    * param:  newAccount
    * return: String
    */
	public void readAccountInfo(Account newAccount) { 

		String str;
		BufferedReader bre = null;

		String strInquiryAccNo = Integer.toString(newAccount.getAccNo());
		// Get the account number for the account and convert it to string

		String inquiryCusName = newAccount.getCustomer().getName();
		// Get the user of the account and the user name of the user to query the user TXT file

		try {
			File newFile = new File("D:\\" + inquiryCusName + ".txt");
			bre = new BufferedReader(new FileReader(newFile));
			while ((str = bre.readLine()) != null) 
			{
				if (str.equals(strInquiryAccNo)) {
					readAccNo = str;
					readAccType = bre.readLine();
					readPin = bre.readLine();
					readBalance = bre.readLine();

					readUnClearedFunds = bre.readLine();
					readIsSuspended = bre.readLine();
					readIsActive = bre.readLine();
					readNoticeNeeded = bre.readLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
    * author:  Zejian Li
    * methodsName: printAccountInfo
    * description: Print 7 pieces of relevant information under the specified account number in the user file
    * param:  newAccount
    * return: String
    */
	public void printAccountInfo(Account newAccount) { 
		readAccountInfo(newAccount);
		System.out.println(readAccNo);
		System.out.println(readAccType);
		System.out.println(readPin);
		System.out.println(readBalance);

		System.out.println(readUnClearedFunds);
		System.out.println(readIsSuspended);
		System.out.println(readIsActive);
		System.out.println(readNoticeNeeded);

	}

    /**
    * author:  Zejian Li
    * methodsName: loginCustomer
    * description: The function is used for customer to login
    * param:  newAccount
    * return: String
    */
	public void loginCustomer(Customer newCustomer) {

		System.out.println("\n" + "Please input your personal information to complete the login£º");
		System.out.println("(Note£ºPress Enter button on keyboard to input 'end' to indicate completion)");
		System.out.println("1. name:");
		System.out.println("2. address");
		System.out.println("3. date of birth:");

		try {
			ArrayList<String> strArray = new ArrayList<>();
			do {
				String str = sc.nextLine();
				if (str.equals("end")) {
					break;
				}
				strArray.add(str);
			} while (true);
			String arrayName = strArray.get(0);
			String arrayAdd = strArray.get(1);
			String arrayDob = strArray.get(2);
			System.out.println("the name you enter:" + arrayName);
			System.out.println("the address you enter:" + arrayAdd);
			System.out.println("the dateOfBirth you enter:" + arrayDob);

			Customer newCustomerLogin = new Customer(arrayName, arrayAdd, arrayDob);
			readCustomerInfo(newCustomerLogin);
			System.out.println("Read from the file: name:" + readName);
			System.out.println("Read from the file: address:" + readAddress);
			System.out.println("Read from the file: datrOfBirth:" + readDateOfBirth);
			if (arrayName.equals(readName)) {
				System.out.println("name matches");
			}
			if (arrayAdd.equals(readAddress)) {
				System.out.println("address matches");
			}
			if (arrayDob.equals(readDateOfBirth)) {
				System.out.println("dateOfBirth matches");
			}

			readCustomerInfo(newCustomerLogin);
			if ((readName.equals(arrayName)) && (readAddress.equals(arrayAdd)) && (readDateOfBirth.equals(arrayDob))) {
				loginSuccess(newCustomerLogin);
			} else {
				System.out.println("\n" + ">_< Login failed, please check your personal information and login again");
				loginCustomer(newCustomerLogin);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There is error in your input, enter again");
			loginCustomer(newCustomer);
		}
	}
	
    /**
    * author:  Zejian Li
    * methodsName: loginSuccess
    * description: The function is used for customer to login successfully
    * param:  newAccount
    * return: String
    */
	public void loginSuccess(Customer newCustomer) { // A screen
		System.out.println("\n" + "Successfully log in the customer");
		System.out.println("(Note£ºEnter the number to select the appropriate service)");
		System.out.println("1. Open a new Account");
		System.out.println("2. Log into a bank account");
		
		try {
			int choice =  Integer.valueOf(sc.nextLine());
			if ((choice == 1) || (choice == 2)) {
				if (choice == 1) {
					newCustomer.addAccount(newCustomer);
				}
				if (choice == 2) {
					newCustomer.loginAccount(newCustomer);
				}
			} else {
				System.out.println("\n" + ">_< The input is wrong, please enter again");
				loginSuccess(newCustomer);
			}
		} catch (Exception e) {
			System.out.println("\n" + ">_< The input is wrong, please enter again");
			loginSuccess(newCustomer);
		}
	}

    /**
    * author:  Zejian Li
    * methodsName: addNewToFile
    * description: The function is used to add new Content to the customer file
    * param:  newCustomer,content
    * return: String
    */
	public void addNewToFile(Customer newCustomer, String content) {
		BufferedWriter out = null;
		String inquiryCusName2 = newCustomer.getName();
		File newFile = new File("D:\\" + inquiryCusName2 + ".txt");

		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile, true)));

			out.write(content + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /**
    * author:  Zejian Li
    * methodsName: clearFunds
    * description: The function is used to clear the un-cleared funds of customer
    * param:  newAccount
    * return: String
    */
	public void clearFunds(Account newAccount) {
	    double zero = 0.0;
	 // 1. Read the size of uncleared Funds in the file and tell the operator
	 // 2. Add the variable uncleared Funds in the file to balance, change to 0.0 and rewrite the file
	 // 3. Successful liquidation, current uncleared Funds: balance:
		readAccountInfo(newAccount);
		double initialBal = newAccount.getBalance(newAccount);
		double updateBal = initialBal + Double.parseDouble	(readUnClearedFunds);

		if (readUnClearedFunds.equals("0")) {
			System.out.println("There is no un-cleared funds");
		} else {

			Transaction newTR12 = new Transaction();
			newTR12.findLineNoOfAccNo(newAccount, newAccount.getAccNo());
			newTR12.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt", (newTR12.linesOfAccNo + 4),
					String.valueOf(zero));
			newTR12.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt", (newTR12.linesOfAccNo + 3),
					String.valueOf(updateBal));

			System.out.println("\n" + "The un-cleared funds: " + Double.parseDouble(readUnClearedFunds) + " $"
					+ "has been successfully credited to the account");
			System.out.println("\n" + "Current Balance: " + updateBal);
		}
	}
	
    /**
    * author:  Zejian Li
    * methodsName: reinstate
    * description: The function is used to reinstate the account of customer
    * param:  newAccount
    * return: String
    */
	public void reinstate(Account newAccount) {
		System.out.println("\n" + "Are you sure you want to reinstate the account?");
		System.out.println("(Note£ºEnter the number to select the appropriate service)");
		System.out.println("1. Confirm");
		System.out.println("2. Cancel");

		int choice =  Integer.valueOf(sc.nextLine());
		if ((choice == 1) || (choice == 2)) {
			if (choice == 1) {
				newAccount.isSuspended = false;
				Transaction newTR66 = new Transaction();
				newTR66.findLineNoOfAccNo(newAccount, newAccount.getAccNo());
				newTR66.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt",
						(newTR66.linesOfAccNo + 5), String.valueOf(newAccount.isSuspended));// 3
				System.out.println("Congratulations, the account has changed from being suspended to normal.");
			}
			if (choice == 2) {
				startUI(newAccount.getCustomer());
			}
		} else {
			System.out.println("\n" + ">_< Your input is wrong, please choose the service again");
			employeeService(newAccount.getCustomer());
		}
	}
	
    /**
    * author:  Zejian Li
    * methodsName: reinstate
    * description: The function is used to set notice when user withdraw from saver account
    * param:  newAccount
    * return: String
    */
	public void setNotice(Account newAccount) {
		System.out.println("For a withdrawal from a Saver account, " + "\n"
				+ "a minimum period of notice (in days) must be given before any withdrawal can be made." + "\n"
				+ "Please input a integer number to indicate the notie in days:");
		try {
			int noticePeriod = Integer.valueOf(sc.nextLine());;

			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String currdate = df.format(date);
			System.out.println("The current date is:" + currdate);
			withdrawalAllowableDate = addDate(noticePeriod);
			System.out.println("Withdrawal is not allowed until: " + df.format(withdrawalAllowableDate));
			strWithdrawalAllowableDate = df.format(withdrawalAllowableDate);

			Transaction newTR21 = new Transaction();
			newTR21.findLineNoOfAccNo(newAccount, newAccount.getAccNo());
			newTR21.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt", newTR21.linesOfAccNo + 7,
					strWithdrawalAllowableDate);
		} catch (Exception e) {
			System.out.println("\n" + ">_< Your input is wrong, please enter again");
			setNotice(newAccount);
		}
	}
	
	public static Date addDate(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, num);
		Date date = calendar.getTime();
		return date;
	}

}
