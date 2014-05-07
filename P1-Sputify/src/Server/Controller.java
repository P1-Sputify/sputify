/**
 * 
 */
package Server;

import java.net.*;
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
	
		public void run() {
			
			final String filePath = "wavfiles/";
			final String fileName = "finishhim.wav";
	
			try {				
				
				//Have to use it to send data to the client
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
				System.out.println("Object output stream created");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				System.out.println("Object input stream created");
				
				//Get user name and password
				//as string array
				String[] userLogin = null;
					userLogin = (String[])ois.readObject();
					System.out.println("User login received: " + userLogin[0] + " " + userLogin[1]);
				
				//If user is OK then send track list to the client
				if(DataStorage.verifyUser(userLogin[0], userLogin[1])) {
					//Send track list to the client as HashTable
					oos.writeObject(DataStorage.tracks);
					oos.flush();
					System.out.println("Track list sended to the client");
					Thread.sleep(3000);
				}
				
				//Wait for client to ask about a audio file
				Integer trackId = 0;
				Boolean waitForTrackId = true;
				while(waitForTrackId) {
					trackId = (Integer)ois.readObject();
					System.out.println("Track ID received");
					waitForTrackId = false;
				}
				
				
				
				//Send audio file to the client
				if (trackId > 0) {
//					//Here comes code to send the audio file to the client
//					oos.writeObject(DataStorage.loadAudioFile(trackId));
//					oos.flush();
//					System.out.println("File sent to the client");
//					Thread.sleep( 3000 );
					
					int totalFramesRead = 0;
					//File fileIn = new File(DataStorage.getTrack(trackId).getLocation());
					//C:/WavFiles/tones_100-900_8_bit_square.wav
					File fileIn = new File("wavfiles/finishhim.wav");
					byte[] audioBytes = null;
					FileOutputStream fos = null;
					BufferedOutputStream bos = null;
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
					    
					    fos = new FileOutputStream(fileIn);
						bos = new BufferedOutputStream(fos);
					    
					    // Try to read numBytes bytes from the file.
					    while ((numBytesRead = audioInputStream.read(audioBytes)) > 0) {
					      // Calculate the number of frames actually read.
					      numFramesRead = numBytesRead / bytesPerFrame;
					      totalFramesRead += numFramesRead;
					      // Here, do something useful with the audio data that's 
					      // now in the audioBytes array...
					      bos.write(audioBytes, 0, numBytesRead);
					    }
					    
					    bos.flush();
						bos.close();
						audioInputStream.close();
						System.out.println("File sent to the client");
						Thread.sleep( 3000 );
					  
					  } catch (IOException e1) {
							System.out.println(e1);
					  }
						
					} catch (IOException | UnsupportedAudioFileException e) {
						System.out.println(e);
					}
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
