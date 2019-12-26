import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * version: V4.0
 * author: Zejian Li
 * className: Account
 * packageName: default  
 * description: This class is used to offer several functions about account service.
 * data: 2019-05-12 20:00
 **/
public class Account {
	static Scanner sc = new Scanner(System.in);

	protected int accNo; 
	protected static String accType; 
	protected int pin; 
	protected double balance; 
	protected String strbalance;
	protected double unClearedFunds = 0.0;
	protected String strUnclearedFunds;

	protected boolean isSuspended = false; 
	protected boolean isActive = true; 
	protected boolean strIsActive;
	protected boolean noticeNeeded = false;

	protected Customer newCustomer;
	protected final int accNo1 = 2016213153;
	boolean isExistent = false; // help determine if the account exists

	static double overdraftLimit = 200.0;
	Deposit newDeposit = new Deposit();
	Withdrawal newWithdrawal = new Withdrawal();

	// once a constructor is defined, it is automatically called when an object is created
	// constructors cannot be called by the programmer, but by the system
	public Account(int accNo, Customer newCustomer) {// create a new Account object when the user creates a new Account in the Customer class
		this.accNo = accNo;
		this.newCustomer = newCustomer;
		this.balance = 0.0;
		this.isActive = true;
		generatePin();
	}

	public void setAccount(int randomAccNo, Customer bindCustomer) {// a customer may have multiple account Numbers
		this.accNo = randomAccNo;
		this.newCustomer = bindCustomer;
		this.balance = 0.0;
		this.isActive = true;
	}

	protected void generatePin() {
		Random r = new Random();
		pin = (100000 + r.nextInt(900000)); // scope:[10 0000£¬99 9999]

	}

	public int getAccNo() {
		return accNo;
	}

	public Customer getCustomer() {// the method to obtain the corresponding user through the known account number
		return newCustomer;
	}

	public double getBalance(Account newAccount) {
		Bank_Control newBK9 = new Bank_Control(newAccount.getCustomer());
		newBK9.readAccountInfo(newAccount);
		strbalance = newBK9.readBalance;
		balance = Double.valueOf(strbalance);
		return balance;
	}

	public double getUnClearedFunds(Account newAccount) {
		Bank_Control newBK10 = new Bank_Control(newAccount.getCustomer());
		newBK10.readAccountInfo(newAccount);
		strUnclearedFunds = newBK10.readUnClearedFunds;
		unClearedFunds = Double.valueOf(strUnclearedFunds);
		return unClearedFunds;
	}

	public int getPin() {
		return pin;
	}

