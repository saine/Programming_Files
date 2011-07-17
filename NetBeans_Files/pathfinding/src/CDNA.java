//------------------------------------------------------------------------------
//TITLE:       CDNA CLASS
//AUTHOR:      PAUL BERTEMES
//CREATED:     15/07/2010
//VERSION:     V2.1 
//DESCRIPTION: This class implements a chromosome structure for the genetic 
//             algorithm. The individual genes represent accelerations in x, y
//             and z coordinates which are used to move a vehicle through the 
//             environment. This class contains routines for mutating and 
//             recombining individual chromosomes. Furthermore, functions for 
//             printing the DNA content on screen and to a file are provided.
//             The deepCopy function was implemented because Java works with 
//             references, causing all mutated versions of a single parent to be 
//             the same.
//
//Functions: 
//
//      1.  public                    CDNA(int nLength) 
//      2.  public                    CDNA(double[][] dGenes,int nLength) 
//      3.  public double[]        getGene(int nIndex) 
//      4.  public CDNA     crossoverChrom(CDNA Partner) 
//      5.  public void        mutateChrom(float fMu)
//      6.  public double[][]     deepCopy(double[][] dGenes, int nLength)
//      7.  public void   printChromScreen()
//      8.  public void     printChromFile(BufferedWriter File)
//
//------------------------------------------------------------------------------
//INCLUDES AND DEFINES
//------------------------------------------------------------------------------

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//------------------------------------------------------------------------------
//CDNA CLASS DEFINITION
//------------------------------------------------------------------------------

public class CDNA 
{
  //Member Variables
  public double[][] m_dDNA;    //n*3 Acc Matrix for each Cell in Environ.
  public int        m_nLength; //Length of Chromosome[Length*Width*Height]
  
  //Constructor (makes a DNA of random Vectors)
  public CDNA(int nLength) 
  {
      //Initialise Random Number Generator
      Random rndGen = new Random();
      
      //Initialise New DNA Sequence Memory
      m_dDNA    = new double[nLength][3];
      m_nLength = nLength;
    
      //Generate Random Acceeration Vectors for Genes Range = [-1;1] 
      for(int i=0;i<nLength;i++) 
      {
          m_dDNA[i][0] = (rndGen.nextDouble()*2.0d)-1.0d;
          m_dDNA[i][1] = (rndGen.nextDouble()*2.0d)-1.0d;
          m_dDNA[i][2] = (rndGen.nextDouble()*2.0d)-1.0d;
    }
  }
  
  //Constructor (makes a DNA of provided Genes)
  public CDNA(double[][] dGenes,int nLength) 
  {
      m_dDNA    = deepCopy(dGenes,nLength);
      m_nLength = nLength;
      
  }
  
  //Returns Specified Gene in Chromosome
  public double[] getGene(double nIndex) 
  {
      //Initialise Gene Space
      double[] dGene = {0,0,0};
      
      //Retrieve Gene Information
      if(nIndex<m_nLength)
      {
          dGene[0] = m_dDNA[(int)nIndex][0];
          dGene[1] = m_dDNA[(int)nIndex][1];
          dGene[2] = m_dDNA[(int)nIndex][2];
      }
      return dGene;
  }
  
  //Crossover
  public CDNA crossoverChrom(CDNA Partner) 
  {
      //Create Random Number Generator
      Random rndGen = new Random();
      
      //Allocate Space for Child
      double[][] dOffspring = new double[m_nLength][3];
      
      //Find CrossOver Point
      int nCrossPoint = rndGen.nextInt(m_nLength);
      
      //Combine two Cromosomes into One Offspring
      for(int i=0;i<m_nLength;i++) 
      {
          double[] dGene = {0,0,0};
          
          if(i<nCrossPoint) dGene = getGene(i).clone();
          else              dGene = Partner.getGene(i).clone();
              
          dOffspring[i][0] = dGene[0];
          dOffspring[i][1] = dGene[1];
          dOffspring[i][2] = dGene[2];
      }
      //Save new Chromosome in CDNA Class
      CDNA Child = new CDNA(dOffspring,m_nLength);
      return(Child);
  }
  
  //Mutate Chromosome Based on Mutation Probability
  public void mutateChrom(float fMu) 
  {  
      Random rndGen = new Random();
      
      for(int i=0;i<m_nLength;i++) 
      {
          float fRand = rndGen.nextFloat();
          if(fRand<fMu && fRand!=0)
          {
                m_dDNA[i][0] = (rndGen.nextDouble()*2.0d)-1.0d;
                m_dDNA[i][1] = (rndGen.nextDouble()*2.0d)-1.0d;
                m_dDNA[i][2] = (rndGen.nextDouble()*2.0d)-1.0d;
          }
      }
  }
  
  //Copy Method: Solution to referencing Problem
  public double[][] deepCopy(double[][]dGenes,int nLength)
  {
      double[][] dCopy = new double[nLength][3];
      
      for(int i =0;i<nLength;i++)
      {
          dCopy[i][0] = dGenes[i][0];
          dCopy[i][1] = dGenes[i][1];
          dCopy[i][2] = dGenes[i][2];
      }
      return(dCopy);
  }
  
  //Print Chromosome Information onScreen
  public void printChromScreen()
  {
      //Print First Gene Information  [xAcc]
      for(int i=0;i<m_nLength;i++)
      {
          System.out.print(m_dDNA[i][0]);
          System.out.print(",");
      }
      System.out.print("\n");
      
      //Print Second Gene Information [yAcc]
      for(int i=0;i<m_nLength;i++)
      {
          System.out.print(m_dDNA[i][1]);
          System.out.print(",");
      }
      System.out.print("\n");
      
      //Print Third Gene Information  [zAcc]
      for(int i=0;i<m_nLength;i++)
      {
          System.out.print(m_dDNA[i][2]);
          System.out.print(",");
      }
      System.out.print("\n");
  }
  
  //
  public void printChromFile(BufferedWriter File)
  {
      //Print First Gene Information  [xAcc]
      for(int i=0;i<m_nLength;i++)
      {
            try 
            {
                File.write(String.valueOf(m_dDNA[i][0]));
                File.write(",");
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(CDNA.class.getName()).log(Level.SEVERE,null,ex);
            }
      }
      try 
      {
            File.write("\n");
      } 
      catch (IOException ex) 
      {
            Logger.getLogger(CDNA.class.getName()).log(Level.SEVERE, null, ex);      
      }
      
      //Print Second Gene Information  [yAcc]
      for(int i=0;i<m_nLength;i++)
      {
            try 
            {
                File.write(String.valueOf(m_dDNA[i][1]));
                File.write(",");
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(CDNA.class.getName()).log(Level.SEVERE,null,ex);
            }
      }
      try 
      {
            File.write("\n");
      } 
      catch (IOException ex) 
      {
            Logger.getLogger(CDNA.class.getName()).log(Level.SEVERE,null,ex);      
      }
      
      //Print Third Gene Information  [zAcc]
      for(int i=0;i<m_nLength;i++)
      {
            try 
            {
                File.write(String.valueOf(m_dDNA[i][2]));
                File.write(",");
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(CDNA.class.getName()).log(Level.SEVERE,null,ex);
            }
      }
      try 
      {
            File.write("\n");
      } 
      catch (IOException ex) 
      {
            Logger.getLogger(CDNA.class.getName()).log(Level.SEVERE,null,ex);      
      }
  }
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------