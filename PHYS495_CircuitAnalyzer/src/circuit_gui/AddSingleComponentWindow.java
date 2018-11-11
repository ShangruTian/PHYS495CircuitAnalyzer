package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
	private JLabel selectLocationLabel;
	
	private JComboBox componentCombobox;
	private JComboBox unitCombobox;
	private JComboBox locationCombobox;
	
	private String[] resistorUnits;
	private String[] capacitorUnits;
	private String[] inductorUnits;
	private String[] components;
	
	private JButton addButton;
	private Circuit circuit;
	
	public AddSingleComponentWindow(Circuit c) {
		this.circuit = c;
		setSize(500,100);
		setLocation(200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
		this.setVisible(true);
	}
	
	public void initializeComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,1));
		
		locationPanel = new JPanel();
		locationPanel.setLayout(new GridLayout(1,2));
		
		newComponentPanel = new JPanel();
		newComponentPanel.setLayout(new GridLayout(1,5));
		
		enterValueLabel = new JLabel("Enter value:");
		enterNameLabel = new JLabel("Enter name:");
		selectLocationLabel = new JLabel("Select the location to insert after:");
		
		valueTextfield = new JTextField();
		nameTextfield = new JTextField();
		
		components = new String[]{"Resistor","Capacitor","Inductor"};
		componentCombobox = new JComboBox(components);
		componentCombobox.setSelectedItem("Resistor");
		
		resistorUnits = new String[] {"Ohms","Kilo Ohms","Mega Ohms"};
		capacitorUnits = new String[] {"Farad","Mico Farad","Nano Farad"};
		inductorUnits = new String[] {"Henry","Micro Henry","Nano Henry"};
		
		unitCombobox = new JComboBox(resistorUnits);
		unitCombobox.setSelectedItem("Ohms");
		
		locationCombobox = new JComboBox();
		
		addButton = new JButton("Add");
		
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
		
		newComponentPanel.add(componentCombobox);
		newComponentPanel.add(valueTextfield);
		newComponentPanel.add(unitCombobox);
		newComponentPanel.add(nameTextfield);
		newComponentPanel.add(addButton);
		
		mainPanel.add(newComponentPanel);
		add(mainPanel);
	}

}
