package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import circuit_logic.*;

public class AddSingleComponentWindow extends JFrame{
	private static final long serialVersionUID = 1;
	
	private JPanel mainPanel;
	private JPanel locationPanel;
	private JPanel newComponentPanel;
	
	private JTextField valueTextfield;
	private JTextField nameTextfield;
	
	private JLabel enterValueLabel;
	private JLabel enterNameLabel;
	private JLabel selectComponentLabel;
	private JLabel selectLocationLabel;
	
	private JComboBox<String> componentCombobox;
	private JComboBox<String> unitCombobox;
	private JComboBox<String> locationCombobox;
	
	private String[] resistorUnits;
	private String[] capacitorUnits;
	private String[] inductorUnits;
	private String[] components;
	private String[] locations;
	
	private JButton addButton;
	private Circuit circuit;
	
	public AddSingleComponentWindow(Circuit c) {
		this.circuit = c;
		setSize(790,100);
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
	
	public void initializeComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,1));
		
		locationPanel = new JPanel();
		locationPanel.setLayout(new GridLayout(1,2));
		
		newComponentPanel = new JPanel();
		newComponentPanel.setLayout(new GridLayout(1,8));
		
		enterValueLabel = new JLabel("Enter value:",JLabel.CENTER);
		enterNameLabel = new JLabel("Enter name:",JLabel.CENTER);
		selectLocationLabel = new JLabel("Select the location to insert after:",JLabel.CENTER);
		selectComponentLabel = new JLabel("Component",JLabel.CENTER);
		
		valueTextfield = new JTextField();
		valueTextfield.getDocument().addDocumentListener(new canCreate());
		nameTextfield = new JTextField();
		nameTextfield.getDocument().addDocumentListener(new canCreate());
		
		components = new String[]{"Resistor","Capacitor","Inductor"};
		componentCombobox = new JComboBox<String>(components);
		componentCombobox.setSelectedItem("Resistor");
		
		resistorUnits = new String[] {"Ohms","Kiloohms","Megaohms"};
		capacitorUnits = new String[] {"Millifarad","Microfarad","Picofarad"};
		inductorUnits = new String[] {"Henry","Microhenry","Milihenry"};
		
		Vector<String> validLocations = new Vector<String>();
		

		for(String s: circuit.getMap().keySet()) {
			if(!s.startsWith("start") && !s.equals("end0")) {
				validLocations.addElement(s);
			}
			if(s.equals("start0")) {
				validLocations.addElement("Circuit start");
			}
		}
		locations = new String[validLocations.size()];
		int i = 0;
		for(String s: validLocations) {
				if(s.startsWith("end")) {
					String num = s.substring(3);
					locations[i] = new String("Parallel Section " + num);
					++i;
				}
				else {
					locations[i] = s;
					++i;
				}
			
		}
		Arrays.sort(locations);
		
		unitCombobox = new JComboBox<String>(resistorUnits);
		unitCombobox.setSelectedItem("Ohms");
		
		locationCombobox = new JComboBox<String>(locations);
		
		addButton = new JButton("Add");
		addButton.setEnabled(false);
		addButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String location = (String) locationCombobox.getSelectedItem();
		    	if(location.equals("Circuit start")) {
		    		location = "start0";
		    	}
		    	else if(location.startsWith("Parallel Section ")) {
		    		location = "end" + location.substring(17);
		    	}
		    	circuit.setCurrentNode(circuit.findNode(location));
		        circuit.addSingleNewNode(nameTextfield.getText(), generateComponent());
		        cleanUp();
		        
		    }
		});
		
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
		
		
	}
	
	public void createGUI() {
		locationPanel.add(selectLocationLabel);
		locationPanel.add(locationCombobox);
		
		mainPanel.add(locationPanel);
		
		newComponentPanel.add(selectComponentLabel);
		newComponentPanel.add(componentCombobox);
		newComponentPanel.add(enterValueLabel);
		newComponentPanel.add(valueTextfield);
		newComponentPanel.add(unitCombobox);
		newComponentPanel.add(enterNameLabel);
		newComponentPanel.add(nameTextfield);
		newComponentPanel.add(addButton);
		
		mainPanel.add(newComponentPanel);
		add(mainPanel);
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
	
	private void cleanUp() {
		circuit.windowEnableButtons();
		this.dispose();
	}
	
	private class canCreate implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			addButton.setEnabled(isValidInput());
			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			addButton.setEnabled(isValidInput());
			
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			addButton.setEnabled(isValidInput());
			
		}
		
	}

}
