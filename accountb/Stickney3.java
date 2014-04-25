/**
* CSC223 Assignment 3 Account Class
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: Creates an account that simulates the storage of funds, and holds data for id, balance, yearly interest rate and date created.
* 				   Allows users to set up the account id, balance and yearly interest manually
* 				   Allows users to deposit and withdraw simulated money from this simulated account
* 				   Allows users to display values associated with this account, as well as the derived value of monthly interest rate
* 				   Allows users to automatically run the test scenario with only one input
*/
package assignment3b;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Stickney3 {
	public static void main(String[] args){
		int testId =1122;
		double testBalance =20000;
		double testWithdrawal =2500;
		double testDeposit =3000;
		double testInterestRate =4.5;
		DecimalFormat currency = new DecimalFormat("0.00");
		Scanner input = new Scanner(System.in);
		
		//prompts user on whether they would like to manually enter a value
		System.out.println("Do you want to enter your own values?  (0:no 1:yes)");
		
		switch(input.nextInt()){//uses user input to determine whether the program will be automated or manually executed
			case 0://executes the test program automatically
				System.out.println("Executing test program...");
				
				AccountStickney test = new AccountStickney(testId, testBalance);//creates a new account using the test id(1122) and balance($20,000)
				System.out.println("New account created. Id: " + test.getId() +" Balance: $"+ test.getBalance());
				
				test.setAnnualInterestRate(testInterestRate);//sets the interest rate of the account to the test value(4.5%)
				System.out.println("Interest rate set to " + test.getAnnualInterestRate() +"%");
				
				test.withdraw(testWithdrawal);//withdraws the test amount($2,500)
				System.out.println("Withdrew $" + currency.format(testWithdrawal) + ". Current balance is now $" + test.getBalance());
				
				test.withdraw(testDeposit);//deposits the test amount($3,000)
				System.out.println("Deposited $" + currency.format(testDeposit) + ". Current balance is now $" +test.getBalance());
				
				//displays the balance of the account, the monthly interest of the account, and the account creation date
				System.out.print("Final balance: $" + test.getBalance() + "\nMonthly interest: " + test.getMonthlyInterestRate() + "%\nAccount creation date: " + test.getDateCreated());
				break;
				
			case 1://manual account creation
				System.out.print("This is only a test program, so answer carefully...\nCreating new account...\n\n");
				AccountStickney user = new AccountStickney();//creates a new uninitialized account
				
				//prompts the user for an account number
				System.out.println("Enter an account number. (integers only please)");
				user.setId(input.nextInt());
				
				//prompts the user for a starting balance, does not proceed until a nonnegative balance is entered
				boolean validValue = false;
				while(validValue == false){
					System.out.println("Enter a positive starting balance.");
					user.setBalance(input.nextDouble());
				
					if(user.getBalance() < 0)
						System.out.println("Invalid balance. Please enter a positive number.");
					else
						validValue=true;		
				}
				
				//prompts the user for a yearly interest rate
				System.out.println("Enter a yearly interest rate. (in %) ");
				user.setAnnualInterestRate(input.nextDouble());
				
				//enters into a loop that allows the user to perform various actions on their account based on user input
				while(true){
					System.out.print("Select an action:\n(0:Withdrawal, 1:Deposit, 2:View current balance, 3:View account creation date, 4:View annual interest rate,\n5:View monthly interest rate, 6:Display account id, Any other number:Exit, Non-integers:Crash the program)\n");
					switch(input.nextInt())	{
						case 0://withdraw
							System.out.println("How much would you like to withdraw?");
							user.withdraw(input.nextDouble());
							System.out.println();
							break;
						case 1://deposit
							System.out.println("How much would you like to deposit?");
							user.deposit(input.nextDouble());
							System.out.println();
							break;
						case 2://display account balance
							System.out.println("Current balance: $" + user.getBalance());
							System.out.println();
							break;
						case 3://display account creation date
							System.out.println("Account creation date: " + user.getDateCreated());
							System.out.println();
							break;
						case 4://display account annual interest rate
							System.out.println("Annual interest rate: " + user.getAnnualInterestRate() +"%");
							System.out.println();
							break;
						case 5://display account monthly interest rate
							System.out.println("Monthly interest rate: " + user.getMonthlyInterestRate() +"%");
							System.out.println();
							break;
						case 6://display account id
							System.out.println("Account id: " + user.getId());
							System.out.println();
							break;
						default://exit
							System.out.println("Exiting...");
							System.exit(1);
					}
				}
			default://if not 1 or 2, exit
				System.out.println("Error: Invalid entry.");
				System.exit(1);
		}
	}
}
