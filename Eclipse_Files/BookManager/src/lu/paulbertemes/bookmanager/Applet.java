package lu.paulbertemes.bookmanager;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Applet extends JApplet
{
	//UID Variable
	private static final long serialVersionUID = 4819217014788416560L;

	//GUI Objects
	private JButton 			B1 = new JButton("Add Book");
	private JButton 			B2 = new JButton("Remove Book");
	private JButton 			B3 = new JButton("Rent Book");
	private JButton 			B4 = new JButton("Return Book");
	private JLabel				L1 = new JLabel("");
	private JTextField			T1 = new JTextField(10);
	private JTextField			T4 = new JTextField(10);
	private JTextArea			T2 = new JTextArea(10,80);
	private JTextArea			T3 = new JTextArea(10,80);
	private JComboBox			C1 = new JComboBox();
	private CButtonListener 	BL = new CButtonListener();
	
	//Program Variables
	private CMyLibrary 			myLib		= new CMyLibrary("Library 1");
	private ArrayList<CBook> 	availList 	= new ArrayList<CBook>();
	private ArrayList<CBook> 	unavailList = new ArrayList<CBook>();
	private ArrayList<CBook> 	cmpList 	= new ArrayList<CBook>();
	
	//Button Listener Class
	private class CButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{			
			//Load Command
			String sButtonLabel = e.getActionCommand(); 
			
			System.out.println(sButtonLabel);

			//Handle Event
			if(sButtonLabel == "Add Book")
			{
				//Create Book Object
				CBook newBook = new CBook(T1.getText());
				
				//Add Book to Library and ComboBox
				myLib.addBook(newBook);
				C1.addItem(newBook.getsTitle());
			}
			else if(sButtonLabel == "Remove Book")
			{
				//Create Book Object
				CBook newBook  = cmpList.get(C1.getSelectedIndex());
				
				//Remove Book
				myLib.removeBook(newBook);
				C1.removeItemAt(C1.getSelectedIndex());
			}
			else if(sButtonLabel == "Rent Book")
			{
				//Create Book Object
				CBook newBook  = cmpList.get(C1.getSelectedIndex());
				CPerson Person = new CPerson(T4.getText());
				myLib.checkOut(newBook, Person);
			}
			else if(sButtonLabel == "Return Book")
			{
				//Create Book Object
				CBook newBook  = cmpList.get(C1.getSelectedIndex());
				myLib.checkIn(newBook);
			}
			
			//UpdateGUI
			updateGUI();
		}

		//Update GUI
		public void updateGUI() 
		{
			//Load Book Lists
			cmpList 	= myLib.getaBooks();
			availList   = myLib.getAvailableBooks();
			unavailList = myLib.getUnAvailableBooks();
			
			//Avtivate Buttons and Fields
			if(cmpList.size() == 0)
			{
				B2.setVisible(false);
				B3.setVisible(false);
				B4.setVisible(false);
				C1.setVisible(false);
			}
			else 
			{
				B2.setVisible(true);
				B3.setVisible(true);
				B4.setVisible(true);
				C1.setVisible(true);
			}
			if(unavailList.size() == 0) B4.setVisible(false);
			else						B4.setVisible(true);
			
			//Redraw TextViews
			T2.setText("");
			T3.setText("");
			
			for(int i=0;i<availList.size();i++)   T2.append(availList.get(i).toString() + "\n");
			for(int i=0;i<unavailList.size();i++) T3.append(unavailList.get(i).toString() + "\n");	
			
			//Set Label
			L1.setText(myLib.toString());
		}
	}
	
	public void init() 
	{
		//Add Button Events
		B1.addActionListener(BL);
		B2.addActionListener(BL);
		B3.addActionListener(BL);
		B4.addActionListener(BL);
		C1.addActionListener(BL);
		
		//Create GUI
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(T1);
		this.getContentPane().add(B1);
		this.getContentPane().add(B2);
		this.getContentPane().add(T4);
		this.getContentPane().add(L1);
		this.getContentPane().add(new JScrollPane(T2));
		this.getContentPane().add(C1);
		this.getContentPane().add(B3);
		this.getContentPane().add(B4);
		this.getContentPane().add(new JScrollPane(T3));
		this.resize(1000,500);
		
		//Setup GUI
		L1.setText(myLib.toString());
		T1.setText("Book");
		T4.setText("Person");
		
		//Update Gui
		BL.updateGUI();
	}
	
	// A main() for the application:
	public static void main(String[] args) 
	{
		JApplet applet  = new Applet();
		JFrame  frame 	= new JFrame("Applet");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.getContentPane().add(applet); 
		frame.setSize(1000,500); 
		
		applet.init(); 
		applet.start(); 
		
		frame.setVisible(true);
	}

}
