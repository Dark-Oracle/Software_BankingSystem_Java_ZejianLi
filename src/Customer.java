import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * version: V4.0
 * author: Zejian Li
 * className: Customer
 * packageName: default  
 * description: This class is used to offer several functions for customer only.
 * data: 2019-05-12 20:00
 **/
public class Customer {
	
	 protected String name;     	 	//User file 0
	 protected String address;			//User file 1
	 protected String dateOfBirth;		//User file 2
	 protected boolean creditStatus;	//User file 3
	 
	 static Scanner sc = new Scanner(System.in); 
	 int age = 15;  //Age = now - birthday
	 boolean loginSuccess = false;
	 
	 //addAccount method's parameters
	 String array2Name ;
	 String array2Add;
	 String array2Dob ;
	 String array2AccType;
	 
	 //parameters in writeFileWhenAddAcc
	 int randomAccNo ; //Gets a random six - digit integer
	 String strRandomAccNo ;//Fixed prefix 2016
	 int intRandomAccNo;//integer
	 
	 int randomAccNo2 ; //Gets a random six - digit integer
	 String strRandomAccNo2 ;//Fixed prefix 2016
	 int intRandomAccNo2 ;//integer
	 
	 int intPin; //Create a new Account object newAccount will automatically call the pin generation function
	 String strPin;//Change the generated integer pin to string
	 
	 int intPin2 ; //Create a new Account object newAccount will automatically call the pin generation function
	 String strPin2 ;
	
	 public Customer() {}
	 
	 public Customer(String name, String address, String dateOfBirth) {
		 this.name = name;
		 this.address = address;
		 this.dateOfBirth = dateOfBirth;
		 creditStatus = false;
	 }
	 
	 public String getName() {
		 return this.name;
	 }
	 public String getAddress() {
		 return this.address;
	 }
	 public String getDateOfBirth() {
		 return this.dateOfBirth;
	 }
	 public boolean getCreditStatus() {
		 return this.creditStatus;
	 }
	 public void setName(String name) {
		 this.name = name;
	 }
	 public void setCreditStatus(boolean creditStatus) {
		 this.creditStatus = creditStatus;
	 }
	 
    /**
    * author:  Zejian Li
    * methodsName: confirmCreditStatus
    * description: check the credit status of the user.
    * param:  newCustomer
    * return: String
    */
	 public boolean confirmCreditStatus(Customer newCustomer) {
		 Bank_Control newBK1 = new Bank_Control(newCustomer);
		 newBK1.readCustomerInfo(newCustomer);
		 if (Bank_Control.readCreditStatus = true){
			 return Bank_Control.readCreditStatus;
		 }else {
			 System.out.println("\n"+"Your credit status is not satisfactory "
		    + "and we regret that we are unable to create a new account for you.");
			 return Bank_Control.readCreditStatus;
		 } 
	 }

    /**
    * author:  Zejian Li
    * methodsName: addAccount
    * description: the function of opening new account.
    * param:  newCustomer
    * return: String
    */
	 public void addAccount(Customer newCustomer) {

		 randomAccNo =(int) ((Math.random()*9+1)*100000); //Gets a random six - digit integer
		 strRandomAccNo = "2016"+Integer.toString(randomAccNo);//Fixed prefix 2016
		 intRandomAccNo =  Integer.parseInt(strRandomAccNo);//change to integer
		 
		 randomAccNo2 =(int) ((Math.random()*9+1)*100000); 
		 strRandomAccNo2 = "2016"+Integer.toString(randomAccNo);
		 intRandomAccNo2 =  Integer.parseInt(strRandomAccNo);
		 
		 System.out.println("\n"+"Please input the following information to add a new Account：");
		 System.out.println("(Note：Press Enter button on keyboard to input 'end' to indicate completion)"); 
		 System.out.println("1. name:");
		 //Enter a name because you may be creating a bank account for someone else
		 System.out.println("2. address");
		 System.out.println("3. date of birth: (format:YYYY-MM-DD,eg:2001-01-02)");
		 System.out.println("4. Account type:(options: saver, current, junior)");
		 
		 try {
			 ArrayList<String> strArray2 = new ArrayList<>();
			 do {
					String str = sc.nextLine();
					if (str.equals("end")) {
						break;
					}
					strArray2.add(str);
				} while (true);
			 
			 array2Name = strArray2.get(0);
			 array2Add = strArray2.get(1);
			 array2Dob = strArray2.get(2);
			 array2AccType = strArray2.get(3);
			 Account.accType = array2AccType;
	 
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
		        String sftBirth = array2Dob;
		        Date dateBirth = null;
		        try{
		            dateBirth = sft.parse(sftBirth);
		        }catch(Exception e){
		            e.printStackTrace();
		        }
		        age = getAgeByBirth(dateBirth);
		        System.out.print("Your age:" + age);
 
			 if ( confirmCreditStatus(newCustomer) == true) {
				 if(array2AccType.equals("junior")){
					 if (age < 16) {
						 if(age<0) {
							 System.out.println("The birthday is after Now,It's unbelievable,please check and enter again");		 
							 addAccount(newCustomer);
						 }else {
							  writeCusFileWhenAddAcc(newCustomer);//Write user information to the TXT document
						 }
					 }else {//age>=16
						 System.out.println("You are not under the age of 16 and cannot create junior account. "
						 + "Please select another type of account.");
						 addAccount(newCustomer);
					 }
				 }else {
					 if(age<0) {
						 System.out.println("The birthday is after Now,It's unbelievable,please check and enter again");		 
						 addAccount(newCustomer);
					 }else {
						  writeCusFileWhenAddAcc(newCustomer);//Write user information to the TXT document
					 }				
				  }
			 	}else {
			 		System.out.println("Your credit status is not good, we can not add account");
			 	}
		 }catch(Exception e){
			 System.out.println("Your input is wrong, please enter again");
			 Bank_Control newBK3 = new Bank_Control(newCustomer);
			 newBK3.loginSuccess(newCustomer);
		 }	 
	}
	 
