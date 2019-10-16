import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*Extended Barebones interpreter with if statements, 
 * print statements, basic operations and comments
 * 
 * 
 * Examples
 * 
 * if statement
 * if x == 5;
 * --code
 * end
 * 
 * if x > 5;
 * --code
 * end
 * 
 * if x == y;
 * --code
 * end;
 * 
 * operators
 * x + 5;
 * x - 4;
 * x * 3;
 * x / 6;
 * x + y;
 * 
 * increase or decrease by 1
 * incr x;
 * decr x;
 * 
 * set var to 0
 * clear x;
 * 
 * while loop
 * 
 * while x not 0 do;
 * --code
 * end;
 * 
 * 
 * todo list
 * 
 * else statements
 * subroutines
 */

public class BareBonesExtended {
	
	static int[] vars = new int[100];
	static String[] varNames = new String[100];
	static int memoryPointer = 0;
	static String varName;
	static String line = null;
	
	static int lineNumber;
	static int[] returnLines = new int[25];
	static int returnLinesCounter = 0;
	static int openPairCounter = 0;
	static int closePairCounter = 0;
	static int skipToEnd = 0;
	static int fileCounter = 0;
    static int operand = 0;
    static int varLocation = 0;
    static String operation = "";
    
    static String[] endType = new String[10];
    static int endTypePointer = 0;
	
	public static void main(String[] args) throws IOException {
	   System.out.println("Welcome to bare bones\n");
	   long startTime = System.nanoTime(); //times the program

	   File file = new File("src\\textFiles\\runThis.txt"); 
	   BufferedReader br = new BufferedReader(new FileReader(file)); 
       String[] wholeFile = new String[500];
       
       
       //this maps strings to the correct method
	   Map<String, Runnable> commands = new HashMap<>();
	   commands.put("clear", () -> clear(wholeFile[lineNumber]));
       commands.put("print", () -> print(wholeFile[lineNumber]));
       commands.put("incr", () -> incr(wholeFile[lineNumber])); 
       commands.put("decr", () -> decr(wholeFile[lineNumber]));
       commands.put("while", () -> whileDo(wholeFile[lineNumber]));
       commands.put("end;", () -> end());
       commands.put("if", () -> ifDo(wholeFile[lineNumber]));
       commands.put("+", () -> add());
       commands.put("-", () -> subtract());
       commands.put("*", () -> multiply());
       commands.put("/", () -> divide());
	   
       String[] operators = {"+","-","*","/"};//used to check what a line is meant to do
       

	   while ((line = br.readLine()) != null) {
		   
		   wholeFile[fileCounter] = line.trim() + " "; //remove whitespace
		   
		 //remove comments
		   if (wholeFile[fileCounter].contains("//")) {
			   wholeFile[fileCounter] = wholeFile[fileCounter].substring(0,wholeFile[fileCounter].indexOf("//")) + " "; 
		   }
		   fileCounter += 1;
		   

	   	} 
	   	br.close();
	   	
	   	
	   	while (lineNumber < fileCounter) {
	   		if (skipToEnd == 0) {
			    if (!wholeFile[lineNumber].equals(" ")) { //removes blank lines	  

			    	
			    	//check if the start of the line contains a command
		    		if (commands.containsKey(wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' ')))) { 
		    			//run command
		    			commands.get(wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' '))).run();	
		    		}
		    		
		    		//check if start of line contains variable
		    		//if so, this means the user is changing a variable with an operator
		    		else if (Arrays.stream(operators).anyMatch(wholeFile[lineNumber].substring(wholeFile[lineNumber].indexOf(" ") + 1,wholeFile[lineNumber].indexOf(" ") + 2)::equals)) { 
		    			
		    			
		    			int posOfOperand = wholeFile[lineNumber].indexOf(" ",wholeFile[lineNumber].indexOf(" ") + 1) + 1;
		    			//checks if an integer of a variable is used as operand
		    			if (wholeFile[lineNumber].substring(posOfOperand,wholeFile[lineNumber].indexOf(";",posOfOperand)).matches(".*\\d.*")) {
			    			operand = Integer.parseInt(wholeFile[lineNumber].substring(wholeFile[lineNumber].indexOf(" ",wholeFile[lineNumber].indexOf(" ") + 1) + 1,wholeFile[lineNumber].indexOf(';')));
		    			}
		    			else {
		    				operand = vars[Arrays.asList(varNames).indexOf(wholeFile[lineNumber].substring(posOfOperand,wholeFile[lineNumber].indexOf(";",posOfOperand)))];
		    			}
		    			
		    			//gets the location of the variable in the array
		    			varLocation = Arrays.asList(varNames).indexOf(wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(" ")));
		    			
