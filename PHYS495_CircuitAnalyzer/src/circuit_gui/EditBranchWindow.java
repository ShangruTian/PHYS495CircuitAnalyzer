package circuit_gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import circuit_logic.Circuit;
public class EditBranchWindow extends JFrame{
	private Circuit circuit;
	
	private JPanel mainPanel;
	private JPanel panelForLabelOne;
	private JPanel deleteSingleBranchPanel;
	private JPanel panelForLabelTwo;
	private JPanel selectParallelSectionPanel;
	private JPanel addComponentPanel;
	
	private JLabel labelOne;
	private JLabel labelTwo;
	private JLabel selectDeleteLabel;
	private JLabel selectBranchLabel;
	private JLabel selectAddLabel;
	private JLabel componentLabel;
	private JLabel valueLabel;
	private JLabel nameLabel;
	
	private JComboBox<String> deleteLocationCombobox;
	private JComboBox<Integer> deleteBranchCombobox;
	private JComboBox<String> addLocationCombobox;
	private JComboBox<String> componentCombobox;
	private JComboBox<String> unitCombobox;
	
	private JTextField valueTextfield;
	private JTextField nameTextfield;
	
	private JButton deleteButton;
	private JButton addButton;
	
	private String[] resistorUnits;
	private String[] capacitorUnits;
	private String[] inductorUnits;
	private String[] components;
	
	public EditBranchWindow(Circuit c) {
		this.circuit = c;
		setSize(720,250);
		setLocation(200,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
		this.setVisible(true);
	}
	
	public void initializeComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));
		
		panelForLabelOne = new JPanel();
		panelForLabelOne.setLayout(new GridLayout(1,1));
		
		deleteSingleBranchPanel = new JPanel();
		deleteSingleBranchPanel.setLayout(new GridLayout(1,5));
		
		panelForLabelTwo = new JPanel();
		panelForLabelTwo.setLayout(new GridLayout(1,1));
		
		selectParallelSectionPanel = new JPanel();
		selectParallelSectionPanel.setLayout(new GridLayout(1,2));
		
		addComponentPanel = new JPanel();
		addComponentPanel.setLayout(new GridLayout(1,8));
		
		labelOne = new JLabel("Delete a branch from selected section");
		labelTwo = new JLabel("Add a branch to selected section");
		selectDeleteLabel = new JLabel("Select a section");
		selectBranchLabel = new JLabel("Select a branch");
		selectAddLabel = new JLabel("Select a section");
		componentLabel = new JLabel("Component");
		valueLabel = new JLabel("Enter value:");
		nameLabel = new JLabel("Enter name:");
		
		valueTextfield = new JTextField();
		nameTextfield = new JTextField();
		
		deleteLocationCombobox = new JComboBox<String>();
		deleteBranchCombobox = new JComboBox<Integer>();
		addLocationCombobox = new JComboBox<String>();
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
		
		deleteButton = new JButton("Delete");
		addButton = new JButton("Add");
		
	}
	
	public void createGUI() {
		panelForLabelOne.add(labelOne);
		mainPanel.add(panelForLabelOne);
		
		deleteSingleBranchPanel.add(selectDeleteLabel);
		deleteSingleBranchPanel.add(deleteLocationCombobox);
		deleteSingleBranchPanel.add(selectBranchLabel);
		deleteSingleBranchPanel.add(deleteBranchCombobox);
		deleteSingleBranchPanel.add(deleteButton);
		mainPanel.add(deleteSingleBranchPanel);
		
		panelForLabelTwo.add(labelTwo);
		mainPanel.add(panelForLabelTwo);
		
		selectParallelSectionPanel.add(selectAddLabel);
		selectParallelSectionPanel.add(addLocationCombobox);
		mainPanel.add(selectParallelSectionPanel);
		
		addComponentPanel.add(componentLabel);
		addComponentPanel.add(componentCombobox);
		addComponentPanel.add(valueLabel);
		addComponentPanel.add(valueTextfield);
		addComponentPanel.add(unitCombobox);
		addComponentPanel.add(nameLabel);
		addComponentPanel.add(nameTextfield);
		addComponentPanel.add(addButton);
		mainPanel.add(addComponentPanel);
		
		add(mainPanel);
	}
	
}
