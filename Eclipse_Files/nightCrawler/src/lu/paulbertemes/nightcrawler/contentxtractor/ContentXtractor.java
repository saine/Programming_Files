/**
 * 
 */
package lu.paulbertemes.nightcrawler.contentxtractor;

import java.io.BufferedReader;
import java.io.FileReader;
import lu.paulbertemes.nightcrawler.utils.TokenList;

/**
 * @author paulbertemes
 *
 */
public class ContentXtractor {
	
	//Member variables
	BufferedReader   m_fileReader;
	TokenList		 m_tokenList;
	
	//ContentXtractor constructor
	public ContentXtractor(){
	}
	
	//tokenize a crawled URL
	public void tokenizeURL(String urlPath) throws Exception{
		
		//Initialize member variables
		m_fileReader = new BufferedReader(new FileReader(urlPath));
		
		//Tokenize web page
		while(true){
			//Read page line
			String readLine = m_fileReader.readLine();
			if(readLine == null) break;
			
			//Tokenize page line
			tokenize(readLine);
		}
	}
	
	//tokenize current line
	private void tokenize(String currLine){
		
		String[] TokensList = currLine.split(">");
		
		for(int i=0;i<TokensList.length;i++)
			System.out.println(TokensList[i]);
	}
	
}
