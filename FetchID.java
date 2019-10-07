import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class FetchID {//sp5r11

	public static void main(String[] args) throws IOException {
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		//create buffered reader
		
		System.out.println("Enter your email id"); //get email id
		String emailIDFind = bufferReader.readLine();
		GetName(emailIDFind);

    }
    
    public static void GetName(String emailIDFind) throws IOException {
		String emailID = "https://www.ecs.soton.ac.uk/people/"; //url to get name from
		URL emailURL = new URL(emailID); //url object
		
        BufferedReader in = new BufferedReader(
        new InputStreamReader(emailURL.openStream()));
        
        String inputLine;
        while ((inputLine = in.readLine()) != null) { 
        	
        	
        	if (inputLine.contains("name")) { //filter out lines which to not contain name
        		if (inputLine.contains("/people/" + emailIDFind)) { //name for the person we are trying to find
        		
        			System.out.println(inputLine.substring(61,(inputLine.indexOf("<",61)))); //output the name
        			//61 is where the name starts, then read the name until the < character is found
        		}
        	}
        	
        }  
        
    }
    
}
