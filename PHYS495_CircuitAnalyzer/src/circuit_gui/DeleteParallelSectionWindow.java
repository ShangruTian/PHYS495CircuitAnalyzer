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

import circuit_logic.Circuit;

public class DeleteParallelSectionWindow extends JFrame{
		private static final long serialVersionUID = 3;
		private Circuit circuit;
		
		private JPanel mainPanel;
		
		private JLabel deleteLabel;
		
		private JComboBox<String> componentCombobox;
		private String[] locations;
		
		private JButton deleteButton;
		public DeleteParallelSectionWindow(Circuit c) {
			this.circuit = c;
			setSize(420,60);
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
			
			deleteLabel = new JLabel("Delete a parallel section");
			
			Vector<String> validLocations = new Vector<String>();
			

			for(String s: circuit.getMap().keySet()) {
				if(s.startsWith("end") && !s.equals("end0")) {
					validLocations.addElement(s);
				}	
			}
			if(validLocations.size() == 0) {
				componentCombobox = new JComboBox<String>();
				deleteButton = new JButton("Delete");
				deleteButton.setEnabled(false);
			}
			
			else {
				locations = new String[validLocations.size()];
				int i = 0;
				for(String s: validLocations) {
					if(s.equals("end0")) {}
					else {
						locations[i] = new String("Parallel Section " + s.substring(3));
						++i;
					}
					
				}
				Arrays.sort(locations);
				
				componentCombobox = new JComboBox<String>(locations);
				
				deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				    	String section = (String) componentCombobox.getSelectedItem();
				    	String num = section.substring(17);
				    	circuit.removeParallelSection(num);
				        cleanUp();
				        
				    }
				});
			}
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
