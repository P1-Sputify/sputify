package Server;

import java.net.*;
import java.util.*;

import javax.sound.sampled.*;

import java.io.*;

public class KlientTest {
	private String serverIP;
	private int serverPort;
	BufferedReader bReader;
	Clip clip1;

	public KlientTest(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		Thread thread = new Thread(new ConnectAndListenToServer());
		thread.start();
	}
	
	public void playSound(byte[] song) {
	     try {
	    	 InputStream listen = new ByteArrayInputStream(song);
	         AudioInputStream ais = AudioSystem.getAudioInputStream(listen);
	         
	         DataLine.Info dli = new DataLine.Info(Clip.class, ais.getFormat());
	         clip1 = (Clip) AudioSystem.getLine(dli);
	         clip1.open(ais);
	         clip1.start();
	         
	     } 
	     catch(Exception ex) {
	         System.out.println("Error with playing sound.");
	         ex.printStackTrace();
	     }
	}
	
	
	private class ConnectAndListenToServer implements Runnable {
		public void run() {
			
			StringBuilder sb = new StringBuilder("");
			
			try {
				Socket socket = new Socket(InetAddress.getByName(serverIP), serverPort);
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
				System.out.println("Object output stream created");
				
				oos.writeObject(new String[] {"User1","Pwd1"});
				oos.flush();
				System.out.println("Username & pass sent to server");

				Hashtable<Integer, Track> htClient;
				
					htClient = (Hashtable<Integer, Track>) ois.readObject();
					System.out.println("Song list received from server");
				
					for (Integer key : htClient.keySet()) {
					   System.out.println(htClient.get(key));
					}
				
				
				oos.writeObject(new Integer(1));
				oos.flush();
				System.out.println("Track ID sent to server");
				Thread.sleep(3000);
				
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				bReader = new BufferedReader(isr);
		
				String song;
				while ((song = bReader.readLine()) != null) {
					
					sb.append(song);
				}
				
				String strConvertToBytes = sb.toString();
				
				byte[] sound = strConvertToBytes.getBytes();
				
				playSound(sound);
				
			} catch (Exception e) {
				System.out.println(e);
					
			} 
	}
	}

	public static void main(String[] args) {
		// new TCPClientD("195.178.232.145", 6666);
		new KlientTest("127.0.0.1", 5556);
	}
}
