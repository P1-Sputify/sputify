package server;

import java.net.*;
import java.util.Hashtable;

import javax.sound.sampled.*;
import javax.swing.JOptionPane;

import java.io.*;
/**
 * 
 * @author Sebastian Aspegren
 *
 */
public class KlientTest {
	private String serverIP;
	private int serverPort;
	

	public KlientTest(String serverIP, int serverPort) {
	//	Thread musicThread=new Thread(new Runnable(){
		//	public void run(){
			//	playSound();
			//}
	//	});
	//	musicThread.start();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		Thread thread = new Thread(new ConnectAndListenToServer());
		thread.start();
	}
	 public void playSound()  {
	     try {
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("wavfiles/Late Knight2.wav").getAbsoluteFile());
	         Clip clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	         clip.start();
	     } catch(Exception ex) {
	         System.out.println("Error with playing sound.");
	         ex.printStackTrace();
	     }}
	 
	 public void playSound(byte[] song) {
	     try {
	    	 System.out.println(">> Read in byte array into input stream");
	    	 InputStream listen = new ByteArrayInputStream(song);
	    	 System.out.println(">> Input Stream created");
	    	 
	    	 System.out.println(">> Create audio input stream from input stream");
	         AudioInputStream ais = AudioSystem.getAudioInputStream(listen);
	         System.out.println(">> Audio input stream created");
	         
	         System.out.println(">> Create new data line info from Audio input stream");
	         DataLine.Info dli = new DataLine.Info(Clip.class, ais.getFormat());
	         System.out.println(">> Create a new Clip");
	         Clip clip1 = (Clip) AudioSystem.getLine(dli);
	         System.out.println(">> Open the clip");
	         clip1.open(ais);
	         System.out.println(">> Play the clip");
	         clip1.start();
	         
	     } 
	     catch(Exception ex) {
	         System.out.println("Error with playing sound.");
	         ex.printStackTrace();
	     }
	}
	 
	private class ConnectAndListenToServer implements Runnable {
		public void run() {
			try {
				Socket socket = new Socket(InetAddress.getByName(serverIP),	serverPort);
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				String message;
				Object recievedObject;
				
				output.writeObject(new String[] {"Sebastian Aspegren","Nethakaaru"});
				output.flush();
				System.out.println("<< Username & pass sent to server");

				recievedObject = input.readObject();
				if(recievedObject instanceof String){
					message = (String)recievedObject;
					System.out.println(message);
				}
				
				System.out.println();
				
				System.out.println(">> Request song list from server");
				output.writeObject("Send playlist");
				
				Hashtable<Integer, Track> htClient = null;
				recievedObject = input.readObject();
				if(recievedObject instanceof String){
					message = (String)recievedObject;
					System.out.println(message);
					return;
				} else {
					System.out.println(">> Create hashtable object for song list");
					htClient = (Hashtable<Integer, Track>) recievedObject;
					System.out.println("<< Song list received from server");
				}
				
				for (Integer key : htClient.keySet()) {
				   System.out.println(htClient.get(key));
				}
				
				System.out.println(">> Sending track ID to the server");
				Integer trackId = 1;
				output.writeObject(trackId);
				output.flush();
				System.out.println(">> Track ID sent to server");
				
				System.out.println(">> Read in byte array as object");
				Object object= input.readObject();
				//byte[] sound = (byte[]) input.readObject();
				
				System.out.println(">> Create byte array from object");
				byte[] sound = (byte[]) object;
				System.out.println(">> Byte array object created");
			    	//JOptionPane.showMessageDialog(null, sound);
			    	//InputStream myInputStream = new ByteArrayInputStream(sound);
			    
				System.out.println(">> The client processing of audio file.");
				output.writeObject("The audio file is received.");
				output.flush();
		    	//System.out.println(">> Play the sound");
				//playSound(sound);
				//System.out.println(">> Sound played");
			
			} catch (IOException e) {
				e.printStackTrace();
					
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} 
	}
	}

	public static void main(String[] args) {
		
		//new KlientTest("10.2.22.157", 57005);
		new KlientTest("localhost", 57005);
	}
}
