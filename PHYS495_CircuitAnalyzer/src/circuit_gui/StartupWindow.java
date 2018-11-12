package circuit_gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import circuit_logic.*;

public class StartupWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JSlider voltageSlider;
	private JSlider frequencySlider;
	
	//private JLabel voltageLabel;
	private JLabel frequencyLabel;
	private JLabel impedanceLabel;
	//private JLabel amplitudeLabel;
	private JLabel angleLabel;
	//private JLabel currNodeLabel;
	
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
	
	private Circuit circuit;
	
	static final int MIN_FREQ = 0;
	static final int MAX_FREQ = 100000;
	static final int DEFAULT_FREQ = 10000;
	
	static final int MIN_VOLTAGE = 1;
	static final int MAX_VOLTAGE = 100;
	static final int DEFAULT_VOLTAGE = 5;
	

	public static void main(String[] args) {
		String test = "Parallel Section 0";
		System.out.println("end" + test.charAt(test.length()-1));
		ChangeComponentWindow sw = new ChangeComponentWindow(null);
	}
	
	public StartupWindow() {
		super("PHYS 495 Circuit Analyzer");
		setSize(720,540);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initComponents();
		createGUI();
		
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
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,1));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,2));
		
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(1,2));
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,4));
		
		//this.circuit = new Circuit();
		
		frequencySlider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	        	frequencyLabel.setText("Frequency: "+ frequencySlider.getValue() + "Hz");
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
		displayPanel.add(angleLabel);
		mainPanel.add(displayPanel);
		
		add(mainPanel);
		
	}
}
