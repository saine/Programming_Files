/**
 * 
 */
package lu.paulbertemes.nightcrawler;

import lu.paulbertemes.nightcrawler.contentxtractor.ContentXtractor;
import lu.paulbertemes.nightcrawler.urldownloader.UrlDownloader;


/**
 * @author paulbertemes
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		//Variables
		UrlDownloader u1 = new UrlDownloader();
		ContentXtractor x1 = new ContentXtractor();
		String str = "tokenizeHTML.html";
		
		//Download or load page
		//u1.fetch(str);
		u1.load(str);
		
		//Tokenize the crawled page
		x1.tokenizeURL(str);
	}

}
