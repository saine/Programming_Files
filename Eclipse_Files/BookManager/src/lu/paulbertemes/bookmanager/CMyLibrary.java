//Package
package lu.paulbertemes.bookmanager;

//Imports
import java.util.ArrayList;

/**
 * @author  Paul Bertemes
 * @version 1.1
 * 
 * This is a Library class to manage books
 */
public class CMyLibrary 
{
	//Member Variables

	/** Libary Name */
	private String 				sLibName;
	/** List holding Books */
	private ArrayList<CBook> 	aBooks;
	/** List holding People */
	private ArrayList<CPerson> 	aPeople;
	
	/** @param The Library Name in String Format */
	public CMyLibrary(String sLibName)
	{
		
		this.sLibName 	= sLibName;
		this.aBooks		= new ArrayList<CBook>();
		this.aPeople	= new ArrayList<CPerson>();
	}

	/** @return Returns the Library Name in String Format */
	public String getsLibName() 
	{
		return sLibName;
	}

	public void setsLibName(String sLibName) 
	{
		this.sLibName = sLibName;
	}

	public ArrayList<CBook> getaBooks() 
	{
		return aBooks;
	}

	public void setaBooks(ArrayList<CBook> aBooks) 
	{
		this.aBooks = aBooks;
	}

	public ArrayList<CPerson> getaPeople() 
	{
		return aPeople;
	}

	public void setaPeople(ArrayList<CPerson> aPeople) 
	{
		this.aPeople = aPeople;
	}
	
	//Add Book to List
	public void addBook(CBook Book)
	{
		aBooks.add(Book);
	}
	
	//Remove Book From List
	public void removeBook(CBook Book)
	{
		aBooks.remove(Book);
	}
	
	//Check out Book
	public boolean checkOut(CBook Book, CPerson Person)
	{	
		if(Book.getPerson() == null)
		{
			Book.setPerson(Person);
			return(true);
		}
		else
		{
			return(false);
		}
	}
	
	//Check In Book
	public boolean checkIn(CBook Book)
	{	
		if(Book.getPerson() != null)
		{
			Book.setPerson(null); 
			return(true);
		}
		else
		{
			return(false);
		}
	}
	
	//Get Available Books
	public ArrayList<CBook> getAvailableBooks()
	{
		//List to hold Available Books
		ArrayList<CBook> ResultList = new ArrayList<CBook>();
		
		for(CBook Book : this.getaBooks())
		{
			if(Book.getPerson() == null)
				ResultList.add(Book);
		}
		
		//Return List of Available Books
		return ResultList;
	}
	
	//Get Available Books
	public ArrayList<CBook> getUnAvailableBooks()
	{
		//List to hold Available Books
		ArrayList<CBook> ResultList = new ArrayList<CBook>();
		
		for(CBook Book : this.getaBooks())
		{
			if(Book.getPerson() != null)
				ResultList.add(Book);
		}
		
		//Return List of Available Books
		return ResultList;
	}
	
	//Overrides ToString Method
	public String toString()
	{
		String sReturn = this.getsLibName() + ": " + this.getAvailableBooks().size() +  "/" +
						 this.getaBooks().size() + " Books Available";
		return(sReturn);
	}
}
