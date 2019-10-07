import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FetchID {

	public static void main(String[] args) throws IOException {
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));	
		System.out.println("Enter your email id"); //get user ID from user	
		String emailIDFind = bufferReader.readLine();
		String name = GetName(emailIDFind);
		System.out.println(name);
        
    }
    
    public static String GetName(String emailIDFind) throws IOException {
    	String name = "Name not found"; //incase name is not found    
    	
		String emailID = "https://www.ecs.soton.ac.uk/people/" + emailIDFind;
		URL emailURL = new URL(emailID);		
		
        BufferedReader in = new BufferedReader(
        new InputStreamReader(emailURL.openStream())); 
        
        String inputLine;
        boolean searching = true;
        int startIndex;
        
        while ((inputLine = in.readLine()) != null & searching) { //loop through the page      	
        	if (inputLine.contains("property=\"name\">")) {
	        	startIndex = inputLine.indexOf("property=\"name\">");	        	
	        	name = inputLine.substring(inputLine.indexOf(">",startIndex) + 1,inputLine.indexOf("<",startIndex));
	        	searching = false;	        	
	        }        	
        }         
        return name;        
    }    
}
