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
		this.setVisible(true);
	}
}
