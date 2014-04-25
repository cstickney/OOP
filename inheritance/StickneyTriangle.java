/**
* CSC223 Assignment 4 Triangle Class
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: The triangle class for assignment 4
* 					Contains a default constructor, a constructor that allows sides to be set, and a constructor that allows sides, color and fill to be set
* 					Contains accessors and mutators for each side
* 					Contains methods to get the area, perimeter, and a string detailing the sides of the triangle
*/
package assignment4;


public class StickneyTriangle extends StickneyGeometricObject{
	private double side1 = 1.0;
	private double side2 = 1.0;
	private double side3 = 1.0;
	
	StickneyTriangle(){
	}
	
	StickneyTriangle(double side1, double side2, double side3){
		this.side1 = side1;
		this.side2 = side2;
		this.side3 = side3;
	}
	StickneyTriangle(double side1, double side2, double side3, String color, boolean filled){
		this.side1 = side1;
		this.side2 = side2;
		this.side3 = side3;
		setColor(color);
		setFilled(filled);
	}

	// side1 accessor
	public double getSide1() {
		return side1;
	}

	//side1 mutator
	public void setSide1(double side1) {
		this.side1 = side1;
	}

	//side2 accessor
	public double getSide2() {
		return side2;
	}

	//side2 mutator
	public void setSide2(double side2) {
		this.side2 = side2;
	}

	//side3 accessor
	public double getSide3() {
		return side3;
	}

	//side3 mutator
	public void setSide3(double side3) {
		this.side3 = side3;
	}
	//returns the area of the triangle
	public double getArea(){
		if(side1+side2<=side3||side1+side3<=side2||side2+side3<=side1){//check if all 2 sides are greater than the third side
			System.out.println("Not a triangle");
			return 0;
		}
		else{
			double halfPerimeter = (side1+side2+side3)/2;
			double area = Math.sqrt(halfPerimeter*(halfPerimeter -side1)*(halfPerimeter-side2)*(halfPerimeter-side3));
			return area;
		}
	}
	//returns the perimeter of the triangle
	public double getPerimeter(){
		double perimeter = side1 + side2 + side3;
		return perimeter;
	}
	public String toString(){//returns a string detailing the sides of the triangle
		return "Triangle: side1 = " + side1 + " side2 = " + side2 +
				" side3 = " + side3;
	}
	
	
}

/**Problem Description:

Design a class named Trianglelastname that extends lastnameGeometricObject. 
The class contains:

• Three double data fields named side1, side2, and side3 with default 
values 1.0 to denote three sides of the triangle.
• A no-arg constructor that creates a default triangle.
• A constructor that creates a triangle with the specified side1, side2, 
and side3.
• The accessor methods for all three data fields.
• A method named getArea() that returns the area of this triangle.
• A method named getPerimeter() that returns the perimeter of this triangle.
• A method named toString() that returns a string description for the triangle.

For the formula to compute the area of a triangle, see Exercise 5.19. The
 toString() method is implemented as follows:

return "Triangle: side1 = " + side1 + " side2 = " + side2 +
" side3 = " + side3;

Draw the UML diagram that involves the classes lastnameTriangle and 
lastnameGeometricObject. '
*/