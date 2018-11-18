package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import circuit_logic.Capacitor;
import circuit_logic.Circuit;
import circuit_logic.CircuitComponent;
import circuit_logic.Inductor;
import circuit_logic.Resistor;


//set current node to selected one before calling add parallel section
public class AddParallelSectionWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 229054261246719664L;
	private Circuit circuit;
	private boolean[] hasComponent;
	private CircuitComponent[] createdComponents;
	private String[] componentNames;
	private int numOfBranch;
	private boolean branchCreated;
	private String[] locations;
	private HashMap<String,Integer> temp;
	
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
	
	private JComboBox<String> locationCombobox;
	private JComboBox<Integer> branchCombobox;
	private JComboBox<String> componentCombobox;
	private JComboBox<String> unitCombobox;
	
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
		setSize(790,250);
		setLocation(200,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
		this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
				     circuit.windowEnableButtons();
				     cleanUp();
				   }
				  });
		this.setVisible(true);
	}
	
	public void initializeComponents() {
		branchCreated = false;
		temp = new HashMap<String,Integer>();
		
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
		
		locationLabel = new JLabel("Location to insert after",JLabel.CENTER);
		numOfBranchLabel = new JLabel("# of branches",JLabel.RIGHT);
		selectBranchLabel = new JLabel("Select a branch",JLabel.CENTER);
		selectComponentLabel = new JLabel("Component",JLabel.CENTER);
		enterNameLabel = new JLabel("Enter name:",JLabel.CENTER);
		enterValueLabel = new JLabel("Enter value:",JLabel.CENTER);
		statusLabel = new JLabel("Status: branches not created",JLabel.CENTER);
		
		
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
		
		resistorUnits = new String[] {"Ohms","Kiloohms","Megaohms"};
		capacitorUnits = new String[] {"Millifarad","Microfarad","Picofarad"};
		inductorUnits = new String[] {"Henry","Microhenry","Milihenry"};
		
		unitCombobox = new JComboBox<String>(resistorUnits);
		unitCombobox.setSelectedItem("Ohms");
		
		Vector<String> validLocations = new Vector<String>();
		

		for(String s: circuit.getMap().keySet()) {
			if(!circuit.findNode(s).isBranchStart() && !s.equals("end0")) {
				validLocations.addElement(s);
			}	
		}
		locations = new String[validLocations.size()];
		int i = 0;
		for(String s: validLocations) {
			if(s.equals("end0")) {}
			else if(s.startsWith("end")) {
				locations[i] = new String("Parallel Section " + s.substring(3));
				++i;
			}
			else if(s.equals("start0")) {
				locations[i] = new String("Input");
				++i;
			}
			else {
				locations[i] = s;
				++i;
			}
			
		}
		Arrays.sort(locations);
		
		locationCombobox = new JComboBox<String>(locations);
		branchCombobox = new JComboBox<Integer>();
		
		branchTextfield = new JTextField();
		branchTextfield.getDocument().addDocumentListener(new createBranch());
		nameTextfield = new JTextField();
		nameTextfield.getDocument().addDocumentListener(new canCreate());
		nameTextfield.setEnabled(false);
		valueTextfield = new JTextField();
		valueTextfield.getDocument().addDocumentListener(new canCreate());
		valueTextfield.setEnabled(false);
		
		createBranchButton = new JButton("Create");
		createBranchButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	branchCreated = true;
		    	numOfBranch = Integer.parseInt(branchTextfield.getText());
		    	hasComponent = new boolean[numOfBranch];
		    	createdComponents = new CircuitComponent[numOfBranch];
		    	componentNames = new String[numOfBranch];
		    	for(int i = 0;i < numOfBranch;++i) {
		    		hasComponent[i] = false;
		    		createdComponents[i] = null;
		    		int num = i + 1;
			    	branchCombobox.addItem(num);
		    	}
		    	branchCombobox.addActionListener (new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				    	addComponentButton.setEnabled(isValidInput() && !branchHasComponent() && !tempHasName(nameTextfield.getText()));
				    }
				});
		    	statusLabel.setText("Status: 0/" + numOfBranch +"branches created");
		        addComponentButton.setEnabled(false);
		        createBranchButton.setEnabled(false);
		        branchTextfield.setEnabled(false);
		        nameTextfield.setEnabled(true);
		        valueTextfield.setEnabled(true);
		    }
		});
		createBranchButton.setEnabled(false);
		addComponentButton = new JButton("Add");
		addComponentButton.setEnabled(false);
		addComponentButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	int index = branchCombobox.getSelectedIndex();
		    	componentNames[index] = nameTextfield.getText();
		    	createdComponents[index] = generateComponent();
		    	hasComponent[index] = true;
		    	temp.put(nameTextfield.getText(), index);
		    	int createdBranches = 0;
		    	for(int i = 0;i < numOfBranch;++i) {
		    		if(hasComponent[i]) {
		    			++createdBranches;
		    		}
		    	}
		    	nameTextfield.setText("");
		    	valueTextfield.setText("");
		    	statusLabel.setText("Status: " + createdBranches +"/" + numOfBranch +"branches created");
		    	addComponentButton.setEnabled(false);
		    	completeButton.setEnabled(isComplete());
		    }
		});
		completeButton = new JButton("Complete");
		completeButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String location = (String) locationCombobox.getSelectedItem();
		    	if(location.equals("Input")) {
		    		location = "start0";
		    	}
		    	if(location.startsWith("Parallel Section ")) {
		    		String num = location.substring(17);
		    		location = "end" + num;
		    	}
		    	circuit.setCurrentNode(circuit.findNode(location));
		    	circuit.addParallelNodes(numOfBranch);
		    	String junctionNum = Integer.toString(circuit.getJunctionNum() - 1);
		    	for(int i = 0;i < numOfBranch;++i) {
		    		circuit.addFirstNodeToBranch(junctionNum, i, componentNames[i], createdComponents[i]);
		    	}
		    	cleanUp();
		    }
		});
		completeButton.setEnabled(false);
		
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
	
	private CircuitComponent generateComponent() {
		if(componentCombobox.getSelectedIndex() == 0) {
			//a resistor
			if(unitCombobox.getSelectedIndex() == 0) {
				return new Resistor(Double.parseDouble(valueTextfield.getText()));
			}
			
			else if (unitCombobox.getSelectedIndex() == 1) {
				return new Resistor(1000 * Double.parseDouble(valueTextfield.getText()));
			}
			
			else {
				return new Resistor(1000000 * Double.parseDouble(valueTextfield.getText()));
			}
		}
		
		else if(componentCombobox.getSelectedIndex() == 1) {
			//a capacitor
			if(unitCombobox.getSelectedIndex() == 0) {
				return new Capacitor(0.001 * Double.parseDouble(valueTextfield.getText()));
			}
			
			else if(unitCombobox.getSelectedIndex() == 1) {
				return new Capacitor(0.000001 * Double.parseDouble(valueTextfield.getText()));
			}
			
			else {
				return new Capacitor(0.000000000001 * Double.parseDouble(valueTextfield.getText()));
			}
		}
		
		else {
			//an inductor
			if(unitCombobox.getSelectedIndex() == 0) {
				return new Inductor(Double.parseDouble(valueTextfield.getText()));
			}
			
			else if(unitCombobox.getSelectedIndex() == 1) {
				return new Inductor(0.001 * Double.parseDouble(valueTextfield.getText()));
			}
			
			else return new Inductor(0.000001 * Double.parseDouble(valueTextfield.getText()));
		}
	}
	
	private boolean isNumber() {
		try {
			Double.parseDouble(valueTextfield.getText());
		}
		
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private boolean isValidInput() {
		String name = nameTextfield.getText();
		return(!name.equals("") && !circuit.nameExist(name) && isNumber());
	}
	
	private boolean branchHasComponent() {
		int selectedBranch = branchCombobox.getSelectedIndex();
		return hasComponent[selectedBranch];
	}
	
	private void cleanUp() {
		circuit.windowEnableButtons();
		this.dispose();
	}
	
	private boolean tempHasName(String s) {
		return temp.containsKey(s);
	}
	
	private boolean isComplete() {
		boolean res = true;
		for(int i = 0;i < numOfBranch;++i) {
			if(!hasComponent[i]) {
				res = false;
			}
		}
		return res;
	}
	
	private class canCreate implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			addComponentButton.setEnabled(isValidInput() && !branchHasComponent() && !tempHasName(nameTextfield.getText()));
			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			addComponentButton.setEnabled(isValidInput() && !branchHasComponent() && !tempHasName(nameTextfield.getText()));
			
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			addComponentButton.setEnabled(isValidInput() && !branchHasComponent() && !tempHasName(nameTextfield.getText()));
			
		}
		
	}
	
	private boolean branchIsNumber() {
		try {
			Integer.parseInt(branchTextfield.getText());
		}
		
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	private class createBranch implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			createBranchButton.setEnabled(!branchCreated && branchIsNumber());
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			createBranchButton.setEnabled(!branchCreated && branchIsNumber());
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			createBranchButton.setEnabled(!branchCreated && branchIsNumber());
			
		}
		
	}
}
