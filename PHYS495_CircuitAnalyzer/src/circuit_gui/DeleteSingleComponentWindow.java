package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.*;

import circuit_logic.Circuit;

public class DeleteSingleComponentWindow extends JFrame{
	private static final long serialVersionUID = 2;
	private Circuit circuit;
	
	private JPanel mainPanel;
	
	private JLabel deleteLabel;
	
	private JComboBox<String> componentCombobox;
	private String[] locations;
	private JButton deleteButton;
	public DeleteSingleComponentWindow(Circuit c) {
		this.circuit = c;
		setSize(380,60);
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
		mainPanel = new JPanel(new GridLayout(1,3));
		
		deleteLabel = new JLabel("Delete a component");
		Vector<String> validLocations = new Vector<String>();
		

		for(String s: circuit.getMap().keySet()) {
			if(!s.startsWith("end") && !s.startsWith("start")) {
				validLocations.addElement(s);
			}	
		}
		locations = new String[validLocations.size()];
		int i = 0;
		for(String s: validLocations) {
				locations[i] = s;
				++i;
			
		}
		componentCombobox = new JComboBox<String>(locations);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String location = (String) componentCombobox.getSelectedItem();
		    	circuit.deleteSingleNode(location);
		        cleanUp();
		        
		    }
		});
		if(i == 0) deleteButton.setEnabled(false);
	}
	
	public void createGUI() {
		mainPanel.add(deleteLabel);
		mainPanel.add(componentCombobox);
		mainPanel.add(deleteButton);
		add(mainPanel);
	}
	
	private void cleanUp() {
		circuit.windowEnableButtons();
		this.dispose();
	}
}
