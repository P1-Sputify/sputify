/**
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * @author Nethakaaru
 * 
 */
public class DataStorage {
	//TrackID as key.
	private Hashtable<String, Track> trackList= new Hashtable<String,Track>();
	//UserID as key
	private Hashtable<String, User> userList= new Hashtable<String,User>();
	
	public DataStorage(){
		loadTracks("textFiles/Tracks.txt");
		loadUsers("textFiles/Users.txt");
	}

	/**
	 * A method used to get a track with a specific id.
	 * 
	 * @param trackId
	 *            The id of the track we want to find.
	 * @return The track if it was found, if not return null.
	 */
	public Track getTrack(String trackId) {

		return trackList.get(trackId);

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
	private void loadTracks(String fileName)
	{
		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader ir = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(ir);

			String[] parts;
			String txt = br.readLine();

			while (txt != null)
			{
				parts = txt.split(";");

				User user = new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
				userList.put(parts[0], user);
				txt = br.readLine();
			}
		} catch (IOException e)
		{
			System.err.println("IOException: " + e.getMessage());

		}
		{

		}
	}
	private void loadUsers(String fileName)
	{
		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader ir = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(ir);

			String[] parts;
			String txt = br.readLine();

			while (txt != null)
			{
				parts = txt.split(";");

				Track track = new Track(parts[0], parts[1], parts[2], parts[3],Integer.parseInt(parts[4]), parts[5]);
				trackList.put(parts[0], track);
				txt = br.readLine();
			}
		} catch (IOException e)
		{
			System.err.println("IOException: " + e.getMessage());

		}
		{

		}
	}
	public static void main(String[]args){
		DataStorage ds= new DataStorage();
		System.out.println(ds.getTrack("7").toString());
	}
}
