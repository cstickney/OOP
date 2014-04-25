/**
 * CSC223 Project
 * Chris Stickney
 * JRE: JavaSE-1.7
 * OS: Windows 7 x64
 */
package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class FileRenamer{
	public static void main(String[] args){//main function
		Scanner input = new Scanner(System.in);
		String extension = "insert extension here";
		clearScreen();
		String path=newPath();//calls method to get working directors and assigns it to path
		
		if(path.endsWith("\\") == false){//makes sure the path ends with '\'
			path = path+ "\\";
		}

		File folder = new File(path); //creates a new file from the path string
		String[] fileList = folder.list(); //creates a string array of all the filenames in the directory
		clearScreen();
		System.out.println("\nContents of " + path + ":");
		printFiles(fileList);//calls method to print files in directory
		
		String[] selectedFiles = includeHidden(fileList, path);//calls method to deal with hidden files and writes result to selectedfiles
		clearScreen();
		System.out.println("\nSelected files in " + path + ":");
		printFiles(selectedFiles);//calls method to print selected files
		
		System.out.println("Type an extension to sort by, or leave blank for all files.");
		extension=input.nextLine();//takes an extension to sort files by
		if( extension.isEmpty() != true){//makes sure any extension has a '.' in front of it
			if(extension.charAt(0) != '.'){
				extension ="." + extension;
			}
		}
		
		selectedFiles = extensionSort(selectedFiles, extension, path);//calls method to sort by extension and writes the result to selectedfiles
		selectedFiles = keywordSort(selectedFiles, path);//calls method to sort by keyword and writes the result to selectedfiles
		clearScreen();
		
		System.out.println("\nSelected files in " + path + ":");
		printFiles(selectedFiles);//calls method to print selected files
		
		String[] fileEdits = new String[selectedFiles.length];
		fileEdits = editMode(selectedFiles);//calls the method that puts the program in edit mode for filenames and writes edited filenames to fileedits
		clearScreen();
		while(true){
			int i=0, end = selectedFiles.length;
			
			System.out.println("Final changes:");
			while(i<end){//shows what file edits will be performed by the program
				System.out.println(selectedFiles[i] +" will be changed to " + fileEdits[i]);
				++i;
			}
			System.out.println("\nConfirm changes? (y/n)");//prompts the user to confirm their changes before writing
			String confirm = input.nextLine();
			confirm = confirm.toLowerCase();
			if(confirm.isEmpty()){
				clearScreen();
				System.out.println("Invalid selection.\n");
			}
			else if(confirm.charAt(0) == 'n'){//quits if the user does not want to make the changes
				System.exit(1);
			}
			else{
				clearScreen();
				System.out.println("Change results:");
				final File changeLog =new File(path, "File Renamer Change Log.txt");//creates a changelog in case user needs to know the old filenames
				if(!changeLog.exists()){//checks if a changelog exists, uses old one if it does
					try{
						changeLog.createNewFile();//creates the changelog
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
				try{
					PrintWriter out = new PrintWriter(path+"File Renamer Change Log.txt");//creates a new printwriter to write to the changelog
					i=0;
					end=selectedFiles.length;
					Date now = new Date();
					
					out.println("Changes as of " + now + ": ");//lists the time the changes were made for user's records
					while(i<end){//checks if filenames have changed and if the change path is already taken, and prints result of the edit to changelog and screen
						File original = new File(path,selectedFiles[i]);
						File rename = new File(path,fileEdits[i]);
						if(fileEdits[i] == selectedFiles[i]){//triggers if no changes have been made
							System.out.println(selectedFiles[i] +"'s filename has not changed.");
							out.println(selectedFiles[i] +"'s filename has not changed.");
						}
						else if(rename.exists()){//triggers if path is taken.
							/**
							 * BUG: will not change filenames that have only been capitalized, because the program recognizes the filenames as
							 * different, but the file methods and windows regard them as them the same
							 */
							System.out.println(selectedFiles[i] +" was NOT changed to " + fileEdits[i] + ": Filename already exists");
							out.println(selectedFiles[i] +" was NOT changed to " + fileEdits[i] + ": Filename already exists");
						}
						else{//renames the file
							original.renameTo(rename);
							System.out.println(selectedFiles[i] +" was changed to " + fileEdits[i]);
							out.println(selectedFiles[i] +" was changed to " + fileEdits[i]);
						}
							++i;
						
					}
					out.close();//closes printwriter
				}
				catch (FileNotFoundException e){//if file was edited while program is running, this might trigger
					e.printStackTrace();
				}
				System.exit(1);
			}
		}
	}
	
	public static String newPath(){//gets a valid working directory to start working in
		Scanner input = new Scanner(System.in);
		boolean valid = false;
		String empty = "";
		
		while (valid != true){//loops until an existing directory is specified
			System.out.println("Enter the path of the folder you wish to work in.");//prompts the user for a directory to work in
			String path = input.nextLine();
			File newPath = new File(path);
			if (newPath.exists()){//checks if the directory exists
				return path;
			}
			else{//loops again if it doesnt exist
				System.out.println("Invalid Path.");
			}
		}
		
		return empty;//unnecessary, but eclipse wouldn't compile without it.
	}
	
	public static String[] includeHidden(String[] fileList, String path){//asks the user if they want to include hidden files or not
		int i=0, j=0, end= fileList.length;
		Scanner input = new Scanner(System.in);
		String selection;
		String[] tempFileList = new String[fileList.length];
		
		
		while(true){
			System.out.println("Do you want to include hidden files? (y/n)");//prompts the user if they want to include hidden files
			selection = input.nextLine();
			selection = selection.toLowerCase();
			if(selection.isEmpty()){
				System.out.println("Invalid selection.");
			}
			else if(selection.startsWith("n")){
				while(i<end){//checks all the files and tests if they are hidden or not
					File test= new File(path + fileList[i]);
					if(test.isHidden()==false){
						
						tempFileList[j] = fileList[i];
						++j;	
					}
					++i;
				}
				String[] finalFileList = new String[j];
				i = 0;
				
				while (i != j){
					finalFileList[i] = tempFileList[i];
					++i;
				}
				return finalFileList;
			}		
			else if(selection.startsWith("y")){
				return fileList;
			}
			else{
				System.out.println("Invalid selection.");
			}
		}
	}

	public static String[] extensionSort(String[] fileList, String extension, String path){//removes all files that don't have a certain extension
		String[] tempFileList = new String[fileList.length];
		if (extension.isEmpty() == false){//if an extension is entered, sorts with it.
			
			int i = 0, j = 0;
			int end = fileList.length;
			while (i != end){//checks if the files end with the specified extension
				if (fileList[i].toLowerCase().endsWith(extension.toLowerCase())){
					File test= new File(path + fileList[i]);
					if(test.isFile()){
						tempFileList[j] = fileList[i];
						++j;
					}
				}
				++i;
			}
			String[] finalFileList = new String[j];
			i = 0;
			
			while (i != j){
				finalFileList[i] = tempFileList[i];
				++i;
			}
			return finalFileList;
		}
		else{
			int i = 0, j = 0;
			int end = fileList.length;
			while (i != end){
					File test= new File(path + fileList[i]);
					if(test.isFile()){
						tempFileList[j] = fileList[i];
						++j;
				}
				++i;
			}
			String[] finalFileList = new String[j];
			i = 0;
			
			while (i != j){
				finalFileList[i] = tempFileList[i];
				++i;
			}
			return finalFileList;
		}
	}
	
	public static String[] keywordSort(String[] fileList, String path){//checks if files contain a keyword, and either includes only those files or removes them from the list
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		String selection;
		String[] tempFileList = new String[fileList.length];
		String keyword;
		
		while(true){//loops until extension sorting is done
			clearScreen();
			System.out.println("\nSelected files in " + path + ":");
			printFiles(fileList);
			System.out.println("Sorting by keyword: \n1: Keep files containing the keyword \n2: Remove files containing the keyword \nAnything else: Start editing filenames");//prompts users to specify if they want to remove files containing the keyword, only use files containing the keyword, or move on to the next step
			selection = input.nextLine();
			String[] fileNames = removeExtensions(fileList);//removes extensions so they do not get mistaken for keywords
			
			if(selection.isEmpty()){//exit state
				return fileList;//returns the modified filelist
			}
			
			switch(selection.charAt(0)){
				case '1'://inclusionary sort
					System.out.println("Enter the keyword you want to include");
					keyword = input2.nextLine();
					if(keyword.isEmpty()){//if no keyword is entered, do nothing.
						break;
					}
					else{//check for keyword in the files keep the ones that have it
						int i = 0, j = 0;
						int end = fileNames.length;
						while (i != end){
							if (fileNames[i].toLowerCase().contains(keyword.toLowerCase())){
								tempFileList[j] = fileList[i];
								++j;
							}
							++i;
						}
						fileList = new String[j];
						i = 0;
						
						while (i != j){
							fileList[i] = tempFileList[i];
							++i;
						}
						break;
					}
				case '2'://exclusionary sort
					System.out.println("Enter the keyword you want to exclude");
					keyword = input2.nextLine();
					if(keyword.isEmpty()){//if no keyword is entered, do nothing.
						break;
					}
					else{//check for keyword in the files discard the ones that have it
						int i = 0, j = 0;
						int end = fileNames.length;
						while (i != end){
							if (fileNames[i].toLowerCase().contains(keyword.toLowerCase()) == false){
								tempFileList[j] = fileList[i];
								++j;
							}
							++i;
						}
						fileList = new String[j];
						i = 0;
						
						while (i != j){
							fileList[i] = tempFileList[i];
							++i;
						}
						break;
					}
			default://exit state
					return fileList;//returns the modified filelist
			}
			
			
		}
	}
	
	public static String[] editMode(String[] fileList){//edit method. calls specific edit methods while running
		String[] fileNames = removeExtensions(fileList);//removes extensions so they are not changed during edits
		String[] extensions = getExtensions(fileList);//pulls extensions from the filenames to be added back after edits are complete
		
		while(true){//main edit mode loop
			System.out.println("Edit mode:");
			System.out.format("%s%44s", "1: Replace text", "2: Append text\n");
			System.out.format("%s%30s", "3: Replace text between two characters", "4: Capitalize all words\n");
			System.out.format("%s%53s", "5: Remove extra spaces", "Anything else: Finalize changes\n");
				Scanner input = new Scanner(System.in);
				Scanner input2 = new Scanner(System.in);
				Scanner input3 = new Scanner(System.in);
				Scanner input4 = new Scanner(System.in);
				String selection = input.nextLine();
				
				if(selection.isEmpty()){
					//exit state
					String[] newFileList = recombine(fileNames, extensions);//puts new filenames back with their extensions
					return newFileList;//returns the edited filenames
				}
				else if(selection.charAt(0) =='1'){//text replacment mode
					System.out.println("what text do you want to replace?");//prompts user for what they want to replace
					String oldText =input2.nextLine();
					System.out.println("replace it with what?");//prompts user for what they want to put in its place
					String newText =input3.nextLine();
					fileNames = replace(fileNames, oldText, newText);//calls replacement method
				}
				else if(selection.charAt(0) =='2'){//text append mode
					boolean appendToEnd;
					System.out.println("what text do you want to append?");//prompts user for the text they want to append
					String newText =input3.nextLine();
					
					System.out.println("1:add the text to the beginning, 2:to the end, anything else:cancel");//prompts user for where to add text
					String position =input2.nextLine();
					if(position.startsWith("1")){//append to the beginning
						appendToEnd = false;
						fileNames = append(fileNames, newText, appendToEnd);//calls append method based on user input
					}
					else if(position.startsWith("2")){//append to the end
						appendToEnd = true;
						fileNames = append(fileNames, newText, appendToEnd);//calls append method based on user input
					}
					else{//cancel
						System.out.println("invalid input");
					}
				}
				else if(selection.charAt(0) == '3'){//replace within 
					System.out.println("This function replaces anything in between two characters.");//in case this function is confusing
					System.out.println("first character:");//prompts user for beginning character
					String start =input2.nextLine();
					char startChar = start.charAt(0);
					
					System.out.println("last character:");//prompts user for end character
					String end =input3.nextLine();
					char endChar = end.charAt(0);
					
					System.out.println("what do you want to replace that with?");//prompts user for replacement text
					String newText =input4.nextLine();
					
					fileNames = replaceWithin(fileNames, startChar, endChar, newText);//calls replace within method based on user criteria
				}
				else if(selection.charAt(0) == '4'){//capitalize
					fileNames = capitalize(fileNames);//calls capitalization method
				}
				else if(selection.charAt(0) == '5'){//remove extra spaces
					fileNames = removeExtraSpaces(fileNames);//calls remove spaces method
				}
				else{//exit state
					String[] newFileList = recombine(fileNames, extensions);//puts new filenames back with their extensions
					return newFileList;//returns the edited filenames
				}
				String[] newFileList = recombine(fileNames, extensions);//puts filenames back temporarily to show changes
				clearScreen();
				System.out.println("current changes:");
				int i=0, end = fileList.length;
				while(i<end){//shows changes file by file
					System.out.println(fileList[i] +" is now " + newFileList[i]);
					++i;
				}
				System.out.println();
				input = new Scanner(System.in);
				
		}
		
	}
	
	public static String[] replace(String[] fileList, String oldText, String newText){//takes the filenames, checks if they contain the text to be replaced, and replaces that text with replacement text if they do
		int i=0, end=fileList.length;
		while(i<end){//checks each file for the text to be replaced
			if(fileList[i].contains(oldText)){//if the file contains the old text, replace it.
				fileList[i] = fileList[i].replace(oldText, newText);
			}
			++i;
		}
		return fileList;
	}
	
	public static String[] append(String[] fileList, String newText, boolean appendToEnd){//takes a string and appends it to the beginning or end of all the filenames
		int i=0, end=fileList.length;
		if(appendToEnd){//appends to the end if user wanted it at the end
			while(i<end){//goes through the files appending the text
				fileList[i] = fileList[i].concat(newText);
				++i;
			}
		}
		else {//adds it to the beginning if the user wanted it at the beginning
			while(i<end){//goes through the files appending the text
			fileList[i] = newText + fileList[i];
			++i;
			}
		}
		return fileList;
	}
	
	public static String[] replaceWithin(String[] fileList, char startChar, char endChar, String newText){//replaces all the text between two user specified characters if it is present in the filename
		String within;//a string to take substrings of what is between the two characters for replacement purposes
		int i=0, end = fileList.length, j=0, strEnd;
		while(i<end){//parses through the files and checks for occurrences of both characters
			strEnd=fileList[i].length()-1;
			if(fileList[i].contains(String.valueOf(startChar)) && fileList[i].contains(String.valueOf(endChar))){//checks if both characters are present
				/**
				 * note: if the start character and the end character are the same, this may return a false positive, so the method checks for
				 * that to ensure no false positives impact the results
				 * 
				 * note: once the method encounters a start character, it will stop at the first end character it runs into. this may cause
				 * problems if more than one occurrence of the start character appears before the end character, or if you wanted the end 
				 * character furthest into the filename.
				 * examples: if start='(', end=')': (()) becomes ), ()) becomes )
				 */
				boolean stringComplete = false;
				while(!stringComplete){//checks a string until it is confirmed that no more replacement operations can be performed
					j=0;
					int startCharPosition = -1, endCharPosition = -1;
					while(j<strEnd){//looks for the start character in all but the last letter of the filename
						if(fileList[i].charAt(j) == startChar){//if start character is found, begins searching for the end character
							startCharPosition = j;//assigns this location to startcharposition
							++j;
							while(j<=strEnd){//looks for the end character in all locations past the start character
								if(fileList[i].charAt(j) == endChar){//if the end character is found, terminates the search
									endCharPosition = j+1;//assigns this location to endcharposition
									j=strEnd+1;//sets j outside the bounds for this and the outside while loops
								}
								++j;
							}
						}
						++j;
					}
					
					if(startCharPosition != -1 && endCharPosition != -1){//checks if both characters were found
						within = fileList[i].substring(startCharPosition, endCharPosition);//creates a substring with the start and end locations
						fileList[i] = fileList[i].replace(within, newText);//replaces the substring with the replacement text
						strEnd=fileList[i].length()-1;//changes the string length variable for the next execution on the string
					}
					else{//if one of the characters is not found, the string is done and the method moves on to the next file
						stringComplete = true;
					}	
				}
				
			}
			++i;
		}
		return fileList;//returns the modified filelist
	}
	
	public static String[] capitalize(String[] fileList){//capitalizes the first letter of all words
		char firstLetter = 'a', capitalFirstLetter = 'A';
		int i=0, end = fileList.length;
		while(i<end){//checks if any words start with a lowercase letter
			firstLetter = 'a';
			capitalFirstLetter = 'A';
			while(firstLetter<='z'){//checks for each letter up to lowercase z
				if(fileList[i].contains(" "+firstLetter)){//checks if the string contains a space followed by the lowercase letter
					fileList[i] = fileList[i].replace(" "+firstLetter, " "+capitalFirstLetter);//replaces the letter with a capital equivalent
				}
				if(fileList[i].charAt(0) == firstLetter){//checks if the string starts with the lowercase letter
					fileList[i] = fileList[i].replaceFirst(String.valueOf(firstLetter), String.valueOf(capitalFirstLetter));//replaces the letter with a capital equivalent
				}
				++firstLetter;//moves to the next letter
				++capitalFirstLetter;//moves to the next letter
			}
			++i;
		}
		return fileList;//returns the modified filelist
	}
	
	public static String[] removeExtraSpaces(String[] fileList){//checks if the filenames contain extra spaces
		int i=0, arrayEnd = fileList.length;
		while(i<arrayEnd){//checks each filename for extra spaces
			boolean stringComplete = false;
			
			while(!stringComplete){//loops until there are no more extra spaces in the filename
				if(fileList[i].contains("  ")){//checks if the file contains two consecutive spaces
					fileList[i] = fileList[i].replaceFirst("  ", " ");
				}

				else if(fileList[i].startsWith(" ") || fileList[i].endsWith(" ")){//checks if the filename starts or ends with a space
					fileList[i] = fileList[i].trim();
				}
				else{//the filename has no more extra spaces, moves on to the next 
					stringComplete = true;
				}
			}
			++i;
		}
		return fileList;//returns the modified filelist
		
	}
	
	public static void printFiles(String[] fileList){//outputs filenames to the screen
		int i=0, end = fileList.length;
		while (i != end){//increments through the array printing each filename
			System.out.println(fileList[i]);
			++i;
		}		
		System.out.println();
	}
			
	public static String[] removeExtensions(String[] fileList){//takes the extensions off of the filenames and returns just the names
		int strParser, strLength, arrayParser=0;
		boolean isPeriod = false;
		String[] withoutExtensions = new String[fileList.length];
		
		while(arrayParser < fileList.length ){//goes through the filenames and removes the extensions and adds them to a new array
			strLength = fileList[arrayParser].length();
			strParser = strLength -1;
			while(isPeriod == false){
				if(fileList[arrayParser].charAt(strParser) == '.'){
					withoutExtensions[arrayParser] = fileList[arrayParser].substring(0, strParser);
					isPeriod = true;
				}
				--strParser;
			}
			++arrayParser;
			isPeriod = false;
		}	
		return withoutExtensions;
	}
	
	public static String[] getExtensions(String[] fileList){//takes the extensions from the filenames and returns them in a string
		int strParser, strLength, arrayParser=0;
		boolean isPeriod = false;
		String[] withoutExtensions = new String[fileList.length];
		
		while(arrayParser < fileList.length ){//goes through all the filenames and takes their extensions and puts them in a separate array
			strLength = fileList[arrayParser].length();
			strParser = strLength -1;
			while(isPeriod == false){
				if(fileList[arrayParser].charAt(strParser) == '.'){
					withoutExtensions[arrayParser] = fileList[arrayParser].substring(strParser);
					isPeriod = true;
				}
				--strParser;
			}
			++arrayParser;
			isPeriod = false;
		}	
		return withoutExtensions;
	}
	
	public static String[] recombine(String[] string1, String[] string2){//recombines two strings into one string
		String[] combined = new String[string1.length];
		int i=0, end= string1.length;
		while(i<end){//goes through the arrays and combines them
			combined[i] = string1[i].concat(string2[i]);
			++i;
		}
		return combined;
	}
	
	public static void clearScreen(){
		int i=0;
		while(i<100){
			System.out.println();
			++i;
		}
		return;
	}

}