package Server;

import java.net.*;

import javax.sound.sampled.*;

import java.io.*;

public class KlientTest {
	private String serverIP;
	private int serverPort;

	public KlientTest(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		Thread thread = new Thread(new ConnectAndListenToServer());
		thread.start();
	}

	private class ConnectAndListenToServer implements Runnable {
		public void run() {
			try {
				Socket socket = new Socket(InetAddress.getByName(serverIP),
						serverPort);
				ObjectInputStream input = new ObjectInputStream(
						socket.getInputStream());
				String message;
				while (true) {
					System.out.println("ClientD: Lyssnar...");
				//	message = (String) input.readObject();
					//System.out.println(message);
					try{
						AudioInputStream audioInputStream=AudioSystem.getAudioInputStream((InputStream) input.readObject());
						Clip clip=AudioSystem.getClip();
						clip.open();
						clip.start();
					}
					catch(Exception ex){}
				}
			} catch (IOException e) {
				System.out.println(e);
			} //catch (ClassNotFoundException e1) {
				//System.out.println(e1);
		//	}
		}
	}

	public static void main(String[] args) {
		// new TCPClientD("195.178.232.145", 6666);
		new KlientTest("127.0.0.1", 5556);
	}
}
