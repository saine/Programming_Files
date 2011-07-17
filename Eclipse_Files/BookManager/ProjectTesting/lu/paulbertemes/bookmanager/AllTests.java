package lu.paulbertemes.bookmanager;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(CBookTest.class);
		suite.addTestSuite(CPersonTest.class);
		//$JUnit-END$
		return suite;
	}

}
