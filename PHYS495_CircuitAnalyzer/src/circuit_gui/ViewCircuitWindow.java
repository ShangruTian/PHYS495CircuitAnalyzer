package circuit_gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import circuit_logic.Circuit;
import circuit_logic.CircuitComponent;

public class ViewCircuitWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -19286893548064587L;

	private Circuit circuit;
	
	private JPanel mainPanel;
	private JPanel displayPanel;
	private JPanel setOutputPanel;
	private JPanel allComponentsPanel;
	
	
	private JLabel selectOutputLabel;
	
	private JComboBox<String> componentCombobox;
	
	
	private JButton setOutputButton;
	
	private JTextArea circuitLabel;
	private JTextArea allComponents;
	private JScrollPane sp1;
	private JScrollPane sp2;
	
	public ViewCircuitWindow(Circuit c) {
		this.circuit = c;
		setSize(480,200);
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
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,1));
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,1));
		
		setOutputPanel = new JPanel();
		setOutputPanel.setLayout(new GridLayout(1,3));
		
		allComponentsPanel = new JPanel();
		allComponentsPanel.setLayout(new GridLayout(1,1));
		
		selectOutputLabel = new JLabel("Select an output terminal");
		
		componentCombobox = new JComboBox<String>();
		
		
		circuitLabel = new JTextArea();
		circuitLabel.setLineWrap(false);
		String result = new String("");
		for(String s:circuit.viewCircuit()) {
			if(!s.equals("Circuit Start") && !s.equals("Circuit End") && !s.equals("(Output terminal)")) {
				componentCombobox.addItem(s);
			}
			result += s;
			if(!s.equals("Circuit End")) {result += " -> ";}
		}
		circuitLabel.setText(result);
		sp1 = new JScrollPane(circuitLabel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		allComponents = new JTextArea();
		allComponents.setLineWrap(true);
		for(String s: circuit.getMap().keySet()) {
			if(s.startsWith("end") ||s.startsWith("start")) {}
			else {
				CircuitComponent curr = circuit.getMap().get(s).getComponent();
				allComponents.append(s + ": " + curr.getType() + ", " + curr.getValue() + "  ");
			}
		}
		sp2 = new JScrollPane(allComponents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setOutputButton = new JButton("Set as output");
		setOutputButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String location = (String) componentCombobox.getSelectedItem();
		    	if(location.startsWith("Parallel Section ")) {
		    		String index = location.substring(17);
		    		location = "end" + index;
		    	}
		    	circuit.setOutput(circuit.findNode(location));
		        cleanUp();
		        
		    }
		});
		if(circuit.getMap().size() == 2) {
			setOutputButton.setEnabled(false);
		}
	}
	
	private void cleanUp() {
		circuit.windowEnableButtons();
		this.dispose();
	}
	
	public void createGUI() {
		displayPanel.add(sp1);
		mainPanel.add(displayPanel);
		
		setOutputPanel.add(selectOutputLabel);
		setOutputPanel.add(componentCombobox);
		setOutputPanel.add(setOutputButton);
		
		
		allComponentsPanel.add(sp2);
		mainPanel.add(allComponentsPanel);
		mainPanel.add(setOutputPanel);
		
		add(mainPanel);
	}
}
