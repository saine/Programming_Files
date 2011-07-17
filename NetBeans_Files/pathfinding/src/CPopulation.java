//------------------------------------------------------------------------------
//TITLE:       CVEHICLE CLASS
//AUTHOR:      PAUL BERTEMES
//CREATED:     17/07/2010
//VERSION:     V2.0 
//DESCRIPTION: This class implements the population parameters and methods 
//             necessary to perform the genetic algorithm. The constructor takes 
//             the parameters of the algorithm and initialises all variables 
//             used by the population. The propagation function takes all the 
//             individuals in the population and moves them through the 
//             environment. Additionally, three functions are implemented to 
//             calulate the Fitness of the population, generate a pool of 
//             potential successors for the current generation and to create a 
//             new population. Finally this class contains a few functions to 
//             retrieve Population parameters from an external source.
//
//Functions: 
//
//      1.  public             CPopulation(int nPopSize,int nTotGen,int nFinish,
//                                         float MutRate,int nLength,int nWidth,
//                                         int nHeight,int nDiam) 
//      2.  public void       propagateGen(CObstacle[] Obstacles,int nSize,
//                                         Graphics Graph) 
//      3.  public boolean   targetReached() 
//      4.  public void            calcFit()
//      5.  public void generateMatingPool() 
//      6.  public void     generateNewPop() 
//      7.  public int   getCurrGeneration() 
//      8.  public float   getTotalFitness() 
//
//------------------------------------------------------------------------------
//INCLUDES AND DEFINES
//------------------------------------------------------------------------------

import java.util.Random;
import javax.media.j3d.TransformGroup;

//------------------------------------------------------------------------------
//CPOPULATION CLASS DEFINITION
//------------------------------------------------------------------------------

public class CPopulation 
{
    //Static Member Variables
    static int   m_nPopSize;       //Maximum Population Size
    static int   m_nTotalGen;      //Number of Generations Created
    static float m_fMutRate;       //Mutation Rate of Population
    static int   m_nDiam;          //GridScale
    static int   m_nLength;        //Length of Environment
    static int   m_nWidth;         //Width of Environment
    static int   m_nHeight;        //Height of Environment
    CVehicle[]   m_Population;     //Current Population Space 
    CVehicle[]   m_matPool;        //Mating Pool used to Build new Population
    Random       m_fRandGenerator; //Random Number Generator
    CObstacle    m_Loc;
    CObstacle    m_Tar;
    
    //Non Static Member Variables
    int m_nGen;                    //Current Generation Number
    int m_nCntFinished;            //How many Vehicles have Finished
    int m_ArrCnt;                  //Number of Elements in Mating Pool
    
    //Constructor [nMaxPop,nTotalGen,numFinished,fMutRate]
    public CPopulation(int nPopSize,float fMutRate,
                       int nLength,int nWidth,int nHeight,int nDiam, 
                       CObstacle Loc,CObstacle Tar) 
    {
        //Initialise Member Variables
        m_fRandGenerator = new Random();
        m_nDiam          = nDiam;
        m_nLength        = nLength;
        m_nWidth         = nWidth;
        m_nHeight        = nHeight;
        m_nPopSize       = nPopSize; 
        m_fMutRate       = fMutRate;
        m_Population     = new CVehicle[m_nPopSize];
        m_matPool        = new CVehicle[m_nPopSize*500];    
        m_Loc            = Loc;
        m_Tar            = Tar;
        m_nGen           = 0;
        
        //Create new Population of Candidate Solutions
        for(int i =0;i<m_nPopSize;i++)
        {
            int      dSize  = nLength/nDiam*nWidth/nDiam*nHeight/nDiam;
            double   dLoc[] = {Loc.m_x,Loc.m_y,Loc.m_z}; 
            double   dTar[] = {Tar.m_x,Tar.m_y,Tar.m_z}; 
            m_Population[i] = new CVehicle(dLoc,dTar,new CDNA(dSize),nPopSize,
                                           10000.0f,nDiam,nLength,nWidth,
                                           nHeight,nDiam); 
        }          
        //Initialise Counter for Chromosomes arriving at Target
        m_nCntFinished = 1;  
    }
    
    //Function to Propagate all Chromosomes along their Generated Paths
    public void propagateGen(CObstacle[] Obstacles,int nSize) 
    {
        //Propagate each Chromosome in the Population
        for(int i=0;i<m_nPopSize;i++) 
        {
            //Check if Chromosomes have Reached Target
            if ((!m_Population[i].Stopped()&&(m_Population[i].Finished()))) 
            {
                m_Population[i].m_Stopped = true;
                m_Population[i].setFinish(m_nCntFinished);
                m_nCntFinished++;
//                m_Population[i].writePath(m_nGen);
            }
            //Propagate Paths
            m_Population[i].run(Obstacles,nSize);
        }
    }
    
    //Check if Individual has Reached Target
    public boolean targetReached() 
    {
        for(int i=0;i<m_nPopSize;i++) 
        {
            if (m_Population[i].Finished()) return true;
        }
        return false;
    }

