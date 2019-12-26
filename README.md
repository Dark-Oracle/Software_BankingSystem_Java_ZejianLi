# BankingSystem
This is a software of Banking System designed by Zejian Li from  Beijing University of Posts and Telecommunications (BUPT)

This banking system can provide a range of services for customer and bank administrators (employees) respectively.

Note:
1. The system will generate a user file in the D disk directory when the user registers, the file name: user name + suffix "txt".
2. After the user registers as a system user, according to the information input by the user, the user file automatically has four lines of information related to the user. They are:
a. Username b. Address c. Date of birth d.credit status. E.g:
A.lizejian
B.bupt
C.1998-10-10
D.true
3. When the user first registers as a system user, the credit status will be true, corresponding to good/satisfactory. However, the user's credit status can be changed to false (Not good/Not satisfactory) at any time in the administrator interface.
4. After the user successfully logs in to the system, the user needs to create a new bank account. Each time a user creates a bank account, the user automatically adds 8 rows of data to the user file. They are:
a. Account number
b. Account type
c. PIN (6 digits)
d. balance
e. un-cleared funds
f. isSuspended (indicating whether the user is in the suspended state)
g. isActive (indicates whether the user's account is closed)
h. noticeNeeded (This data will be rewritten to a date that can be withdrawn only after the account type is a savings account and the user sets notice, other types of accounts are false)

Running:
1. Put all java files in "java sources" into a same path
2. Compile all java files under the path
3. Run finalTest.java
4. You can enter information yourself to register the customer and account when you run the system(You have to rememer them to login.)
5. To test the system, you can use the following information in "lizejian.txt" in D disk to login customer: name:lizejian, address:bupt, dateOfBirth:1997-10-10
6. To test the system, you can use the following information in "lizejian.txt" in D disk to login account: account number: 2016927298, pin: 530823, account type: saver
7. When you are running the system, it is not allowed to open the "lizejian.txt" file, since it will cause error. To successfully run the system, you should close the file.
