package server;

import java.net.*;

import javax.sound.sampled.*;

import java.io.*;

public class KlientTest {
	private String serverIP;
	private int serverPort;
	

	public KlientTest(String serverIP, int serverPort) {
	//	playSound();
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
					    //	Object object= input.readObject();
					    	sound=(byte[] ) object;
					    	InputStream myInputStream = new ByteArrayInputStream(sound);
					     //   AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(myInputStream);
					    //    Clip clip = AudioSystem.getClip();
					  //      clip.open(audioInputStream);
					//        clip.start();
					    } catch(Exception ex) {
					        System.out.println("Error with playing sound.");
					        ex.printStackTrace();
					    
					}
				}
			} catch (IOException e) {
				System.out.println(e);
					
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	}

	public static void main(String[] args) {
		// new TCPClientD("195.178.232.145", 6666);
		new KlientTest("127.0.0.1", 5556);
	}
}
