package circuit_gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import circuit_logic.Circuit;
import circuit_logic.CircuitNode;

public class DrawCircuitWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7986868152805354377L;
	private Circuit circuit;
	private JPanel mainPanel;
	private JTextArea drawArea;
	
	private StringBuilder firstLine;
	private StringBuilder secondLine;
	private StringBuilder thirdLine;
	
	public DrawCircuitWindow(Circuit c) {
		this.circuit = c;
		setLocation(900,200);
		setSize(circuit.canDraw() * 5,300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initializeComponents();
		createGUI();
		pack();
		this.setVisible(true);
	}
	
	private void initializeComponents() {
		drawArea = new JTextArea();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		firstLine = new StringBuilder();
		secondLine = new StringBuilder();
		thirdLine = new StringBuilder();
		firstLine.append("         ");
		secondLine.append("Input-");
		thirdLine.append("         ");
		CircuitNode start = circuit.findNode("start0");
		while(start != circuit.findNode("end0")) {
			if(start.isBranchStart()) {
				if(start != circuit.findNode("start0") && !start.getComponent().getType().equals("JunctionEnd")) {
					String name = start.getName();
					int buffer = name.length() + 1;
					for(int i = 0;i < buffer;++i) {
						firstLine.append(" ");
						thirdLine.append(" ");
					}
					secondLine.append(start.getName());
					secondLine.append("-");
				}
				firstLine.append("  ");
				secondLine.append("|");
				thirdLine.append("  ");
				int size = start.getChildren().size();
				if(size == 2) {
					CircuitNode firstNode = start.getChildren().get(0).next();
					CircuitNode secondNode = start.getChildren().get(1).next();
					String firstName = firstNode.getName();
					String secondName = secondNode.getName();
					int max = Math.max(firstName.length(),secondName.length());
					firstLine.append(firstName);
					for(int i = 0;i < max - firstName.length();++i) {
						firstLine.append(" ");
					}
					firstLine.append("  ");
					for(int j = 0;j < max;++j) {
						secondLine.append(" ");
					}
					secondLine.append("|-");
					thirdLine.append(secondName);
					for(int k = 0;k < max - secondName.length();++k) {
						thirdLine.append(" ");
					}
					thirdLine.append("  ");
					start = start.next();
				}
				else {
					CircuitNode firstNode = start.getChildren().get(0).next();
					CircuitNode secondNode = start.getChildren().get(1).next();
					CircuitNode thirdNode = start.getChildren().get(2).next();
					String firstName = firstNode.getName();
					String secondName = secondNode.getName();
					String thirdName = thirdNode.getName();
					int max = Math.max(Math.max(firstName.length(), secondName.length()), thirdName.length());
					firstLine.append(firstName);
					for(int i = 0;i < max - firstName.length();++i) {
						firstLine.append(" ");
					}
					firstLine.append("  ");
					secondLine.append(secondName);
					for(int j = 0;j < max - secondName.length();++j) {
						secondLine.append(" ");
					}
					secondLine.append("|-");
					thirdLine.append(thirdName);
					for(int k = 0;k < max - thirdName.length();++k) {
						thirdLine.append(" ");
					}
					thirdLine.append("  ");
					start = start.next();
				}
			}
			else if(start.getComponent().getType().equals("JunctionEnd") || start == circuit.findNode("start0")) {
				start = start.next();
			}
			else {
				String name = start.getName();
				int size = name.length() + 1;
				for(int i = 0;i < size;++i) {
					firstLine.append(" ");
					thirdLine.append(" ");
				}
				secondLine.append(start.getName());
				secondLine.append("-");
				start = start.next();
			}
		}
		secondLine.append("Ground");
		firstLine.append('\n');
		secondLine.append('\n');
		drawArea.append(firstLine.toString());
		drawArea.append(secondLine.toString());
		drawArea.append(thirdLine.toString());
		drawArea.setEditable(false);
	}
	
	private void createGUI() {
		mainPanel.add(drawArea);
		add(mainPanel);
	}

}
