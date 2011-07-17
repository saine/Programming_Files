//Package
package lu.paulbertemes.logfile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * This Class handles logging capabilities for debugging and monitoring purposes. 
 * It uses a single Log file as means to document the application progress.
 * 
 * @author paulbertemes
 * @version 1.1 
 */
public class LogFile 
{
	//Private Fields
	private static 	String 				m_sFileName;
	private 		RandomAccessFile 	m_hndFile;
	
	//Constructor
	public LogFile() 
	{
		m_sFileName = "";
		m_hndFile	= null;
	}
	
	//Create and Initialize The Log File
	public void createLogFile(String sFileName) throws Exception
	{
		//Initialize Member Variables
		m_sFileName = sFileName;
		m_hndFile	= new RandomAccessFile(sFileName, "rw");
		
		//Initialize HTML frame for Log File
		m_hndFile.writeBytes("<html><head><title>"+sFileName+"</title></head><body> ");
		
		//Print Message
		System.out.println("Log file "+sFileName+": Has been created");
	}

	public void closeLogFile() throws Exception
	{
		m_hndFile.writeBytes("</body></html>");
		m_hndFile.close();
	}
	
	public void writeDocInfo(String sAuthor, String sCompany, String sVersion, Date dDateCreated) throws IOException
	{
		m_hndFile.writeBytes("<h1>Document Information</h1>");
		m_hndFile.writeBytes("<table border='0'>");
		m_hndFile.writeBytes("<tr> <td>AUTHOR:</td><td>"+sAuthor+"</td></tr>");
		m_hndFile.writeBytes("<tr> <td>COMPANY:</td><td>"+sCompany+"</td></tr>");
		m_hndFile.writeBytes("<tr> <td>VERSION:</td><td>"+sVersion+"</td></tr>");
		m_hndFile.writeBytes("<tr> <td>CREATED:</td><td>"+dDateCreated+"</td></tr>");
		m_hndFile.writeBytes("</table>");
		
		//Print Console Message
		System.out.println("Log File "+m_sFileName+": Header information has been written");
	}
	
	public void writeTopic(String sTopic) throws Exception
	{
		m_hndFile.writeBytes("<h1>"+ sTopic +"</h1>");
		
		//Print Console Message
		System.out.println("Log File "+m_sFileName+": New topic "+sTopic+" has been created");
	}
	
	//main Testing 
	public static void main(String[] args) throws Exception
	{
		LogFile log1 = new LogFile();
		log1.createLogFile("LogFile.html");
		log1.writeDocInfo("Paul Bertemes","private","v1.1",new Date());
		log1.writeTopic("Initialization");
		log1.closeLogFile();
	}
}
