/**
* CSC223 Assignment 9 Graphs
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: Node class for assignment 9.
*/
package assignment9;

public class Node{
	private int id =-1;
	private String name = "unused";
	private boolean[] adjacent = new boolean[10];
	
	public Node(int id, String name, boolean[] adjacent){
		this.id= id;
		this.name= name;
		this.adjacent = adjacent;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public boolean[] getAdjacentArray(){
		return adjacent;
	}
	public void setAdjacent(int location, boolean adjacent){
		this.adjacent[location] = adjacent;
	}
	public void setAdjacentArray(boolean adjacent[]){
		this.adjacent = adjacent;
	}
}