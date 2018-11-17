package circuit_gui;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.ApplicationFrame;

public class lineChartFrame extends JFrame{
	public lineChartFrame(String applicationTitle) {
		super(applicationTitle);
	}
	public void scp(ChartPanel cp) {
		setContentPane(cp);
	}
}
