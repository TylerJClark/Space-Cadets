import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * A client which can will have a name,
 * and can send messages to the server.
 * Clients will also receive messages other users have sent
 * 
 * @author TYLER
 *
 */
public class Client extends Thread{
	static boolean end = false;
	Socket socket;
	
	/**
	 * Starts the thread which listens for messages
	 * from the server
	 * 
	 * @param givenSocket the socket used for receiving messages
	 * from the server
	 */
	public Client(Socket givenSocket) {
		socket = givenSocket;
		start();
	}
	/**
	 * asks the user for their username in the room,
	 * then they can repeatedly type messages to send to the server
	 * @param args takes the ip address of the server as string
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        //opens socket
        try (Socket socket = new Socket(args[0], 59121)) {
        	//this is used to start the thread to listen for messages
        	//from the server
        	@SuppressWarnings("unused")
			Client myClient = new Client(socket);
        	
        	//gives the username that will appear
        	//by messages that this user sends
            System.out.println("Enter your name");
            Scanner scanner = new Scanner(System.in);
            
            //output from socket
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String input;
            while (scanner.hasNextLine() & !end) {
            	input = scanner.nextLine();
                out.println(input);
                //ends the loop if user wants to quit
                if (input.equals("quit")) {
                	end = true;
                }
            }
        }
    }
    /**
     * Thread which listens for messages from the server.
     * Ends if the user leaves the chat
     */
    public void run(){
        while (!end) {
        	Scanner in = null;
			try {
				//gets messages from server
				in = new Scanner(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
        	System.out.println(in.nextLine());
        	
        }
     }
}