/**
 * 
 */
package lu.paul.bertemes.nightcrawler;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.*;

/**
 * @author paulbertemes
 *
 */

public class UrlParser 
{
   private URL 		m_pageUrl;
   private String 	m_pageContent;
   
   public void setPageContents(StringBuffer content, URL contentUrl){
	   
     m_pageContent 	= new String(content.toString());
     m_pageUrl 		= contentUrl; 
   }
   
   public int getLength(){
     return(m_pageContent.length());
   }

   public String  getPageContents(){
     return(m_pageContent);
   }
   
   public URL getPageUrl(){
    return(m_pageUrl);  
  }
    
   //*************
   // Remove trailing "/" from a URL's host if present.
   //Sorry the function name is misleading, at first I thought I need to remove "www" from a URL, since www.xyz.com is the same with xyz.com
   private String removeWwwFromUrl(String tUrl) 
   {
          //String tUrl = pageUrl.toString();
          
          if(tUrl.endsWith("/")) 
          {
              tUrl = tUrl.substring(0,tUrl.length()-1);
            }
       return (tUrl);
   }
   
   //*****************
   // Parse through page contents and retrieve links.
   public  ArrayList retrieveLinks( HashSet crawledList,boolean limitHost)
   {
        // Compile link matching pattern.
        Pattern p = Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(m_pageContent);
        // Create list of link matches.
        ArrayList linkList = new ArrayList();
      
        while (m.find()) {
         String link = m.group(1).trim();
         // Skip empty links.
         if (link.length() < 1) {
          continue;
         }
         // Skip links that are just page anchors.
         if (link.charAt(0) == '#') {
          continue;
         }
         // Skip mailto links.
         if (link.indexOf("mailto:") != -1) {
          continue;
         }
         // Skip JavaScript links.
         if (link.toLowerCase().indexOf("javascript") != -1) {
            continue;
         }
         
         if(link.startsWith("."))
            link = link.substring(1);
            
            
         //get baseURL
         //String baseUrl = baseUrlStr(pageUrl.toString(),pageUrl.getFile(),pageUrl.getProtocol());
         String baseUrl =Util.getBaseUrl(m_pageUrl);
         
        // System.out.println("baseUrl : " + baseUrl);
         // Prefix absolute and relative URLs if necessary.
         if (link.indexOf("://") == -1) {
           // Handle absolute URLs.
           if (link.charAt(0) == '/') {
             link = "http://" + m_pageUrl.getHost() + link;
             // Handle relative URLs.
           } else {
             String file = m_pageUrl.getFile();
             if (file.indexOf('/') == -1) {
               link = "http://" + m_pageUrl.getHost() + "/" + link;
               
             } else {
   
               //String path = file.substring(0, file.lastIndexOf('/') + 1);
               //link = "http://" + pageUrl.getHost() + path + link;
               link = baseUrl + link;
             }
           }
          }
         
        // Remove anchors from link.
         int index = link.indexOf('#');
         if (index != -1) {
         link = link.substring(0, index);
     }
      // Remove leading "www" from URL's host if present.
      link = removeWwwFromUrl(link);
      // Verify link and skip if invalid.
      URL verifiedLink = Util.verifyUrl(link);
      if (verifiedLink == null) {
        continue;
      }
      /* If specified, limit links to those
      having the same host as the start URL. */
      if (limitHost && !m_pageUrl.getHost().toLowerCase().equals(verifiedLink.getHost().toLowerCase()))
      {
        continue;
      }
      // Skip link if it has already been crawled.
      if (crawledList.contains(link)) {
        continue;
      }
      // Add link to list.
      linkList.add(link);
     }
      
      
      return (linkList);
   }//
      
   
   
   private String getTitlePage()
   {
     
     return null;
   }
   
   
   private String stripHtmlTags()
   {
     
     return null;
   }
}