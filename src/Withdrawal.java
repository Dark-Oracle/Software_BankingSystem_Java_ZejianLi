import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * version: V4.0
 * author: Zejian Li
 * className: finalTest
 * packageName: default  
 * description: This class is used to offer functions of withdrawal.
 * data: 2019-05-12 20:00
 **/
public class Withdrawal extends Transaction{
	
	protected static Scanner sc = new Scanner(System.in);
	protected boolean withdrawalAllowed = true; 
	Date dateReadNoticeNeeded;//The date on which you can withdraw money
    Date currDate;//current date
	

    /**
    * author:  Zejian Li
    * methodsName: noticeMessage
    * description: The operation interface when user withdraw from the account.
    * param:  newCustomer
    * return: String
    */
	public void noticeMessage(Account newAccount) {//screen F
		 System.out.println("\n"+"Please input the following information to withdraw funds£º");
		 System.out.println("(Note£ºPress Enter button on keyboard to indicate completion)"); 
		 System.out.println("1. Account type:(options: saver, current, junior)");
		 System.out.println("2. Account number:");
		 System.out.println("3. PIN:");
		 System.out.println("4. Amount to withdraw:");
		 ArrayList<String> strArray4 = new ArrayList<>();
		 do {
				String str = sc.nextLine();
				if (str.equals("end")) {
					break;
				}
				strArray4.add(str);
			} while (true);
		 String arrayType = strArray4.get(0);
		 String arrayAccNo = strArray4.get(1);
		 String arrayPIN = strArray4.get(2);
		 String arrayAmount = strArray4.get(3);
		 amount =Integer.parseInt(arrayAmount);
		 if(arrayType.equals("saver")) {
			 Account newAccountAccordingToUserInput = new Account(Integer.valueOf(arrayAccNo),newAccount.getCustomer());
			 Bank_Control newBK23 = new Bank_Control(newAccountAccordingToUserInput.getCustomer());
			
			 newBK23.readAccountInfo(newAccountAccordingToUserInput);
			 
			 if(newBK23.readNoticeNeeded.equals("ture") || 
					 newBK23.readNoticeNeeded.equals("false") ) {//If the user first setNotice
				 
				 newBK23.setNotice(newAccount);
				 System.out.println("You have set notice successfuly.");
				 noticeMessage(newAccount) ;
			 }else {
				 /*The user has already set the notification before, 
				  * now only need to judge the current date and the specified date size.
				  */
				 
				 //The string format date read from the txt file is converted to the date type.
				 try  
				 {  
				     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  			     
				     dateReadNoticeNeeded = sdf.parse(newBK23.readNoticeNeeded);//The date on which you can withdraw money
				     currDate = new Date();//current date
				 }  
				 catch (ParseException e)  
				 {  
				     System.out.println(e.getMessage());  
				 }  

				 if ( dateReadNoticeNeeded.before(currDate)) {
					 /*You can withdraw money if the specified date is before the current date*/
					 double assumeBalanceResult = newAccount.getBalance(newAccount) - amount;
					 if( assumeBalanceResult >= 0) {
						 //If the balance after withdrawal is greater than 0, the withdrawal operation can be performed
						 
						 double readBalance2 = newAccount.getBalance(newAccount);//1
						 double newBalance2 = readBalance2 - amount;//2
						 findLineNoOfAccNo(newAccount,newAccount.getAccNo()); 
						 
						 replaceTxtByLineNo("D:\\"+newAccount.getCustomer().getName()+".txt",
			    				 (linesOfAccNo+3), String.valueOf(newBalance2));//3

						 System.out.println("\n"+"You successfully withdraw "+amount+" $"+
				    				"\n"+"Current Balance: "+ newAccount.getBalance(newAccount));
				    			 
				    		 System.out.println("Current Time: "+ df.format(System.currentTimeMillis())); 
					 } else {
						 System.out.println("\n"+"The amount you withdraw exceeds the balance of the account. "
						 		+ "Please input the withdrawal amount again ");
						 noticeMessage();
					 	}
				 }else {
					 System.out.println("Withdrawal is not allowed until:"+ dateReadNoticeNeeded);
				 }
			 }
	 
			
		 }else if (arrayType.equals("current")){
			 double assumeBalanceResult2 = newAccount.getBalance(newAccount) - amount;
			 if( (assumeBalanceResult2 + Account.overdraftLimit ) >= 0 ) {
				 
				 /*1. Read the balance variable in the user file. 2. Write the balance variable - amount
				  * 3. Write the changed balance variable to the file.*/
				 
				 double readBalance3 = newAccount.getBalance(newAccount);//1
				 double newBalance3 = readBalance3 - amount;//2
				 findLineNoOfAccNo(newAccount,newAccount.getAccNo()); 
				 
				 replaceTxtByLineNo("D:\\"+newAccount.getCustomer().getName()+".txt",
	    				 (linesOfAccNo+3), String.valueOf(newBalance3));//3
				 
				 System.out.println("\n"+"You successfully withdraw "+amount+" $"+
		    				"\n"+"Current Balance: "+ newAccount.getBalance(newAccount));
		    			 
		    		 System.out.println("Current Time: "+ df.format(System.currentTimeMillis())); 
			 }else {
				 System.out.println("\n"+"The amount you withdraw exceeds the overdraft limit. "
					 		+ "Please input the withdrawal amount again ");
					 noticeMessage();
			 	}
		 }else if (arrayType.equals("junior")) {
			 double assumeBalanceResult3 = newAccount.getBalance(newAccount) - amount;
			 if( assumeBalanceResult3 >= 0) {
				 
				 /*1. Read the balance variable in the user file. 2. Write the balance variable -amount
				  * 3. Write the changed balance variable to the file.*/
				 
				 double readBalance4 = newAccount.getBalance(newAccount);//1
				 double newBalance4 = readBalance4 - amount;//2
				 findLineNoOfAccNo(newAccount,newAccount.getAccNo()); 
				 
				 replaceTxtByLineNo("D:\\"+newAccount.getCustomer().getName()+".txt",
	    				 (linesOfAccNo+3), String.valueOf(newBalance4));//3
				 
				 System.out.println("\n"+"You successfully withdraw "+amount+" $"+
		    				"\n"+"Current Balance: "+ newAccount.getBalance(newAccount));
		    			 
		    		 System.out.println("Current Time: "+ df.format(System.currentTimeMillis())); 
			 } else {
				 System.out.println("\n"+"The amount you withdraw exceeds the balance of the account. "
				 		+ "Please input the withdrawal amount again ");
				 noticeMessage();
			 	}
		 }else {
			 System.out.println("The account type you enter is wrong, please input again");
			 noticeMessage();
		 }
	}	 
}
