package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import circuit_logic.Circuit;
public class ChangeComponentWindow extends JFrame{
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
	
	private JButton changeButton;
	private Circuit circuit;
	
	public ChangeComponentWindow(Circuit c) {
		this.circuit = c;
		setSize(650,100);
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
		newComponentPanel.setLayout(new GridLayout(1,8));
		
		enterValueLabel = new JLabel("Enter value:");
		enterNameLabel = new JLabel("Enter name:");
		selectLocationLabel = new JLabel("Select the component to change:");
		selectComponentLabel = new JLabel("Component");
		
		valueTextfield = new JTextField();
		nameTextfield = new JTextField();
		
		components = new String[]{"Resistor","Capacitor","Inductor"};
		componentCombobox = new JComboBox<String>(components);
		componentCombobox.setSelectedItem("Resistor");
		
		resistorUnits = new String[] {"Ohms","Kilo Ohms","Mega Ohms"};
		capacitorUnits = new String[] {"Farad","Mico Farad","Nano Farad"};
		inductorUnits = new String[] {"Henry","Micro Henry","Nano Henry"};
		
		unitCombobox = new JComboBox<String>(resistorUnits);
		unitCombobox.setSelectedItem("Ohms");
		
		locationCombobox = new JComboBox<String>();
		
		changeButton = new JButton("Change");
		
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
		newComponentPanel.add(changeButton);
		
		mainPanel.add(newComponentPanel);
		add(mainPanel);
	}

}
