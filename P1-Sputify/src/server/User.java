/**
 * 
 */
package server;

/**
 * @author mehmedagica
 *
 */
public class User {

	private int id;
	//The users real name.
	private String name;
	private String password;
	//The name the user has chosen as what is commonly known as the "username".
	private String screenName;
	
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

	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", screenName=" + screenName + "]";
	}
	
}
