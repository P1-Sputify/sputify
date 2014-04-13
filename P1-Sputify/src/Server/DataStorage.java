/**
 * This class represents a data storage/base where all the information if stored. 
 */
package Server;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Sebastian Aspegren
 * 
 */
public class DataStorage {
	
	private String filePath = ("mp3files/");
	
	public DataStorage() {
		
		loadAudioFile(filePath + "Heroes of Newerth Sounds - Witch Slayer Voice.mp3");
		
	}
	
	/**
	 * Read in audio file
	 * @param fileName
	 */
	public static void loadAudioFile(String fileName) {
		
		int totalFramesRead = 0;
		File fileIn = new File(fileName);
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
//		  // Set an arbitrary buffer size of 1024 frames.
//		  int numBytes = 1024 * bytesPerFrame; 
//		  byte[] audioBytes = new byte[numBytes];
//		  try {
//		    int numBytesRead = 0;
//		    int numFramesRead = 0;
//		    // Try to read numBytes bytes from the file.
//		    while ((numBytesRead = 
//		      audioInputStream.read(audioBytes)) != -1) {
//		      // Calculate the number of frames actually read.
//		      numFramesRead = numBytesRead / bytesPerFrame;
//		      totalFramesRead += numFramesRead;
//		      // Here, do something useful with the audio data that's 
//		      // now in the audioBytes array...
//		    }
//		  } catch (IOException e1) {
//				System.out.println(e1);
//		  }
			
		} catch (IOException | UnsupportedAudioFileException e) {
			System.out.println(e);
		}
		
	}


	/**
	 * A method used to verify the user by comparing the given username and
	 * password to the ones in the dataStorage.
	 * 
	 * @param userName
	 *            The username we want to verify and compare
	 * @param password
	 *            The password we want to verify and compare.
	 * 
	 * @return True if the user exists. False if the user was not found.
	 */
	public boolean verifyUser(String userName, String password) {

		return false;

	}

	/**
	 * A method used to get a track with a specific id.
	 * 
	 * @param trackId
	 *            The id of the track we want to find.
	 * @return The track if it was found, if not return null.
	 */
	public Track getTrack(int trackId) {

		return null;

	}

	/**
	 * A method used to get a user with a specific id.
	 * 
	 * @param UserId
	 *            The id of the user we want to find.
	 * 
	 * @return The user if it was found. If the user was not found return null.
	 */
	public User getUser(int UserId) {

		return null;

	}
}
