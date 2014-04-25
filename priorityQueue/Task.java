/**
* CSC223 Assignment 8 Heap Priority Queue
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: 
*/
package assignment8;

public class Task{
	private double priority = 0;
	private String taskName = "unnamed task: no description.";
	
	public Task(double priority, String taskName){
		this.priority= priority;
		this.taskName= taskName;
	}
	
	public String getTaskName(){
		return taskName;
	}
	
	public void setPriority(double priority){
		this.priority = priority;
	}

	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	public double getPriority(){
		return priority;
	}
}
