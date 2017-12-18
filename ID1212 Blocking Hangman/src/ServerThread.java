import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// class is intended to be executed as a thread
public class ServerThread implements Runnable {
	
	// cannot be static since the threads will them share them instead of having them each separate
	private BufferedReader fromClient;
	private PrintWriter toClient;
	
	//blocking - when you try to read to the socket, when you call readline it will block, it will not return until there is something to read
		//		when accepting a socket it will not do anything (block) until it receives a connection
	
	private String word;
	private String dashedWord;
	private StringBuilder sb;
	private int score = 0;
	private int numOfGuesses = 0;
	
	Socket client;	// client that we receive input from
	
	public ServerThread(Socket client) {
		
		this.client = client;
		
	}
	
	// entry point for thread
	public void run() {  
		
		try {
			
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			toClient = new PrintWriter(client.getOutputStream(), true);
	                
			  
			  while (true) {
				  
				  String clientInput = fromClient.readLine();
			 
			/*	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} */
				  				  
				  if (clientInput.equals("start game")) {
					  
						  word = chooseWord();
						  numOfGuesses = word.length();
						  dashedWord = dashWord(word);
						  sb = new StringBuilder(dashedWord);
						  toClient.println("Number of guesses: " + numOfGuesses);
						  toClient.println("Word: " + dashedWord);
						  toClient.println("Guess character or word");
				  }
				  
				  
				  else if (clientInput.equals("quit")) {				  
					  toClient.println("disconnect");
					  client.close();				  
				  }
				  
				  // user guessed the correct word
				  else if (clientInput.equals(word)) {
					  
					  score++;
					  dashedWord = word;
					  toClient.println("You won! Current score: " + score);
					  toClient.println("Enter 'start game' to continue playing ");
				  }
				  
				  else if (!clientInput.equals(word) && clientInput.length() != 1) {
					  
					  numOfGuesses--;
					  toClient.println(sb.toString());
					  
						 if (numOfGuesses == 0) {
							 score--;
							 toClient.println("You lost! Current score: " + score);
							 toClient.println("The correct word was: " + word);
							 toClient.println("Enter 'start game' to continue playing ");
						 }
				  }
				  
				  // user guessed on a character 
				  else if (clientInput.length() == 1) {
					  
					  String saveWord = sb.toString();
					  
					  checkChar(word, clientInput.charAt(0), sb);
						  
						  // wrong character guess
						  if (saveWord.equals(sb.toString())) {
							  numOfGuesses--;
						  }
						  
						 toClient.println(sb.toString());
						 
						 if (!sb.toString().contains("-")) {				 
							 score++;
							 toClient.println("You won! Current score: " + score);
							 toClient.println("Enter 'start game' to continue playing ");
						 }
						 
						 if (numOfGuesses == 0) {
							 score--;
							 toClient.println("You lost! Current score: " + score);
							 toClient.println("The correct word was: " + word);
							 toClient.println("Enter 'start game' to continue playing ");
						 } 
				  }  
			  }
		}
		catch (IOException e) {
			System.out.println("Server disconnected");
		}	
	}
	
	public void checkChar(String word, char c, StringBuilder sb){
		
		ArrayList<Integer> letterPositions = new ArrayList<Integer>();
		
		  for (int i = 0; i < word.length(); i++) {
			  
			  if (c == word.charAt(i)) 
				  letterPositions.add(i);		  
		  }
		  					  				  
		  for (int i = 0; i < letterPositions.size(); i++) {
			 					 
			 sb.setCharAt(letterPositions.get(i), c);
		  }
	}
		
	// function that chooses a random word from a text file
	public String chooseWord() throws IOException  {
		
		// reads all lines at once
		List<String> lines = Files.readAllLines(new File("words.txt").toPath());
		
		Random rand = new Random();
				
		// choose a word randomly
		return lines.get(rand.nextInt(lines.size()));
	}
	
	public String dashWord(String word) {
		
		String dashedWord = word.replaceAll(".", "-");
		
		return dashedWord;
	}

}
