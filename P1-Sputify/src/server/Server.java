/**
 * 
 */
package server;

import java.net.*;
import java.util.Hashtable;
import java.io.*;

import javax.swing.*;

/**
 * @authors mehmedagica, Sebastian Aspegren
 * 
 */
public class Server {

	private int		port;
	private String	state	= null;
	/**
	 * The constructor for the server. Creates a thread with a new connect.
	 * 
	 * @param port
	 *            the port we wish to bind the server to.
	 */
	public Server(int port) {
		this.port = port;

		System.out.println("Server started");
		JOptionPane.showMessageDialog(null, "Server started");
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
			// Thread clientListener;

			try {
				InetAddress host = InetAddress.getLocalHost();
				serverSocket = new ServerSocket(port);
				System.out.println("Server socket with IP " + host.getHostAddress() + " created on port " + port);
				while (true) {
					socket = serverSocket.accept();
					System.out.println("Server socket accept client");
					clientThread = new Thread(new TalkToClient(socket));
					// clientListener = new Thread(new ListenToClient(socket));
					clientThread.start();
					// clientListener.start();
					System.out.println("A new client thread created and started");
				}
			} catch (IOException e1) {
				System.out.println(e1);
			}

			try {
				serverSocket.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * A private class that sends output to the client
	 * 
	 * @author Sebastian Aspegren
	 * @author Amir Mehmedagic
	 */
	private class TalkToClient implements Runnable {

		private Socket	socket;

		/**
		 * The constructor for TalkToClient. Receives a socket that it uses for
		 * output.
		 * 
		 * @param socket
		 *            The socket we use for output.
		 */
		public TalkToClient(Socket socket) {
			this.socket = socket;
		}

		/**
		 * The run method. It attempts to send a byte[] to the client.
		 */
		public void run() {

			try {
				// Have to use it to send data to the client
				System.out.println(">> Creating Object Output Stream");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				System.out.println(">> Object Output Stream created");

				// Have to use it to receive data from the client
				System.out.println(">> Creating Object Input Stream");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				System.out.println(">> Object input stream created");

				Object recievedObject;
				String message;
				String[] loginArray = null;
				boolean loggedIn = false;

				while (socket.isConnected()) {
					
					recievedObject = ois.readObject();

					if (recievedObject instanceof String[]) {
						loginArray = (String[]) recievedObject;
						System.out.println("<< User login received: " + loginArray[0] + " " + loginArray[1]);
						if (DataStorage.verifyUser(loginArray[0], loginArray[1])) {
							loggedIn = true;
							oos.writeObject("login success");
							System.out.println(">> Sent login success message");
						} else {
							loggedIn = false;
							loginArray = null;
							oos.writeObject("login failed");
							System.out.println(">> Sent login failed message");
						}
					} else if (recievedObject instanceof String) {
						message = (String) recievedObject;
						if (message.equalsIgnoreCase("send playlist")) {
							if (loggedIn) {
								Hashtable<Integer, Track> songList = DataStorage.tracks;
								System.out.println(">> Create Track list and send it to the client as Hash Table");
								oos.writeObject(songList);
								oos.flush();
								System.out.println(">> Track list sent to the client");
							} else {
								System.out.println(">> ERROR! Incorrect login name and/or password");
								oos.writeObject("not logged in");
							}
						} else if (message.equalsIgnoreCase("logout")) {
							loggedIn = false;
							loginArray = null;
							oos.writeObject("logged out");
						} else {
							System.out.println(message);
						}
					} else if (recievedObject instanceof Integer) {
						Integer trackId = (Integer) recievedObject;
						System.out.println("<< Track ID received");

						if (trackId > 0) {
							// Send the audio file to the client
							oos.writeObject(Controller.loadAudioFile(DataStorage.getTrack(trackId).getLocation()));
							oos.flush();
							System.out.println("File sent to the client");
						}
					}
				}
			} catch (Exception e) {
				if(e.getClass().getName().contains("SocketException"))
					System.out.println(">>The client reseted connection or client socket fails of some reason.");
				else
					e.printStackTrace();
			}

			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * A class used to listen to input from the client.
	 * 
	 * @author Sebastian Aspegren
	 * @author Amir Mehmedagic
	 */
	// private class ListenToClient implements Runnable {
	// private Socket socket;
	// /**
	// * The constructor for ListenToClient. Receives a socket which it uses for
	// input.
	// * @param socket
	// */
	// public ListenToClient(Socket socket) {
	// this.socket = socket;
	// }
	//
	// /**
	// * The run method. It attempts to write what was sent from the client if
	// it receives a specific string.
	// */
	// public void run() {
	//
	//
	// try {
	//
	// //Have to use it to receive data from the client
	// System.out.println(">> Creating Object Onput Stream");
	// ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	// System.out.println(">> Object input stream created");
	//
	// System.out.println("<< Wait for user name and password from the client");
	// //Get user name and password
	// //as string array
	// userLogin = (String[])ois.readObject();
	// System.out.println("<< User login received: " + userLogin[0] + " " +
	// userLogin[1]);
	// setState("Send TrackList");
	//
	// System.out.println("<< Wait for track id from client...");
	// //Wait for client to ask about a audio file
	// Integer trackId = (Integer)ois.readObject();
	// System.out.println("<< Track ID received");
	// setState("Send AudioFile");
	//
	//
	// // while (true) {
	// //
	// //
	// // Object input = ois.readObject();
	// // //If client sent "Halloj"
	// // if (input.equals("Halloj")) {
	// // System.out.println("This was sent from the client "
	// // + input);
	// // JOptionPane.showMessageDialog(null, input);
	// // setState("Start");
	// //
	// // }
	// //
	// // }
	// } catch (Exception e1) {
	// System.out.println(e1);
	// }
	//
	// try {
	//
	// socket.close();
	// } catch (IOException e) {
	// System.out.println(e);
	// }
	// }
	//
	// }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// /** The main method for the server.
	// * @param args
	// */
	// public static void main(String[] args) {
	//
	// new Server(57005);
	// }

}