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
	
	public void playSound(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
//		stream = AudioSystem.getAudioInputStream(new URL(
//      "http://hostname/audiofile"));

	    AudioFormat format = stream.getFormat();
	    if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
	      format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format
	          .getSampleRate(), format.getSampleSizeInBits() * 2, format
	          .getChannels(), format.getFrameSize() * 2, format.getFrameRate(),
	          true); // big endian
	      stream = AudioSystem.getAudioInputStream(format, stream);
	    }

	    SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, stream
	        .getFormat(), ((int) stream.getFrameLength() * format.getFrameSize()));
	    SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
	    line.open(stream.getFormat());
	    line.start();

	    int numRead = 0;
	    byte[] buf = new byte[line.getBufferSize()];
	    while ((numRead = stream.read(buf, 0, buf.length)) >= 0) {
	      int offset = 0;
	      while (offset < numRead) {
	        offset += line.write(buf, offset, numRead - offset);
	      }
	    }
	    line.drain();
	    line.stop();
	}
	
	
	public static synchronized void playASound(final String url) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(url));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		    	  e.printStackTrace();
		      }
		    }
		  }).start();
		}
	
	private class ConnectAndListenToServer implements Runnable {
		public void run() {
			
			StringBuilder sb = new StringBuilder("");
			
			try {
				Socket socket = new Socket(InetAddress.getByName(serverIP), serverPort);
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
				System.out.println(">> Object output stream created");
				
				oos.writeObject(new String[] {"User1","Pwd1"});
				oos.flush();
				System.out.println("<< Username & pass sent to server");

				System.out.println(">> Create hashtable object for song list");
				Hashtable<Integer, Track> htClient = (Hashtable<Integer, Track>) ois.readObject();
				
				System.out.println("<< Song list received from server");
			
				for (Integer key : htClient.keySet()) {
				   System.out.println(htClient.get(key));
				}
				
				System.out.println(">> Sending track ID to the server");
				Integer trackId = 1;
				oos.writeObject(trackId);
				oos.flush();
				System.out.println(">> Track ID sent to server");
				
				//playSound("C:/WavFiles/tones_100-900_8_bit_square.wav");
				//playASound("wavfiles/finishhim.wav");
				
				System.out.println(">> Create Input Stream And Buffered Reader");
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				bReader = new BufferedReader(isr);
		
				System.out.println(">> Create string to read stream into it");
				String song;
				while ((song = bReader.readLine()) != null) {
					sb.append(song);
					System.out.println(">> Reading from stream...");
				}
				
				System.out.println(">> Convert String to Bytes and get it to the byte array");
				String strConvertToBytes = sb.toString();
				byte[] sound = strConvertToBytes.getBytes();
				Integer songLenght = sound.length;
				
				System.out.println(">> At the end play the sound");
				playSound(sound);
			
			} 
			catch (EOFException e) {
				e.printStackTrace();

			}
				 
			catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	public static void main(String[] args) {
		// new TCPClientD("195.178.232.145", 6666);
		new KlientTest("127.0.0.1", 5556);
	}
}
