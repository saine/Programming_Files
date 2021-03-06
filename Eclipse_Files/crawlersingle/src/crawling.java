import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.*;

public class crawling
{
  //Properties
  private String startUrl;
  private int maxUrls = -1;
  private boolean limitHost = false;
  private boolean crawling = false;
  private Date currentDate;
  private int counter=0;
  
  // Set up crawl lists.
  HashSet crawledList = new HashSet();
  LinkedHashSet toCrawlList = new LinkedHashSet();
  
 // Cache of robot disallow lists.
  private HashMap disallowListCache = new HashMap();
 
  // user-defined crawler's objects variables
    WebPage  pageContents;
    dbOracle webDB ;
    UrlManager currentUrl;
  
  //hold configuration file name    
    String configFile = null;
    
   //Constructor
   public crawling() throws Exception
   {
          configFile = "crawler.conf";
         
          crawling = false;
          counter = 0;
          
          //readConfigFile(configFile);
          webDB = new dbOracle(configFile);
          currentUrl = new UrlManager();
          pageContents = new WebPage();
         
          
   }
   
    // Read Configuration file crawler.conf 
  private void readConfigFile(String fileName) throws Exception
  {
     Properties configFile = new Properties();
     configFile.load(new FileInputStream(fileName));
    
     String strTemp;
     
     strTemp = configFile.getProperty("limithost").trim();
     if (strTemp.toLowerCase().equals("true"))
         limitHost = true;
     else
         limitHost = false; //default value for limitHost
     
     strTemp = configFile.getProperty("maxurl").trim();
     if(Integer.parseInt(strTemp) > 0)
          maxUrls = Integer.parseInt(strTemp);
     else
          maxUrls = -1;
    
  }
  
  
   //*****************
   // Check if robot is allowed to access the given URL.
   private boolean isRobotAllowed(URL urlToCheck) 
   {
       String host = urlToCheck.getHost().toLowerCase();
       // Retrieve host's disallow list from cache.
       ArrayList disallowList = (ArrayList) disallowListCache.get(host);
       
       // If list is not in the cache, download and cache it.
       if (disallowList == null) 
        {
         disallowList = new ArrayList();
         try {
            URL robotsFileUrl =
            new URL("http://" + host + "/robots.txt");
            
            // Open connection to robot file URL for reading.
            BufferedReader reader =
            new BufferedReader(new InputStreamReader(
            robotsFileUrl.openStream()));
            
            // Read robot file, creating list of disallowed paths.
            String line;
            while ((line = reader.readLine()) != null) 
            {
              if (line.toLowerCase().indexOf("Disallow:") == 0) {
                String disallowPath =  line.toLowerCase().substring("Disallow:".length());
                // Check disallow path for comments and remove if present.
                int commentIndex = disallowPath.indexOf("#");
                if (commentIndex != - 1) {
                  disallowPath =
                  disallowPath.substring(0, commentIndex);
                }
                // Remove leading or trailing spaces from disallow path.
                disallowPath = disallowPath.trim();
                // Add disallow path to list.
                disallowList.add(disallowPath);
               }
            }
            // Add new disallow list to cache.
            disallowListCache.put(host, disallowList);
     
           } catch (Exception e) 
           {
             /* Assume robot is allowed since an exception
             is thrown if the robot file doesn't exist. */
             return true;
           }
         }
        /* Loop through disallow list to see if
        crawling is allowed for the given URL. */
        String file = urlToCheck.getFile();
        for (int i = 0; i < disallowList.size(); i++) 
        {
           String disallow = (String) disallowList.get(i);
           if (file.startsWith(disallow)) {
             return false;
           }
        }
         return true;
   }
   
  
   // Perform the actual crawling.
   private void crawl() throws Exception
    {
      //set crawling variable to true;
      crawling = true;
      String contentType=null;
    
      startUrl = currentUrl.readStartUrl(configFile);
         
      // Add start URL to the to crawl list.
      toCrawlList.add(startUrl);
      
      /* Perform actual crawling by looping through the To Crawl list. */
      while (crawling && toCrawlList.size() > 0)
      {
         /* Check to see if the max URL count has
         been reached, if it was specified.*/
         if (maxUrls != -1) {
            if ((counter == maxUrls)||(toCrawlList.size()==0)) {
              break;
            }
         }
         // Get URL at bottom of the list.
         String url = (String) toCrawlList.iterator().next();
         URL verifiedUrl = null;
         
         // Remove URL from the To Crawl list.
         toCrawlList.remove(url);
         
         //check if the address is valid and it is of http protocol , process only http protocol
         if (currentUrl.isVerified(url))
         {  
           verifiedUrl = currentUrl.getCurrentUrl();
         }
          else
         {  
            continue;
         } 
         
         // Skip URL if robots are not allowed to access it.
            if (!isRobotAllowed(verifiedUrl)) {
              continue;
            }

        // Get some information about the content
         try{    
              currentUrl.retrieveContentInfo();
              contentType = currentUrl.getContentType();
            }catch (Exception e)
            {
              counter = webDB.storeToDB(pageContents,currentUrl,counter);
              continue;  
            }
            
            if(contentType == null)
            {
               continue; 
            }
                     
            if(contentType.toLowerCase().indexOf("text") == -1)
            {
                continue;
            }
            
            
            // Download the page at the given URL.
            pageContents = currentUrl.downloadPage();
            
            
            // Add page to the crawled list.
            crawledList.add(url);
            
            //store the page in database
            counter = webDB.storeToDB(pageContents,currentUrl,counter);
       
            /* If the page was downloaded successfully, retrieve all its
            links and then see if it contains the search string. */
            if (pageContents != null && pageContents.getLength() > 0)
            {
                // Retrieve list of valid links from page.
                ArrayList links = pageContents.retrieveLinks(crawledList,limitHost);
             
                // Add links to the To Crawl list.
                toCrawlList.addAll(links);     
            }
      
      }//while
    }//end of crawl()
    

   //Main
   public static void  main(String[] args) throws Exception   
   {
     try{     
       crawling myCrawler = new crawling();
       myCrawler.crawl();
    
     } catch (Exception e)
     {
       e.printStackTrace();
     }
       
    }
   
}
    
   