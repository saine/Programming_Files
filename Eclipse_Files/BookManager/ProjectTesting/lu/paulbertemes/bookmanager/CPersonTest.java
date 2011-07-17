package lu.paulbertemes.bookmanager;

import junit.framework.TestCase;

import org.junit.Test;

public class CPersonTest extends TestCase 
{

	@Test
	public void testCPerson() 
	{
		CPerson P1 = new CPerson();
		assertEquals("Unknown Name",P1.getsName());
	}

	@Test
	public void testSetsName() 
	{
		CPerson P1 = new CPerson();
		P1.setsName("Another Name");
		assertEquals("Another Name",P1.getsName());
	}

	@Test
	public void testSetnMaxBooks() 
	{
		CPerson P1 = new CPerson();
		P1.setnMaxBooks(100);
		assertEquals(100,P1.getnMaxBooks());
	}
	
	@Test
	public void testToString() 
	{
		CPerson P1 = new CPerson();
		assertEquals("Unknown Name (3 Books)",P1.toString());
	}

}
