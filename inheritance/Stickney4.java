/**
* CSC223 Assignment 4 Triangle Class
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: Tests all the triangle class methods.
*/
package assignment4;

import java.util.Scanner;


public class Stickney4{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
			System.out.println("Do you want to create a default triangle, or a triangle with specified sides?  (0:default 1:specified sides)");
			switch(input.nextInt()){
			case 0:
				StickneyTriangle newTriangle = new StickneyTriangle();
				while(true){
					System.out.println("press 0 if you want side1's length, 1 if you want side2's length, 2 if you want side3's length");
					System.out.println("3 if you want area, 4 if you want perimeter, 5 if you want triangle details in a string, anything else quits ");
					switch(input.nextInt()){
						case 0:
							System.out.println(newTriangle.getSide1());
							break;
						case 1:
							System.out.println(newTriangle.getSide1());
							break;
						case 2:
							System.out.println(newTriangle.getSide1());
							break;
						case 3:
							System.out.println(newTriangle.getArea());
							break;
						case 4:
							System.out.println(newTriangle.getPerimeter());
							break;
						case 5:
							System.out.println(newTriangle.toString());
							break;
						default:
							System.exit(1);
					}
				}
			case 1:
				System.out.println("Enter first side length");
				double side1= input.nextDouble();
				System.out.println("Enter second side length");
				double side2= input.nextDouble();
				System.out.println("Enter third side length");
				double side3= input.nextDouble();
				StickneyTriangle newTriangle2 = new StickneyTriangle(side1, side2, side3);
				System.out.println("Enter first side length");
				while(true){
					System.out.println("press 0 if you want side1's length, 1 if you want side2's length, 2 if you want side3's length");
					System.out.println("3 if you want area, 4 if you want perimeter, 5 if you want triangle details in a string, anything else quits ");
					switch(input.nextInt()){
						case 0:
							System.out.println(newTriangle2.getSide1());
							break;
						case 1:
							System.out.println(newTriangle2.getSide1());
							break;
						case 2:
							System.out.println(newTriangle2.getSide1());
							break;
						case 3:
							System.out.println(newTriangle2.getArea());
							break;
						case 4:
							System.out.println(newTriangle2.getPerimeter());
							break;
						case 5:
							System.out.println(newTriangle2.toString());
							break;
						default:
							System.exit(1);
					}
				}
				
			default:
				System.out.println("you broke it. invalid input. quitting.");
				System.exit(1);
		}
		
	}
}
