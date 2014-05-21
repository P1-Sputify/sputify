/**
 * 
 */
package server;

import java.io.Serializable;

/**
 * @authors mehmedagica, Sebastian Aspegren.
 * 
 * A class representing a song/track. The track had an id, a name, an artist, a length and an album it belongs to.
 * Added a line to class description
 *
 */
public class Track implements Serializable {
	
	private int id;
	private String name;
	private String artist;
	//The length of the track measured in seconds.
	private int length;
	private String album;
	private String location;
	/**
	 * The constructor for the track.
	 * @param id 
	 * 			the id of the track.
	 * @param name
	 * 			the name of the track.
	 * @param artist
	 * 			the artist who made the track.
	 * @param time
	 * 			the time it takes to listen to the track.
	 * @param album
	 * 			the album of the track.
	 * @param location
	 * 			where on the hard drive the track is located.
	 */
	public Track(int id, String name, String artist, int time, String album, String location){
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.length = time;
		this.album = album;
		this.location = location;
		
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}
	
	/**
	 * 
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * The toString method.
	 * @return returns a string with information about the track.
	 */
	public String toString() {
		return "Track [id=" + id + ", name=" + name + ", artist=" + artist + ", length=" + length + ", album=" + album  + ", location=" + location + "]";
	}
	

}
