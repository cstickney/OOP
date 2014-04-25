/**
* CSC223 Assignment 6 Hash Table
* Chris Stickney, Ryan Bergquist, Mike Meyer
* JRE: JavaSE-1.7
* OS: Windows 7 x64
* 
* Program summary: Generates hash codes based on the titles of the books, and then assigns them a slot on a shelf based on their hash code
*/
package assignment6;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Stickney6{
	public static void main(String[] args){
		String[] bookLocations = new String[1000];//location where the books are stored
		String bookName;
		int prime = 31, alternatePrime = 53, hash, selection=0;
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		
		while(true){//main program loop
			System.out.println("1:list books, 2: list slots, 3: find book, 4: add book, anything else: exit");
			try{
				selection =input.nextInt();
			}
			catch(InputMismatchException ex){//exit state
				System.exit(1);
			}
			
			switch(selection){
				case 1://lists all books that are in the system and their locations
					listBooks(bookLocations);
					break;
				case 2://lists all book locations and their contents
					listSlots(bookLocations);
					break;
				case 3://allows users to put in a book name and see if it's in the system
					System.out.println("Enter the title of the book you want to find.");
				
					bookName = input2.nextLine();
				
					hash = findBook(prime, alternatePrime, bookName, bookLocations);
					if(hash == -1){
						System.out.println("Book not found.");
					}
					else{
						System.out.println("Slot " + hash + ": " + bookLocations[hash]);
					}
					break;
				case 4://adds a book to the system
					System.out.println("Enter the title of the book you want to add.");
					bookName = input2.nextLine();
					int alternateHash = Math.abs(bookName.hashCode() * alternatePrime) % bookLocations.length;//the second hash location for the book
					hash = generateHash(prime, bookName, bookLocations);//first hash, returns -1 if the location is already taken
					if(hash == -1){
						hash = generateHash(alternatePrime, bookName, bookLocations);//second hash, returns -1 if the location is already taken
						if(hash == -1){
							hash = nextAvailableSlot(alternateHash, bookLocations);//moves on from the second hash and checks each slot until an open one is found
							if(hash == -1){//if the list is full, returns -1
								System.out.println("No available locations.");
							}
							else{
								bookLocations[hash] = bookName;
								System.out.println("Slot " + hash + ": " + bookLocations[hash]);
							}
						}
						else{
							bookLocations[hash] = bookName;
							System.out.println("Slot " + hash + ": " + bookLocations[hash]);
						}
					}
					else{
						bookLocations[hash] = bookName;
						System.out.println("Slot " + hash + ": " + bookLocations[hash]);
					}
					break;
				default://exit state
					System.exit(1);
			}
			
		}
		
		
	}
	
	public static void listBooks(String[] bookLocations){//goes through all the slots and finds which ones have books in them
		int search = 0;
		while (search < bookLocations.length){//checks for a location that's string is null, and adds the book to that location
			if (bookLocations[search] != null){
				System.out.println("Slot " + search + ": "
						+ bookLocations[search]);
			}
			++search;
		}
		
	}
	
	public static void listSlots(String[] bookLocations){//lists the contents of all the slots
		int search = 0;
		while (search < bookLocations.length){//if the location is null, says the location contains (empty), otherwise, the name of the book
			if (bookLocations[search] == null){
				System.out.println("Slot " + search + ": (empty)");
			}
			else{
				System.out.println("Slot " + search + ": "
						+ bookLocations[search]);
			}
			++search;
		}
	}
	
	public static int findBook(int prime, int alternatePrime, String bookName, String[] bookLocations){//finds a book based on user string
		int hash = Math.abs(bookName.hashCode() * prime) % bookLocations.length;//the first hash location for the book
		int alternateHash = Math.abs(bookName.hashCode() * alternatePrime) % bookLocations.length;//the second hash location for the book
		int search = alternateHash + 1;
		
		if (bookLocations[hash] == null){//if the first hash is null, the book cant be in the system, so it returns -1
			return -1;
		}
		System.out.println(bookName+ "=" + bookLocations[hash]);
		if(bookLocations[hash].equals(bookName)){//checks the first hash against the book name, returns the location if a match
			return hash;
		}
		else if (bookLocations[alternateHash] == null){//if the second hash is null, the book cant be in the system, so it returns -1
			
				return -1;
				
			}
			else if (bookLocations[alternateHash].equals(bookName)){//checks the second hash against the book name, returns the location if a match
					return alternateHash;
				}
				else{
					while (search < bookLocations.length){//checks from one past the second hash to the end of the array
						if (bookLocations[search] == null){//if any location past the second hash is null, the book isnt in the system
							return -1;//so it returns -1
						}
						if (bookLocations[search].equals(bookName)){//checks the location against the book name, returns it if a match
							return search;
						}
						++search;//if not a match, increments the search and checks the next location
					}
					search = 0;//goes back to the beginning of the array
					while (search < alternateHash){//checks from the beginning of the array to the second hash
						if (bookLocations[search] == null){//if any location past the second hash is null, the book isnt in the system
							return -1;//so it returns -1
						}
						if (bookLocations[search] == bookName){//checks the location against the book name, returns it if a match
							return search;
						}
						++search;//if not a match, increments the search and checks the next location
					}
				}
		return -1;//if none of the locations are null and no matches found, the book is not in the system
	}
	
	public static int generateHash(int prime, String bookName, String[] bookLocations){//generates a hash with a book name and prime number
		int hash = Math.abs(bookName.hashCode() * prime) % bookLocations.length;//gets the hashcode for the name, multiplies it by a prime, and mods it into the array's range
		if (bookLocations[hash] == null){//if a location is null, returns the location
			return hash;
		}
		else{//returns -1 if the hash is in use
			return -1;
		}
	}
	
	public static int nextAvailableSlot(int failedHash, String[] bookLocations){//finds the next available slot if the hash failed
		int search = failedHash + 1;//starts looking immediately after the second hash
		while (search < bookLocations.length){//looks for an opening between the second hash and the end of the array
			if (bookLocations[search] == null){//if the slot is empty, returns the search location
				return search;
			}
			++search;//increments and checks the next location
		}
		search = 0;//goes to the beginning of the array after the search hits the end of the array
		while (search < failedHash){//checks between the beginning of the array and the second hash
			if (bookLocations[search] == null){//if the slot is empty, returns the search location
				return search;
			}
			++search;//increments and checks the next location
		}
		return -1;//if no slots are null, the array is full and it returns -1
	}
}
