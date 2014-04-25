/**
* CSC223 Assignment 2
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: Takes filing status and taxable income from the user and returns their total tax
*/
package assignment2;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class Stickney2 {
	public static void main(String[] args){
				
		//prompt for filing status using a JOptionPane and take user input as an integer "status"
		String filingStatusString = JOptionPane.showInputDialog("Enter filing status number: \n0: Single   1: Married Filing Jointly or Qualified Widow(er)   2: Married Filing Separately   3: Head of Household");
		int status = Integer.parseInt(filingStatusString);
		
		//prompt for taxable income using a JOptionPane and take user input as an integer "taxable income"
		String taxableIncomeString = JOptionPane.showInputDialog("Enter your taxable income:"); 
		double taxableIncome = Double.parseDouble(taxableIncomeString);
		
		//call compute tax method
		double tax = computetax(status, taxableIncome);
		
		//formats tax to always display 2 decimal places using DecimalFormat and then writes that value to a string.
		DecimalFormat currency = new DecimalFormat("0.00");
		String taxString = currency.format(tax);
		
		//display tax in a JOptionPane
		JOptionPane.showMessageDialog(null, "Tax is $" + taxString );		
	}

	public static double computetax(int status, double taxableIncome){//takes filing status and taxable income and returns the tax on that income
		//integer variables used to hold the upper bound of each tax bracket. Note: the 35% tax bracket has no upper bound, and thus no variable is needed
		int ten=0; 
		int fifteen=0;
		int twentyfive=0;
		int twentyeight=0;
		int thirtythree=0;
		double tax;
		
		switch (status) { //assigns upper bound of each percentage based on what filing status is chosen
			case 0: //single
				ten = 8350;
				fifteen = 33950;
				twentyfive = 82250;
				twentyeight = 171550;
				thirtythree = 372950;
				break;
			case 1: //married filing jointly or qualified widow(er)
				ten = 16700;
				fifteen = 67900;
				twentyfive = 137050;
				twentyeight = 208850;
				thirtythree = 372950;
				break;
			case 2: //married filing separately
				ten = 8350;
				fifteen = 33950;
				twentyfive = 68525;
				twentyeight = 104425;
				thirtythree = 186475;
				break;
			case 3: //head of household
				ten = 11950;
				fifteen = 45500;
				twentyfive = 117450;
				twentyeight = 190200;
				thirtythree = 372950;
				break;
			default: //numbers other than 0-3
				System.out.println("Error: Invalid filing status.");
				System.exit(1);
		}
		
		//checks the upper bound of each tax bracket, stopping when taxableIncome is less than an upper bound or in the highest tax bracket, and uses the equation for that tax bracket.
		if(isgreaterthan(ten, taxableIncome)==false){//tests if in 10% bracket
			tax = taxableIncome*.1;
		}
		else if(isgreaterthan(fifteen, taxableIncome)==false){//tests if in 15% bracket
			tax = ten*.1 +(taxableIncome-ten)*.15;
		}
		else if(isgreaterthan(twentyfive, taxableIncome)==false){//tests if in 25% bracket
			tax = ten*.1 +(fifteen-ten)*.15 +(taxableIncome-fifteen)*.25;
		}
		else if(isgreaterthan(twentyeight, taxableIncome)==false){//tests if in 28% bracket
			tax = ten*.1 +(fifteen-ten)*.15 +(twentyfive-fifteen)*.25 +(taxableIncome-twentyfive)*.28;
		}
		else if(isgreaterthan(thirtythree, taxableIncome)==false){//tests if in 33% bracket
			tax = ten*.1 +(fifteen-ten)*.15 +(twentyfive-fifteen)*.25 +(twentyeight-twentyfive)*.28 +(taxableIncome-twentyeight)*.33;
		}
		else{//is in 35% bracket at this point
			tax = ten*.1 +(fifteen-ten)*.15 +(twentyfive-fifteen)*.25 +(twentyeight-twentyfive)*.28 +(thirtythree-twentyeight)*.33 +(taxableIncome -thirtythree)*.35;
		}
		return tax;
	}
	
	public static boolean isgreaterthan(int percentCap, double taxableIncome){ //checks if variable1 > variable2
		//checks if taxableIncome extends past the cap of the tax bracket.
		if(taxableIncome > percentCap)
			return true;//taxable income is greater
		else
			return false;//tax bracket upper bound is greater
	}
}

