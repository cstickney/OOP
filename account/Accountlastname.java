/**
* CSC223 Assignment 3 Account Class
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: The account class for assignment 3
* 				   Contains constructors to create an account with no inputs or with specified id and starting balance
* 				   Contains accessors to return the account's id, balance, monthly interest rate, yearly interest rate and date created
* 				   Contains mutators to alter the account's id, balance and yearly interest rate
* 				   Contains methods to deposit and withdraw money to/from the account's balance
*/
package assignment3;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Accountlastname{
	private int id=0;
	private double balance =0;
	private double annualInterestRate =0;
	Date dateCreated = new Date();
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	DecimalFormat currency = new DecimalFormat("0.00");
		
	Accountlastname(){//create an account with no variables initialized
	}
	
	Accountlastname(int newId, double newBalance){//create an account with a predefined id and balance
		id=newId;
		balance=newBalance;
	}
	
	int getId(){//return the id of this account
		return id;
	}
	String getBalanceFormatted(){//return the balance of this account formatted to have only two decimal places
		return currency.format(balance);
	}
	double getBalance(){//return the unaltered balance of this account
		return balance;
	}
	double getAnnualInterestRate(){//return the annual interest rate of this account
		return annualInterestRate;
	}
	void setId(int newId){//set a new id for this account
		id=newId;
	}
	void setBalance(double newBalance){//set a new balance for this account
		balance=newBalance;
	}
	void setAnnualInterestRate(double newAnnualInterestRate){//set a new annual interest rate for this account
		annualInterestRate = newAnnualInterestRate;
	}
	String getDateCreated(){//return the date this account was created in mm/dd/yyyy format
		return dateFormat.format(dateCreated);
	}
	double getMonthlyInterestRate(){//returns the monthly interest rate, based on the yearly interest rate divided by 12
		double monthlyInterestRate = annualInterestRate/12;
		return monthlyInterestRate;
	}
	void withdraw(double withdrawal){//subtracts a user inputed double from the balance, unless this would put the account in the negative
		if(balance >= withdrawal)
			balance -= withdrawal;
		else
			System.out.println("Withdrawal amount exceeds available funds");
	}
	void deposit(double deposit){//adds a user inputed double to the balance
		balance += deposit;
	}
}