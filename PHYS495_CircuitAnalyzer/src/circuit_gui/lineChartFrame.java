package circuit_gui;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

public class lineChartFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3406481835695901468L;
	public lineChartFrame(String applicationTitle) {
		super(applicationTitle);
	}
	public void scp(ChartPanel cp) {
		setContentPane(cp);
	}
}
