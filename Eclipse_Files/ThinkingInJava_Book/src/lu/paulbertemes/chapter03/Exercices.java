//Package
package lu.paulbertemes.chapter03;

import java.util.Random;

/**
 * @author 	paulbertemes
 * @version	v1.1
 */
//Exercices Class
public class Exercices 
{
	public Exercices()
	{
		System.out.println("Chapter Exercices: Chapter03 \n");
	}
	
	//Ternary Function
	private int ternary(int i) 
	{
		return i < 10 ? i * 100 : i * 10;
	}
	
	//Alternative
	private int alternative(int i) 
	{ 
		if (i < 10) return i * 100; 
		else 		return i * 10;
	}
	
	//Main Function
	public static void main(String[] args) 
	{
		//Exercices Object
		Exercices Ex = new Exercices();
		
		//Perform Exercices
		Ex.Exercice01();
		Ex.Exercice02();
		Ex.Exercice03();
		Ex.Exercice04();
		Ex.Exercice05();
		Ex.Exercice06("abc", "ABC");
		Ex.Exercice06("String1", "String1");
		Ex.Exercice07();
		Ex.Exercice09(1);
		Ex.Exercice09(21);
		Ex.Exercice09(11);
	}
	
	//Exercice01
	private void Exercice01()
	{
		//Declare Variables
		float x = 33.2f;
		float y = 100.1f;
		float z = 1;
		
		//Calculate A and B
		float a = x + y - 2/2 + z;
		float b = x + (y - 2)/(2 + z);
		
		System.out.println("- Ex01: Calulation 1 gives " + a + " and Calulation 2 gives " + b);
	}
	
	//Exercice02
	private void Exercice02()
	{
		int Res1 = ternary(44);
		int Res2 = alternative(44);
		
		System.out.println("- Ex02: The results for exercice02 are " + Res1 + " and " + Res2);
	}
	
	//Exercice03
	private void Exercice03()
	{
		//Random Object
		Random rndGen = new Random();
		
		int Begin 	= 0;
		int End 	= 100;
		int Num		= rndGen.nextInt(300);
		
		if(Num>=Begin && Num<=End)
			System.out.println("- Ex03: The number " + Num + " is between 0 and 100");
		else
			System.out.println("- Ex03: The number " + Num + " is NOT between 0 and 100");
	}
	
	//Exercice04
	private void Exercice04()
	{
		System.out.print( "- Ex04: ");		
		for(int i=0;i<100;i++) System.out.print((i+1) + ", ");	
		System.out.print("\n");
	}

	//Exercice05
	private void Exercice05()
	{
		System.out.print( "- Ex05: ");		
		for(int i=0;i<100;i++) 
		{
			System.out.print((i+1) + ", ");	
			
			if((i+1) == 47) break;
		}
		System.out.print("\n");
	}
	
	//Exercice06
	private void Exercice06(String String1, String String2)
	{
		System.out.println("- Ex06: "+String1+" == "+String2+" " 		+ (String1==String2));
		System.out.println("- Ex06: "+String1+" != "+String2+" " 		+ (String1!=String2));
		System.out.println("- Ex06: "+String1+".equals("+String2+") " 	+ String1.equals(String2));
		System.out.println("- Ex06: !"+String1+".equals("+String2+") " + !String1.equals(String2));
	}
	
	//Exercice07
	private void Exercice07()
	{
		Random rndGen = new Random();
		
		for(int i=0;i<25;i++)
		{
			Integer rndNum = rndGen.nextInt(100);
			Integer rndCmp = rndGen.nextInt(100);
			
			if(rndNum > rndCmp)			System.out.println("- Ex07: "+ rndNum + " is greater than " + rndCmp); 
			else if(rndNum == rndCmp)	System.out.println("- Ex07: "+ rndNum + " is equal to " 	+ rndCmp);
			else						System.out.println("- Ex07: "+ rndNum + " is smaller than " + rndCmp);
		}
	}
	
	//Exercice09
	public void Exercice09(int nNum)
	{
		boolean isPrime = true;
		
		for(int i=2;i<nNum;i++)
		{
			if((nNum%i)==0) isPrime = false;
		}
		
		if(isPrime) System.out.println("- Ex09: " + nNum + " is a prime number");
		else 		System.out.println("- Ex09: " + nNum + " is NOT a prime number");
	}
}
