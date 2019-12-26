import java.util.Scanner;

/**
 * version: V4.0
 * author: Zejian Li
 * className: Deposit
 * packageName: default  
 * description: This class is used to offer functions for deposit.
 * data: 2019-05-12 20:00
 **/
public class Deposit extends Transaction{

	protected boolean cleared;
	protected static Scanner sc = new Scanner(System.in);
	
	
	
	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}
	
	public void noticeMessage(Account newAccount,int accNo) {//screen D
		 System.out.println("Please enter the amount you want to deposit:");
		 System.out.println("(Note: Click Enter on keyboard to indicate completion)"); 
		 amount = Integer.valueOf(sc.nextLine());; 
		 System.out.println("Please choose the kind of money you want to deposit: cash or cheque:");
		 System.out.println("(Note£ºEnter the number to select the appropriate service)"); 
		 System.out.println("1. Cash"); 
		 System.out.println("2. Cheque");
	     int choice = Integer.valueOf(sc.nextLine());;      
	     if ((choice == 1) || (choice ==2) ){
	         
	    	 if (choice == 1){
	    		/*1. Read the balance variable in the user file 
	    		 * 2. Save the balance variable +amount
	    		 * 3. Write the changed balance variable to the file.*/
	    		 double readBalance1 = newAccount.getBalance(newAccount);//1
	    		 double newBalance = readBalance1 + amount;//2
	    		 
	    		 findLineNoOfAccNo(newAccount,accNo); 
	    		 replaceTxtByLineNo("D:\\"+newAccount.getCustomer().getName()+".txt",
	    				 (linesOfAccNo+3), String.valueOf(newBalance));//3
	    		 
	    		 System.out.println("linesOfAccNo+3:"+ (linesOfAccNo+3));
	    		 System.out.println("\n"+"You successfully deposit "+amount+" $"+
	    				"\n"+"Current Balance: "+ newAccount.getBalance(newAccount));

	    		 System.out.println("Current Time: "+ df.format(System.currentTimeMillis())); 
	    		 //Get System Time
	         }
	         if (choice == 2){
	        	 /*1. Read the unClearedFunds variable in the user file 
	        	  * 2. Write the unClearedFunds variable + amount
	        	  * 3. Write the changed unClearedFunds variable to the file*/        	 
	        	 double readUnClearedFunds = newAccount.getUnClearedFunds(newAccount);//1
	    		 double newUnClearedFunds = readUnClearedFunds + amount;//2
	    		 
	    		 findLineNoOfAccNo(newAccount,accNo); 
	    		 replaceTxtByLineNo("D:\\"+newAccount.getCustomer().getName()+".txt",
	    				 (linesOfAccNo+4), String.valueOf(newUnClearedFunds));//3
	        	 	        	 
	        	 newAccount.unClearedFunds += amount; 
	        	  newAccount.deposit(0);
	        	  System.out.println("You have "+amount+" $ to be cleared, and it will be credited to account after clearing");
	        	  System.out.println("Current Time: "+ df.format(System.currentTimeMillis())); 
	         }
	     }
	     else{
	     System.out.println("---The input is wrong, please start again---"+"\n"+"\n");
	     noticeMessage();
	     }
	}

}
