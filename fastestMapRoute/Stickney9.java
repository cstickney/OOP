/**
* CSC223 Assignment 9 Graphs
* Chris Stickney, Chris Pien, Jesse Pomerenk, Kaden Buckabee
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: Simple airline graph, not directed, not weighted.
*/
package assignment9;

import java.util.Scanner;

public class Stickney9{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		String textIn;
		int numberOfCities = 0;
		Node[] cityGraph = new Node[10];
		
		
		while(true){//main program loop
			if(cityGraph[0] == null){
				System.out.println("1:edit mode 2:info mode, 3: create sample map, 4: exit ");
			}
			else{
				System.out.println("1:edit mode 2:info mode, 4: exit ");
			}
			textIn = input.nextLine();
			if(textIn.isEmpty()){
				System.out.println("Invalid selection");
			}
			else{
				boolean exitLoop = false;
/* 
 * Switch for main control
 * Used for entering editing, info, and simple map modes
 */
				switch(textIn.charAt(0)){
     //edit mode
				case '1':
					while(!exitLoop){
						System.out.println("Edit mode:");
						System.out.println("1:List all cities 2:List all connections, 3:Add a city, 4:Remove a city, 5:Add a connection, 6:Remove a connection, 7: Change mode");
						textIn = input.nextLine();
						if(textIn.isEmpty()){
							System.out.println("Invalid selection");
						}
						else{
							
			//Switch for editing
							switch(textIn.charAt(0)){
							case '1':
								listCities(cityGraph);
								break;
							case '2':
								listConnections(cityGraph, numberOfCities);
								break;
							case '3'://add a city
								cityGraph = addCity(cityGraph, numberOfCities);
								numberOfCities++;
								break;
							case '4':
								cityGraph = removeCity(cityGraph, numberOfCities);
								break;
							case '5':
								cityGraph = addConnection(cityGraph, numberOfCities);
								break;
							case '6':
								cityGraph = removeConnection(cityGraph, numberOfCities);
								break;
							case '7':
								exitLoop=true;
								break;
							default:
								System.out.println("Invalid selection");	
							}
						}
						input = new Scanner(System.in);
					}
						
					break;
	//info mode		
				case '2':
					while(!exitLoop){
						System.out.println("Info mode:");
						System.out.println("1:List all cities 2:List all connections, 3:Find shortest route between two locations, 4: Change mode");
						textIn = input.nextLine();
						if(textIn.isEmpty()){
							System.out.println("Invalid selection");
						}
						else{
				//switch for info mode			
							switch(textIn.charAt(0)){
							case '1':
								listCities(cityGraph);
								break;
							case '2':
								listConnections(cityGraph, numberOfCities);
								break;
							case '3':
								findRoute(cityGraph, numberOfCities);
								break;
							case '4':
								exitLoop=true;
								break;
							default:
								System.out.println("Invalid selection");	
							}
						}
						input = new Scanner(System.in);
					}
					break;
	//sample map mode
		//generates a sample map for quick demonstration purposes
		//will only generate once			
				case '3':
						if(cityGraph[0] == null){
							cityGraph = sampleData();
							numberOfCities = 5;
						}
						else{
							System.out.println("Invalid selection");
						}
						break;	
	//exit			
				case '4'://exit
					System.exit(1);
	//error				
				default:
					System.out.println("Invalid selection");
				}
			}
			input = new Scanner(System.in);
		}
	}
	
	
	//creation of sample graph
	//returns graph with 5 sample cities
	private static Node[] sampleData(){
		Node[] newGraph = new Node[10];
	//create an adjacency map for 5 cities
		boolean[] node0Connections = {false, true, true, true, false, false, false, false, false, false};//3 connections
		boolean[] node1Connections = {true, false, false, true, false, false, false, false, false, false};//2 connections
		boolean[] node2Connections = {true, false, false, false, false, false, false, false, false, false};//1 connection
		boolean[] node3Connections = {true, true, false, false, false, false, false, false, false, false};//2 connections
		boolean[] node4Connections = {false, false, false, false, false, false, false, false, false, false};//no connections
	//create array of sample cities using adjacency map 
		newGraph[0] = new Node(0, "Sample 1", node0Connections);
		newGraph[1] = new Node(1, "Sample 2", node1Connections);
		newGraph[2] = new Node(2, "Sample 3", node2Connections);
		newGraph[3] = new Node(3, "Sample 4", node3Connections);
		newGraph[4] = new Node(4, "Sample 5", node4Connections);
		return newGraph;
	}
	
	
	//route finding method given city graph array and length
	private static void findRoute(Node[] cityGraph, int numberOfCities){
		int[][] shortestPaths = new int[numberOfCities][numberOfCities];//[selects the node][contains the path]
		int[] pathLength = new int[numberOfCities];
		int current = -1;
		boolean[] pathFound = new boolean[numberOfCities];
		boolean[] destinationsChecked = new boolean[numberOfCities];
		boolean newPaths = false;
		String firstCityName, secondCityName;
		int start=-1, destination=-1;
		Scanner input = new Scanner(System.in);
		
		//set array of all path lengths to -1, will latter contain transversal data
		int i=0, j=0;
		while(i < numberOfCities){
			while(j<numberOfCities){
				shortestPaths[i][j] = -1;
				++j;
			}
			++i;
		}
		
		// obtain and find starting position in graph
		System.out.println("Start from what city?");
		firstCityName = input.nextLine();
		i=0;
		while(i!= numberOfCities){
			if(cityGraph[i].getId() >=0){
				if(cityGraph[i].getName().equals(firstCityName)){
					start = i;
					i=numberOfCities-1;
				}
			}
			i++;
		}
		if(start == -1){
			System.out.println("No matches found.");
			return;
		}
		
		//obtain and find ending position in graph
		System.out.println("End in what city?");
		secondCityName = input.nextLine();
		i=0;
		while(i!= numberOfCities){
			if(cityGraph[i].getId() >=0){
				if(cityGraph[i].getName().equals(secondCityName)){
					destination = i;
					i=numberOfCities-1;
				}
			}
			i++;
		}
		if(destination == -1){
			System.out.println("No matches found.");
			return;
		}
		
		else{
	/*
	 * begin path finding
	 */
			shortestPaths[start][0] = start;
			pathLength[start] = 1;
			pathFound[start] = true;
			i=0;
			j=0;
			
			while(true){

				newPaths = false;
				i=0;
				while(i<numberOfCities){
			//find new path from current node 
			//flag all checked destinations
					if(pathFound[i] && !destinationsChecked[i]){
						current = i;
						i=numberOfCities;
						destinationsChecked[current] = true;
						newPaths = true;
					}
					else{
						++i;
					}
				}
				
	//if paths exists take it check it
			//full loop of method will have to occur several times in order for complete path to be found
				
				i=0;
				if(newPaths == true){		
					while(i<numberOfCities){
						if(cityGraph[current].getAdjacentArray()[i]){
							if(pathLength[i]>pathLength[current]+1 || pathLength[i] == 0){
								j=0;
								while(j<pathLength[current]){
									shortestPaths[i][j] = shortestPaths[current][j];
									++j;
								}
								shortestPaths[i][j] = i;
								pathLength[i] = pathLength[current] +1;
								pathFound[i] = true;
							}
						}
						++i;
					}
				}
				
				//if full path found print it out
				else if(pathFound[destination] == true){
					System.out.println("Shortest Path from " + firstCityName+" to "+secondCityName+ ":");
					i=0;
					while(shortestPaths[destination][i] != destination){
						System.out.print(cityGraph[shortestPaths[destination][i]].getName() + ">");
						++i;
					}
					System.out.print(cityGraph[destination].getName());
					System.out.println();
					return;
				}
				else{
					System.out.println("No path found.");
					return;
				}			
			}				
		}
	}
	
	
	
	private static Node[] removeConnection(Node[] cityGraph, int numberOfCities){
		String firstCityName, secondCityName;
		int firstCityLocation = -1, secondCityLocation = -1;
		Scanner input = new Scanner(System.in);
		//gather 1st city, find it.
		System.out.println("Enter the name of one of the cities that has the connection you want to remove");
		firstCityName = input.nextLine();
		int i=0;
		while(i!= numberOfCities){
			if(cityGraph[i].getId() >=0){
				if(cityGraph[i].getName().equals(firstCityName)){
					firstCityLocation = i;
					i=numberOfCities-1;
				}
			}
			i++;
		}
		
		if(firstCityLocation == -1){
			System.out.println("No matches found.");
			return cityGraph;
		}
		//gather second city, find it.
		System.out.println("Enter the name of the other city that has the connection you want to remove");
		secondCityName = input.nextLine();
		i=0;
		while(i!= numberOfCities){
			if(cityGraph[i].getId() >=0){
				if(cityGraph[i].getName().equals(secondCityName)){
					secondCityLocation = i;
					i=numberOfCities-1;
				}
			}
			i++;
		}
		
		if(secondCityLocation == -1){
			System.out.println("No matches found.");
			return cityGraph;
		}
		//remove connection via adjacency arrays
		else{
			cityGraph[firstCityLocation].setAdjacent(secondCityLocation, false);
			cityGraph[secondCityLocation].setAdjacent(firstCityLocation, false);
			return cityGraph;
		}
	}
	
	
	
	private static Node[] addConnection(Node[] cityGraph, int numberOfCities){
		String firstCityName, secondCityName;
		int firstCityLocation = -1, secondCityLocation = -1;
		Scanner input = new Scanner(System.in);
		//gather and find 1st city
		System.out.println("Enter the name of one of the cities to add the connection to");
		firstCityName = input.nextLine();
		int i=0;
		while(i!= numberOfCities){
			if(cityGraph[i].getId() >=0){
				if(cityGraph[i].getName().equals(firstCityName)){
					firstCityLocation = i;
					i=numberOfCities-1;
				}
			}
			i++;
		}
		
		if(firstCityLocation == -1){
			System.out.println("No matches found.");
			return cityGraph;
		}
		
		//gather and find 2nd city
		System.out.println("Enter the name of the other city to add the connection to");
		secondCityName = input.nextLine();
		i=0;
		while(i!= numberOfCities){
			if(cityGraph[i].getId() >=0){
				if(cityGraph[i].getName().equals(secondCityName)){
					secondCityLocation = i;
					i=numberOfCities-1;
				}
			}
			i++;
		}
		
		if(secondCityLocation == -1){
			System.out.println("No matches found.");
			return cityGraph;
		}
		//connect cities via adjacency arrays
		else{
			cityGraph[firstCityLocation].setAdjacent(secondCityLocation, true);
			cityGraph[secondCityLocation].setAdjacent(firstCityLocation, true);
			return cityGraph;
		}
	}
	
	
	private static Node[] removeCity(Node[] cityGraph, int numberOfCities){
		String name;
		int location = -1;
		Scanner input = new Scanner(System.in);
		//gather and find city
		System.out.println("Enter the name of the city you want to delete.");
		name = input.nextLine();
		int i=0;
		while(i!= numberOfCities){
			if(cityGraph[i].getId() >=0){
				if(cityGraph[i].getName().equals(name)){
					location = i;
					i=numberOfCities-1;
				}
			}
			i++;
		}
		
		if(location == -1){
			System.out.println("No matches found.");
			return cityGraph;
		}
		//remove city, set all adjacency arrays to reflect
		else{
			cityGraph[location].setId(-1);
			i=0;
			while(i!= numberOfCities){
				if(cityGraph[location].getAdjacentArray()[i]){
					cityGraph[location].setAdjacent(i, false);
					cityGraph[i].setAdjacent(location, false);
				}
				++i;
			}
			System.out.println(cityGraph[location].getName() + " removed!");
			return cityGraph;
		}
	}
	
	
	public static void listCities(Node[] cityGraph){
		int i=0, k=1;
	//list all cities
		while(cityGraph[i] != null){
			if(cityGraph[i].getId() >=0){
				System.out.println("City " + k + ": " + cityGraph[i].getName());
				++k;
			}
			++i;
		}
	}
	
	public static void listConnections(Node[] cityGraph, int numberOfCities){
		int i=0, j=0, k=1, a=1;
		boolean hasConnections = false;
	//list all edges from all cities, there will be repeats
		while(cityGraph[i] != null){
			if(cityGraph[i].getId() >=0){
				System.out.println("City " + a + ": " + cityGraph[i].getName());
				++a;
				while(j<numberOfCities){
					if(cityGraph[i].getAdjacentArray()[j] == true){
						System.out.println("Connection " + k + ": "+ cityGraph[i].getName() + " to " + cityGraph[j].getName());
						++k;
						hasConnections = true;
					}
					++j;
				}
			}
			if(!hasConnections && cityGraph[i].getId() >=0){
				System.out.println("Has no connections.");

			}
			++i;
			j=0;
			k=1;
			hasConnections = false;
		}
	}
	
	public static Node[] addCity(Node[] cityGraph, int numberOfCities){
		boolean[] adjacent;
		//make new adjacency array if one doesn't exist
		if(cityGraph[0] == null){
			adjacent = new boolean[10];
		}
		//
		else if(cityGraph[0].getAdjacentArray().length == numberOfCities){
			adjacent = new boolean[cityGraph[0].getAdjacentArray().length + 10];
		}
		
		else{
			adjacent = new boolean[cityGraph[0].getAdjacentArray().length];
		}
		Scanner input = new Scanner(System.in);
		String name;
		
		//expands array by 10 if full
		if(numberOfCities == cityGraph.length){
			Node[] graphCopy = new Node[cityGraph.length+10];
			int i=0;
			while(i!=cityGraph.length){
				graphCopy[i] = cityGraph[i];
				++i;
			}
			cityGraph = graphCopy;
		}
		
	
		System.out.println("Input the city name.");
		name =input.nextLine();
		//gather city find slot for it
		int i = 0;
		while(i!= numberOfCities){
			if(cityGraph[i] ==null){
				i=numberOfCities+1;
			}
			else if(cityGraph[i].getId() == -1){
				i++;
			}
			else{
				//expand adjacency array if full
				if(numberOfCities == cityGraph[i].getAdjacentArray().length){
					boolean[] adjacentCopy = new boolean[cityGraph[i].getAdjacentArray().length+10];
					int j=0;
					while(i!=cityGraph[i].getAdjacentArray().length){
						adjacentCopy[j] = cityGraph[i].getAdjacentArray()[j];
						++j;
					}
					cityGraph[i].setAdjacentArray(adjacentCopy);
				}
				//gather all connections,find them, and input into adjacency array
				boolean validInput=false;
				while(!validInput){
					System.out.println("Does this city connect to " + cityGraph[i].getName()+ "? (y/n)");
					String textIn = input.nextLine();
					if(textIn.isEmpty()){
						System.out.println("Invalid selection");
					}
					else{
						textIn = textIn.toLowerCase();
						switch(textIn.charAt(0)){
						case 'y':
							validInput=true;
							adjacent[i] = true;
							cityGraph[i].setAdjacent(numberOfCities, true);
							++i;
							break;
						case 'n':
							validInput=true;
							++i;
							break;
						default:
							System.out.println("Invalid selection");	
						}
					}
					input = new Scanner(System.in);
				}	
			}
		}
		//insert new city into city list
		cityGraph[numberOfCities] = new Node(numberOfCities, name, adjacent);
		return cityGraph;
	}
}