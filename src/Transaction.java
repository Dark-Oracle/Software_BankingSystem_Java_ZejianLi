import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
/**
 * version: V4.0
 * author: Zejian Li
 * className: finalTest
 * packageName: default  
 * description: This class is used to offer several functions of transaction.
 * data: 2019-05-12 20:00
 **/
public class Transaction {
	
	double amount=0.0;
	Date date, time;
	int linesOfAccNo=0;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	public void noticeMessage() {}
	
	 public void findLineNoOfAccNo(Account newAccount,int accNo) {
		 //Find the number of rows where the account is located
			String str;
			BufferedReader bre = null;

			try {
				File newFile=new File("D:\\"+newAccount.getCustomer().getName()+".txt");
				bre = new BufferedReader(new FileReader(newFile));
				int lines =0;
				while ((str = bre.readLine())!= null) 
				{
				lines ++;
					if (str.equals(String.valueOf(accNo))) {
						String str1 = str;
						linesOfAccNo=lines;
		            	System.out.println(linesOfAccNo+"\t"+str1);
					}
				}
			}catch (IOException e) {
		         e.printStackTrace();
		        }
	}
 
	 public void replaceTxtByLineNo(String path,int lineNo,String newStr) {
	       
			String temp = "";
	        try {
	            File file = new File(path);
	            FileInputStream fis = new FileInputStream(file);
	            InputStreamReader isr = new InputStreamReader(fis);
	            BufferedReader br = new BufferedReader(isr);
	            StringBuffer buf = new StringBuffer();

	            //Save the previous contents of the line
	            for (int j = 1; (temp = br.readLine()) != null; j++) {
	                if(j==lineNo){
	                    buf = buf.append(newStr);
	                }else{
	                    buf = buf.append(temp);
	                }
	                buf = buf.append(System.getProperty("line.separator"));
	            }

	            br.close();
	            FileOutputStream fos = new FileOutputStream(file);
	            PrintWriter pw = new PrintWriter(fos);
	            pw.write(buf.toString().toCharArray());
	            pw.flush();
	            pw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } 
 
}
