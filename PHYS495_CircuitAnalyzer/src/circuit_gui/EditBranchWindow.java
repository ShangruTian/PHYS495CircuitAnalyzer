package circuit_gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import circuit_logic.Capacitor;
import circuit_logic.Circuit;
import circuit_logic.CircuitComponent;
import circuit_logic.CircuitNode;
import circuit_logic.Inductor;
import circuit_logic.Resistor;
public class EditBranchWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5855111411718122458L;

	private Circuit circuit;
	
	private JPanel mainPanel;
	private JPanel selectSectionPanel;
	private JPanel displayBranchPanel;
	private JPanel deleteBranchPanel;
	private JPanel panelForLabelTwo;
	private JPanel addComponentPanel;
	
	private JLabel labelOne;
	private JLabel labelTwo;
	private JLabel selectLabel;
	private JLabel componentLabel;
	private JLabel valueLabel;
	private JLabel nameLabel;
	private JLabel selectBranchLabel;
	
	private JTextArea viewSelectedBranch;
	private JScrollPane sp;
	
	private JComboBox<String> locationCombobox;
	private JComboBox<Integer> viewBranchCombobox;
	private JComboBox<Integer> deleteBranchCombobox;
	private JComboBox<String> componentCombobox;
	private JComboBox<String> unitCombobox;
	
	private JTextField valueTextfield;
	private JTextField nameTextfield;
	
	private JButton deleteButton;
	private JButton addButton;
	private JButton viewButton;
	
	private String[] resistorUnits;
	private String[] capacitorUnits;
	private String[] inductorUnits;
	private String[] components;
	
	private String[] locations;
	
	public EditBranchWindow(Circuit c) {
		this.circuit = c;
		setSize(790,200);
		setLocation(200,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		disableAll();
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
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));
		
		selectSectionPanel = new JPanel();
		selectSectionPanel.setLayout(new GridLayout(1,5));
		
		displayBranchPanel = new JPanel();
		displayBranchPanel.setLayout(new GridLayout(1,1));
		
		panelForLabelTwo = new JPanel();
		panelForLabelTwo.setLayout(new GridLayout(1,1));
		
		deleteBranchPanel = new JPanel();
		deleteBranchPanel.setLayout(new GridLayout(1,3));
		
		addComponentPanel = new JPanel();
		addComponentPanel.setLayout(new GridLayout(1,8));
		
		labelOne = new JLabel("Delete a branch");
		labelTwo = new JLabel("Add a branch with a new component",JLabel.CENTER);
		selectLabel = new JLabel("Select a section");
		selectBranchLabel = new JLabel("Select a branch");
		componentLabel = new JLabel("Component");
		valueLabel = new JLabel("Enter value:");
		nameLabel = new JLabel("Enter name:");
		
		valueTextfield = new JTextField();
		valueTextfield.getDocument().addDocumentListener(new canCreate());
		nameTextfield = new JTextField();
		nameTextfield.getDocument().addDocumentListener(new canCreate());
		
		viewBranchCombobox = new JComboBox<Integer>();		
		deleteBranchCombobox = new JComboBox<Integer>();
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
		
		deleteButton = new JButton("Delete");
		addButton = new JButton("Add");
		viewButton = new JButton("View");
		
		viewSelectedBranch = new JTextArea();
		viewSelectedBranch.setLineWrap(true);
		sp = new JScrollPane(viewSelectedBranch,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		Vector<String> validLocations = new Vector<String>();
		

		for(String s: circuit.getMap().keySet()) {
			if(s.startsWith("end") && !s.equals("end0")) {
				validLocations.addElement(s);
			}	
		}
		if(validLocations.size() == 0) {
			locationCombobox = new JComboBox<String>();
			disableAll();
		}
		
		else {
			locations = new String[validLocations.size() + 1];
			locations[0] = new String("(Choose a section)");
			int i = 1;
			for(String s: validLocations) {
				if(s.equals("end0")) {}
				else {
					locations[i] = new String("Parallel Section " + s.substring(3));
					++i;
				}
				
			}
			Arrays.sort(locations);
			locationCombobox = new JComboBox<String>(locations);
		}

		locationCombobox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(validLocations.size() == 0) {
		        	
		        }
		        else if(((String)locationCombobox.getSelectedItem()).equals("(Choose a section)")) {
		        	viewBranchCombobox.removeAllItems();
		        	deleteBranchCombobox.removeAllItems();
		        	disableAll();
		        }
		        else {
		        	viewSelectedBranch.setText("");
		        	viewBranchCombobox.removeAllItems();
		        	deleteBranchCombobox.removeAllItems();
		        	String location = (String)locationCombobox.getSelectedItem();
		        	String num = location.substring(17);
		        	String name = new String("end" + num);
		        	CircuitNode target = circuit.findNode(name).prev();
		        	int size = target.getChildren().size();
		        	for(int i = 0;i < size;++i) {
		        		int index = i + 1;
		        		viewBranchCombobox.addItem(index);
		        		deleteBranchCombobox.addItem(index);
		        	}
		        	enableAll();
		        	addButton.setEnabled(isValidInput());
		        }
		    }
		});
		
		viewButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String result = new String("");
		    	String section = (String)locationCombobox.getSelectedItem();
		    	String num = section.substring(17);
		    	int branch = viewBranchCombobox.getSelectedIndex();
		        for(String s: circuit.viewBranch(num, branch)) {
					result += s;
					if(!s.equals("Branch End")) {result += " -> ";}
		        }
		        
		        viewSelectedBranch.setText(result);
		        
		    }
		});
		
		addButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String section = (String)locationCombobox.getSelectedItem();
		    	String num = section.substring(17);
		    	circuit.addSingleBranch(num);
		    	String target = new String("end" + num);
		    	int size = circuit.findNode(target).prev().getChildren().size();
		    	circuit.addFirstNodeToBranch(num, size-1, nameTextfield.getText(), generateComponent());
		        cleanUp();
		        
		    }
		});
		
		deleteButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String section = (String)locationCombobox.getSelectedItem();
		    	String num = section.substring(17);
		    	int branch = deleteBranchCombobox.getSelectedIndex();
		    	circuit.removeSingleBranch(num, branch);
		        cleanUp();		        
		    }
		});
		
	}
	
	private void disableAll() {
		deleteButton.setEnabled(false);
		addButton.setEnabled(false);
		viewButton.setEnabled(false);
		valueTextfield.setEnabled(false);
		nameTextfield.setEnabled(false);
	}
	
	private void enableAll() {
		deleteButton.setEnabled(true);
		addButton.setEnabled(true);
		viewButton.setEnabled(true);
		valueTextfield.setEnabled(true);
		nameTextfield.setEnabled(true);
	}
	
	public void createGUI() {
		selectSectionPanel.add(selectLabel);
		selectSectionPanel.add(locationCombobox);
		selectSectionPanel.add(selectBranchLabel);
		selectSectionPanel.add(viewBranchCombobox);
		selectSectionPanel.add(viewButton);
		mainPanel.add(selectSectionPanel);
		
		displayBranchPanel.add(sp);
		mainPanel.add(displayBranchPanel);
		
		
		
		deleteBranchPanel.add(labelOne);
		deleteBranchPanel.add(deleteBranchCombobox);
		deleteBranchPanel.add(deleteButton);
		mainPanel.add(deleteBranchPanel);
		
		panelForLabelTwo.add(labelTwo);
		mainPanel.add(panelForLabelTwo);
		
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
