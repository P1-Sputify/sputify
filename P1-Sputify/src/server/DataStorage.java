/**
 * 
 */
package server;

import java.util.Hashtable;
/**
 * @author Nethakaaru
 * 
 */
public class DataStorage {
	//TrackID as key.
	private Hashtable<String, Track> tracks;

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
