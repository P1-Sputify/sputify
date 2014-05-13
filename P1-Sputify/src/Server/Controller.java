/**
 * 
 */
package Server;

import java.net.*;
import java.util.Hashtable;
import java.io.*;

import javax.sound.sampled.*;

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
			    
			   
			  
			  } catch (IOException e1) {
					System.out.println(e1);
			  }
				
			} catch (IOException | UnsupportedAudioFileException e) {
				System.out.println(e);
			}
			 return audioBytes;
		}
	
		public void run() {
			
			final String fp = "wavfiles/";
			final String fn = "finishhim.wav";
	
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
					
//					byte[] audioBytes = null;
//					FileOutputStream fos = null;
//					BufferedOutputStream bos = null;
					// somePathName is a pre-existing string whose value was
					// based on a user selection.
//					try {
						
						//System.out.println(">> Create a new file with queued Track ID...");
						//File fileIn = new File(DataStorage.getTrack(trackId).getLocation());						
					
						System.out.println(">> Send file as byte array...");
						oos.writeObject(loadAudioFile(DataStorage.getTrack(trackId).getLocation()));
						oos.flush();
						System.out.println(">> File sended to the client");
//						if(fileIn.exists() && !fileIn.isDirectory()) { 
//							System.out.println(">> File exist...");
//							
//						} else {
//							System.out.println(">> ERROR! File not found");
//							System.out.println(">> ERROR! File not sended to the client");
//						}
//						
//						System.out.println(">> A new file created...");
						
//						//AudioFileFormat aff = AudioSystem.getAudioFileFormat(fileIn);
//						
//						//System.out.println(">> AudioFileFormat:\nByteLenght: " + aff.getByteLength() + "\nFormat: " + aff.getFormat());
//						
//						System.out.println(">> Create Audio Input Stream with created file...");
//						//BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream(DataStorage.getTrack(trackId).getLocation()));  
//						//AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
//						
//						//Here is failure
//						AudioInputStream ais = AudioSystem.getAudioInputStream(fileIn);
//						System.out.println(">> Audio Input Stream created...");
//						
//						System.out.println(">> Get count bytes per frame");
//						int bytesPerFrame = ais.getFormat().getFrameSize();
//						if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
//							// some audio formats may have unspecified frame size
//							// in that case we may read any amount of bytes
//							bytesPerFrame = 1;
//						} 
//						
//						System.out.println(">> Set buffer size and create new byte array");
//						// Set an arbitrary buffer size of 1024 frames.
//						int numBytes = 4096 * bytesPerFrame; 
//						audioBytes = new byte[numBytes];
//						try {
//							int numBytesRead = 0;
//							int numFramesRead = 0;
//					    
//							System.out.println(">> Create File and Buffer Output Streams");
//							fos = new FileOutputStream(fileIn);
//							bos = new BufferedOutputStream(fos);
//					    
//							System.out.println(">> Read bytes...");
//							// Try to read numBytes bytes from the file.
//							while ((numBytesRead = ais.read(audioBytes)) > 0) {
//								// Calculate the number of frames actually read.
//								numFramesRead = numBytesRead / bytesPerFrame;
//								//Read bytes array into buffered output stream
//								System.out.println(">> ... and send to client throught buffered output stream...");
//								bos.write(audioBytes, 0, numBytesRead);
//							}
//					    
//							bos.flush();
//							bos.close();
//							ais.close();
//							System.out.println("File sent to the client");
							
					  
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
						
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
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
