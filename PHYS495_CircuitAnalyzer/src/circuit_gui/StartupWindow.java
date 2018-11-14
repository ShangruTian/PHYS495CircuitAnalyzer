package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import circuit_logic.*;

public class StartupWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSlider frequencySlider;
	
	private JLabel frequencyLabel;
	private JLabel impedanceLabel;
	private JLabel angleLabel;
	private JLabel leadLabel;
	
	private JButton addSingleComponent;
	private JButton deleteSingleComponent;
	private JButton addParallelComponents;
	private JButton deleteParallelComponents;
	private JButton changeComponentButton;
	private JButton viewCircuitButton;
	private JButton editBranchButton;
	private JButton calculateButton;
	
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel sliderPanel;
	private JPanel displayPanel;
	private JPanel anglePanel;
	
	private Circuit circuit;
	
	private boolean canCalculate;
	
	static final int MIN_FREQ = 0;
	static final int MAX_FREQ = 100000;
	static final int DEFAULT_FREQ = 10000;
	
	static final int MIN_VOLTAGE = 1;
	static final int MAX_VOLTAGE = 100;
	static final int DEFAULT_VOLTAGE = 5;
	

	public static void main(String[] args) {
		String[] test1 = new String[] {"Kappa","Keepo","Kippa"};
		JComboBox<String> test = new JComboBox<String>(test1);
		test.setSelectedIndex(1);
		System.out.println(test.getSelectedItem());
		StartupWindow sw = new StartupWindow();
	}
	
	public StartupWindow() {
		super("PHYS 495 Circuit Analyzer");
		setSize(720,540);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initComponents();
		createGUI();
		addActionListeners();
		canCalculate = false;
		this.setVisible(true);
	}
	
	private void initComponents() {
		//voltageSlider = new JSlider(JSlider.HORIZONTAL,MIN_VOLTAGE,MAX_VOLTAGE,DEFAULT_VOLTAGE);
		frequencySlider = new JSlider(JSlider.HORIZONTAL,MIN_FREQ,MAX_FREQ,DEFAULT_FREQ);
		//voltageSlider.setMinorTickSpacing(10);
		frequencySlider.setMinorTickSpacing(1000);
		
		//voltageSlider.setMajorTickSpacing(10);
		frequencySlider.setMajorTickSpacing(10000);
		
		//voltageSlider.setPaintTicks(true);
		frequencySlider.setPaintTicks(false);
		
		//voltageSlider.setPaintLabels(true);
		frequencySlider.setPaintLabels(false);
		

		
		addSingleComponent = new JButton("Add single component");
		deleteSingleComponent = new JButton("Delete single component");
		addParallelComponents = new JButton("Add a parallel section");
		deleteParallelComponents = new JButton("Delete a parallel section");
		changeComponentButton = new JButton("Change a component");
		viewCircuitButton = new JButton("View entire circuit");
		editBranchButton = new JButton("View/Edit parallel section");
		calculateButton = new JButton("Calculate impedance");
		
		frequencyLabel = new JLabel("Frequency: "+ frequencySlider.getValue() + "Hz",JLabel.CENTER);
		//voltageLabel = new JLabel("Peak to peak voltage: 5V",JLabel.CENTER);
		//currNodeLabel = new JLabel("Current node:");
		
		
		impedanceLabel = new JLabel("Impedance:",JLabel.CENTER);
		//amplitudeLabel = new JLabel("Current Amplitude:",JLabel.CENTER);
		angleLabel = new JLabel("Phase Angle:",JLabel.CENTER);
		leadLabel = new JLabel("Lead:",JLabel.CENTER);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,1));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,2));
		
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(1,2));
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,4));
		
		anglePanel = new JPanel();
		anglePanel.setLayout(new GridLayout(2,1));
		
		this.circuit = new Circuit();
		
		frequencySlider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	        	frequencyLabel.setText("Frequency: "+ frequencySlider.getValue() + "Hz");
	        	if(canCalculate) {
	        		ComplexNumber imp =circuit.calculateTotalImpedance(frequencySlider.getValue());
	        		impedanceLabel.setText("Impedance: "+ imp.getRealPart() + "+" + imp.getImaginaryPart() + "j");
	        		double ang = circuit.calculatePhaseAngle(frequencySlider.getValue());
	        		angleLabel.setText("Phase angle: " + ang + "Pi");
	        		leadLabel.setText(circuit.findLeadingVector(frequencySlider.getValue()));
	        	}
	        }
	    });
	}
	
	private void createGUI() {
		
		buttonPanel.add(addSingleComponent);
		buttonPanel.add(deleteSingleComponent);
		buttonPanel.add(addParallelComponents);
		buttonPanel.add(deleteParallelComponents);
		buttonPanel.add(viewCircuitButton);
		buttonPanel.add(editBranchButton);
		mainPanel.add(buttonPanel);
		
		sliderPanel.add(frequencyLabel);
		sliderPanel.add(frequencySlider);
		mainPanel.add(sliderPanel);
		
		displayPanel.add(changeComponentButton);
		displayPanel.add(calculateButton);
		displayPanel.add(impedanceLabel);
		
		anglePanel.add(leadLabel);
		anglePanel.add(angleLabel);
		displayPanel.add(anglePanel);
		
		mainPanel.add(displayPanel);
		
		add(mainPanel);
		
	}
	
	public void addActionListeners() {
		addSingleComponent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				new AddSingleComponentWindow(circuit);
			}

		});
		
		deleteSingleComponent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				new DeleteSingleComponentWindow(circuit);
			}

		});
		
		addParallelComponents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				new AddParallelSectionWindow(null);
			}

		});
		deleteParallelComponents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				new DeleteParallelSectionWindow(null);
			}

		});
		
		viewCircuitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				new ViewCircuitWindow(circuit);
			}

		});
		
		editBranchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				new EditBranchWindow(null);
			}

		});
		
		changeComponentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				new ChangeComponentWindow(null);
			}

		});
	}
}
