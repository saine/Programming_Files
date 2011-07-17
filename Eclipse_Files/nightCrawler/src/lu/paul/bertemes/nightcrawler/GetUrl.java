/**
 * 
 */
package lu.paul.bertemes.nightcrawler;

import java.io.FileWriter;
import java.net.URL;
import java.util.Scanner;

/**
 * @author paulbertemes
 *
 */
public class GetUrl {

	public GetUrl(){}
	
	public void fetch (URL url) throws Exception
	{
		//Variables
		Scanner urlScanner 		= new Scanner(url.openStream());
		FileWriter writeFile	= new FileWriter("crawled_index.html");
		
		//Read Content of the Website
		while(urlScanner.hasNext())
		{
			//Write into HTML File (Download page)
			writeFile.write(urlScanner.nextLine());
		}	
	}
}