    /**
    * author:  Zejian Li
    * methodsName: writeCusFileWhenAddAcc
    * description: write the data of account info when user open new account.
    * param:  newCustomer
    * return: String
    */
	 public void writeCusFileWhenAddAcc(Customer newCustomer) {
		 double initBalance = 0.0;
		     Bank_Control newBK2 = new Bank_Control(newCustomer);
			 newBK2.readCustomerInfo(newCustomer);//Read the user file information
			   if(array2Name.equals(newBK2.readName) && array2Add.equals(newBK2.readAddress)
			    && ( array2Dob.equals(newBK2.readDateOfBirth) )  ) {
				 
				Customer firstNewCustomer = null;
				
				/*When the user creates a new bank Account, he creates a newAccount object, newAccount, 
				 * with the constructor parameters associated with that Account
				 */
				Account firstNewAccount = new Account(intRandomAccNo, firstNewCustomer);
				 
				 intPin = firstNewAccount.pin; //Create a new Account object newAccount will automatically call the pin generation function
				 strPin = String.valueOf(intPin);//Change the generated integer pin to string
				 
				 System.out.println("Your unique Account Number is:"+ intRandomAccNo);
				 System.out.println("Your unique PIN is:" +strPin);
				 
				 newBK2.addNewToFile(newCustomer, strRandomAccNo);//Append 4: account number
				 newBK2.addNewToFile(newCustomer, array2AccType); //5: account type
				 newBK2.addNewToFile(newCustomer, strPin);		  //6: account password
				 newBK2.addNewToFile(newCustomer, String.valueOf(initBalance));//7
				 newBK2.addNewToFile(newCustomer, String.valueOf(firstNewAccount.unClearedFunds));//8
				 
				 newBK2.addNewToFile(newCustomer, String.valueOf(firstNewAccount.isSuspended));//9
				 newBK2.addNewToFile(newCustomer, String.valueOf(firstNewAccount.isActive));//10
				 newBK2.addNewToFile(newCustomer, String.valueOf(firstNewAccount.noticeNeeded));//11

				 newBK2.loginSuccess(newCustomer); //After successful creation, it will automatically return to the A interface
			   }else if(array2Name.equals(newBK2.readName) == false && array2Add.equals(newBK2.readAddress)==false
					    && array2Dob.equals(newBK2.readDateOfBirth) == false  ) 
			   	{//The basic information of the user did not find a match, instead of appending, 
				 //directly write the user's 12 pieces of information to a new file
				
				Customer secondNewCustomer = null;
				Account secondNewAccount = new Account(intRandomAccNo2, secondNewCustomer);
				//In the second case, create another new account
				
				intPin2 = secondNewAccount.pin; //Create a new Account object newAccount will automatically call the pin generation function
				strPin2 = String.valueOf(intPin2);
				
				 System.out.println("Your unique Account Number is:"+ strRandomAccNo2);
				 System.out.println("Your unique PIN is:" +strPin2);
			 
				String strBalnace = String.valueOf(initBalance);
				
				File file = new File("D:/"+ array2Name+".txt");
				FileWriter fw;
				try {
					fw = new FileWriter(file.getAbsoluteFile(),false);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(array2Name);
					bw.newLine();
					bw.write(array2Add);
					bw.newLine();
					bw.write(array2Dob);
					bw.newLine();
					bw.write(newBK2.strCreditStatus); 
					bw.newLine();
					bw.write(strRandomAccNo2);
					bw.newLine();
					bw.write(array2AccType);
					bw.newLine();
					bw.write(strPin2);
					bw.newLine();
					bw.write(strBalnace);
					bw.newLine();
					bw.write(String.valueOf(secondNewAccount.unClearedFunds));
					bw.newLine();
					bw.write(String.valueOf(secondNewAccount.isSuspended));
					bw.newLine();
					bw.write(String.valueOf(secondNewAccount.isActive));
					bw.newLine();
					bw.write(String.valueOf(secondNewAccount.noticeNeeded));
					bw.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				 newBK2.loginSuccess(newCustomer); //After successful creation, it will automatically return to the A screen
				
			 }else {
				 System.out.println("Your input of name,address,and date of birth "
				 		+ "does not match the information when you register, please input again");
				 addAccount(newCustomer);
			 }
	 }
	 
	    /**
	    * author:  Zejian Li
	    * methodsName: judgeAccount
	    * description: The interface when the user login the account
	    * param:  newCustomer
	    * return: String
	    */
	 public void loginAccount(Customer newCustomer) {
		 System.out.println("\n"+"Please input the following information to log into your account：");
		 System.out.println("(Note：Press Enter button on keyboard to input 'end' to indicate completion)"); 
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
	 	try {
	 		 judgeAccount(array3AccNo,array3PIN,newCustomer);
	 		 //Determine whether the account number and PIN entered by the user exist in the user file
	 		
	 		 if (loginSuccess == true) {
	 			 //The account number and PIN entered by the user exist in the user file, indicating that they have been registered
	 			 int intArrayAccNo = Integer.parseInt(array3AccNo);
	 			
	 			Bank_Control newBK = new Bank_Control(newCustomer);//根据newCustomer新建Bank_Control
	 			Account newAccount = new Account(intArrayAccNo,newCustomer);
	 			newBK.readAccountInfo(newAccount);
	 			
	 			if(newBK.readIsActive.equals("true")) {	 				
				 			newAccount.loginAccountSuccess(newAccount);
	 			}else {
	 				System.out.println("Sorry, your account has been closed, we can not offer service for you");
	 			}
	 			
	 			
	 		 }else {//The account number and PIN entered by the user do not exist in the user file, indicating registration
	 			System.out.println("\n"+">_< Login failed, please confirm you have registered and check the information you input");
	 			loginAccount(newCustomer);
	 		 } 	
	 	}catch (Exception e) {
	    	 System.out.println("Custoemr.java: loginAccount() Exception");
	    	 loginAccount(newCustomer);
	     	}	     
	 }
	 
    /**
    * author:  Zejian Li
    * methodsName: judgeAccount
    * description: Determine whether the account type and PIN entered by the user exist in the user file
    * param:  readAccNoInput,readAccPinInput,newCustomer
    * return: age
    */
	 public void judgeAccount(String readAccNoInput, String readAccPinInput, Customer newCustomer) {
			String str;
			BufferedReader bre = null;
			int lines1=0,lines2=0;

			try {
				File newFile=new File("D:\\"+newCustomer.getName()+".txt");
				bre = new BufferedReader(new FileReader(newFile));
				int lines =-1;
				while ((str = bre.readLine())!= null)
				{
				lines ++;
				if (str.equals(readAccNoInput)) {
					String str1 = str;
					lines1=lines;
	            	System.out.println(lines1+"\t"+str1);
				}
				if (str.equals(readAccPinInput)) {
					String str1 = str;
					lines2 = lines;
	            	System.out.println(lines2+"\t"+str1);
				}
				if( (lines2-lines1) == 2 ) { //If the account password is 2 more lines than the account number
					loginSuccess=true;
				}
				}
			}catch (IOException e) {
		         loginAccount(newCustomer);
		         System.out.println("Esrror in judgeAccount(String readAccNoInput, String readAccPinInput, Customer newCustomer)");
		        }
	}
	 
	    /**
	    * author:  Zejian Li
	    * methodsName: getAgeByBirth
	    * description: Obtain the age of user by the birthday date.
	    * param:  birthday
	    * return: age
	    */
        public static int getAgeByBirth(Date birthday){        
            /*Get a Date object from the Calendar object*/
            Calendar cal = Calendar.getInstance();
            /*Put the date of birth into the binary object of the Calendar type, and convert the Calendar and Date types.*/
            Calendar bir = Calendar.getInstance();
            bir.setTime(birthday);
            /*If the birthday is greater than the current date, an exception is thrown: the date of birth cannot be greater than the current date*/
            if(cal.before(birthday)){
            	throw new IllegalArgumentException("The birthday is before Now,It's unbelievable");
            }
            /*Retrieves the current date*/
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayNow = cal.get(Calendar.DAY_OF_MONTH);
            /*Retrieves the date of birth*/
            int yearBirth = bir.get(Calendar.YEAR);
            int monthBirth = bir.get(Calendar.MONTH);
            int dayBirth = bir.get(Calendar.DAY_OF_MONTH);
            /*The approximate age is the current year minus the year of birth*/
            int age = yearNow - yearBirth;
            /*If the current month is less than the birth month, 
             * or if the current month is equal to the birth month but the current day is less than the birth date, 
             * then the age age is one year less*/
            if(monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)){
                age--;
            }
            return age;
        }

}
