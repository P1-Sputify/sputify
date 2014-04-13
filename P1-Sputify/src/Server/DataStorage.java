/**
 * This class represents a data storage/base where all the information if stored. 
 */
package Server;

/**
 * @author Sebastian Aspegren
 * 
 */
public class DataStorage {
	
	public DataStorage() {
		
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
