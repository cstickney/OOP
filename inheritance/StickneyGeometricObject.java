/**
* CSC223 Assignment 4 Triangle Class
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: The Geometric Object class for assignment 4
* 				   Contains a default constructor and a constructor that takes color and fill
* 				   Contains accessors for the date of creation, color and fill
* 				   Contains mutators for color and fill
* 				   Contains a method that outputs the data contained within the object
*/
package assignment4;


public class StickneyGeometricObject{

	private String color = "white";
	private boolean filled;
	private java.util.Date dateCreated;
	
	/* Construct a default geometric object */
	public StickneyGeometricObject() {
		dateCreated = new java.util.Date();
	}
	
	/* Construct a geometric object with the specified color 
	 * and filled value */
	public StickneyGeometricObject(String color, boolean filled) {
		this.color = color;
		this.filled = filled;
	}
	
	/* Return color */
	public String getColor() {
		return color;
	}
	
	/* Set a new color */
	public void setColor(String color) {
		this.color = color;
	}
	
	/* Return filled. Since filled is boolean,
	 * its get method is named isFilled */
	public boolean isFilled() {
		return filled;
	}
	
	/* Set a new filled */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	/* Get dateCreated */
	public java.util.Date getDateCreated() {
		return dateCreated;
	}
	
	/* Return a string representation of this object */
	public String toString() {
		return "created on " + dateCreated + "\ncolor: " + color +
				" and filled: " + filled;
	}
}