package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import circuit_logic.Circuit;
import circuit_logic.CircuitComponent;


//set current node to selected one before calling add parallel section
public class AddParallelSectionWindow extends JFrame{
	private Circuit circuit;
	private boolean[] hasComponent;
	private CircuitComponent[] createdComponents;
	private int numOfBranch;
	
	private JPanel mainPanel;
	private JPanel locationPanel;
	private JPanel numOfBranchPanel;
	private JPanel selectBranchPanel;
	private JPanel componentPanel;
	private JPanel finalPanel;
	
	private JLabel locationLabel;
	private JLabel numOfBranchLabel;
	private JLabel selectBranchLabel;
	private JLabel selectComponentLabel;
	private JLabel enterNameLabel;
	private JLabel enterValueLabel;
	private JLabel statusLabel;
	
	private JComboBox locationCombobox;
	private JComboBox branchCombobox;
	private JComboBox componentCombobox;
	private JComboBox unitCombobox;
	
	private JTextField branchTextfield;
	private JTextField nameTextfield;
	private JTextField valueTextfield;
	
	private JButton createBranchButton;
	private JButton addComponentButton;
	private JButton completeButton;
	
	private String[] resistorUnits;
	private String[] capacitorUnits;
	private String[] inductorUnits;
	private String[] components;
	
	public AddParallelSectionWindow(Circuit c) {
		this.circuit = c;
		setSize(720,650);
		setLocation(200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
		this.setVisible(true);
	}
	
	public void initializeComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));
		
		locationPanel = new JPanel();
		locationPanel.setLayout(new GridLayout(1,2));
		
		numOfBranchPanel = new JPanel();
		numOfBranchPanel.setLayout(new GridLayout(1,3));
		
		selectBranchPanel = new JPanel();
		selectBranchPanel.setLayout(new GridLayout(1,2));
		
		componentPanel = new JPanel();
		componentPanel.setLayout(new GridLayout(1,8));
		
		finalPanel = new JPanel();
		finalPanel.setLayout(new GridLayout(1,2));
		
		locationLabel = new JLabel("Location to insert after");
		numOfBranchLabel = new JLabel("# of branches");
		selectBranchLabel = new JLabel("Select a branch");
		selectComponentLabel = new JLabel("Component");
		enterNameLabel = new JLabel("Enter a name");
		enterValueLabel = new JLabel("Enter a value");
		statusLabel = new JLabel("Status: branches not created");
		
		
		components = new String[]{"Resistor","Capacitor","Inductor"};
		componentCombobox = new JComboBox<String>(components);
		componentCombobox.setSelectedItem("Resistor");
		componentCombobox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(componentCombobox.getSelectedIndex() == 0) {
		        	unitCombobox.removeAllItems();
		        	for(String s: resistorUnits) {
		        		unitCombobox.addItem(s);
		        	}
		        }
		        if(componentCombobox.getSelectedIndex() == 1) {
		        	unitCombobox.removeAllItems();
		        	for(String s: capacitorUnits) {
		        		unitCombobox.addItem(s);
		        	}
		        }
		        if(componentCombobox.getSelectedIndex() == 2) {
		        	unitCombobox.removeAllItems();
		        	for(String s: inductorUnits) {
		        		unitCombobox.addItem(s);
		        	}
		        }
		    }
		});
		
		resistorUnits = new String[] {"Ohms","Kilo Ohms","Mega Ohms"};
		capacitorUnits = new String[] {"Farad","Mico Farad","Nano Farad"};
		inductorUnits = new String[] {"Henry","Micro Henry","Nano Henry"};
		
		unitCombobox = new JComboBox<String>(resistorUnits);
		unitCombobox.setSelectedItem("Ohms");
		
		locationCombobox = new JComboBox<String>();
		branchCombobox = new JComboBox<Integer>();
		
		branchTextfield = new JTextField();
		nameTextfield = new JTextField();
		valueTextfield = new JTextField();
		
		createBranchButton = new JButton("Create");
		addComponentButton = new JButton("Add");
		completeButton = new JButton("Complete");
		
	}
	
	public void createGUI() {
		locationPanel.add(locationLabel);
		locationPanel.add(locationCombobox);
		
		mainPanel.add(locationPanel);
		
		numOfBranchPanel.add(numOfBranchLabel);
		numOfBranchPanel.add(branchTextfield);
		numOfBranchPanel.add(createBranchButton);
		
		mainPanel.add(numOfBranchPanel);
		
		selectBranchPanel.add(selectBranchLabel);
		selectBranchPanel.add(branchCombobox);
		
		mainPanel.add(selectBranchPanel);
		
		componentPanel.add(selectComponentLabel);
		componentPanel.add(componentCombobox);
		componentPanel.add(enterValueLabel);
		componentPanel.add(valueTextfield);
		componentPanel.add(unitCombobox);
		componentPanel.add(enterNameLabel);
		componentPanel.add(nameTextfield);
		componentPanel.add(addComponentButton);
		
		mainPanel.add(componentPanel);
		
		finalPanel.add(statusLabel);
		finalPanel.add(completeButton);
		
		mainPanel.add(finalPanel);
		
		add(mainPanel);
	}
}
