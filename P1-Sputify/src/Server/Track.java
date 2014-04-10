/**
 * 
 */
package Server;

/**
 * @author mehmedagica
 * 
 * A class representing a song/track. The track had an id, a name, an artist, a length and an album it belongs to.
 *
 */
public class Track {
	
	private int id;
	private String name;
	private String artist;
	//The length of the track measured in seconds.
	private int length;
	private String album;
	
	public Track(int id, String artist, String name,String album,  int time ){
		this.id=id;
		this.name=name;
		this.artist=artist;
		this.album=album;
		this.length=time;
		
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Track [id=" + id + ", name=" + name + ", artist=" + artist
				+ ", length=" + length + ", album=" + album + "]";
	}
	

}
