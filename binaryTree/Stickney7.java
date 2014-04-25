/**
* CSC223 Assignment 7 Binary Tree Student Lookup
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: 
*/
package assignment7;

import java.util.Scanner;


public class Stickney7{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		Student[] roster = new Student[100];
		String textIn;
		char selection;
		int location = -1;
		
		while(true){//main program loop
			System.out.println("1: add student, 2: find student, 3:remove student 4: add info to student, 5: print roster, 6: exit");//prompts the user to make a selection
			textIn = input.nextLine();
			if(textIn.isEmpty()){
				System.out.println("Invalid selection");
			}
			else{
				selection = textIn.charAt(0);
				switch(selection){
				case '1'://adds a student to the roster
					roster = addStudent(roster);
					break;
				case '2'://checks to see if a student is in the roster. if found, prints their name and info
					location = findStudent(roster);
					if(location == -1){//student is not in the roster
						System.out.println("Student is not in the roster.");
					}
					else{//student is in the roster, prints the student's name and info
						System.out.println(roster[location].getName()+ ": "+ roster[location].getInfo());
					}
					break;
				case '3': //removes a student from the roster
					roster = removeStudent(roster);
					break;
				case '4':
					roster = addInfo(roster);
					break;
				case '5': //prints all the students in the roster in alphabetical order
					printRoster(roster);
					break;
				case '6'://terminates program
					System.exit(1);
				default:
					System.out.println("Invalid selection");
				}
			}
		}
	}

	public static Student[] addStudent(Student[] roster){//adds a student to the tree
		Scanner input = new Scanner(System.in);
		String newName;
		int classSize = Student.getClassSize();
		int root = Student.getRoot();
		int currentNode;
		
		if(classSize == roster.length){//if all the slots in the array have been used, does not add a student and returns
			System.out.println("Class is full. Cannot add additional students.");
			return roster;
		}
		else{//array has space left in it
			System.out.println("What is this Student's name?");//prompts for the student's name to be added
			newName = input.nextLine();
			roster[classSize]= new Student(newName);
			if(root == -1){//creates a root for the tree
				Student.setRoot(classSize);
			}
			else{//function that sorts this into the tree
				boolean isLeaf = false;
				currentNode = root;
				while(!isLeaf){//loops until the name can be placed at the bottom of the tree
					if(roster[currentNode].getName().compareTo(newName) > 0){//if the name is lower in the alphabet than the node's name
						if(roster[currentNode].getLeftChild() == -1){//if the left child is open, makes the new name the left child and exits the loop
							roster[currentNode].setLeftChild(classSize);
							roster[classSize].setParent(currentNode);
							isLeaf = true;
						}
						else{//left child is taken, moves to the left child and checks again
							currentNode = roster[currentNode].getLeftChild();
						}
					}
					else{//if the name is equal to or higher in the alphabet than the node's name
						if(roster[currentNode].getRightChild() == -1){//if the right child is open, makes the new name the right child
							roster[currentNode].setRightChild(classSize);
							roster[classSize].setParent(currentNode);
							isLeaf = true;
						}
						else{//right child is taken, moves to the right child and checks again
							currentNode = roster[currentNode].getRightChild();
						}
					}
				}
				
			}
			
			Student.setClassSize(classSize+1);//increments to the next available slot in the array
			return roster;
		}
	}

	private static int findStudent(Student[] roster){//finds a student's location in the tree and returns its location in the array, or returns -1 if not present
		Scanner input = new Scanner(System.in);
		String newName;
		int location=-1;
		int currentNode = Student.getRoot();
		boolean searchComplete = false;
		
		System.out.println("What name are you looking for?");//prompts the user for the student's name
		newName = input.nextLine();
		while(!searchComplete){//loops until the name is found or shown to be not in the tree
			if(roster[currentNode].getName().equals(newName)){//if the currently checked node has the name
				location = currentNode;
				searchComplete = true;
			}
			else{//the names do not match
				if(roster[currentNode].getName().compareTo(newName) > 0){//if the searched name is before the node's name in the alphabet
					if(roster[currentNode].getLeftChild() == -1){//if there is no left node, terminates the search
						location=-1;
						searchComplete = true;
					}
					else{//if there is a left node, moves to it
						currentNode = roster[currentNode].getLeftChild();
					}
				}
				else{//if the searched name is equal to (dont see how this could happen, but...) or after the nodes name in the alphabet
					if(roster[currentNode].getRightChild() == -1){//if there is no right node, terminates the search
						location=-1;
						searchComplete = true;
					}
					else{//if there is a right node, moves to it
						currentNode = roster[currentNode].getRightChild();
					}
				}
			}
		}
		
		return location;
	}
	
	private static Student[] addInfo(Student[] roster){//adds info to a student
		Scanner input = new Scanner(System.in);
		int location = findStudent(roster);
		char selection;
		
		if(location == -1){//student is not found in the tree
			System.out.println("Student is not in the roster.");
			return roster;
		}
		else{
			System.out.println(roster[location].getName()+ " : "+ roster[location].getInfo());//prints the student's name and info
			while(true){//loops until a valid input is selected
				System.out.println("Do you want to append or replace the student's info? 1:append, 2: replace");//prompts the user for how they want to add info
				
				String textIn = input.nextLine();
				if(textIn.isEmpty()){
					System.out.println("Invalid selection");
				}
				else{
					selection =textIn.charAt(0);
					if(selection == '1'){//append
						System.out.println("Enter some info to append.");
						roster[location].appendInfo(input.nextLine());
						return roster;
					}
					else if(selection == '2'){//replace
						System.out.println("Enter some new info.");
						roster[location].setInfo(input.nextLine());
						return roster;
					}
					else{
						System.out.println("Invalid selection");

					}
				}
			}
		}
		
	}
	
	private static Student[] removeStudent(Student[] roster){//removes a student from the tree so it can no longer be found in the roster
		int location = findStudent(roster);
		if(location == -1){//if the student is not present in the roster
			System.out.println("Student is not in the roster.");
			return roster;
		}
		int parent = roster[location].getParent();
		int leftChild = roster[location].getLeftChild();
		int rightChild = roster[location].getRightChild();
		int currentNode= rightChild;
		boolean isLeaf = false;
		
		if(roster[location].getLeftChild() ==-1 && roster[location].getRightChild() == -1){//node is a leaf
			if(roster[location].getParent() == -1){//node is the root
				Student.setRoot(-1);
			}
			else{
				if(roster[parent].getRightChild() == location){//resets parent's right child
					roster[parent].setRightChild(-1);
				}
				else{//resets parent's left child
					roster[parent].setLeftChild(-1);
				}
			}
		}
		else if(roster[location].getLeftChild() != -1 && roster[location].getRightChild() == -1){//node has only a left child
			if(roster[location].getParent() == -1){//node is the root
				Student.setRoot(leftChild);
				roster[leftChild].setParent(-1);
			}
			else{//node is not the root
				roster[parent].setLeftChild(leftChild);
				roster[leftChild].setParent(parent);
			}
		}
		else if(roster[location].getRightChild() != -1 && roster[location].getLeftChild() == -1){//node has only a right child
			if(roster[location].getParent() == -1){//node is the root
				Student.setRoot(rightChild);
				roster[rightChild].setParent(-1);
				
			}
			else{//node is not the root
				roster[parent].setRightChild(rightChild);
				roster[rightChild].setParent(parent);
			}
		}
		else{//node has two children
			while(!isLeaf){//searches for the leftmost node in the right subtree
				if(roster[currentNode].getLeftChild() != -1){//checks if the current node has a left child
					currentNode = roster[currentNode].getLeftChild();
				}
				else{
					isLeaf = true;
				}
			}
			roster[location].setName(roster[currentNode].getName());
			roster[location].setInfo(roster[currentNode].getInfo());
			roster = removeDuplicate(roster, currentNode);//removes the copied node from the tree
		}
		return roster;
	}
	
	private static Student[] removeDuplicate(Student[] roster, int location){//removes the copied student from the tree when removing a node with 2 children
		int parent = roster[location].getParent();
		int leftChild = roster[location].getLeftChild();
		int rightChild = roster[location].getRightChild();
		
		if(roster[location].getLeftChild() ==-1 && roster[location].getRightChild() == -1){//node is a leaf
			if(roster[location].getParent() == -1){//node is the root
				Student.setRoot(-1);
			}
			else{
				if(roster[parent].getRightChild() == location){//resets parent's right child
					roster[parent].setRightChild(-1);
				}
				else{//resets parent's left child
					roster[parent].setLeftChild(-1);
				}
			}
		}
		else if(roster[location].getLeftChild() != -1 && roster[location].getRightChild() == -1){//node has only a left child
			if(roster[location].getParent() == -1){//node is the root
				Student.setRoot(leftChild);
			}
			else{//node is not the root
				roster[parent].setLeftChild(leftChild);
				roster[leftChild].setParent(parent);
			}
		}
		else{//node has only a right child
			if(roster[location].getParent() == -1){//node is the root
				Student.setRoot(rightChild);
			}
			else{//node is not the root
				roster[parent].setRightChild(rightChild);
				roster[rightChild].setParent(parent);
			}
		}
		return roster;
	}
	
	private static void printRoster(Student[] roster){//prints all the students in the class in alphabetical order
		int lastNode = -1, currentNode = Student.getRoot();
		if(currentNode == -1){//if the tree is empty, return
			return;
		}
		else while(true){
			if(lastNode == -1 || lastNode == roster[currentNode].getParent()){//root or came from parent. tries to go left, prints self, tries to go right, tries to go up
				if(roster[currentNode].getLeftChild() != -1){//left node is present, goes to left node
					lastNode = currentNode;
					currentNode = roster[lastNode].getLeftChild();
				}
				else if(roster[currentNode].getLeftChild() == -1){//left node is absent, prints self and checks right node
					System.out.println(roster[currentNode].getName() + ": "+ roster[currentNode].getInfo()); //prints the name and info of the current node
					if(roster[currentNode].getRightChild() != -1){//right node is present, goes to right node
						lastNode = currentNode;
						currentNode = roster[lastNode].getRightChild();
					}
					else if(roster[currentNode].getRightChild() == -1){//right node is absent, tries to go up, returns if currentNode = root
						if(currentNode != Student.getRoot()){//current node != root, currentnode is now parent
							lastNode = currentNode;
							currentNode = roster[lastNode].getParent();
						}
						else{//current node is root, returns
							return;
						}
					}
				}
			}
			else if(lastNode == roster[currentNode].getLeftChild()){//came from left child. prints self, tries to go right, then tries to go up
				System.out.println(roster[currentNode].getName() + ": "+ roster[currentNode].getInfo()); //prints the name and info of the current node
				if(roster[currentNode].getRightChild() != -1){//right node is present, goes to right node
					lastNode = currentNode;
					currentNode = roster[lastNode].getRightChild();
				}
				else if(roster[currentNode].getRightChild() == -1){//right node is absent, tries to go up, returns if currentNode = root
					if(currentNode != Student.getRoot()){//current node != root, currentnode is now parent
						lastNode = currentNode;
						currentNode = roster[lastNode].getParent();
					}
					else{//current node is root, returns
						return;
					}
				}
			}
			else{//came from right child, tries to go up
				if(currentNode != Student.getRoot()){//current node != root, currentnode is now parent
					lastNode = currentNode;
					currentNode = roster[lastNode].getParent();
				}
				else{//current node is root, returns
					return;
				}
			}
			
		}		
	}
}
