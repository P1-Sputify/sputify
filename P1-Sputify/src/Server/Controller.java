/**
 * 
 */
package Server;

import java.net.*;
import java.util.Hashtable;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @authors mehmedagica, Sebastian Aspegren
 *
 */
public class Controller {
	
	
	private int port;
	private String[] messages;
	DataStorage ds;
	
	public Controller(int port, String[] strCom) {
		this.port = port;
		this.messages = strCom;
		this.ds = new DataStorage();
		
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
					System.out.println("Server socket accept client connection");
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
		
		public byte[] loadAudioFile(String fileName) {
			
			
			return null;
		}
	
		public void run() {
			
			//final String filePath = "wavfiles/";
			//final String fileName = "finishhim.wav";
	
			try {				
				
				//Have to use it to send data to the client
				System.out.println(">> Creating Object Output Stream");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
				System.out.println(">> Object Output Stream created");
			
				
				//Have to use it to receive data from the client
				System.out.println(">> Creating Object Onput Stream");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				System.out.println(">> Object input stream created");
				
				
				
				System.out.println("<< Wait for user name and password from the client");
				//Get user name and password
				//as string array
				String[] userLogin = (String[])ois.readObject();
				System.out.println("<< User login received: " + userLogin[0] + " " + userLogin[1]);
				
				Hashtable<Integer, Track> songList = DataStorage.tracks;
				
				System.out.println(">> Create Track list and send it to the client as Hash Table");
				//If user is OK then send track list to the client
				if(DataStorage.verifyUser(userLogin[0], userLogin[1])) {
					oos.writeObject(songList);
					oos.flush();
					System.out.println(">> Track list sended to the client");
				}
				else {
					System.out.println(">> ERROR! Incorrect login name and/or password");
				}
				
				System.out.println("<< Wait for track id from client...");
				//Wait for client to ask about a audio file
				Integer trackId = (Integer)ois.readObject();
				System.out.println("<< Track ID received");
				
				//Send audio file to the client
				if (trackId > 0) {
//					//Here comes code to send the audio file to the client
//					oos.writeObject(DataStorage.loadAudioFile(trackId));
//					oos.flush();
//					System.out.println("File sent to the client");
//					Thread.sleep( 3000 );
					
					byte[] audioBytes = null;
					FileOutputStream fos = null;
					BufferedOutputStream bos = null;
					// somePathName is a pre-existing string whose value was
					// based on a user selection.
					try {
						
						System.out.println(">> Create a new file with queued Track ID...");
						File fileIn = new File(DataStorage.getTrack(trackId).getLocation());
						System.out.println(">> A new file created...");
						
						System.out.println(">> Create Audio Input Stream with created file...");
						//BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream(DataStorage.getTrack(trackId).getLocation()));  
						//AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
						
						//Here is failure
						AudioInputStream ais = AudioSystem.getAudioInputStream(fileIn);
						System.out.println(">> Audio Input Stream created...");
						
						System.out.println(">> Get count bytes per frame");
						int bytesPerFrame = ais.getFormat().getFrameSize();
						if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
							// some audio formats may have unspecified frame size
							// in that case we may read any amount of bytes
							bytesPerFrame = 1;
						} 
						
						System.out.println(">> Set buffer size and create new byte array");
						// Set an arbitrary buffer size of 1024 frames.
						int numBytes = 4096 * bytesPerFrame; 
						audioBytes = new byte[numBytes];
						try {
							int numBytesRead = 0;
							int numFramesRead = 0;
					    
							System.out.println(">> Create File and Buffer Output Streams");
							fos = new FileOutputStream(fileIn);
							bos = new BufferedOutputStream(fos);
					    
							System.out.println(">> Read bytes...");
							// Try to read numBytes bytes from the file.
							while ((numBytesRead = ais.read(audioBytes)) > 0) {
								// Calculate the number of frames actually read.
								numFramesRead = numBytesRead / bytesPerFrame;
								//Read bytes array into buffered output stream
								System.out.println(">> ... and send to client throught buffered output stream...");
								bos.write(audioBytes, 0, numBytesRead);
							}
					    
							bos.flush();
							bos.close();
							ais.close();
							System.out.println("File sent to the client");
					  
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					} catch (IOException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
				}
				
			} 
			catch(Exception e1) {
				e1.printStackTrace();
				//System.out.println( e );
			}
	
			try {
				socket.close();
			} 
			catch( IOException e ) {
				e.printStackTrace();
				//System.out.println( e );
			}
		}
	}
}
