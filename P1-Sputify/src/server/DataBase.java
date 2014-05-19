/**
 * 
 */
package server;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * @author Nethakaaru
 *
 */
public class DataBase {
	public static Connection connection;
	public static Statement statement;
	
	
	
	public static void connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://195.178.232.16:3306/ac9574","ac9574","Sputify7");
            statement = connection.createStatement();
            System.out.println("Successful SQL connect");
            JOptionPane.showMessageDialog(null, "Successful sql connect");
        } catch(ClassNotFoundException e1) {
            System.out.println("Databas-driver hittades ej: "+e1);
        }
    }
    
    public static void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }
    public static void test(){
    	String sql = "SELECT * FROM ac9574.track";
    	try {
			ResultSet rs = statement.executeQuery(sql);
			JOptionPane.showMessageDialog(null, rs.getString("name"));
		} catch (SQLException e) {
			System.out.println("Error @ statement.executeQuery");
		}
    	
    }
	public static void main(String[] args) {
	//	DataBase db= new DataBase();
		try {
			connect();
			test();
			disconnect();
		} catch (SQLException e) {
			
			System.out.println("Error in connection");
		}

	}

}
