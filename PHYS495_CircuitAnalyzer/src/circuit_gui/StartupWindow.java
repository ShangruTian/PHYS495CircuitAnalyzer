package circuit_gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;


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
	private JSlider minFrequencySlider;
	
	private JLabel frequencyLabel;
	private JLabel minFrequencyLabel;
	private JTextArea realImpedanceLabel;
	private JTextArea imaginaryImpedanceLabel;
	private JTextArea angleLabel;
	private JTextArea leadLabel;
	
	private JScrollPane sp1;
	private JScrollPane sp2;
	private JScrollPane sp3;
	private JScrollPane sp4;
	
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
	private JPanel minSliderPanel;
	private JPanel displayPanel;
	private JPanel anglePanel;
	private JPanel impedancePanel;
	private NumberFormat formatter;
	
	private Circuit circuit;
	
	private boolean canCalculate;
	
	static final int MIN_FREQ = 1;
	static final int MAX_FREQ = 100000;
	static final int DEFAULT_FREQ = 10000;
	
	static final int MIN_VOLTAGE = 1;
	static final int MAX_VOLTAGE = 100;
	static final int DEFAULT_VOLTAGE = 5;
	

	public static void main(String[] args) {
		//System.out.println("debug:");
		StartupWindow sw = new StartupWindow();
		sw.showInfoWindow();
	}
	
	
	public StartupWindow() {
		super("PHYS 495 Circuit Analyzer");
		setSize(900,450);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initComponents();
		createGUI();
		addActionListeners();
		canCalculate = false;
		this.setVisible(true);
	}
	
	public void showInfoWindow() {
		new InfoWindow().setVisible(true);
	}
	
	public boolean frequencyIsCorrect() {
		return minFrequencySlider.getValue() < frequencySlider.getValue();
	}
	
	private void initComponents() {
		
		formatter = new DecimalFormat("0.##E0");
		frequencySlider = new JSlider(JSlider.HORIZONTAL,MIN_FREQ,MAX_FREQ,DEFAULT_FREQ);
		minFrequencySlider = new JSlider(JSlider.HORIZONTAL,MIN_FREQ,MAX_FREQ,MIN_FREQ);
		frequencySlider.setMinorTickSpacing(1000);
		minFrequencySlider.setMinorTickSpacing(1000);
		
		frequencySlider.setMajorTickSpacing(10000);
		minFrequencySlider.setMajorTickSpacing(10000);
		
		frequencySlider.setPaintTicks(false);
		minFrequencySlider.setPaintTicks(false);
		
		frequencySlider.setPaintLabels(false);
		minFrequencySlider.setPaintLabels(false);
		
		

		
		addSingleComponent = new JButton("Add single component");
		deleteSingleComponent = new JButton("Delete single component");
		addParallelComponents = new JButton("Add a parallel section");
		deleteParallelComponents = new JButton("Delete a parallel section");
		changeComponentButton = new JButton("Change a component");
		viewCircuitButton = new JButton("View entire circuit");
		editBranchButton = new JButton("View/Edit parallel section");
		calculateButton = new JButton("Plot voltage and phase angle");

		
		frequencyLabel = new JLabel("Max frequency on plot(also used for calculation): "+ frequencySlider.getValue() + "Hz",JLabel.CENTER);
		minFrequencyLabel = new JLabel("Min frequency on plot: "+ minFrequencySlider.getValue() + "Hz",JLabel.CENTER);
		
		
		realImpedanceLabel = new JTextArea("Impedance:");
		realImpedanceLabel.setLineWrap(true);
		realImpedanceLabel.setEditable(false);
		sp1 = new JScrollPane(realImpedanceLabel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		imaginaryImpedanceLabel = new JTextArea("Voltage percentage:");
		imaginaryImpedanceLabel.setLineWrap(true);
		imaginaryImpedanceLabel.setEditable(false);
		sp2 = new JScrollPane(imaginaryImpedanceLabel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		angleLabel = new JTextArea("Phase Angle:");
		angleLabel.setLineWrap(true);
		angleLabel.setEditable(false);
		sp3 = new JScrollPane(angleLabel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		leadLabel = new JTextArea("Lead:");
		leadLabel.setLineWrap(true);
		leadLabel.setEditable(false);
		sp4 = new JScrollPane(leadLabel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,1));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,2));
		
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(1,2));
		
		minSliderPanel = new JPanel();
		minSliderPanel.setLayout(new GridLayout(1,2));
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,4));
		
		anglePanel = new JPanel();
		anglePanel.setLayout(new GridLayout(2,1));
		
		impedancePanel = new JPanel();
		impedancePanel .setLayout(new GridLayout(2,1));
		
		
		
		frequencySlider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
        		calculateButton.setEnabled(frequencyIsCorrect());
	        	frequencyLabel.setText("Max frequency on plot(also used for calculation): "+ frequencySlider.getValue() + "Hz");
	        	if(frequencyIsCorrect()) {
	        		calculateButton.setText("Plot voltage and phase angle");
	        		if(canCalculate) {
		        		canCalculate = false;
		        		JSlider js = (JSlider)ce.getSource();
		        		double frequency = (double)js.getValue();
		        		ComplexNumber imp =circuit.calculateTotalImpedance(frequency);
		        		double temp = Math.sqrt(imp.getRealPart() * imp.getRealPart() + imp.getImaginaryPart() * imp.getImaginaryPart());
		        		realImpedanceLabel.setText("Impedance: " + formatter.format(temp));
		        		imaginaryImpedanceLabel.setText("Voltage percentage: " + formatter.format(circuit.calculateVoltagePercentage(frequency)));
		        		double ang = circuit.calculatePhaseAngle(frequencySlider.getValue());
		        		angleLabel.setText("Phase angle(degree): " + formatter.format((float)ang * 57.2958));
		        		leadLabel.setText(circuit.findLeadingVector(frequency));
		        		canCalculate = true;
		        		//System.out.println("output: " + imp.getRealPart() + " " + imp.getImaginaryPart());
		        	}
	        	}
	        	else {
	        		calculateButton.setText("Please adjust min/max frequency");
	        	}
	        }
	    });
		
		minFrequencySlider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
        		calculateButton.setEnabled(frequencyIsCorrect());
	        	minFrequencyLabel.setText("Min frequency on plot: "+ minFrequencySlider.getValue() + "Hz");
	        	if(frequencyIsCorrect()) {
	        		calculateButton.setText("Plot voltage and phase angle");
	        	}
	        	else {
	        		calculateButton.setText("Please adjust min/max frequency");
	        	}
	        }
	    });
		
		this.circuit = new Circuit();
		circuit.setWindow(this);
		calculateButton.setEnabled(circuit.hasOutput());
	}
	
	public void reenableButtons() {
		addSingleComponent.setEnabled(true);
		deleteSingleComponent.setEnabled(true);
		addParallelComponents.setEnabled(true);
		deleteParallelComponents.setEnabled(true);
		changeComponentButton.setEnabled(true);
		viewCircuitButton.setEnabled(true);
		editBranchButton.setEnabled(true);
		calculateButton.setEnabled(true);
		
	}
	
	public void disableButtons() {
		addSingleComponent.setEnabled(false);
		deleteSingleComponent.setEnabled(false);
		addParallelComponents.setEnabled(false);
		deleteParallelComponents.setEnabled(false);
		changeComponentButton.setEnabled(false);
		viewCircuitButton.setEnabled(false);
		editBranchButton.setEnabled(false);
		calculateButton.setEnabled(false);
	}
	
	public void setCalculateButton() {
		calculateButton.setEnabled(circuit.hasOutput());
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
		
		minSliderPanel.add(minFrequencyLabel);
		minSliderPanel.add(minFrequencySlider);
		mainPanel.add(minSliderPanel);
		mainPanel.add(sliderPanel);
		
		impedancePanel.add(sp1);
		impedancePanel.add(sp2);
		
		displayPanel.add(changeComponentButton);
		displayPanel.add(calculateButton);
		displayPanel.add(impedancePanel);
		
		anglePanel.add(sp4);
		anglePanel.add(sp3);
		displayPanel.add(anglePanel);
		
		mainPanel.add(displayPanel);
		
		add(mainPanel);
		
	}
	
	public void addActionListeners() {
		addSingleComponent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				disableButtons();
				realImpedanceLabel.setText("Impedance: ");
				imaginaryImpedanceLabel.setText("Voltage percentage");
				angleLabel.setText("Phase angle(degree): ");
				leadLabel.setText("Lead: ");
				new AddSingleComponentWindow(circuit);
			}

		});
		
		deleteSingleComponent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				disableButtons();
				realImpedanceLabel.setText("Impedance: ");
				imaginaryImpedanceLabel.setText("Voltage percentage");
				angleLabel.setText("Phase angle(degree): ");
				leadLabel.setText("Lead: ");
				new DeleteSingleComponentWindow(circuit);
			}

		});
		
		addParallelComponents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				disableButtons();
				realImpedanceLabel.setText("Impedance: ");
				imaginaryImpedanceLabel.setText("Voltage percentage:");
				angleLabel.setText("Phase angle(degree): ");
				leadLabel.setText("Lead: ");
				new AddParallelSectionWindow(circuit);
			}

		});
		deleteParallelComponents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				disableButtons();
				realImpedanceLabel.setText("Impedance: ");
				imaginaryImpedanceLabel.setText("Voltage percentage:");
				angleLabel.setText("Phase angle(degree): ");
				leadLabel.setText("Lead: ");
				new DeleteParallelSectionWindow(circuit);
			}

		});
		
		viewCircuitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				disableButtons();
				realImpedanceLabel.setText("Impedance: ");
				imaginaryImpedanceLabel.setText("Voltage percentage:");
				angleLabel.setText("Phase angle(degree): ");
				leadLabel.setText("Lead: ");
				new ViewCircuitWindow(circuit);
				if(circuit.canDraw() != -1) {
					new DrawCircuitWindow(circuit);
				}
			}

		});
		
		editBranchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				disableButtons();
				realImpedanceLabel.setText("Impedance: ");
				imaginaryImpedanceLabel.setText("Voltage percentage:");
				angleLabel.setText("Phase angle(degree): ");
				leadLabel.setText("Lead: ");
				new EditBranchWindow(circuit);
			}

		});
		
		changeComponentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canCalculate = false;
				disableButtons();
				realImpedanceLabel.setText("Impedance: ");
				imaginaryImpedanceLabel.setText("Voltage percentage:");
				angleLabel.setText("Phase angle(degree): ");
				leadLabel.setText("Lead: ");
				new ChangeComponentWindow(circuit);
			}

		});
		
		calculateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!circuit.hasOutput()) {
					realImpedanceLabel.setText("Impedance:(no output) ");
					imaginaryImpedanceLabel.setText("Voltage percentage:(no output)");
					angleLabel.setText("Phase angle:(no output)");
					leadLabel.setText("Lead:(no output)");
				}
				else {
					canCalculate = true;
					ComplexNumber imp =circuit.calculateTotalImpedance(frequencySlider.getValue());
					double temp = Math.sqrt(imp.getRealPart() * imp.getRealPart() + imp.getImaginaryPart() * imp.getImaginaryPart());
					realImpedanceLabel.setText("Impedance: " + formatter.format(temp));
	        		imaginaryImpedanceLabel.setText("Voltage percentage: " + formatter.format(circuit.calculateVoltagePercentage(frequencySlider.getValue())));
	        		double ang = circuit.calculatePhaseAngle(frequencySlider.getValue());
	        		angleLabel.setText("Phase angle(degree): " + formatter.format((float)ang * 57.2958));
	        		leadLabel.setText(circuit.findLeadingVector(frequencySlider.getValue()));
	        		lineChartFrame lcf = circuit.createPlot(minFrequencySlider.getValue(),frequencySlider.getValue());
	        		lcf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        		lineChartFrame lcf2 = circuit.createAnglePlot(minFrequencySlider.getValue(),frequencySlider.getValue());
	        		lcf2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
			}

		});
	}
}
