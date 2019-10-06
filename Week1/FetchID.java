import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class FetchID {//sp5r11

	public static void main(String[] args) throws IOException {
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter your email id");
		String emailIDFind = bufferReader.readLine();
		GetName(emailIDFind);
		//GetRelated(emailIDFind);
		
		String findFirstURl = bufferReader.readLine();
		
		

        
    }
    
    public static void GetName(String emailIDFind) throws IOException {
		String emailID = "https://www.ecs.soton.ac.uk/people/";
		URL emailURL = new URL(emailID);
		
        BufferedReader in = new BufferedReader(
        new InputStreamReader(emailURL.openStream()));
        
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
        	//System.out.println(inputLine.indexOf("<",61));
        	
        	
        	if (inputLine.contains("name")) {
        		if (inputLine.contains("/people/" + emailIDFind)) {
        		
        			System.out.println(inputLine.substring(61,(inputLine.indexOf("<",61))));
        		}
        		//System.out.println(inputLine);
        	}
        	
        }  
        
    }
    public static void GetRelated(String emailIDFind) throws IOException {
		String emailID = "https://www.ecs.soton.ac.uk/people/" + emailIDFind + "related_people";
		URL emailURL = new URL(emailID);
		
        BufferedReader in = new BufferedReader(
        new InputStreamReader(emailURL.openStream()));
        
        String inputLine;
        System.out.println("");
        System.out.println("Related People:");
        System.out.println("");
        while ((inputLine = in.readLine()) != null) {
        	//System.out.println(inputLine.indexOf("<",61));
        	
        	
        	if (inputLine.contains("<tr><td><span class=\"js-tableSort-name")) {
        		//System.out.println(inputLine);
        		System.out.println(inputLine.substring(61,(inputLine.indexOf("<",61))));

        	}
        	
        	
        }  
        
    }

    
    public static void GetFirstURL(String search) {

    }
    
}