    //Caluclate Fitness for all Chromosomes in Population
    public void calcFit() 
    {
        for (int i=0;i<m_nPopSize;i++) 
        {
            m_Population[i].calcFitness();
        }
        m_nCntFinished = 1;
    }

    //generate a mating pool
    public void generateMatingPool() 
    {
        //Temporary Variable
        m_ArrCnt = 0;
            
        //Caluclate Average Fitness for Population
        double totalFitness = getTotalFitness()/m_nPopSize;

        //Calculate Normalized Fitness for each Member
        for (int i=0;i<m_nPopSize;i++) 
        {
            //Calculate Fitness Proportional to Total Fitness
            double normFit = m_Population[i].getFitness()/totalFitness;
            int n = (int)(normFit*m_nPopSize);
            if(n==0) n = 1;
            
            //Populate Mating Pool
            for(int j=m_ArrCnt;j<n+m_ArrCnt;j++) m_matPool[j] = m_Population[i];
            m_ArrCnt += n;
        }
    }

    //Create New Generation
    public void generateNewPop(int Method) 
    {
        //Use Traditional GA with Crossover and Mutation
        if(Method == 0)
        {
            for (int i = 0; i < m_nPopSize; i++) 
            {
                //Pick two random Parents
                CVehicle Parent1 = m_matPool[m_fRandGenerator.nextInt(m_ArrCnt)];
                CVehicle Parent2 = m_matPool[m_fRandGenerator.nextInt(m_ArrCnt)];

                //Get Their Gene Information
                CDNA Genes1 = Parent1.getGenes();
                CDNA Genes2 = Parent2.getGenes();

                //Create Offspring by Performing Recombination
                CDNA child = Genes1.crossoverChrom(Genes2);
                //Mutate Offspring
                child.mutateChrom(m_fMutRate);

                //Save Child in new Population
                double   dLoc[]      = {m_Loc.m_x,m_Loc.m_y,m_Loc.m_z}; 
                double   dTar[]      = {m_Tar.m_x,m_Tar.m_y,m_Tar.m_z};
                TransformGroup oldTG = m_Population[i].m_objTG;
                m_Population[i]      = new CVehicle(dLoc,dTar,child,m_nPopSize,
                                                    10000.0f,m_nDiam,m_nLength,
                                                    m_nWidth,m_nHeight,m_nDiam);
                m_Population[i].m_objTG = oldTG;
            }
        }
        else if(Method == 1)
        {
            int       dSize    = m_nLength/m_nDiam*m_nWidth/m_nDiam*m_nHeight/m_nDiam;
            double[]  bestFit  = {0};
            boolean[] Finished = {false};
            CDNA Parent       = getMaxChrom(bestFit,Finished);
            double[][] dGenes = new double[dSize][3];
            
            //Copy Gene Information
            for(int j=0;j<dSize;j++)
            {
                double[] dGene = Parent.getGene(j);
                dGenes[j][0] = dGene[0];
                dGenes[j][1] = dGene[1];
                dGenes[j][2] = dGene[2];
            }
            dGenes = Parent.deepCopy(dGenes,dSize);

            for (int i = 0; i < m_nPopSize; i++)
            {
                //Create Offspring
                CDNA Child = new CDNA(dGenes,dSize);
                if(i != 0) Child.mutateChrom(m_fMutRate);
                Parent.printChromScreen();
                Child.printChromScreen();
                
                //Save Child in new Population
                double   dLoc[]      = {m_Loc.m_x,m_Loc.m_y,m_Loc.m_z}; 
                double   dTar[]      = {m_Tar.m_x,m_Tar.m_y,m_Tar.m_z};
                TransformGroup oldTG = m_Population[i].m_objTG;
                m_Population[i]      = new CVehicle(dLoc,dTar,Child,m_nPopSize,
                                                    10000.0f,m_nDiam,m_nLength,
                                                    m_nWidth,m_nHeight,m_nDiam);
                m_Population[i].m_objTG = oldTG;
            }
        }
        //Increment Generation Count
        m_nGen++;
    }

    //Get Curretn Generation Number
    public int getCurrGeneration() 
    {
        return m_nGen;
    }

    //Calculate Total Fitness for the Population
    public double getTotalFitness() 
    {
        double fTotFit = 0;
        for (int i=0;i<m_nPopSize;i++) fTotFit += m_Population[i].getFitness();
        return fTotFit;
    }
    
    //Return Best Chromosome for a Generation and its Fitness
    public CDNA getMaxChrom(double[] BestFit,boolean[] Finished)
    {
        double maxFit   = 0.0f;
        int   maxIndex = 0;
        
        //Find Maximum Fitness Value
        for (int i=0;i<m_nPopSize;i++)
        {
            double currFit = m_Population[i].getFitness();
            if(currFit>=maxFit)
            {
                maxFit   = currFit;
                maxIndex = i;
            } 
            BestFit[0] = maxFit;
        }
        Finished[0] = m_Population[maxIndex].Finished();
        
        //Return Fitness and Chromosome Info 
        return(m_Population[maxIndex].getGenes());
    }
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------