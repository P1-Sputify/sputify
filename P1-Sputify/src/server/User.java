/**
 * 
 */
package server;

/**
 * @authors mehmedagica, Sebastian Aspegren
 * 
 * A class representing a user. The user has an id, a name, a password and a screen name.
 *
 */
public class User {

	private int id;
	//The users real name.
	private String name;
	private String password;
	//The name the user has chosen as what is commonly known as the "username".
	private String screenName;
	/**
	 * The constructor for the user.
	 * @param id
	 * 			the id of the user.
	 * @param name
	 * 			the name of the user.
	 * @param password
	 * 			the password the user has.
	 * @param screenName
	 * 			the name the user want to be known as.
	 */
	public User(int id, String name, String password, String screenName){
		this.id=id;
		this.name=name;
		this.password=password;
		this.screenName=screenName;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}
	/**
	 * The toString method.
	 * @return returns a string with information about the user.
	 */
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", screenName=" + screenName + "]";
	}
	
}
