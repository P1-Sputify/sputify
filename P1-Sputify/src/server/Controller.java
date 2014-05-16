/**
 * This class represents a data storage/base where all the information if stored. 
 */
package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Sebastian Aspegren
 * 
 */
public class Controller {
	 private DataStorage ds;
	 private Server newServer;
	
	public Controller() {
		this.ds = new DataStorage();
		this.newServer = new Server(57005);
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
	/**
	 * Read in audio file
	 * @param fileName
	 */
	public static byte[] loadAudioFile(String fileName) {
		
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
		  
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
			
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		 return audioBytes;
		
	}
	/**
	 * A method that reads a wav file and returns the amplitude of it in bytes.
	 * @param inFile
	 */
	public static void loadwavfile(String inFile){
		 		File file= new File(inFile);
		 		try {
		 			DataInputStream DIS= new DataInputStream(new FileInputStream(file));
		 			//short test=0;
		 			byte test=0;
		 		int counter=0;
		 
		 			while(DIS.available()>1){
		 			//	test=DIS.readShort();
		 				test=DIS.readByte();
		 				counter++;
		 		//		if(test<0)
		 		//			test=-test;	
		 
		 				System.out.print(test + ", ");
		 					
		 				}
		 			System.out.print(counter);
		 		}catch(Exception e){
		 			
		 		}
		  }
}