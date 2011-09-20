/**
 * 
 */
package lu.paulbertemes.nightcrawler.urldownloader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Scanner;

/**
 * @author paulbertemes
 *
 */
public class UrlDownloader {

	//Member variables
	Scanner 	m_URLStream;
	URL			m_URLAddress;
	FileWriter 	m_writeFile;
	
	//UrlDownloader constructor
	public UrlDownloader() throws Exception{
	
		m_URLAddress = new URL		 ("http://127.0.0.1:80/index.html");
		
		//m_URLStream  = new Scanner	 (m_URLAddress.openStream());
		m_writeFile  = new FileWriter("crawledURL.html");
	}
	
	//
	public void fetch(String urlString) throws Exception{
		//Variables
		FileWriter writeFile = new FileWriter("crawledURL.html");
		
		//Read content of the web site
		while(m_URLStream.hasNext()){
			//Write into HTML file
			writeFile.write(m_URLStream.nextLine());
		}	
	}
	
	//Load pre-saved raw url file
	public void load(String fileName) throws Exception{
		//Variables
		RandomAccessFile loadFile 	= new RandomAccessFile("crawlURL.html","r");
		FileWriter 		 writeFile 	= new FileWriter("crawledURL.html");
		String			 readLine   = new String(loadFile.readLine());
		
		//Read content of the web site
		while(readLine != null){
			//Write into HTML file
			writeFile.write(readLine);
			readLine = loadFile.readLine();
		}
		
	}
}
