package lu.paulbertemes.bookmanager;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.Test;

public class TestCMyLibrary extends TestCase
{
	//Member Variables
	CMyLibrary 	L1;
	CBook 		B1;
	CBook 		B2;
	CPerson 	P1;
	CPerson 	P2;
	
	@Test
	public void testCMyLibrary() 
	{
		CMyLibrary L1 = new CMyLibrary("Library1");
		
		assertEquals(L1.getsLibName(),"Library1");
		assertTrue	(L1.getaBooks() instanceof ArrayList);
		assertTrue	(L1.getaPeople() instanceof ArrayList);
	}
	
	@Test
	public void testCheckOut()
	{
		setup	();
		addItems();
		
		for(int i=0;i<2;i++)
		{
			assertTrue(L1.checkOut(B1, P1));
			assertFalse(L1.checkOut(B1, P2));
			assertTrue(L1.checkIn(B1));
			assertFalse(L1.checkIn(B1));
			assertTrue(L1.checkOut(B1, P2));
			assertFalse(L1.checkOut(B1, P1));
			assertTrue(L1.checkIn(B1));
			assertFalse(L1.checkIn(B1));
			
			assertTrue(L1.checkOut(B2, P1));
			assertFalse(L1.checkOut(B2, P2));
			assertTrue(L1.checkIn(B2));
			assertFalse(L1.checkIn(B1));
			assertTrue(L1.checkOut(B2, P2));
			assertFalse(L1.checkOut(B2, P1));
			assertTrue(L1.checkIn(B2));
			assertFalse(L1.checkIn(B1));
		}
		
	}

	private void addItems() 
	{
		L1.addBook(B1);
		L1.addBook(B2);
	}

	private void setup() 
	{
		L1 	= new CMyLibrary("Library1");
		B1 		= new CBook		("Book1");
		B2 		= new CBook		("Book2");
		P1 		= new CPerson	("Person1");
		P2 		= new CPerson	("Person2");
	}

}
