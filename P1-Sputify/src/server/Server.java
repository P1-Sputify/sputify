/**
 * 
 */
package server;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

/**
 * @authors mehmedagica, Sebastian Aspegren
 * 
 */
public class Server {

	private int port;

/**
 * The constructor for the server. Creates a thread with a new connect.
 * 
 * @param port the port we wish to bind the server to.
 */
	public Server(int port) {
		this.port = port;

		System.out.println("Server started");
		System.out.println("Server waiting for client connections...");
		// Handles client connection-give me a thread
		Thread connectThread = new Thread(new Connect());
		connectThread.start();
		System.out.println("A connect thread created and started...");
	}

	/**
	 * A private class representing the connection between the client and the
	 * server.
	 * 
	 * @author Sebastian Aspegren
	 * 
	 */
	private class Connect implements Runnable {
		/**
		 * The run method. It attempts to create a server on the given port then
		 * creates new threads where it attempts to communicate with the client.
		 */
		public void run() {
			ServerSocket serverSocket = null;
			Socket socket;
			// Thread that gives output
			Thread clientThread;
			// Thread that receives input
			Thread clientListener;

			try {
				serverSocket = new ServerSocket(port);
				System.out.println("Server socket created on port " + port);
				while (true) {
					socket = serverSocket.accept(); // Här ska kommunikationen
													// med klienten startas
					System.out.println("Server socket accept client");
					clientThread = new Thread(new TalkToClient(socket));
					clientListener = new Thread(new ListenToClient(socket));
					clientThread.start();
					clientListener.start();
					System.out
							.println("A new client thread created and started");
				}
			} catch (IOException e1) {
				System.out.println(e1);
			}

			try {
				
				serverSocket.close();
			} catch (Exception e) {
			}
		}
	}
/**
 * A private class that sends output to the client
 * @author Sebastian Aspegren
 *
 */
	private class TalkToClient implements Runnable {

		private Socket socket;
/**
 * The constructor for TalkToClient. Receives a socket that it uses for output.
 * 
 * @param socket 
 * 				The socket we use for output.
 */
		public TalkToClient(Socket socket) {
			this.socket = socket;
		}
/**
 * The run method. It attempts to send a byte[] to the client.
 */
		public void run() {

			final String filePath = "wavfiles/";
			final String fileName = "finishhim.wav";

			try {

				// Get/Read the file from hard drive or database

				// Have to use it to send data to the client
				ObjectOutputStream oos = new ObjectOutputStream(
						socket.getOutputStream());
				System.out.println("Object output stream created");
				// Here comes code to send the audio file to the client
				oos.writeObject(Controller.loadAudioFile(filePath + fileName));
				oos.flush();
				Thread.sleep(3000);
				System.out.println("File sent to the client");
			} catch (Exception e1) {
				System.out.println(e1);
			}

			try {
				if(!socket.isConnected())
				socket.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
/**
 * A class used to listen to input from the client.
 * @author Sebastian Aspegren
 *
 */
	private class ListenToClient implements Runnable {
		private Socket socket;
/**
 * The constructor for ListenToClient. Receives a socket which it uses for input.
 * @param socket
 */
		public ListenToClient(Socket socket) {
			this.socket = socket;
		}

		/**
		 * The run method. It attempts to write what was sent from the client if it receives a specific string.
		 */
		public void run() {
			

			try {
				ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
				while (true) {
				
				
					Object input = ois.readObject();
					//If client sent "Halloj"
					if (input.equals("Halloj")) {
						System.out.println("This was sent from the client "
								+ input);
						JOptionPane.showMessageDialog(null, input);

					}
				
				}
			} catch (Exception e1) {
				System.out.println(e1);
			}

			try {
			
				socket.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}

	}

	/** The main method for the server.
	 * @param args
	 */
	public static void main(String[] args) {
	
		new Server(57005);
	}

}
