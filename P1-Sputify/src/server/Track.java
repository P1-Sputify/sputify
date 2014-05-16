/**
 * 
 */
package server;

import java.io.Serializable;

/**
 * @author mehmedagica
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Track [id=" + id + ", name=" + name + ", artist=" + artist + ", length=" + length + ", album=" + album  + ", location=" + location + "]";
	}
	

}
