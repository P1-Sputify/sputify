package server;

import java.net.*;

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
	private class ConnectAndListenToServer implements Runnable {
		public void run() {
			try {
				Socket socket = new Socket(InetAddress.getByName(serverIP),
						serverPort);
				ObjectInputStream input = new ObjectInputStream(
						socket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				Object object= input.readObject();
		int counter=0;
			byte[] sound;
				while (true) {
					
					    try {
					    	counter++;
					    	if(counter==3)
					    		output.writeObject("Halloj");
					    	output.writeObject("hej");
					   
					    	sound=(byte[] ) object;
					    	if(counter==3)
					    		JOptionPane.showMessageDialog(null, sound);
					    	InputStream myInputStream = new ByteArrayInputStream(sound);
					
					    } catch(Exception ex) {
					        System.out.println("Error with playing sound.");
					        ex.printStackTrace();
					    
					}
				}
			} catch (IOException e) {
				System.out.println(e);
					
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} 
	}
	}

	public static void main(String[] args) {
		
		new KlientTest("127.0.0.1", 57005);
	}
}
