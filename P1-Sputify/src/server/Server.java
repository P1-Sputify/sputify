/**
 * 
 */
package server;

import java.net.*;
import java.util.*;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * @authors mehmedagica, Sebastian Aspegren
 * 
 */
public class Server {

	private int		port;
	private String	state	= null;
	private DataStorage ds;
	private AdminGUI adminGUI;
	private Thread connectThread;
	
	/**
	 * The constructor for the server. Creates a thread with a new connect.
	 * 
	 * @param port
	 *            the port we wish to bind the server to.
	 */
	public Server(int port) {
		this.ds = new DataStorage();
		this.adminGUI = new AdminGUI(this);
		this.port = port;
	}
		
	public void serverStart() {
		connectThread = new Thread(new Connect());
		connectThread.start();
		//System.out.println("Server started");
		adminGUI.appendText("Server started");
	}
	
	public void serverStop() {
		
		connectThread.interrupt();
		connectThread = null;
		//adminGUI.appendText("Server stopped");
		//adminGUI.dispose();
		System.exit(1);
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
				//InetAddress host = InetAddress.getLocalHost();
				serverSocket = new ServerSocket(port);
				//System.out.println("Server socket with IP " + host.getHostAddress() + " created on port " + port);
				adminGUI.appendText("Server socket with IP " + getIP() + " created on port " + port);

				while (true) {
					socket = serverSocket.accept();
//					System.out.println("Server socket accept client");
					adminGUI.appendText("Server socket accept client");
					clientThread = new Thread(new TalkToClient(socket));
					clientThread.start();
//					System.out.println("A new client thread created and started");
					adminGUI.appendText("A new client thread created and started");
				}
			} catch (IOException e1) {
//				System.out.println(e1);
				adminGUI.appendText(e1.getMessage());
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
//				System.out.println(">> Creating Object Output Stream");
				adminGUI.appendText(">> Creating Object Output Stream");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//				System.out.println(">> Object Output Stream created");
				adminGUI.appendText(">> Object Output Stream created");

				// Have to use it to receive data from the client
//				System.out.println(">> Creating Object Input Stream");
				adminGUI.appendText(">> Creating Object Input Stream");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//				System.out.println(">> Object input stream created");
				adminGUI.appendText(">> Object input stream created");

				Object recievedObject;
				String message;
				String[] loginArray = null;
				boolean loggedIn = false;

				while (socket.isConnected()) {
					
					recievedObject = ois.readObject();

					if (recievedObject instanceof String[]) {
						loginArray = (String[]) recievedObject;
//						System.out.println("<< User login received: " + loginArray[0] + " " + loginArray[1]);
						adminGUI.appendText("<< User login received: " + loginArray[0] + " " + loginArray[1]);
						if (DataStorage.verifyUser(loginArray[0], loginArray[1])) {
							loggedIn = true;
							oos.writeObject("login success");
//							System.out.println(">> Sent login success message");
							adminGUI.appendText(">> Sent login success message");
						} else {
							loggedIn = false;
							loginArray = null;
							oos.writeObject("login failed");
//							System.out.println(">> Sent login failed message");
							adminGUI.appendText(">> Sent login failed message");
						}
					} else if (recievedObject instanceof String) {
						message = (String) recievedObject;
						if (message.equalsIgnoreCase("send playlist")) {
							if (loggedIn) {
								Hashtable<Integer, Track> songList = DataStorage.tracks;
//								System.out.println(">> Create Track list and send it to the client as Hash Table");
								adminGUI.appendText(">> Create Track list and send it to the client as Hash Table");
								oos.writeObject(songList);
								oos.flush();
//								System.out.println(">> Track list sent to the client");
								adminGUI.appendText(">> Track list sent to the client");
							} else {
//								System.out.println(">> ERROR! Incorrect login name and/or password");
								adminGUI.appendText(">> ERROR! Incorrect login name and/or password");
								oos.writeObject("not logged in");
							}
						} else if (message.equalsIgnoreCase("logout")) {
							loggedIn = false;
							loginArray = null;
							oos.writeObject("logged out");
						} else {
//							System.out.println(message);
							adminGUI.appendText(message);
						}
					} else if (recievedObject instanceof Integer) {
						Integer trackId = (Integer) recievedObject;
//						System.out.println("<< Track ID received");
						adminGUI.appendText("<< Track ID received");

						if (trackId > 0) {
							// Send the audio file to the client
							oos.writeObject(loadAudioFile(DataStorage.getTrack(trackId).getLocation()));
							oos.flush();
//							System.out.println("File sent to the client");
							adminGUI.appendText("File sent to the client");
						}
					}
				}
			} catch (Exception e) {
				if(e.getClass().getName().contains("SocketException"))
//					System.out.println(">>The client reseted connection or client socket fails of some reason.");
				adminGUI.appendText(">>The client reseted connection or client socket fails of some reason.");
				else
					e.printStackTrace();
				adminGUI.appendText(e.getMessage());
			}

			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				adminGUI.appendText(e.getMessage());
			}
		}
	}

	
	/**
	 * Read in audio file
	 * @param fileName
	 */
	public static byte[] loadAudioFile(String fileName) {
		
		int totalFramesRead = 0;
		File fileIn = new File(fileName);
		byte[] audioBytes = null;
		// somePathName is a pre-existing string whose value was
		// based on a user selection.
		try {
		  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
		  int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
		    if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
		    // some audio formats may have unspecified frame size
		    // in that case we may read any amount of bytes
		    bytesPerFrame = 1;
		  } 
		  // Set an arbitrary buffer size of 1024 frames.
		  int numBytes = 1024 * bytesPerFrame; 
		  audioBytes = new byte[numBytes];
		  try {
		    int numBytesRead = 0;
		    int numFramesRead = 0;
		    // Try to read numBytes bytes from the file.
		    while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
		      // Calculate the number of frames actually read.
		      numFramesRead = numBytesRead / bytesPerFrame;
		      totalFramesRead += numFramesRead;
		      // Here, do something useful with the audio data that's 
		      // now in the audioBytes array...
		    } 
		  
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
			
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		 return audioBytes;
		
	}
	
	/**
	 * A method that reads a wav file and returns the amplitude of it in bytes.
	 * @param inFile
	 */
	public static void loadwavfile(String inFile) {
		//Turn the path into a file
 		File file= new File(inFile);
 		try {
 			DataInputStream DIS= new DataInputStream(new FileInputStream(file));
 			//short test=0;
 			byte test=0;
 		int counter=0;
 		//while there is more to read from the inputStream
 			while(DIS.available()>1){
 			//	test=DIS.readShort();
 				test=DIS.readByte();
 				counter++;
 		//		if(test<0)
 		//			test=-test;	
 //print out the byte and a ","
 				System.out.print(test + ", ");
 					
 				}
 			//print out the amount of bytes printed out.
 			System.out.print(counter);
 			DIS.close();
 		}catch(Exception e){
 			
 		}
	}
	
	/**
	 * A method that returns the ip of the server.
	 * 
	 * @return the servers ip address.
	 */
	public String getIP() {
		InetAddress host;
		try {
			host = InetAddress.getLocalHost();
			return host.getHostAddress();
		} catch (UnknownHostException e) {	
			adminGUI.appendText(e.getMessage());
		}
		return "UNKNOWN";

	}
	
	public String getTracks() {
		String strTracks = "";
		ArrayList<Track> t = ds.getTrackValues();
		for (int i = 0; i < t.size(); i++) {
			strTracks = strTracks + "\n" + t.get(i).toString() + "\n";
		}
		return strTracks;
	}
	
	public String getUsers() {
		String strUsers = "";
		ArrayList<User> t = ds.getUserValues();
		for (int i = 0; i < t.size(); i++) {
			strUsers = strUsers + "\n" + t.get(i).toString() + "\n";
		}
		return strUsers;
	}
	
	public void updateDataPanelWithTracks() {
		adminGUI.getTaData().setText("");
		adminGUI.getTaData().setText(getTracks());
		adminGUI.getTaData().setCaretPosition(0);
	}
	
	public void updateDataPanelWithUsers() {
		adminGUI.getTaData().setText("");
		adminGUI.getTaData().setText(getUsers());
		adminGUI.getTaData().setCaretPosition(0);
	}

	 /** The main method for the server.
	 * @param args
	 */
	 public static void main(String[] args) {
		 new Server(57005);
	 }

}