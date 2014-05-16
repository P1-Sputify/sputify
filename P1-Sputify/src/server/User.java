/**
 * 
 */
package server;

/**
 * @authors mehmedagica, Sebastian Aspegren
 *
 */
public class User {

	private String id;
	//The users real name.
	private String name;
	private String password;
	//The name the user has chosen as what is commonly known as the "username".
	private String screenName;
	
	public User(String id, String name, String password, String screenName){
		this.id=id;
		this.name=name;
		this.password=password;
		this.screenName=screenName;
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
	 * @return A string with information regarding the user.
	 */
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", screenName=" + screenName + "]";
	}
	
}
