/**
 * This class represents a data storage/base where all the information if stored. 
 */
package server;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Sebastian Aspegren
 * @rev		Amir Mehmedagic
 */
public class DataStorage {
	
	private AdminGUI adminGUI;
	public static Hashtable<Integer, Track> tracks;
	public static TreeMap<Integer, User> users;
	
	public static Connection connection;
    public static Statement statement;
    
    ResultSet rsTracks;
    ResultSet rsUsers;
    
    String dbName = "ac9574";
    //String dbName = "dbsputify";
	
    /**
     * Constructor
     * Create data structures for audio tracks and users
     * And populate them with data from database
     */
	public DataStorage(AdminGUI adminGUI) {
		
		this.adminGUI = adminGUI;
		
		tracks = new Hashtable<Integer, Track>(100);
		users = new TreeMap<Integer, User>();
		
		getTracks();	
		getUsers();
	}
	
	/**
	 * Create result sets and get data from database 
	 * @throws SQLException
	 */
	public void getTracks() {
		
		try {
			adminGUI.appendText("Connecting to database, please wait...");
			connect();
			rsTracks = statement.executeQuery("SELECT * FROM " + dbName + ".track");
			loadTracks(rsTracks);
			disconnect();
			adminGUI.appendText("Connected to database");
		} catch (SQLException e) {
			adminGUI.appendText( e.getMessage());
		}		
	}
	
	/**
	 * Create result sets and get data from database 
	 * @throws SQLException
	 */
	public void getUsers() {
		
		try {
			connect();
			rsUsers = statement.executeQuery("SELECT * FROM " + dbName + ".user");
			loadUsers(rsUsers);
			disconnect();
		} catch (SQLException e) {
			adminGUI.appendText(e.getMessage());
		}		
		
	}
	
	/**
	 * Load tracks from DB to the HashTable
	 * @throws SQLException
	 */
	public void loadTracks(ResultSet rsTracks) {
		
		try {
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
		} catch (SQLException e) {
			adminGUI.appendText(e.getMessage());
		}
	}

	/**
	 * Load users from DB to the MapTree
	 * @throws SQLException
	 */
	public void loadUsers(ResultSet rsUsers) {
		
		try {
			while(rsUsers.next()) {
				
				User aUser = new User(
						rsUsers.getInt("id"),
						rsUsers.getString("name"),
						rsUsers.getString("password"),
						rsUsers.getString("screenName"));
			
				addUserToMapTree(rsUsers.getInt("id"), aUser);
			}
		} catch (SQLException e) {
			adminGUI.appendText(e.getMessage());
		}
	}
	
	/**
	 * Add a Track to the HashTable
	 * @param trackId, int, track ID
	 * @param trackData, Track, track object with data
	 */
	public void addTrackToHashTable(Integer trackId, Track trackData) {
		tracks.put(trackId, trackData);
	}
	
	/**
	 * Add a User to the MapTree
	 * @param userId, int, user ID
	 * @param userData, User, user object with data
	 */
	public void addUserToMapTree(Integer userId, User userData) {
		users.put(userId, userData);
	}
	
	/**
	 * Update a track
	 * @param trackId
	 * @param trackData
	 * @throws SQLException
	 */
	public void updateATrack(Integer trackId, Track trackData) {
		
		try {
			connect();
			statement.executeUpdate("UPDATE " + dbName + ".track SET" +
					" trackName = " + trackData.getName() +
					" artist = " + trackData.getArtist() +
					" length = " + trackData.getLength() +
					" album = " + trackData.getAlbum() +
					" location = " + trackData.getLocation() +
					" WHERE id=" + trackId);
			disconnect();
			
			getTracks();
			
		} catch (SQLException e) {
			adminGUI.appendText(e.getMessage());
		}
	}
	
	/**
	 * Update a user
	 * @param userId
	 * @param userData
	 * @throws SQLException
	 */
	public void updateAUser(Integer userId, User userData) {
		
		try {
			connect();
			statement.executeUpdate("UPDATE " + dbName + ".track SET" +
					" name = " + userData.getName() +
					" password = " + userData.getPassword() +
					" screenName = " + userData.getScreenName() +
					" WHERE id=" + userId);
			disconnect();

			getUsers();
		} catch (SQLException e) {
			adminGUI.appendText(e.getMessage());
		}		
	}
	
	/**
	 * Delete a track
	 * @param trackId
	 * @throws SQLException
	 */
	public void deleteATrack(Integer trackId) {
		
		try {
			connect();
			statement.executeUpdate("DELETE FROM " + dbName + ".track" +
					" WHERE id=" + trackId);
			disconnect();

			getTracks();
		} catch (SQLException e) {
			adminGUI.appendText(e.getMessage());
		}
	}
	
	/**
	 * Delete a user
	 * @param userId
	 * @throws SQLException
	 */
	public void deleteAUser(Integer userId) {
		
		try {
			connect();
			statement.executeUpdate("DELETE FROM " + dbName + ".track" +
					" WHERE id=" + userId);
			disconnect();

			getUsers();
		} catch (SQLException e) {
			adminGUI.appendText(e.getMessage());
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
	public static boolean verifyUser(String userName, String password) {

        for(Integer key: users.keySet()){
        	String uN = users.get(key).getName();
        	String uP = users.get(key).getPassword();
        	if(uN.equals(userName) && uP.equals(password))
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
	
	public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsputify","root","Mornar22!0");
            connection = DriverManager.getConnection("jdbc:mysql://195.178.232.7:4040/AC9574","AC9574","Sputify7");
            statement = connection.createStatement();
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println("Databas-driver hittades ej: "+e);
            e.getStackTrace();
            adminGUI.appendText(e.getMessage());
        }
    }
    
    public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }
	
	public static int countRows(ResultSet resultSet) throws SQLException {
    	resultSet.last();    
        int rowCount = resultSet.getRow();
        resultSet.beforeFirst();
        
        return rowCount;
    }
	
	/**
	 * Get track list as ArrayList<Track>
	 * 
	 * @return ArrayList<Track>
	 */
	public ArrayList<Track> getTrackValues() {	
		ArrayList<Track> t = new ArrayList<Track>();
		Set<Map.Entry<Integer, Track>> eSet = tracks.entrySet();
		
		for(Entry<Integer, Track> etr : eSet) {
			t.add(etr.getValue());
		}
		
		return t;
	}
	
	/**
	 * Get user list as ArrayList<User>
	 * 
	 * @return ArrayList<User>
	 */
	public ArrayList<User> getUserValues() {	
		ArrayList<User> t = new ArrayList<User>();
		Set<Map.Entry<Integer, User>> eSet = users.entrySet();
		
		for(Entry<Integer, User> etr : eSet) {
			t.add(etr.getValue());
		}
		
		return t;
	}
}
