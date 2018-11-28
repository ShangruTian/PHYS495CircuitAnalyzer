package circuit_gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import circuit_logic.Circuit;
import circuit_logic.CircuitComponent;
import circuit_logic.CircuitNode;

public class ViewCircuitWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -19286893548064587L;

	private Circuit circuit;
	
	private JPanel mainPanel;
	private JPanel displayPanel;
	private JPanel setOutputPanel;
	private JPanel labelPanel;
	private JPanel allComponentsPanel;
	
	private NumberFormat formatter;
	
	private JLabel selectOutputLabel;
	private JLabel start;
	private JLabel end;
	
	private JComboBox<String> startLocationCombobox;
	private JComboBox<String> endLocationCombobox;
	
	
	private JButton setOutputButton;
	
	private JTextArea circuitLabel;
	private JTextArea allComponents;
	private JScrollPane sp1;
	private JScrollPane sp2;
	
	private String firstString;
	private String secondString;
	
	public ViewCircuitWindow(Circuit c) {
		this.circuit = c;
		setSize(900,250);
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
		formatter = new DecimalFormat("0.##E0");
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,1));
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1,1));
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,1));
		
		setOutputPanel = new JPanel();
		setOutputPanel.setLayout(new GridLayout(1,5));
		
		allComponentsPanel = new JPanel();
		allComponentsPanel.setLayout(new GridLayout(1,1));
		
		selectOutputLabel = new JLabel("Select locations for your 2 output terminals",JLabel.CENTER);
		start = new JLabel("1st terminal",JLabel.CENTER);
		end = new JLabel("2nd terminal",JLabel.CENTER);
		
		startLocationCombobox = new JComboBox<String>();
		endLocationCombobox = new JComboBox<String>();
		
		
		circuitLabel = new JTextArea();
		circuitLabel.setLineWrap(false);
		circuitLabel.setEditable(false);
		StringBuilder result = new StringBuilder();
		for(String s:circuit.viewCircuit()) {
			if(!s.equals("Function generator") && !s.equals("Back to function generator") && !s.equals("||")) {				
					startLocationCombobox.addItem(s);
					endLocationCombobox.addItem(s);
			}
			result.append(s);
	
			if(!s.equals("Back to function generator")) {
				result.append("->");
			}
		}
		
		result.append('\n');
		firstString = result.toString();
		circuitLabel.append(firstString);
		if(circuit.hasOutput()){
			String startName = circuit.getOutputStart().getName();
			String endName = circuit.getOutputEnd().getName();
			if(startName.startsWith("end")) {
				String firstIndex = startName.substring(3);
				startName = "Parallel section " + firstIndex;
			}
			if(endName.startsWith("end")) {
				String secondIndex = endName.substring(3);
				endName = "Parallel section " + secondIndex;
			}
			secondString = new String("Output terminals: Before " + startName + ", After " + endName);
		}
		else secondString = new String("(No output terminals selected)");
		circuitLabel.append(secondString);
		sp1 = new JScrollPane(circuitLabel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		startLocationCombobox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String s = (String) startLocationCombobox.getSelectedItem();
		        endLocationCombobox.removeAllItems();
		        if(s.startsWith("Parallel Section ")) {
		        	String index = s.substring(17);
		        	s = "end" + index;
		        }
		        CircuitNode startNode = circuit.findNode(s);
		        while(startNode != circuit.findNode("end0")) {
		        	if(startNode.getComponent().getType().equals("JunctionEnd")) {
		        		endLocationCombobox.addItem("Parallel Section " + (int)startNode.getComponent().getValue());
		        		startNode = startNode.next();
		        	}
		        	else {
		        		endLocationCombobox.addItem(startNode.getName());
		        		startNode = startNode.next();
		        	}
		        }
		    }
		});
		startLocationCombobox.setEnabled(circuit.getMap().size() != 2);
		endLocationCombobox.setEnabled(circuit.getMap().size() != 2);
		allComponents = new JTextArea();
		allComponents.setLineWrap(true);
		allComponents.setEditable(false);
		for(String s: circuit.getMap().keySet()) {
			if(s.startsWith("end") ||s.startsWith("start")) {}
			else {
				CircuitComponent curr = circuit.getMap().get(s).getComponent();
				allComponents.append(s + ": " + curr.getType() + ", " + formatter.format(curr.getValue()) + "  ");
			}
		}
		sp2 = new JScrollPane(allComponents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		setOutputButton = new JButton("Set terminals");
		setOutputButton.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String location1 = (String) startLocationCombobox.getSelectedItem();
		    	if(location1.startsWith("Parallel Section ")) {
		    		String index = location1.substring(17);
		    		location1 = "end" + index;
		    	}
		    	circuit.setOutputStart(circuit.findNode(location1));
		    	String location2 = (String) endLocationCombobox.getSelectedItem();
		    	if(location2.startsWith("Parallel Section ")) {
		    		String index = location2.substring(17);
		    		location2 = "end" + index;
		    	}
		    	circuit.setOutputEnd(circuit.findNode(location2));
		        cleanUp();
		        
		    }
		});

		if(circuit.getMap().size() == 2) {
			setOutputButton.setEnabled(false);
		}


	}
	
	private void cleanUp() {
		circuit.windowEnableButtons();
		circuit.getWindow().setCalculateButton();
		this.dispose();
	}
	
	public void createGUI() {
		displayPanel.add(sp1);
		mainPanel.add(displayPanel);
		
		labelPanel.add(selectOutputLabel);

		
		setOutputPanel.add(start);
		setOutputPanel.add(startLocationCombobox);
		setOutputPanel.add(end);
		setOutputPanel.add(endLocationCombobox);
		setOutputPanel.add(setOutputButton);
		
		
		allComponentsPanel.add(sp2);
		mainPanel.add(allComponentsPanel);
		mainPanel.add(labelPanel);
		mainPanel.add(setOutputPanel);
		
		add(mainPanel);
	}
}
