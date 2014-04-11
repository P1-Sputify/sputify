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
		
		//Handles client connection-give me a thread
		Thread connectThread = new Thread(new Connect()); 
		connectThread.start();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] meddelanden = { "Veni, vidi, vici", 
				"Jag kom, jag såg, jag segrade",
				"Alea iacta est", 
				"Tärningen är kastad",
				"Et tu Brute", 
				"Även du, min käre Brutus" };
		new Controller( 5556, meddelanden );
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
				serverSocket = new ServerSocket( port );
				while( true ) {
					socket = serverSocket.accept(); // Här ska kommunikationen med klienten startas
					clientThread = new Thread( new TalkToClient( socket ) );
					clientThread.start();
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
	
			try {
				
				//Get/Read the file from hard drive or database
				//Buffert????
				
				//Have to use it to send data to the client
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
				
				//Here comes code to send the audio file to the client
				for( int i = 0; i < messages.length; i++ ) { 
					oos.writeObject( messages[ i ] );
					oos.flush();
					Thread.sleep( 3000 );
				}

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
