/**
 * This class represents a data storage/base where all the information if stored. 
 */
package Server;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Sebastian Aspegren
 * 
 */
public class DataStorage {
	
	private String filePath = ("mp3files/");
	public static Hashtable<Integer, Track> tracks;
	public static TreeMap<Integer, User> users;
	
	public DataStorage() {
		
		//loadAudioFile(filePath + "Heroes of Newerth Sounds - Witch Slayer Voice.mp3");
		try {
			loadTracks();
			loadUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load tracks from DB to the HashTable
	 * @throws SQLException
	 */
	public void loadTracks() throws SQLException {
		
		ResultSet rsTracks = MySqlDB.getResultSet("SELECT * FROM ac9574.track");
		
		while(rsTracks.next()) {
			
			Track aTrack = new Track(
					rsTracks.getInt("id"),
					rsTracks.getString("trackName"),
					rsTracks.getString("artist"),
					rsTracks.getInt("length"),
					rsTracks.getString("album"),
					rsTracks.getString("location"));
			
			addTrackToHashTable(rsTracks.getInt("id"), aTrack);
        }
	}

	/**
	 * Load users from DB to the MapTree
	 * @throws SQLException
	 */
	public void loadUsers() throws SQLException {
		
		ResultSet rsUsers = MySqlDB.getResultSet("SELECT * FROM ac9574.user");
		
		while(rsUsers.next()) {
			
			User aUser = new User(
					rsUsers.getInt("id"),
					rsUsers.getString("name"),
					rsUsers.getString("password"),
					rsUsers.getString("screenName"));
			
			addUserToMapTree(rsUsers.getInt("id"), aUser);
        }	
	}
	
	/**
	 * Add a Track to the HashTable
	 * @param trackId, int, track ID
	 * @param trackData, Track, track object with data
	 */
	public void addTrackToHashTable(int trackId, Track trackData) {
		tracks.put(trackId, trackData);
	}

	/**
	 * Add a User to the MapTree
	 * @param userId, int, user ID
	 * @param userData, User, user object with data
	 */
	public void addUserToMapTree(int userId, User userData) {
		users.put(userId, userData);
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
		    
		   
		  
		  } catch (IOException e1) {
				System.out.println(e1);
		  }
			
		} catch (IOException | UnsupportedAudioFileException e) {
			System.out.println(e);
		}
		 return audioBytes;
		
	}

	/**
	 * Read in audio file
	 * @param trackId
	 */
	public static byte[] loadAudioFile(Integer trackId) {
		
		
		
		int totalFramesRead = 0;
		File fileIn = new File(getTrack(trackId).getLocation());
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
		    
		   
		  
		  } catch (IOException e1) {
				System.out.println(e1);
		  }
			
		} catch (IOException | UnsupportedAudioFileException e) {
			System.out.println(e);
		}
		 return audioBytes;
		
	}

	
//	NOT USED
//	User verification will be done in the controller
//	TreeMap containing users is done as static and accessible by controller	
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
	public static boolean verifyUser(String userName, String password) {

//        for(Integer key: users.keySet()){
//        	if(users.get(key).getName()==userName && users.get(key).getPassword()==password)
//        		return true;
//        }
         
        for(Map.Entry<Integer, User> entry : users.entrySet()) {
        	if(entry.getValue().getName()==userName && entry.getValue().getPassword()==password)
        		return true;
        }
        
		return false;
	}

	/**
	 * A method used to get a track with a specific id.
	 * 
	 * @param trackId
	 *            The id of the track we want to find.
	 * @return The track if it was found, if not return null.
	 */
	public static Track getTrack(int trackId) {
		Track gTrack = null;
		if (tracks.containsKey(trackId))
			gTrack = tracks.get(trackId);
		return gTrack;
	}

	/**
	 * A method used to get a user with a specific id.
	 * 
	 * @param UserId
	 *            The id of the user we want to find.
	 * 
	 * @return The user if it was found. If the user was not found return null.
	 */
	public static User getUser(int userId) {
		User gUser = null;
		if(users.containsKey(userId))
			gUser = users.get(userId);
		return gUser;
	}
}
