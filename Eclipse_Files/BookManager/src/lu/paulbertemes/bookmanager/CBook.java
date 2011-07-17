//Package
package lu.paulbertemes.bookmanager;

//Book Class
public class CBook 
{
	//Member Variables
	private String 	sTitle;
	private String 	sAuthor;
	private CPerson	Person;
	
	//Constructor
	public CBook(String sTitle)
	{
		this.sTitle		= sTitle;
		this.sAuthor 	= "Unknown Author";
		this.Person		= null;
	}

	//Set Person Which has the Book
	public void setPerson(CPerson Person)
	{
		this.Person = Person;	
	}
	
	//Get Person Which has the Book
	public CPerson getPerson()
	{
		return(this.Person);	
	}
	
	//Get Book Title
	public String getsTitle() 
	{
		return sTitle;
	}

	//Set Book Title
	public void setsTitle(String sTitle) 
	{
		this.sTitle = sTitle;
	}

	//Get Book Author
	public String getsAuthor() 
	{
		return sAuthor;
	}

	//Set Book Author
	public void setsAuthor(String sAuthor) 
	{
		this.sAuthor = sAuthor;
	}
	
	//Overrides ToString Method
	public String toString()
	{
		String sReturn;
		
		//Check if Book is Available
		if(this.getPerson() == null)
			sReturn = "Available";
		else
			sReturn = "Checked out to " + this.getPerson().getsName();
		
		return(this.getsTitle() + ", "+ this.getsAuthor() + " " + sReturn);
		
	}
}
