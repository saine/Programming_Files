//Package
package lu.paulbertemes.jgapminimisecoins;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

//GUI Class
public class Applet extends JApplet 
{
	//Serial ID
	private static final long serialVersionUID = 1L;
	
	//GUI Member Variables
	private static JButton 			m_btnRunGA 			= new JButton("Run Genetic Algorithm");
	private static JTextField 		m_txtNumRuns 		= new JTextField("100", 10);
	private static JTextField 		m_txtCoinsAmount 	= new JTextField("99", 10);
	private static JTextArea 		m_txtProgressReport = new JTextArea(20,60);
	private 	   CEventListener  	m_AL				= new CEventListener();
	
	//Member Variables
	Configuration 		m_Config;
	CFitnessFunction 	m_FitFun;
	Chromosome 			m_sampleChromosome;
	Genotype 			m_Population;
	IChromosome 		m_bestSolutionSoFar;

	//Event Manager Class
	private class CEventListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Retrieve Action Sent
			String sCmd = e.getActionCommand();
			
			if(sCmd == "Run Genetic Algorithm")
			{
				//Variable Allocation
				m_Config 			= new DefaultConfiguration();
				Gene[] sampleGenes 	= new Gene[4];
					
				//Set Sample Gene
				try {
					sampleGenes[0] = new IntegerGene(m_Config, 0, 3);
				} catch (InvalidConfigurationException e3) {
					e3.printStackTrace();
				}  // Quarters
				try {
					sampleGenes[1] = new IntegerGene(m_Config, 0, 2);
				} catch (InvalidConfigurationException e3) {
					e3.printStackTrace();
				}  // Dimes
				try {
					sampleGenes[2] = new IntegerGene(m_Config, 0, 1);
				} catch (InvalidConfigurationException e3) {
					e3.printStackTrace();
				}  // Nickels
				try {
					sampleGenes[3] = new IntegerGene(m_Config, 0, 4);
				} catch (InvalidConfigurationException e3) {
					e3.printStackTrace();
				}  // Pennies
				
				try {
					m_Config.setSampleChromosome(new Chromosome(m_Config, sampleGenes));
				} catch (InvalidConfigurationException e3) {
					e3.printStackTrace();
				}
		 
				try {
					m_Config.setPopulationSize(500);
				} catch (InvalidConfigurationException e3) {
					e3.printStackTrace();
				}
				
				//Create Fitness Function Object
				try 
				{
					m_FitFun = new CFitnessFunction(getnTargetAmount());
					m_Config.setFitnessFunction(m_FitFun);
				} 
				catch (InvalidConfigurationException e2) 
				{
					e2.printStackTrace();
				}
				
				//Initialise Population
				try 
				{	
					m_Population = Genotype.randomInitialGenotype(m_Config);
				} 
				catch (InvalidConfigurationException e1) 
				{
					e1.printStackTrace();
				}
				
				m_bestSolutionSoFar = null;
				
				//Run Genetic Algorithm
				run(getnRuns());
			}
		}
	}
	
	//Initialise Program
	public void init()
	{
		//Add EventManager to Components
		m_btnRunGA.addActionListener(m_AL);
		
		//Set Layout
		this.getContentPane().setLayout(new FlowLayout());
		
		//Add GUI Objects to Applet
		this.getContentPane().add(new JLabel("Enter Amount of Coins: "));
		this.getContentPane().add(m_txtCoinsAmount);
		this.getContentPane().add(new JLabel("Enter Number of Runs: "));
		this.getContentPane().add(m_txtNumRuns);
		this.getContentPane().add(m_btnRunGA);
		this.getContentPane().add(new JScrollPane(m_txtProgressReport));
		
		//Resize Applet
		this.resize(800, 500);
	}
	
	//Rung Genetic Algorithm
	public void run(int nRuns)
	{
		for(int i=0;i<nRuns;i++)
		{
			m_Population.evolve();
		}
		m_bestSolutionSoFar = m_Population.getFittestChromosome();
		printBestSolution();
		
		Configuration.reset();
	}
	
	private void printBestSolution()
	{
		//Show Progress Report
		m_txtProgressReport.removeAll();
		m_txtProgressReport.repaint();
		m_txtProgressReport.append("\n The best solution contained the following: \n");
		m_txtProgressReport.append(m_FitFun.getNumberOfCoinsAtGene(m_bestSolutionSoFar, 0 ) + " quarters \n");
		m_txtProgressReport.append(m_FitFun.getNumberOfCoinsAtGene(m_bestSolutionSoFar, 1 ) + " dimes \n");
		m_txtProgressReport.append(m_FitFun.getNumberOfCoinsAtGene(m_bestSolutionSoFar, 2 ) + " nickels \n");
		m_txtProgressReport.append(m_FitFun.getNumberOfCoinsAtGene(m_bestSolutionSoFar, 3 ) + " pennies \n");
		m_txtProgressReport.append("For a total of "+m_FitFun.amountOfChange(m_bestSolutionSoFar) + 
						   		   " cents in "+m_FitFun.getTotalNumberOfCoins(m_bestSolutionSoFar)+" coins. \n");
	}
	
	//Return Target Amount Value
	public static int getnTargetAmount()
	{
		return(Integer.parseInt(m_txtCoinsAmount.getText()));
	}
	
	//Return Number of Runs
	public static int getnRuns()
	{
		return(Integer.parseInt(m_txtNumRuns.getText()));
	}
	
	public static void main(String[] args) throws Exception 
	{
		//Create Frame and Applet Object
		JApplet applet  = new Applet();
		JFrame  frame 	= new JFrame("Applet");

		//Add Applet to Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.getContentPane().add(applet); 
		
		//Start Applet
		applet.init(); 
		applet.start(); 
		
		//Show GUI
		frame.setVisible(true);
	}		  
}
