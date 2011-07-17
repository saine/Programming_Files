//Package
package lu.paulbertemes.bookmanager;

//Person Class
public class CPerson 
{
	//Member Variables
	private String sName;
	private int	   nMaxBooks;
	
	//Void Constructor
	public CPerson()
	{
		sName 		= "Unknown Name";
		nMaxBooks 	= 3;
	}

	//Initialised Constructor
	public CPerson(String sName)
	{
		this.sName 	= sName;
		nMaxBooks	= 3;
	}
	
	//Get Person Name
	public String getsName() 
	{
		return sName;
	}

	//Set Person Name
	public void setsName(String sName) 
	{
		this.sName = sName;
	}

	//Get Maximum Rentable Books
	public int getnMaxBooks() {
		return nMaxBooks;
	}

	//Set Maximum Rentable Books
	public void setnMaxBooks(int nMaxBooks) 
	{
		this.nMaxBooks = nMaxBooks;
	}
	
	//Override toString Method
	public String toString() 
	{
		return(this.getsName() + " (" + this.getnMaxBooks() + " Books)" );
	}
}