		    			commands.get(wholeFile[lineNumber].substring(wholeFile[lineNumber].indexOf(" ") + 1,wholeFile[lineNumber].indexOf(" ") + 2)).run(); //run the command
		    		}
		    		
		    		//if line does not contain any recognised syntax, give error
		    		else {
		    			System.err.println("Invalid Syntax");
		    		}
			    }

	   		}
	   		
	   		else {
	   		//used to pass over if and while statements which are not run
	   			if (wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' ')).equals("end;")){
	   				skipToEnd -= 1;
	   			}
	   			
	   			//this keeps track of if and while statements passed so the program knows which line to go to,
	   			//instead of stopping at the wrong end statements
	   			else if (wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' ')).equals("while") |
	   					wholeFile[lineNumber].substring(0,wholeFile[lineNumber].indexOf(' ')).equals("if")) {
	   				skipToEnd += 1;
	   			}
	   		}
		    lineNumber += 1;
	   	}
	   	//end of program
	   	//calculate time taken
	   	long endTime   = System.nanoTime();
	   	long totalTime = endTime - startTime;
	   	System.out.println("Time taken: " + totalTime / 1000 + " microseconds");
	   	System.out.println("Program finished");
	   	
	}

	
	public static void clear(String line) { //sets a var to 0
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
	
	//output
	public static void print(String line) {
		   varName = line.substring(6,line.indexOf(";",6));
		   if (Arrays.asList(varNames).contains(varName)) {
			   System.out.println(vars[Arrays.asList(varNames).indexOf(varName)]);
		   }
		   else {//if variable has not been created
			  System.out.println("Variable " + varName + " created");
			  vars[memoryPointer] = 0;
			  varNames[memoryPointer] = varName;
			  memoryPointer += 1;
		   }
	}
	
	//decrease variable by 1
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
	
	//increase variable by 1
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
	
	//while loop
	public static void whileDo(String line) {
		   varName = line.substring(6,line.indexOf(" ",7));
		   
		   if (Arrays.asList(varNames).contains(varName) & vars[Arrays.asList(varNames).indexOf(varName)] != 0) { //run while loop
			   returnLines[returnLinesCounter] = lineNumber - 1;
			   returnLinesCounter += 1;
			   endType[endTypePointer] = "while";
			   endTypePointer += 1;
		   }
		   
		   else { //dont run while loop
			   skipToEnd += 1;
		   }

	}
	
	public static void ifDo(String line) {
		int posInString = line.indexOf(" ",3);
		try { //checks if variable exists
		int var = vars[Arrays.asList(varNames).indexOf(line.substring(3,posInString))];

		
		int posInString2 = line.indexOf(" ",posInString + 1);
		operation = line.substring(posInString + 1,posInString2);
		
		int operand2;
		if (line.substring(posInString2 + 1,line.indexOf(";",posInString2 + 1)).matches(".*\\d.*")) {
			operand2 = Integer.parseInt(line.substring(posInString2 + 1,line.indexOf(";",posInString2 + 1)));
		}
		else {
			operand2 = vars[Arrays.asList(varNames).indexOf(line.substring(posInString2 + 1,line.indexOf(";",posInString2 + 1)))];
		}

		//if any of these run, the if statement is skipped as condition was not met
		if (operation.equals("==")) {
			if (!(var == operand2)) {
				skipToEnd += 1;

				//skip this section
			}
			//else it will just carry on executing
		}
		
		else if (operation.equals(">")) {
			if (!(var > operand2)) {
				skipToEnd += 1;
			}
		}
		
		else if (operation.equals("<")) {
			if (!(var < operand2)) {
				skipToEnd += 1;
			}
		}
		
		else if (operation.equals(">=")) {
			if (!(var >= operand2)) {
				skipToEnd += 1;
			}
		}
		
		else if (operation.equals("<=")) {
			if (!(var <= operand2)) {
				skipToEnd += 1;
			}
		}
		
		else { //if statement has run, program should continue as normal
			endType[endTypePointer] = "if";
			endTypePointer += 1;
		}
		
		}
		catch(Exception e){
			System.err.println("Variable not found");
		}

	}
	
	public static void end() { //shows the end of a while or if statement
		if (endTypePointer > 0) { //if the last conditional was a while loop, loop back to the top
			if (endType[endTypePointer - 1].equals("while")) {
				returnLinesCounter -= 1;
				lineNumber = returnLines[returnLinesCounter];
			}
			endTypePointer -= 1;
		}
		
	}
	
	//operations
	public static void add() {
		vars[varLocation] += operand;
	}
	
	public static void subtract() {
		vars[varLocation] -= operand;
	}
	
	public static void multiply() {
		vars[varLocation] *= operand;
	}
	
	public static void divide() {
		vars[varLocation] /= operand;
	}

}
