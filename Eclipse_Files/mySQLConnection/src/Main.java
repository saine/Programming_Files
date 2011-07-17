/**
 * 
 */

import java.sql.*;

/**
 * @author paulbertemes
 *
 */
public class Main{
	
	public static void main(String[] args) throws Exception{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/testdb","root","root");
		
		PreparedStatement stmt = con.prepareStatement("select * from names");
		
		ResultSet r = stmt.executeQuery();
		
		System.out.println(r.getFetchSize());
		while(r.next()){
			
			System.out.println(r.getString(1) + " " + r.getString(2));
		}
		
	}
}
