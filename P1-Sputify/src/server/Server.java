package server;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * @authors mehmedagica, Sebastian Aspegren
 * 
 * The class Server. Server functions as a server and a controller for the program. It handles logic 
 * and connections with clients.
 * 
 */
public class Server {

	private int		port;
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
		this.adminGUI = new AdminGUI(this);
		this.ds = new DataStorage(adminGUI);
		this.port = port;
	}
		
	public void serverStart() {
		connectThread = new Thread(new Connect());
		connectThread.start();
		adminGUI.appendText("Server started");
	}
	
	public void serverStop() {
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
		
			

			try {
				serverSocket = new ServerSocket(port);
				adminGUI.appendText("Server socket with IP " + getIP() + " created on port " + port);

				while (true) {
					socket = serverSocket.accept();
					adminGUI.appendText("Server socket accept client");
					clientThread = new Thread(new TalkToClient(socket));
					clientThread.start();

					adminGUI.appendText("A new client thread created and started");
				}
			} catch (IOException e1) {
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
				adminGUI.appendText(">> Creating Object Output Stream");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				adminGUI.appendText(">> Object Output Stream created");

				// Have to use it to receive data from the client
				adminGUI.appendText(">> Creating Object Input Stream");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				adminGUI.appendText(">> Object input stream created");

				Object recievedObject;
				String message;
				String[] loginArray = null;
				boolean loggedIn = false;

				while (socket.isConnected()) {
					
					recievedObject = ois.readObject();

					if (recievedObject instanceof String[]) {
						loginArray = (String[]) recievedObject;
						adminGUI.appendText("<< User login received: " + loginArray[0] + " " + loginArray[1]);
						if (DataStorage.verifyUser(loginArray[0], loginArray[1])) {
							loggedIn = true;
							oos.writeObject("login success");
							adminGUI.appendText(">> Sent login success message");
						} else {
							loggedIn = false;
							loginArray = null;
							oos.writeObject("login failed");
							adminGUI.appendText(">> Sent login failed message");
						}
						
					} else if (recievedObject instanceof String) {
						message = (String) recievedObject;
						if (message.equalsIgnoreCase("send playlist")) {
							if (loggedIn) {
								Hashtable<Integer, Track> songList = DataStorage.tracks;
								adminGUI.appendText(">> Create Track list and send it to the client as Hash Table");
								oos.writeObject(songList);
								oos.flush();
								adminGUI.appendText(">> Track list sent to the client");
							} else {
								adminGUI.appendText(">> ERROR! Incorrect login name and/or password");
								oos.writeObject("not logged in");
							}
						} else if (message.equalsIgnoreCase("logout")) {
							loggedIn = false;
							loginArray = null;
							oos.writeObject("logged out");
						} else {
							adminGUI.appendText(message);
						}
					} else if (recievedObject instanceof Integer) {
						Integer trackId = (Integer) recievedObject;
						adminGUI.appendText("<< Track ID received");

						if (trackId > 0) {
							// Send the audio file to the client
//							oos.writeObject(loadAudioFile(DataStorage.getTrack(trackId).getLocation()));
							oos.writeObject(loadwavfile(DataStorage.getTrack(trackId).getLocation()));
							oos.flush();
							adminGUI.appendText("File sent to the client");
						}
					}
				}
			} catch (Exception e) {
				if(e.getClass().getName().contains("SocketException"))
				adminGUI.appendText(">>The client reseted connection or client socket fails of some reason.");
				else
				adminGUI.appendText(e.getMessage());
			}

			try {
				socket.close();
			} catch (IOException e) {
				adminGUI.appendText(e.getMessage());
			}
		}
	}
	/**
	 * A method that reads a wav file and returns the amplitude of it in bytes.
	 * @param inFile
	 */
	public static byte[] loadwavfile(String inFile) {
		//Turn the path into a file
 		File file= new File(inFile);
 		try {
 			DataInputStream DIS= new DataInputStream(new FileInputStream(file));
 			//short test=0;
 			byte[] test= new byte[DIS.available()];
 		int counter=0;
 		//while there is more to read from the inputStream
 			while(DIS.available()>1){
 			//	test=DIS.readShort();
 				test[counter]=DIS.readByte();
 				counter++;
 		//		if(test<0)
 		//			test=-test;	
 //print out the byte and a ","
 	//			System.out.print(test + ", ");
 					
 				}
 			//print out the amount of bytes printed out.
 	//		System.out.print(counter);
 		
 			DIS.close();
 				return test;
 		}catch(Exception e){
 	
 		}
		return null;
	
	
	}
	
//	/**
//	 * Read in audio file
//	 * @param fileName
//	 */
//	public static byte[] loadAudioFile(String fileName) {
//		
//		File fileIn = new File(fileName);
//		byte[] audioBytes = null;
//		// somePathName is a pre-existing string whose value was
//		// based on a user selection.
//		try {
//		  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
//		  int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
//		    if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
//		    // some audio formats may have unspecified frame size
//		    // in that case we may read any amount of bytes
//		    bytesPerFrame = 1;
//		  } 
//		  // Set an arbitrary buffer size of 1024 frames.
//		  int numBytes = 1024 * bytesPerFrame; 
//		  audioBytes = new byte[numBytes];
//		  try {
//		    int numBytesRead = 0;
//		    int numFramesRead = 0;
//		    // Try to read numBytes bytes from the file.
//		    while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
//		      // Calculate the number of frames actually read.
//		      numFramesRead = numBytesRead / bytesPerFrame;
//		      // Here, do something useful with the audio data that's 
//		      // now in the audioBytes array...
//		    } 
//		  
//		  } catch (IOException e) {
//			  e.printStackTrace();
//			  
//		  }
//			
//		} catch (IOException | UnsupportedAudioFileException e) {
//			e.printStackTrace();
//		}
//		 return audioBytes;
//		
//	}
//	

	
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