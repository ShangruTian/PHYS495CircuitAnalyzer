package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import circuit_logic.Circuit;

public class DeleteSingleComponentWindow extends JFrame{
	private static final long serialVersionUID = 2;
	private Circuit circuit;
	
	private JPanel mainPanel;
	
	private JLabel deleteLabel;
	
	private JComboBox componentCombobox;
	
	private JButton deleteButton;
	public DeleteSingleComponentWindow(Circuit c) {
		this.circuit = c;
		setSize(300,60);
		setLocation(200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
		this.setVisible(true);
	}
	
	public void initializeComponents() {
		mainPanel = new JPanel(new GridLayout(1,3));
		
		deleteLabel = new JLabel("Delete a component");
		
		componentCombobox = new JComboBox();
		
		deleteButton = new JButton("Delete");
	}
	
	public void createGUI() {
		mainPanel.add(deleteLabel);
		mainPanel.add(componentCombobox);
		mainPanel.add(deleteButton);
		add(mainPanel);
	}
}
