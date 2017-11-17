import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
		
	
	public static void main(String[] args) {
		
		
		try {
			
			ServerSocket listeningSocket = new ServerSocket(4711);	//create listening socket
			
			while (true) {
				
			
				Socket client = listeningSocket.accept();	// accept connection from client
				
				Thread serverThread = new Thread(new ServerThread(client));
				serverThread.setPriority(Thread.MAX_PRIORITY);
				serverThread.start();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}				
}


