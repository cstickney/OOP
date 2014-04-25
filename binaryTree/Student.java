/**
* CSC223 Assignment 7 Student class
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: The Student class for assignment 7
*/
package assignment7;


public class Student{

	private String name = "empty";
	private String info = "insert info here";
	private int leftChild = -1;
	private int rightChild = -1;
	private int parent = -1;
	private static int root = -1;
	private static int classSize=0;
	
	public Student() {//default constructor
	}
	
	public Student(String name) {//construct a student with a specified name
		this.name = name;
	}

	public String getName(){//returns the student's name
		return name;
	}

	public void setName(String name){//sets the student's name
		this.name = name;
	}

	public String getInfo(){//returns the student's info
		return info;
	}

	public void setInfo(String info){//sets the student's info
		this.info = info;
	}
	
	public void appendInfo(String info){//appends to the students info
		this.info = this.info + " "+ info;
	}

	public int getLeftChild(){//returns the left child of the node
		return leftChild;
	}

	public void setLeftChild(int leftChild){//sets the left child of the node
		this.leftChild = leftChild;
	}

	public int getRightChild(){//returns the right child of the node
		return rightChild;
	}

	public void setRightChild(int rightChild){//sets the right child of the node
		this.rightChild = rightChild;
	}

	public int getParent(){//gets the parent of the node
		return parent;
	}

	public void setParent(int parent){//sets the parent of the node
		this.parent = parent;
	}
	
	public static int getRoot(){//gets the root of the tree
		return root;
	}

	public static void setRoot(int root){//sets the root of the tree
		Student.root = root;
	}

	public static int getClassSize(){//gets the size of the class (the school kind, not the java kind)
		return classSize;
	}

	public static void setClassSize(int classSize){//gets the size of the class (the school kind, not the java kind)
		Student.classSize = classSize;
	}
	
}