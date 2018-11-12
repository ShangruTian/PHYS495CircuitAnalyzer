package circuit_gui;

import java.awt.GridLayout;

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
		
		private JButton deleteButton;
		public DeleteParallelSectionWindow(Circuit c) {
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
			
			deleteLabel = new JLabel("Delete a parallel section");
			
			componentCombobox = new JComboBox<String>();
			
			deleteButton = new JButton("Delete");
		}
		
		public void createGUI() {
			mainPanel.add(deleteLabel);
			mainPanel.add(componentCombobox);
			mainPanel.add(deleteButton);
			add(mainPanel);
		}
}
