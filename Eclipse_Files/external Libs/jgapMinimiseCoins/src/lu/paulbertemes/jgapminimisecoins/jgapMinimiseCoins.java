//Package
package lu.paulbertemes.jgapminimisecoins;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

//JgapMinimiseCoins Class
public class jgapMinimiseCoins 
{
	//Main Function
	public jgapMinimiseCoins() throws Exception 
	{
		//Variable Allocation
		Configuration Config 	= new DefaultConfiguration();
		Gene[] sampleGenes 		= new Gene[4];
		
		//Set Target Amount
		int nTargetAmount = 99;
		
		//Create Fitness Function Object
		CFitnessFunction FitFun = new CFitnessFunction(nTargetAmount);
		
		//Set Fitness Function
		Config.setFitnessFunction(FitFun);
		
		//Set Sample Gene
		  sampleGenes[0] = new IntegerGene(Config, 0, 3);  // Quarters
		  sampleGenes[1] = new IntegerGene(Config, 0, 2);  // Dimes
		  sampleGenes[2] = new IntegerGene(Config, 0, 1);  // Nickels
		  sampleGenes[3] = new IntegerGene(Config, 0, 4);  // Pennies

		  Chromosome sampleChromosome = new Chromosome(Config, sampleGenes);

		  Config.setSampleChromosome(sampleChromosome);

		  Config.setPopulationSize(500);
		  
		  Genotype population = Genotype.randomInitialGenotype(Config);
		  
		  IChromosome bestSolutionSoFar = null;

		  for( int i = 0; i < 100; i++ )
		  {
		      population.evolve();
		      bestSolutionSoFar = population.getFittestChromosome();
		  }

		  System.out.println( "The best solution contained the following: " );
		  System.out.println(FitFun.getNumberOfCoinsAtGene(bestSolutionSoFar,0 ) + " quarters." );
		  System.out.println(FitFun.getNumberOfCoinsAtGene(bestSolutionSoFar, 1 ) + " dimes." );
		  System.out.println(FitFun.getNumberOfCoinsAtGene(bestSolutionSoFar, 2 ) + " nickels." );
		  System.out.println(FitFun.getNumberOfCoinsAtGene(bestSolutionSoFar, 3 ) + " pennies." );
		  System.out.println("For a total of " + FitFun.amountOfChange(bestSolutionSoFar ) + 
							 " cents in "+FitFun.getTotalNumberOfCoins(bestSolutionSoFar )+" coins.");
		  
	}
}
