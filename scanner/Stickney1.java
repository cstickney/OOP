/**
* CSC223 Assignment 1
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
*/
package assignment1;

import java.util.Scanner;

public class Stickney1 {
	public static void main(String[] args){
		
		//new scanner
		Scanner input = new Scanner(System.in);
		
		//prompt for investment amount
		System.out.print("Enter investment amount: ");
		double investmentAmount = input.nextDouble();
		
		//prompt for yearly interest rate
		System.out.print("Enter yearly interest rate (in %): ");
		double annualInterestRate = input.nextDouble();
		//convert to decimal
		annualInterestRate = annualInterestRate/100;
		//convert to quarterly
		double quarterlyInterestRate = annualInterestRate/4;
		
		//prompt for years
		System.out.print("Enter number of years: ");
		double numberOfYears = input.nextDouble();
		
		//compute future investment value
		double futureInvestmentValue = investmentAmount * Math.pow(1 + quarterlyInterestRate,numberOfYears*4);
		
		//display future investment value
		System.out.printf("Accumulated value is %.2f", futureInvestmentValue);
		
	}
}

/**
 * Write a program that reads in investment amount, annual interest rate, number of years, and displays the future investment value using the compound interest formula:

futureInvestmentValue = (investmentAmount * (1 + quarterlyInterestRate))^(numberOfYears*4)
should be             = investmentAmount * (1 + quarterlyInterestRate)^(numberOfYears * 4)

We will assume that interest is compounded quarterly. Remember quarterlyInterestRate = annualInterestRate / 4

Hint: Use the Math.pow(a, b) method. This method returns "a" raised to the "b" power.

Here is a sample run:

Enter investment amount: 1000

Enter yearly interest rate: 4.32

Enter number of years: 1

Accumulated value is 1043.90

Submit the following items:
- Download the text file.
- You will submit an analysis of your program and your program. Use the moodle links to upload your edited analysis file and program.
*/
