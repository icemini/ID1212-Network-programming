import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable{
	
	private static Socket socket;
	private static BufferedReader fromServer; 
	
	public ClientThread(Socket socket, BufferedReader fromServer) {
		
		this.socket = socket;
		this.fromServer = fromServer;
	}

	@Override
	public void run() {
		
		// answer from server
		while (true) {
			
			//read and print answer from server
			String serverInput;
			try {
				serverInput = fromServer.readLine();
			 
			
			if (serverInput.equals("disconnect")) {
				socket.close();
				socket = null;
			}
			System.out.println(serverInput);
		}
			catch (IOException e) {
				System.out.println("Client disconnected");
				break;
			}
			
			}
		} 
	}