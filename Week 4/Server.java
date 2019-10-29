import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * Chat room application, allowing users to message via a server
 * uses threading to deal with multiple users
 * 
 * 
 * A server to receive messages from users 
 * and send them to all other users in connected to
 * the server
 * 
 * @author TYLER
 *
 */
public class Server {
	/**
	 * Listens for connections, then starts new threads for each user
	 * @param args no args
	 * @throws IOException
	 */
    public static void main(String[] args) throws IOException {
    	
    	//create server with port number
        try (ServerSocket listener = new ServerSocket(59121)) {
        	
            System.out.println("Server is running...");
            
            //Create pool of threads, then use a thread to handle
            //the new user
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Capitalizer(listener.accept()));
            }
        }
    }

}
/**
 * A class for each user connected.
 * Stores the name, and maintains connection.
 * Listens for messages the user sends, and sends messages
 * this user sends
 * @author TYLER
 *
 */
class Capitalizer implements Runnable {
    private Socket socket;
    private String name = null;
    static ArrayList<Socket> allUsers = new ArrayList<Socket>();

    /**
     * Constructor which gives the user a socket and 
     * adds the new user socket
     * to the total list of users
     * 
     * @param socket the users socket
     */
    Capitalizer(Socket socket) {
        this.socket = socket;
        allUsers.add(this.socket);
    }

    /**
     * Gets messages from the user and sends them to 
     * all users in the list
     * Also messages when someone joins the room
     */
    @Override
    public void run() {
    	String message;
        System.out.println("Connected: " + socket);
        try {
        	//input from socket
            Scanner in = new Scanner(socket.getInputStream());
            //output from socket
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            PrintWriter messageOut;
            while (in.hasNextLine()) {
            	//defines a new user's username
            	if (name == null) {
	            	name = in.nextLine();
	            	message = "[Server]: " + name + " has joined the room";
	            	System.out.println(message);
	                            
	            	//tells other users that this user has joined
	            	//the room
            		for (int i = 0; i < allUsers.size(); i++) {
            			messageOut = new PrintWriter(allUsers.get(i).getOutputStream(), true);
            			messageOut.println(message);
            		}
            		
            	}
            	//used to send messages that users type
            	else {
            		message = "[" + name + "]: " + in.nextLine();
            		System.out.println(message);
            		
            		//send messages sent by users to all other users
            		for (int i = 0; i < allUsers.size(); i++) {
            			messageOut = new PrintWriter(allUsers.get(i).getOutputStream(), true);
            			messageOut.println(message);
            		}
            	}
            }
        } catch (Exception e) {
            System.out.println("Error:" + socket);
        } finally {
            try { socket.close(); } catch (IOException e) {}
            System.out.println("Closed: " + socket);
        }
    }
}
