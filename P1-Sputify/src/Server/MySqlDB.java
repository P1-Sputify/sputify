/**
 * 
 */
package Server;

import java.sql.*;
import java.util.*;

/**
 * @author mehmedagica
 *
 */
public class MySqlDB {

//	public static Connection connection;
//    public static Statement statement;
//    
//    public static void showResultSet(ResultSet resultSet) throws SQLException {
//        ResultSetMetaData meta = resultSet.getMetaData();
//        String res = "";
////        StringBuffer result = new StringBuffer();
//        int colCount = meta.getColumnCount();
//        for(int i=1; i<=colCount; i++)
//            res += meta.getColumnLabel(i) + ", ";
////            result.append(meta.getColumnLabel(i)+"\t");
//        res += "\n";
////        result.append("\n");
//        while(resultSet.next()) {
//            for(int i=1; i<=colCount; i++)
//                res += resultSet.getObject(i).toString() + ", ";
////              result.append(resultSet.getObject(i)+"\t");
//            	res += "\n";
////            	result.append("\n");
//            	
////            	System.out.print(resultSet.getObject("id"));
////            	System.out.print(resultSet.getObject("trackName"));
////            	System.out.print(resultSet.getObject("artist"));
////            	System.out.print(resultSet.getObject("length"));
////            	System.out.print(resultSet.getObject("album"));
////            	System.out.print(resultSet.getObject("location"));
////            	System.out.println("");
//        }
//        
//        //System.out.println("Number of Rows=" + countRows(resultSet));
//        javax.swing.JOptionPane.showMessageDialog(null, res);
////        System.out.println(result);
//    }
    
	/**
	 * Get Result Set for a given SQL query
	 * @return ResultSet
	 */
//	public static ResultSet getResultSet(String sqlString) throws SQLException {	
//		ResultSet result = null;
//        //connect();
//        result = statement.executeQuery(sqlString); 
//        //disconnect();
//		return result;
//	}
    
    /**
     * Method to count rows in the Result Set
     * @param resultSet
     * @return count of rows in result set, int
     * @throws SQLException
     */
//    public static int countRows(ResultSet resultSet) throws SQLException {
//    	resultSet.last();    
//        int rowCount = resultSet.getRow();
//        resultSet.beforeFirst();
//        
//        return rowCount;
//    }
    
//    public static void connect() throws SQLException {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsputify","root","Mornar22!0");
//            //connection = DriverManager.getConnection("jdbc:mysql://195.178.232.7:4040/AC9574","AC9574","Sputify7");
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        } catch(ClassNotFoundException e1) {
//            System.out.println("Databas-driver hittades ej: "+e1);
//        }
//    }
//    
//    public static void disconnect() throws SQLException {
//        statement.close();
//        connection.close();
//    }
    
//    public static void main(String[] args) {
//        try {
//            connect();
//            
//            ResultSet result = statement.executeQuery("SELECT * FROM dbsputify.track");
//            //ResultSet result = statement.executeQuery("SELECT * FROM ac9574.track");
//            showResultSet(result);
//            
//            disconnect();
//        } catch(SQLException e) {
//            System.out.println(e);
//        }
//    }

}
