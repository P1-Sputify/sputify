/**
 * 
 */
package Server;

import java.net.*;
import java.io.*;

/**
 * @authors mehmedagica, Sebastian Aspegren
 *
 */
public class Controller {
	
	
	private int port;
	private String[] messages;
	
	public Controller(int port, String[] strCom) {
		this.port = port;
		this.messages = strCom;
		
		System.out.println("Server started");
		System.out.println("Server waiting for client connections...");
		//Handles client connection-give me a thread
		Thread connectThread = new Thread(new Connect()); 
		connectThread.start();
		System.out.println("A connect thread created and started...");
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] meddelanden = null;
		new Controller(5556, meddelanden);
	}
	
	/**
	 * A private class representing the connection between the client and the server.
	 * 
	 * @author Sebastian Aspegren
	 *
	 */
	private class Connect implements Runnable { 
		/**
		 * The run method. It attempts to create a server on the given port
		 *  then creates a new thread where it attempts to communicate with the client.
		 */
		public void run() {
			ServerSocket serverSocket = null;
			Socket socket; 
			Thread clientThread;
		
			try {
				serverSocket = new ServerSocket(port);
				System.out.println("Server socket created on port " + port);
				while( true ) {
					socket = serverSocket.accept(); // Här ska kommunikationen med klienten startas
					System.out.println("Server socket accept client");
					clientThread = new Thread(new TalkToClient(socket));
					clientThread.start();
					System.out.println("A new client thread created and started");
				}
			} 
			catch( IOException e1 ) {
				System.out.println( e1 );
			}
		
			try {
				serverSocket.close();
			} 	
			catch( Exception e ) {} 
		} 
	}
	
	private class TalkToClient implements Runnable { 
		
		private Socket socket;
	
		public TalkToClient(Socket socket) {
			this.socket = socket;
		}
	
		public void run() {
			
			final String filePath = "mp3files/";
			final String fileName = "Heroes of Newerth Sounds - Witch Slayer Voice.mp3";
	
			try {
				
				//Get/Read the file from hard drive or database
				
				
				//Have to use it to send data to the client
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
				System.out.println("Object output stream created");
				//Here comes code to send the audio file to the client
				oos.writeObject(DataStorage.loadAudioFile(filePath + fileName));
				oos.flush();
				Thread.sleep( 3000 );
				System.out.println("File sended to the client");
			} 
			catch(Exception e1 ) {
				System.out.println( e1 );
			}
	
			try {
				socket.close();
			} 
			catch( IOException e ) { 
				System.out.println( e );
			}
		}
	}
}
