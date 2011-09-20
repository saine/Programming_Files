/**
 * 
 */
package lu.paulbertemes.nightcrawler.utils;

import java.util.ArrayList;

/**
 * @author paulbertemes
 *
 */
public class TokenList {
	
	//Member variables
	ArrayList<Integer> m_wordTokensList;
	ArrayList<Integer> m_tagTokensList;
	
	//Constructor TokenList
	public TokenList(){
		
		//Initialize member variables
		m_wordTokensList 	= new ArrayList<Integer>();
		m_tagTokensList		= new ArrayList<Integer>();
	}
	
	//Retrieve Word count for specific line
	public int getWordCount(int numLine){
		
		return(m_wordTokensList.get(numLine));
	}
	
	//Retrieve Tag count for specific line
	public int getTagCount(int numLine){
		
		return(m_tagTokensList.get(numLine));
	}

	//Retrieve Word count for specific line
	public void setWordCount(int numWords){
		
		m_wordTokensList.add(numWords);
	}
	
	//Retrieve Word count for specific line
	public void setTagCount(int numWords){
		
		m_tagTokensList.add(numWords);
	}
}
