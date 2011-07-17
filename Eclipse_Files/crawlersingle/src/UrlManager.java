import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.*;


public class UrlManager  {
  
  URL pageUrl  ;
  String startUrl;
  URL currentUrl ;
  
  private int bufferSize = 4096;
  
  int httpResponseCode = 0;
  String contentType = null;
  Date lastModified = null;
  
  String strUrl  = null;
  String hashCode=null;
  String protocol=null;
  
  WebPage wwwPage = new WebPage();
  
  public void retrieveContentInfo() throws Exception
  {
    HttpURLConnection httpConn = (HttpURLConnection)  currentUrl.openConnection(); 
    
    httpConn.setDefaultUseCaches(false);
    httpResponseCode = httpConn.getResponseCode();
    contentType = httpConn.getContentType(); 
    lastModified = new Date(httpConn.getLastModified());
                    
    httpConn.disconnect();
    
    strUrl = currentUrl.toString();
    hashCode = Util.getHashValue(strUrl);
    protocol = currentUrl.getProtocol();
  }
  
  public String getHashCode()
  {
     return hashCode; 
  }
  public String getStrUrl()
  {
     return strUrl; 
  }
  public String getProtocol()
  {
     return protocol;
  }
  
  public Date getLastModified()
  {
     return lastModified;
  }
  
  public URL getCurrentUrl()
  {
     return currentUrl;
  }
  public String getContentType()
  {
      return contentType; 
  }
  public int getResponseCode()
  {
     return httpResponseCode; 
  }
 
  public void setCurrentUrl(String strUrl)
  {
    
  }
 
  // read starting URL from configFile
  public String readStartUrl(String configFile) throws Exception
  {
      readUrlFromConfigFile(configFile);
      return startUrl; 
  }
  
   // Read Configuration file crawler.conf 
  private void readUrlFromConfigFile(String fileName) throws Exception
  {
     Properties configFile = new Properties();
     configFile.load(new FileInputStream(fileName));
     
     startUrl = configFile.getProperty("url1").trim();
  }
  
 
   
   //*****************
   // Download page at given URL.
   public  WebPage downloadPage() 
   { 
     
    try {
          // Open connection to URL for reading.
          BufferedReader reader = new BufferedReader(new InputStreamReader(currentUrl.openStream()));
          // Read page into buffer.
          String line;
          StringBuffer pageBuffer = new StringBuffer(bufferSize);
          while ((line = reader.readLine()) != null) {
            pageBuffer.append(line);
            
           }
        
          wwwPage.setPageContents(pageBuffer,currentUrl);  
          return wwwPage;
          
       } catch (Exception e) 
       {
        System.out.println("Error ["+ this.toString() + ".downloadPage()] : " + e);
       }
       return null;
   }
   
   public boolean isVerified(String url)
  {
    try{
          currentUrl = Util.verifyUrl(url);
       }catch(Exception e)
       {
          return false; 
       }
     return true;
  }
   
   
  
   
  
}