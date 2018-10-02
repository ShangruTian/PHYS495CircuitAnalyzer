package circuit_gui;
import java.awt.*;
import javax.swing.*;
import circuit_logic.*;

public class StartupWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSlider voltageSlider;
	private JSlider frequencySlider;
	
	private JLabel voltageLabel;
	private JLabel frequencyLabel;
	private JLabel impedanceLabel;
	private JLabel amplitudeLabel;
	private JLabel angleLabel;
	private JLabel currNodeLabel;
	
	private JButton addSingleComponent;
	private JButton deleteSingleComponent;
	private JButton addParallelComponents;
	private JButton deleteParallelComponents;
	private JButton viewAllButton;
	private JButton editBranchButton;
	private JButton calculateButton;
	
	static final int MIN_FREQ = 0;
	static final int MAX_FREQ = 100000;
	static final int DEFAULT_FREQ = 1000;
	
	static final int MIN_VOLTAGE = 1;
	static final int MAX_VOLTAGE = 100;
	static final int DEFAULT_VOLTAGE = 5;
	

	public static void main(String[] args) {
		System.out.println("start" + 0);
		StartupWindow sw = new StartupWindow();
	}
	
	public StartupWindow() {
		super("PHYS 495 Circuit Analyzer");
		setSize(720,720);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		initComponents();
		
		//this.setVisible(true);
	}
	
	private void initComponents() {
		voltageSlider = new JSlider(JSlider.HORIZONTAL,MIN_VOLTAGE,MAX_VOLTAGE,DEFAULT_VOLTAGE);
		frequencySlider = new JSlider(JSlider.HORIZONTAL,MIN_FREQ,MAX_FREQ,DEFAULT_FREQ);
		
		voltageSlider.setMinorTickSpacing(10);
		frequencySlider.setMinorTickSpacing(1);
		
		voltageSlider.setMajorTickSpacing(10);
		frequencySlider.setMajorTickSpacing(10000);
		
		voltageSlider.setPaintTicks(true);
		frequencySlider.setPaintTicks(false);
		
		voltageSlider.setPaintLabels(true);
		frequencySlider.setPaintLabels(false);
		
		addSingleComponent = new JButton("Add single component");
		deleteSingleComponent = new JButton("Delete single component");
		addParallelComponents = new JButton("Add a parallel section");
		deleteParallelComponents = new JButton("Delete a parallel section");
		viewAllButton = new JButton("View all components");
		editBranchButton = new JButton("Edit current parallel section");
		calculateButton = new JButton("Calculate impedance");
		
		frequencyLabel = new JLabel("Frequency: 1000Hz",JLabel.CENTER);
		voltageLabel = new JLabel("Peak to peak voltage: 5V",JLabel.CENTER);
		currNodeLabel = new JLabel("Current node:");
		
		
		impedanceLabel = new JLabel("Impedance:",JLabel.CENTER);
		amplitudeLabel = new JLabel("Current Amplitude:",JLabel.CENTER);
		angleLabel = new JLabel("Phase Angle:",JLabel.CENTER);
		
		
	}
	
	private void createGUI() {
		
	}
}
