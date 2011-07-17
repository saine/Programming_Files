//Package
package lu.paulbertemes.jgapminimisecoins;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

//CFitnessFunction Class
public class CFitnessFunction extends FitnessFunction
{
	//Serial ID
	private static final long serialVersionUID = 1L;
	
	//Member Variables
	private final int m_nAimedAmount;
	
	//Constructor
	public CFitnessFunction(int nAmount)
    {
		m_nAimedAmount = nAmount;
    }

	//Evaluate Chromosome Fitness
    public double evaluate(IChromosome Chromosome)
    {
    	//Get Information on Chromosome
        int nChangeAmount 		= amountOfChange(Chromosome);
        int nTotalCoins 		= getTotalNumberOfCoins(Chromosome);
        int nChangeDifference 	= Math.abs(m_nAimedAmount-nChangeAmount);

        //Assign Fitness Based on the difference between Aimed and Actual Amount
        double dFitness = 99 - nChangeDifference;

        //Assign Fitness Based on how few Coins are needed to make up Amount
        if(nChangeAmount == m_nAimedAmount) dFitness += 100 - (10 * nTotalCoins);

        //Return Fitness Value
        return(Math.abs(dFitness));
    }

    //Get Amount of Change produced by the Chromosome
    public int amountOfChange(IChromosome Chromosome)
    {
        int nQuarters 	= this.getNumberOfCoinsAtGene(Chromosome, 0);
        int nDimes		= this.getNumberOfCoinsAtGene(Chromosome, 1);
        int nNickels 	= this.getNumberOfCoinsAtGene(Chromosome, 2);
        int nPennies 	= this.getNumberOfCoinsAtGene(Chromosome, 3);

        return(nQuarters*25 + nDimes*10 + nNickels*5 + nPennies);
    }

    //Retrieve Gene Information
    public int getNumberOfCoinsAtGene(IChromosome Chromosome, int nPos)
    {
        return((Integer) Chromosome.getGene(nPos).getAllele());
    }

    //Retrieve Total Number of Coins
    public int getTotalNumberOfCoins(IChromosome Chromosome)
    {
    	//Total Number of Coins
        int nTotCoins 	= 0;
        int nOfGenes 	= Chromosome.size();
        
        //Calculate Number of Coins
        for( int i=0;i<nOfGenes;i++) nTotCoins += getNumberOfCoinsAtGene(Chromosome, i);
 
        return(nTotCoins);
    }
}
