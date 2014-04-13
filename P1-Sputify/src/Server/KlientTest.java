package Server;

import java.net.*;

import javax.sound.sampled.*;

import java.io.*;

public class KlientTest {
	private String serverIP;
	private int serverPort;
	

	public KlientTest(String serverIP, int serverPort) {
		playSound();
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		Thread thread = new Thread(new ConnectAndListenToServer());
		thread.start();
	}
	 public void playSound() {
	     try {
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("wavfiles/finishhim.wav").getAbsoluteFile());
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
		
			byte[] sound;
				while (true) {
					
					    try {
					    	sound=(byte[] ) input.readObject();
					    	InputStream myInputStream = new ByteArrayInputStream(sound);
					        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(myInputStream);
					        Clip clip = AudioSystem.getClip();
					        clip.open(audioInputStream);
					        clip.start();
					    } catch(Exception ex) {
					        System.out.println("Error with playing sound.");
					        ex.printStackTrace();
					    
					}
				}
			} catch (IOException e) {
				System.out.println(e);
					
			} 
	}
	}

	public static void main(String[] args) {
		// new TCPClientD("195.178.232.145", 6666);
		new KlientTest("127.0.0.1", 5556);
	}
}
