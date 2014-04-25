/**
* CSC223 Assignment 8 Heap Priority Queue
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: 
*/
package assignment8;

import java.util.Comparator;

public class TaskCompare implements Comparator<Task>{

	@Override
	public int compare(Task listedTask, Task newTask){
		if(listedTask.getPriority() < newTask.getPriority()){//returns 0 if the listed task's priority level is not less than the new task's priority level
			return 0;
		}
		else{//returns 1 if the listed task's priority level is less than the new task's priority level
			return -1;
		}
		
	}
	
}
