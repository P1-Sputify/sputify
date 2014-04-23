/**
 * 
 */
package server;

/**
 * @authors mehmedagica, Sebastian Aspegren
 * 
 * A class representing a song/track. The track had an id, a name, an artist, a length and an album it belongs to.
 * Added a line to class description
 *
 */
public class Track {
	
	private String id;
	private String name;
	private String artist;
	//The length of the track measured in seconds.
	private int length;
	private String album;
	//Where the file is located on the harddrive. AKA the file path.
	private String location;
	
	public Track(String id, String artist, String name,String album,  int time, String location ){
		this.id=id;
		this.name=name;
		this.artist=artist;
		this.album=album;
		this.length=time;
		this.location=location;
		
	}
	/**
	 * 
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the id
	 */
	public String getId() {
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
	 * @return A string with information regarding the track.
	 */
	public String toString() {
		return "Track [Name = " + name + ", Artist = " + artist
				+ ", length = " + length + " seconds" + ", album = " + album + "]";
	}
	

}
