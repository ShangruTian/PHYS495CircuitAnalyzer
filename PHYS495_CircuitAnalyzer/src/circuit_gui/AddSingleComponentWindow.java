package circuit_gui;
import java.awt.*;

import javax.swing.*;

import circuit_logic.*;

public class AddSingleComponentWindow extends JFrame{
	private static final long serialVersionUID = 1;
	
	private JPanel mainPanel;
	
	private JTextField value;
	private JTextField name;
	private JLabel enterValueLabel;
	private JLabel enterNameLabel;
	private JComboBox component;
	private JComboBox unit;
	private JButton addButton;
	private Circuit circuit;
	
	public AddSingleComponentWindow(Circuit c) {
		this.circuit = c;
		setSize(200,600);
		setLocation(200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
	}
	
	public void initializeComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,5));
		
		enterValueLabel = new JLabel("Enter value:");
		
		
		
		
	}
	
	public void createGUI() {
		
	}

}
