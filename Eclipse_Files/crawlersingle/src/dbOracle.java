// Oracle JDBC driver class
import java.sql.*;

import com.mysql.jdbc.Connection;

import java.io.*;
import java.util.Date;
import java.util.*;

public class dbOracle
{
  
  String hashCode = null;
  String strUrl  = null;
  String pageContents = null;
  String protocol = null;
  int httpResponseCode = 0;
  Date downloadTime = null;

   /* Variables to hold database details */
  private String dbHost = null;
  private String dbPort = null;
  private String dbSID  = null;
  private String dbUsername = null;  // Replace the username where the database table was created.
  private String dbPassword = null;  // Replace the password of the username.
  private String dbString   = null;
  
   /* Database Connection object */
  private Connection conn = null;
 
    // Create a property object to hold the username, password and
  // the new property SetBigStringTryClob.
  private Properties props = new Properties();
  
  public  dbOracle(String a)throws Exception
  {
	  dbHost="localhost";
	  dbPort = "3306";
	  dbSID = "root";
	  conn = (Connection) DriverManager.getConnection("jdbc:mysql://"+dbHost,dbSID,dbSID);
  }
 
   //Store to DB
   public  int  storeToDB(WebPage wwwPage, UrlManager pageUrl, int counter) throws Exception
    {
       Date currentTime = new Date();
       
       hashCode = pageUrl.getHashCode();
       strUrl   = pageUrl.getStrUrl();
       if (wwwPage == null)
          pageContents = "";
       else 
          pageContents = wwwPage.getPageContents();
       
       protocol = pageUrl.getProtocol();
       httpResponseCode = pageUrl.getResponseCode();
       java.sql.Timestamp downloadTime = new java.sql.Timestamp(currentTime.getTime());
      
       
       // Create a PreparedStatement object.
       PreparedStatement pstmt = null;
  
       try {
           // Create the database connection, if it is closed.
           if ((conn==null)||conn.isClosed()){
             // Connect to the database.
            //conn = DriverManager.getConnection( this.dbString, this.props );
              conn = (Connection) DriverManager.getConnection(dbString,dbUsername,dbPassword);      
           }
     
           // Create SQL query to insert data into the CLOB column in the database.
           String sql = "INSERT INTO webdocs VALUES(?,?,?,?,?,?)";
     
           // Create the OraclePreparedStatement object
           pstmt = (PreparedStatement) conn.prepareStatement(sql);
     
           // Use the same setString() method which is enhanced to insert
           // the CLOB data. The string data is automatically transformed into a
           // clob and inserted into the database column. Make sure that the
           // Connection property - 'SetBigStringTryClob' is set to true for
           // the insert to happen.
           pstmt.setString(1,hashCode);
           pstmt.setString(2,strUrl);
           pstmt.setString(3,pageContents);
           pstmt.setString(4,protocol);
           pstmt.setInt(5,httpResponseCode);
           pstmt.setTimestamp(6,downloadTime);

           // Execute the PreparedStatement
           pstmt.executeUpdate();
           counter++;
           System.out.println("[" + counter + "] "+ strUrl + " [" + httpResponseCode +"]\n\n");

         } catch (SQLException sqlex) {
               // Catch Exceptions and display messages accordingly.
               System.out.println("SQLException while connecting and inserting into " +
                                 "the database table: " + sqlex.toString());
         } catch (Exception ex) {
               System.out.println("Exception while connecting and inserting into the" +
                                  " database table: " + ex.toString());
         } finally {
               // Close the Statement and the connection objects.
               if (pstmt!=null) pstmt.close();
               if (conn!=null)   conn.close();         
         } 
         return counter;

    }    //storeToDB
   
  
}