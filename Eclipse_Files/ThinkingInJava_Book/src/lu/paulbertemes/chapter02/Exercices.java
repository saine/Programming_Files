// Package
package lu.paulbertemes.chapter02;

//Imports
import java.util.Date;

/**
 * @author 		paulbertemes
 * @version		v1.1 - Exercises
 */
//Exercises Class
//Exercices Completed
public class Exercices 
{
	//Inner Class for Exercice02
	private class ATypeName
	{
		//Constructor
		public ATypeName()
		{
			System.out.println("- Ex02: ATypeName Object created");
		}
	}
	
	//Inner Class for Exercice03 and Exercice04
	private class DataOnly 
	{
		
		@SuppressWarnings("unused") int 	i = 0;
		@SuppressWarnings("unused") float 	f = 0;
		@SuppressWarnings("unused") boolean b = false;
		
		//Constructor - NoArgs
		public DataOnly()
		{
			System.out.println("- Ex03: DataOnly Object created");
		}
		
		//Constructor - Initialised
		public DataOnly(int i, float f, boolean b)
		{
			//Initialise Member Variables
			this.i = i;
			this.f = f;
			this.b = b;
			
			System.out.print  ("- Ex04: DataOnly Object created and initialised ");
			System.out.println("with " + i + ", " + f +", " + b);
		}
	}
	
	//Inner Class for Exercice06
	public static class StaticTest 
	{
		static int i = 47;
	}
	
	//Inner Class for Exercice06
	private class StaticFun
	{		
		//Static Method
		public int incr() 
		{
			return(StaticTest.i++); 
		}
	}
	
	//Inner Class for Exercice08
	class AllTheColorsOfTheRainbow 
	{ 
		private int anIntegerRepresentingColors; 
		
		public void changeTheHueOfTheColor(int newHue) 
		{
			anIntegerRepresentingColors = newHue;
			
			System.out.println("- Ex08: The new hue is " + anIntegerRepresentingColors);
		}
	}
	
	//Main Function
	public static void main(String[] args) 
	{
		//Exercices Objects
		Exercices Ex 		= new Exercices();
		String[]  exArgs	= {"Help", "me", "now"};
		
		//Perform Exercices
		Ex.Exercice01();
		Ex.Exercice02();
		Ex.Exercice03();
		Ex.Exercice04();
		Ex.Exercice05();
		Ex.Exercice06();
		Ex.Exercice07(exArgs);
		Ex.Exercice08();
		
		System.out.println("\n Completed: Chapter02");
	}
	
	//Static Function
	public static int storage(String s) 
	{ 
		return(s.length() * 2);
	}
	
	//Constructor
	public Exercices()
	{
		System.out.println("Chapter Exercices: Chapter02 \n");
	}
	
	//Exercice01 Function
	private void Exercice01()
	{
		System.out.println("- Ex01: Hello World -> " + new Date());
	}
	
	//Exercice02 Function
	private void Exercice02()
	{
		@SuppressWarnings("unused")
		ATypeName aTypename = new ATypeName();
	}
	
	//Exercice03 Function
	private void Exercice03()
	{
		@SuppressWarnings("unused")
		DataOnly dataOnly = new DataOnly();
	}
	
	//Exercice04 Function
	private void Exercice04()
	{
		@SuppressWarnings("unused")
		DataOnly dataOnly = new DataOnly(1,2.1f,true);
	}
	
	//Exercice05 Function
	private void Exercice05()
	{
		//Variables
		String 	TestStr = "ThisIsAString";
		int		nLength = storage(TestStr);
		
		System.out.println("- Ex05: The String "+TestStr+" is "+nLength+" characters long");
	}

	//Exercice06 Function
	private void Exercice06()
	{
		StaticFun staticFun = new StaticFun();
		
		System.out.println("- Ex06: i has a value of " + staticFun.incr());
	}
	
	//Exercice07 Function
	private void Exercice07(String[] args)
	{
		System.out.print  ("- Ex07: The arguments taken from the command line are ");
		System.out.println(args[0] + ", " + args[1] + ", " + args[2]);
	}
	
	//Exercice08 Function
	private void Exercice08()
	{
		AllTheColorsOfTheRainbow Color = new AllTheColorsOfTheRainbow();
		
		Color.changeTheHueOfTheColor(13);
	}
}
