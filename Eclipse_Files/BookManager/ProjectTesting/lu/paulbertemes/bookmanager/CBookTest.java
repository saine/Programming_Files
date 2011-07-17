package lu.paulbertemes.bookmanager;

import junit.framework.TestCase;

import org.junit.Test;

public class CBookTest extends TestCase 
{

	@Test
	public void testCBook() 
	{
		CBook B1 = new CBook("Book1");
		assertEquals("Book1", B1.getsTitle());
		assertEquals("Unknown Author", B1.getsAuthor());
	} 
	
	@Test
	public void testGetPerson() 
	{
		CBook B1 = new CBook("Book1");
		assertEquals("Unknown Name", B1.getPerson().getsName());
	} 
	

}