	public void clearBalance(Account newAccount) {
		newAccount.balance = 0.0;
		Transaction newTr2 = new Transaction();
		newTr2.findLineNoOfAccNo(newAccount, newAccount.getAccNo());
		newTr2.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt", (newTr2.linesOfAccNo + 3),
				String.valueOf(newAccount.balance));
	}

	public boolean isSuspended() {
		return this.isSuspended;
	}

	public void deposit(double amount) {
		balance = balance + amount;

	}

	public void withdraw(double amount) {
		balance = balance - amount;
	}


    /**
    * author:  Zejian Li
    * methodsName: loginAccountSuccess
    * description: The interface when user login the account successfully.
    * param:  newAccount
    * return: String
    */
	public void loginAccountSuccess(Account newAccount) {// C screen
		System.out.println("\n" + "Login into the account successfully");
		System.out.println("(Note£ºEnter the number to select the appropriate service)");
		System.out.println("1. Inqury the balance");
		System.out.println("2. Deposit");
		System.out.println("3. Withdraw");
		System.out.println("4. Suspend the account");
		System.out.println("5. Close the account");
		System.out.println("6. Exit the system");

		int choice = Integer.valueOf(sc.nextLine());;

		Bank_Control newBK88 = new Bank_Control(newAccount.getCustomer());
		newBK88.readAccountInfo(newAccount);

		if ((choice == 1) || (choice == 2) || (choice == 3) || (choice == 4) || (choice == 5) || (choice == 6)) {
			if (choice == 1) {

				
				  if(newBK88.readIsActive.equals("false") || newBK88.readIsSuspended.equals("true") ) {
				  System.out.println("Sorry, this account has been suspended/closed, we can not offer service");
				  loginAccountSuccess(newAccount);

				  }else { inquryBalance(newAccount); loginAccountSuccess(newAccount); }
				 
			}
			if (choice == 2) {
				  if(newBK88.readIsActive.equals("false") || newBK88.readIsSuspended.equals("true") ) {
					  System.out.println("Sorry, this account has been suspended/closed, we can not offer service");
					loginAccountSuccess(newAccount);
				} else {
					addDeposit(newAccount);
					loginAccountSuccess(newAccount);
				}
			}
			if (choice == 3) {
				  if(newBK88.readIsActive.equals("false") || newBK88.readIsSuspended.equals("true") ) {
					  System.out.println("Sorry, this account has been suspended/closed, we can not offer service");
					loginAccountSuccess(newAccount);
				} else {
					newAccount.addWithdrawal(newAccount);
					loginAccountSuccess(newAccount);
				}
			}
			if (choice == 4) {
				 if(newBK88.readIsActive.equals("false") || newBK88.readIsSuspended.equals("true") ) {
					  System.out.println("Sorry, this account has been suspended/closed, we can not offer service");
					loginAccountSuccess(newAccount);
				} else {
					setSuspended(newAccount);
					loginAccountSuccess(newAccount);
				}
			}
			if (choice == 5) {
				 if(newBK88.readIsActive.equals("false") || newBK88.readIsSuspended.equals("true") ) {
					  System.out.println("Sorry, this account has been suspended/closed, we can not offer service");
					loginAccountSuccess(newAccount);
				} else {
					close(newAccount);
					loginAccountSuccess(newAccount);
				}
			}
			if (choice == 6) {
				Customer newCustomer = new Customer();
				Bank_Control newBK = new Bank_Control();
				newBK.startUI(newCustomer);
			}
		} else {
			System.out.println("\n" + ">_< Your input is wrong, please choose the service again");
			loginAccountSuccess(newAccount);
		}
	}

    /**
    * author:  Zejian Li
    * methodsName: inquryBalance
    * description: The function is used to offer balance inquiring service for user.
    * param:  newAccount
    * return: String
    */
	public static void inquryBalance(Account newAccount) {
		System.out.println("\n" + "The balance of your account is: " + newAccount.getBalance(newAccount));
	}

    /**
    * author:  Zejian Li
    * methodsName: addDeposit
    * description: The function is used to offer deposit service for user.
    * param:  newAccount
    * return: String
    */
	public void addDeposit(Account newAccount) {
		System.out.println("Please input the Account Number to deposit:");
		System.out.println("(Note£ºPress click Enter button on keyboard to indicate completion)");
		
		int accNo = Integer.valueOf(sc.nextLine());;

		accountExist(accNo, newAccount);
		
		  if (isExistent == false) {// Indicates the account number exists
			  System.out.println("The account numbe does not exist");
		} 
		 

	}
	
    /**
    * author:  Zejian Li
    * methodsName: setSuspended
    * description: The function is used to offer withdrawal service for user.
    * param:  newAccount
    * return: String
    */
	public void addWithdrawal(Account newAccount) {
		
		newWithdrawal.noticeMessage(newAccount);
	}

    /**
    * author:  Zejian Li
    * methodsName: setSuspended
    * description: The function is used to judge that whether the account of customer exists.
    * param:  accNo, newAccount
    * return: String
    */
	public void accountExist(int accNo, Account newAccount) {// If the account exists, you can deposit money

		String str;
		BufferedReader bre = null;
		String strAccNo = Integer.toString(accNo);
		try {
			File newFile = new File("D:\\" + newAccount.getCustomer().getName() + ".txt");
			bre = new BufferedReader(new FileReader(newFile));// File is the path of the file + the name of the file + the suffix of the file

			while ((str = bre.readLine()) != null) // Determines that the last line does not exist and ends the loop empty
			{

				if (str.equals(strAccNo)) {
					System.out.println("\n" + "The account number " + strAccNo + " does exist");
					isExistent = true;
					newDeposit.noticeMessage(newAccount, accNo);
				}
				
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("accountExist exception!!!!");
		}
	}

    /**
    * author:  Zejian Li
    * methodsName: setSuspended
    * description: The function is used to suspend the account of customer
    * param:  newAccount
    * return: String
    */
	public void setSuspended(Account newAccount) {
		System.out.println("\n" + "Are you sure you want to suspend the account?");
		System.out.println("(Note£ºEnter the number to select the appropriate service)");
		System.out.println("1. Confirm");
		System.out.println("2. Cancel");

		int choice = Integer.valueOf(sc.nextLine());;
		if ((choice == 1) || (choice == 2)) {
			if (choice == 1) {

				newAccount.isSuspended = true;
				Transaction newTr = new Transaction();
				newTr.findLineNoOfAccNo(newAccount, newAccount.getAccNo());
				newTr.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt", (newTr.linesOfAccNo + 5),
						String.valueOf(isSuspended));
				System.out.println("The account has successfully changed from normal to suspended");
			}
			if (choice == 2) {
				loginAccountSuccess(newAccount);
			}
		} else {
			System.out.println("\n" + ">_< Your input is wrong, please choose the service again");
		}
	}

    /**
    * author:  Zejian Li
    * methodsName: close
    * description: The function is used to close the account of customer
    * param:  newAccount
    * return: String
    */
	public void close(Account newAccount) {

		if (newAccount.getBalance(newAccount) > 0) {
			System.out.println("\n" + "You haven't clear all the balance in this account, clear them firstly");
			System.out.println("\n" + "Are you sure to clear your balnce in the account");
			System.out.println("(Note£ºEnter the number to select the appropriate service)");
			System.out.println("1. Confirm");
			System.out.println("2. Cancel");
			int choice = Integer.valueOf(sc.nextLine());;
			if ((choice == 1) || (choice == 2)) {
				if (choice == 1) {
					newAccount.clearBalance(newAccount);// Rewrite the balance variable as 0.0

					newAccount.isActive = false;
					Transaction newTr1 = new Transaction();
					newTr1.findLineNoOfAccNo(newAccount, newAccount.getAccNo());
					newTr1.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt",
							(newTr1.linesOfAccNo + 6), String.valueOf(newAccount.isActive));
					// Rewrite isActive variable to false

					System.out.println("\n" + "You have closed the account successfully");
					loginAccountSuccess(newAccount);
				}
				if (choice == 2) {
					loginAccountSuccess(newAccount);
				}
			} else {
				System.out.println("\n" + ">_< Your input is wrong, please choose the service again");
			}

		} else if (newAccount.getBalance(newAccount) < 0) {
			System.out.println("You have overdrawn your balance in this account. "
					+ "Please deposit money to make up the balance");
			loginAccountSuccess(newAccount);

		} else {
			System.out.println("\n" + "Are you sure you want to close the account?");
			System.out.println("(Note£ºEnter the number to select the appropriate service)");
			System.out.println("1. Confirm");
			System.out.println("2. Cancel");

			int choice = Integer.valueOf(sc.nextLine());;
			if ((choice == 1) || (choice == 2)) {
				if (choice == 1) {
					newAccount.isActive = false;
					Transaction newTr3 = new Transaction();
					newTr3.findLineNoOfAccNo(newAccount, newAccount.getAccNo());
					newTr3.replaceTxtByLineNo("D:\\" + newAccount.getCustomer().getName() + ".txt",
							(newTr3.linesOfAccNo + 6), String.valueOf(newAccount.isActive));
					// Rewrite isActive variable to false
				}
				if (choice == 2) {
					loginAccountSuccess(newAccount);
				}
			} else {
				System.out.println("\n" + ">_< Your input is wrong, please choose the service again");
			}
		}

	}

}
