package circuit_gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import circuit_logic.Circuit;

public class ViewCircuitWindow extends JFrame{
	private Circuit circuit;
	
	private JPanel mainPanel;
	private JPanel displayPanel;
	private JPanel setOutputPanel;
	private JPanel allComponentsPanel;
	
	private JLabel circuitLabel;
	private JLabel selectOutputLabel;
	
	private JComboBox<String> componentCombobox;
	
	private JButton setOutputButton;
	
	private JTextArea allComponents;
	private JScrollPane sp;
	
	public ViewCircuitWindow(Circuit c) {
		this.circuit = c;
		setSize(480,200);
		setLocation(200,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
		this.setVisible(true);
	}
	
	public void initializeComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,1));
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,1));
		
		setOutputPanel = new JPanel();
		setOutputPanel.setLayout(new GridLayout(1,3));
		
		allComponentsPanel = new JPanel();
		allComponentsPanel.setLayout(new GridLayout(1,1));
		
		circuitLabel = new JLabel();
		selectOutputLabel = new JLabel("Select an output terminal");
		
		componentCombobox = new JComboBox<String>();
		
		setOutputButton = new JButton("Set as output");
		
		allComponents = new JTextArea();
		sp = new JScrollPane(allComponents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void createGUI() {
		displayPanel.add(circuitLabel);
		mainPanel.add(displayPanel);
		
		setOutputPanel.add(selectOutputLabel);
		setOutputPanel.add(componentCombobox);
		setOutputPanel.add(setOutputButton);
		mainPanel.add(setOutputPanel);
		
		allComponentsPanel.add(sp);
		mainPanel.add(allComponentsPanel);
		
		add(mainPanel);
	}
}
