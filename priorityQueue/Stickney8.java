/**
* CSC223 Assignment 8 Heap Priority Queue
* Chris Stickney
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: 
*/
package assignment8;

import java.util.PriorityQueue;
import java.util.Scanner;


public class Stickney8{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		String textIn;
		char selection;
		int capacity = 100;
		TaskCompare comparison = new TaskCompare();
		PriorityQueue<Task> queue = new PriorityQueue<Task>(capacity, comparison);
		String task;
		double priority;
		
		
		while(true){
			System.out.println("1: add task, 2:check current task, 3:mark current task as completed, 4: exit ");
			textIn = input.nextLine();
			if(textIn.isEmpty()){
				System.out.println("Invalid selection");
			}
			else{
				selection = textIn.charAt(0);
				switch(selection){
				case '1'://adds a task to the heap
					System.out.println("Input a task name and description");
					task= input.nextLine();
					System.out.println("Input a priority level for this task (integers only) (higher numbers = higher priority)");
					priority= input.nextDouble();
					Task newTask = new Task(priority, task);
					queue.add(newTask);
					break;
				case '2'://checks what the next task is on the heap
					try{
						System.out.println(queue.peek().getTaskName());
					}
					catch(NullPointerException e){
						System.out.println("Queue is empty.");
					}
					break;
				case '3': //prints the task that was just completed,
					try{
						System.out.println(queue.peek().getTaskName()+ " completed.");
						queue.remove();
						Task[] tasks = queue.toArray(new Task[capacity]);
						PriorityQueue<Task> queueCopy = new PriorityQueue<Task>(capacity, comparison);
						int i=0;
						boolean go = true;
						while(go){
							if(i<=capacity){
								if(tasks[i] == null){
									go = false;
								}
								else{
									queueCopy.add(tasks[i]);
								}
							}
								
							else{
								go = false;
							}
							++i;
						}
						queue = queueCopy;
						try{
							System.out.println("Next Task: "+ queue.peek().getTaskName());
						}
						catch(NullPointerException e){
							System.out.println("Queue is empty.");
						}
					}
					catch(NullPointerException e){
						System.out.println("Queue is empty.");
					}
					break;

				case '4': //exit
					System.exit(1);
				default:
					System.out.println("Invalid selection");
				}
			}
			input = new Scanner(System.in);
		}
	}
}
