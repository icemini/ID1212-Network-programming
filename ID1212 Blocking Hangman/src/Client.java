
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;


public class Client {
	
	
	private static Socket socket;
	private static LinkedBlockingQueue queue;
	private static Scanner scanner = new Scanner(System.in);
	
	private static BufferedReader fromServer;
	private static PrintWriter toServer;
	
	public static void main(String[] args) {
		
		System.out.println("Enter 'connect' to connect to server");
		
		String input = scanner.nextLine();
		
		Client client = new Client();
		
		if (input.equals("connect")); {
			
		queue = new LinkedBlockingQueue();
		
		String hostname = "localhost";
		int portnumber = 4711;
		
		 try {
			socket = new Socket(hostname, portnumber);
			
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			toServer = new PrintWriter(socket.getOutputStream(), true); //purpose of Printwriter is to use the println method, autoflush is set to true
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		 
		 System.out.println("Enter 'start game' to begin playing ");
		
		   client.initializeClient();
		   client.initializeSender();
		}
		   client.handleUserInput();	  
	}
	
	// thread for receiving data from the server
	public void initializeClient() {
		
		Thread clientThread = new Thread(new ClientThread(socket, fromServer));
		clientThread.start();		
	}
	
	// thread for sending the user commands from the queue to the server
	public void initializeSender() {
		
		Thread sendingThread = new Thread(new sendingThread(socket, queue, toServer));
		sendingThread.start();	
	}
	
	// handles the data from the user and adds it to a queue
	public void handleUserInput() {
		
		while (true) {
			
			String input = scanner.nextLine();
			
			queue.add(input);	
		}
	}
	
	static class sendingThread implements Runnable {
		Socket socket;
		PrintWriter toServer;			//whatever is written to the output stream will be received by the connecting socket on the receiving side
		LinkedBlockingQueue queue;		//use LinkedBlockingQueue for blocking

		public sendingThread(Socket socket, LinkedBlockingQueue queue, PrintWriter toServer) {
			this.socket = socket;
			this.queue = queue;
			this.toServer = toServer;
		}

		@Override
		public void run() {
				
			while (true) {
			try {
				// take first message of queue and send to server
				toServer.println(queue.take());
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
				}
			}
		}
	}
}
