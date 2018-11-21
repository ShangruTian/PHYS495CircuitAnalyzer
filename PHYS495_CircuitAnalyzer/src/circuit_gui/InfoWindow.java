package circuit_gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1499443470506957853L;
	private JPanel mainPanel;
	private JTextArea text;
	private JScrollPane sp;
	
	public InfoWindow() {
		setSize(640,200);
		setLocation(300,300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initComponents();
		createGUI();
		this.setVisible(true);
	}
	
	private void initComponents() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		text = new JTextArea();
		text.setLineWrap(true);
		
		StringBuilder firstTip = new StringBuilder();
		firstTip.append("Notes for using this program:");
		firstTip.append('\n');
		text.append(firstTip.toString());
		
		StringBuilder secondTip = new StringBuilder();
		secondTip.append("1)Opening a new window will disable all buttons in the main window. To re-enable them, close your current window.");
		secondTip.append('\n');
		text.append(secondTip.toString());
		
		StringBuilder thirdTip = new StringBuilder();
		thirdTip.append("2)The program's calculation is based everything you inserted in the program by default. If you wish to calculate only part of the circuit, you can set the output terminal before any section except the section right after the input.");
		thirdTip.append('\n');
		text.append(thirdTip.toString());
		
		StringBuilder forthTip = new StringBuilder();
		forthTip.append("3)The program can only plot when your min frequency is smaller than the max frequency. To zoom in on a certain part, drag your mouse from upper left to lower right across that part. To zoom out, drag your mouse from lower right to upper left.");
		forthTip.append('\n');
		text.append(forthTip.toString());
		
		StringBuilder fifthTip = new StringBuilder();
		fifthTip.append("4)Use the view/edit parallel section button to view the components of any branch in the selected parallel secion. You can also delete or add branches in that window.");
		text.append(fifthTip.toString());
		sp = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	private void createGUI() {
		mainPanel.add(sp);
		add(mainPanel);
	}
}
