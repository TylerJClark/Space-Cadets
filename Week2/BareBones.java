import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Print statements have also been added
 * example:
 * print x;
 */
public class BareBones {
	
	//used to store variables. Names are used to reference variables
	//memory pointer is used to get the position of the variable in the array
	static int[] vars = new int[100];
	static String[] varNames = new String[100];
	static int memoryPointer = 0;
	
	static String varName; //stores a variable name
	
	static String line = null;
	static int lineNumber;
	
	static int[] returnLines = new int[25];
	static int returnLinesCounter = 0;
	
	static int skipToEnd = 0;
	static int fileCounter = 0;
	
	public static void main(String[] args) throws IOException {
	   System.out.println("Welcome to bare bones\n");

	   
	   //read from file path
	   File file = new File("src\\textFiles\\runThis.txt"); 
	   BufferedReader br = new BufferedReader(new FileReader(file)); 
       String[] wholeFile = new String[500];
       
       
       //create a map which maps all of the commands to different operations in the language
	   Map<String, Runnable> commands = new HashMap<>();
	   commands.put("clear", () -> clear(wholeFile[lineNumber]));
       commands.put("print", () -> print(wholeFile[lineNumber]));
       commands.put("incr", () -> incr(wholeFile[lineNumber]));
       commands.put("decr", () -> decr(wholeFile[lineNumber]));
       commands.put("while", () -> whileDo(wholeFile[lineNumber]));
       commands.put("end;", () -> whileEnd(wholeFile[lineNumber]));   

       //removes whitespace and counts how many lines there are 
	   while ((line = br.readLine()) != null) {	   
		   wholeFile[fileCounter] = line.trim() + " ";
		   fileCounter += 1;		   
	   	} 
	   	br.close();	   	
	   	
	   	//reads each commands until the bottom line is passed
	   	while (lineNumber < fileCounter) {
	   		
	   		//skips over sections which arent supposed to be run, such as if statement that aren't true	   		
	   		if (skipToEnd == 0) {
	   			
	   			//removes blank lines	
			    if (!wholeFile[lineNumber].equals("")) {
			    	   
			       //runs the command on this line
				   commands.get(wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' '))).run();
			    }
	   		}
	   		else {
	   			//checks when how many different loops statements need to be skipped over
	   			if (wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' ')).equals("end;")){
	   				skipToEnd -= 1;
	   			}
	   			else if (wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' ')).equals("while")) {
	   				skipToEnd += 1;
	   			}
	   		}
		    lineNumber += 1;
	   	}
	   	System.out.println("Program finished");	   	
	}
	
	/*
	 * sets a variable equal to 0
	 * if variable has not yet been created, it is made
	 */
	public static void clear(String line) {
	    varName = line.substring(6,line.indexOf(";",6));
	    if (Arrays.asList(varNames).contains(varName)) {
		    vars[Arrays.asList(varNames).indexOf(varName)] = 0;
	    }
	    else {
		   System.out.println("Variable " + varName + " created");
		   vars[memoryPointer] = 0;
		   varNames[memoryPointer] = varName;
		   memoryPointer += 1;
	    }
	}
	
	/*
	 * outputs a variable to console
	 * if variable has not yet been created, it is initialised with value 0
	 */
	public static void print(String line) {
		   varName = line.substring(6,line.indexOf(";",6));
		   if (Arrays.asList(varNames).contains(varName)) {
			   System.out.println(vars[Arrays.asList(varNames).indexOf(varName)]);
		   }
		   else {
			  System.out.println("Variable " + varName + " created");
			  vars[memoryPointer] = 0;
			  varNames[memoryPointer] = varName;
			  memoryPointer += 1;
		   }
	}
	
	/*
	 * lowers the value of a variable by 1.
	 * lowest value of a variable is 0, so will not lower any further
	 * if variable has not been made, will initialise it to 0
	 */
	
	public static void decr(String line) {
		   varName = line.substring(5,line.indexOf(";",5));
		   if (Arrays.asList(varNames).contains(varName)) {
			   if (vars[Arrays.asList(varNames).indexOf(varName)] > 0) {
				   vars[Arrays.asList(varNames).indexOf(varName)] -= 1;
			   }
		   }
		   else {
			  System.out.println("Variable " + varName + " created");
			  vars[memoryPointer] = 0; //initialise var with 0
			  varNames[memoryPointer] = varName;
			  memoryPointer += 1;
		   }
	}
	
	/*
	 * increases variable value by 1
	 * will create variable if it doesn't already exist
	 */
	public static void incr(String line) {
		   varName = line.substring(5,line.indexOf(";",5));
		   if (Arrays.asList(varNames).contains(varName)) {
			   vars[Arrays.asList(varNames).indexOf(varName)] += 1;
		   }
		   else {
			  System.out.println("Variable " + varName + " created");
			  vars[memoryPointer] = 1;  //initialise with 1
			  varNames[memoryPointer] = varName;
			  memoryPointer += 1;
		   }
	}
	
	/*
	 * runs a loop until a variable equals 0
	 * updates the return lines array, so at the end of the loop, it knows where to return to
	 */
	public static void whileDo(String line) {
		   varName = line.substring(6,line.indexOf(" ",7));
		   if (Arrays.asList(varNames).contains(varName) & vars[Arrays.asList(varNames).indexOf(varName)] != 0) {
			   returnLines[returnLinesCounter] = lineNumber - 1;
			   returnLinesCounter += 1;		   
		   }
		   
		   else {
			   skipToEnd += 1;
		   }
	}
	
	/*
	 * Shows the end of a while loop
	 * makes the line number return to the top of the while loop
	 */
	public static void whileEnd(String line) {
		returnLinesCounter -= 1;
		lineNumber = returnLines[returnLinesCounter];
	}
	
}