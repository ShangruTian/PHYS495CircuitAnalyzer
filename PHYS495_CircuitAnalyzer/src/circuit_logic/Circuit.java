package circuit_logic;

import java.util.HashMap;
import java.util.Vector;


import circuit_gui.StartupWindow;
import circuit_gui.lineChartFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class Circuit {
	private CircuitNode start0;
	private CircuitNode end0;
	private CircuitNode current;
	private CircuitNode outputStartingNode;
	private CircuitNode outputEndingNode;
	private static int JunctionNum;
	private HashMap<String,CircuitNode> CircuitMap;
	private StartupWindow sw;
	public Circuit() {
		JunctionNum = 0;
		start0 = new CircuitNode(new JunctionStart(JunctionNum));
		end0 = new CircuitNode(new JunctionEnd(JunctionNum));
		CircuitMap = new HashMap<String,CircuitNode>();
		start0.setNext(end0);
		end0.setPrev(start0);
		current = start0;
		start0.setName("Circuit Start");
		end0.setName("Circuit End");
		CircuitMap.put("start0", start0);
		CircuitMap.put("end0", end0);
		outputStartingNode = start0;
		JunctionNum ++;
	}
	

	
	public lineChartFrame createPlot(int minFrequency, int maxFrequency) {
		int sampleSize = maxFrequency - minFrequency + 1;
		double[] freq = new double[sampleSize];
		for(int i = 0;i < sampleSize;++i) {
			double temp = minFrequency + i;
			freq[i] = temp;
		}
		double[] percentage = new double[sampleSize];
		for(int j = 0;j < sampleSize;++j) {

			percentage[j] = calculateVoltagePercentage(freq[j]);
		}
		XYSeries series = new XYSeries("Voltage percentage vs. Frequency");
		for(int k = 0;k < sampleSize;++k) {
			series.add(freq[k],percentage[k]);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Voltage percentage vs. Frequency", "Frequency", "Voltage percentage", dataset,PlotOrientation.VERTICAL,true,true,false);
		chart.getXYPlot().getRangeAxis().setAutoRange(true);
		ChartPanel cp = new ChartPanel(chart);
		lineChartFrame lcf = new lineChartFrame("Voltage percentage vs. Frequency");
		lcf.scp(cp);
		lcf.pack();
		RefineryUtilities.centerFrameOnScreen(lcf);
		lcf.setVisible(true);
		return lcf;
	}
	
	public lineChartFrame createAnglePlot(int minFrequency, int maxFrequency) {
		int sampleSize = maxFrequency - minFrequency + 1;
		double[] freq = new double[sampleSize];
		for(int i = 0;i < sampleSize;++i) {
			double temp = minFrequency + i;
			freq[i] = temp;
		}
		double[] angle = new double[sampleSize];
		for(int j = 0;j < sampleSize;++j) {
			angle[j] = calculatePhaseAngle(freq[j]) * 57.2958;
		}
		XYSeries series = new XYSeries("Phase angle vs. Frequency");
		for(int k = 0;k < sampleSize;++k) {
			series.add(freq[k],angle[k]);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Phase angle vs. Frequency", "Frequency", "Phase angle", dataset,PlotOrientation.VERTICAL,true,true,false);
		chart.getXYPlot().getRangeAxis().setAutoRange(true);
		ChartPanel cp = new ChartPanel(chart);
		lineChartFrame lcf = new lineChartFrame("Phase angle vs. Frequency");
		lcf.scp(cp);
		lcf.pack();
		RefineryUtilities.centerFrameOnScreen(lcf);
		lcf.setLocation(500, 200);
		lcf.setVisible(true);
		return lcf;
	}
	
	private boolean pureCapacitive() {
		boolean res = true;
		for(String s: CircuitMap.keySet()) {
			CircuitComponent component = CircuitMap.get(s).getComponent();
			if(component.getType().equals("JunctionEnd")||component.getType().equals("JunctionStart")) {
				
			}
			else if(!component.getType().equals("Capacitor")) {
				res = false;
			}
		}
		return res;
	}
	
	public int canDraw() {
		//if can't, return -1
		//else return the length of the circuit
		CircuitNode start = start0.next();
		int res = 1;
		while(start != end0) {
			if(start.getComponent().getType().equals("JunctionEnd")) {
				CircuitNode temp = start.prev();
				if(temp.getChildren().size() < 4 && temp.getChildren().size() > 1) {
					for(CircuitNode node: temp.getChildren()) {
						node = node.next();
						if(!node.isBranchStart() && node.next() == start) {
							++res;
						}
						else {
							return -1;
						}
					}
					start = start.next();
				}
				else {
					return -1;
				}
			}
			else {
				++res;
				start = start.next();
			}
		}
		++res;
		return res;
	}
	
	public void setWindow(StartupWindow s) {
		this.sw = s;
	}
	
	public void windowEnableButtons() {
		sw.reenableButtons();
	}
	
	public StartupWindow getWindow() {
		return this.sw;
	}
	public HashMap<String,CircuitNode> getMap(){
		return CircuitMap;
	}
	
	public void setOutputStart(CircuitNode node) {
		this.outputStartingNode = node;
	}
	
	public void setOutputEnd(CircuitNode node) {
		this.outputEndingNode = node;
	}
	
	public boolean hasOutput() {
		return outputStartingNode != null && outputEndingNode != null;
	}
	
	public CircuitNode getOutputStart() {
		return outputStartingNode;
	}
	
	public CircuitNode getOutputEnd() {
		return outputEndingNode;
	}
	
	
	
	public boolean nameExist(String name) {
		return CircuitMap.containsKey(name);
	}
	
	public void setCurrentNode(CircuitNode node) {
		this.current = node;
	}
	
	public CircuitNode findNode(String name) {
		return CircuitMap.get(name);
	}
	
	public void sameComponentChangeValue(String name,CircuitComponent c) {
		CircuitMap.get(name).changeComponent(c);
	}
	
	public void changeNodeToDifferentComponent(String oldName,String newName, CircuitComponent c) {
		CircuitNode oldNode = CircuitMap.get(oldName);
		setCurrentNode(oldNode);
		addSingleNewNode(newName,c);
		if(oldNode  == outputStartingNode ) {
			setOutputStart(findNode(newName));
		}
		else if(oldNode == outputEndingNode) {
			setOutputEnd(findNode(newName));
		}
		deleteSingleNode(oldName);
		setCurrentNode(CircuitMap.get(newName));
	}
	
	//must use set current node method before calling this method
	public void addSingleNewNode(String name,CircuitComponent c) {
		CircuitNode newNode = new CircuitNode(c);
		newNode.setName(name);
		newNode.setPrev(current);
		//newNode.setNext(current.next());
		if(current.isBranchStart()) {
			for(int i = 0;i < current.getChildren().size();++i) {
				current.getChildren().get(i).setPrev(newNode);
			}
			newNode.addChildren(current.getChildren());
			current.removeChildren();
			newNode.setNext(current.next());
			current.next().setPrev(newNode);
			current.setNext(newNode);
			CircuitMap.put(name, newNode);
		}
		else if(current.next().getComponent().getType().equals("JunctionEnd")) {
			newNode.setNext(current.next());
			current.setNext(newNode);
			CircuitMap.put(name, newNode);
		}
		else {
			newNode.setNext(current.next());
			current.next().setPrev(newNode);
			current.setNext(newNode);
			CircuitMap.put(name, newNode);
		}
		
	}
	
	public void deleteSingleNode(String name) {
		CircuitNode toDelete = CircuitMap.get(name);
		if(toDelete == outputStartingNode) {
			outputStartingNode = null;
			outputEndingNode = null;
		}
		else if(toDelete == outputEndingNode) {
			outputStartingNode = null;
			outputEndingNode = null;
		}
		CircuitNode toDeletePrev = toDelete.prev();
		
		//if the node is right before a parallel section
		if(toDelete.isBranchStart()) {
			Vector<CircuitNode> children = toDelete.getChildren();
			for(int i = 0;i < children.size();++i) {
				children.get(i).setPrev(toDeletePrev);
			}
			toDeletePrev.addChildren(children);
			toDeletePrev.setNext(toDelete.next());
			toDelete.next().setPrev(toDeletePrev);
			CircuitMap.remove(name);
		}
		
		else if(toDelete.next().getComponent().getType().equals("JunctionEnd")) {
			toDeletePrev.setNext(toDelete.next());
			CircuitMap.remove(name);
		}
		
		else {
			toDelete.next().setPrev(toDeletePrev);
			toDeletePrev.setNext(toDelete.next());
			CircuitMap.remove(name);
		}
	}
	
	//must use set current node before calling this method
	public void addParallelNodes(int numOfBranches) {
		String endName = "end" + JunctionNum;
		CircuitNode end = new CircuitNode(new JunctionEnd(JunctionNum));
		end.setName(endName);
		end.setPrev(current);
		end.setNext(current.next());
		if(!current.next().getComponent().getType().equals("JunctionEnd")) {
			current.next().setPrev(end);
		}
		current.setNext(end);
		CircuitMap.put(endName, end);
		Vector<CircuitNode> branches = new Vector<CircuitNode>();
		for(int i = 0;i < numOfBranches; ++i) {
			CircuitNode start = new CircuitNode(new JunctionStart(JunctionNum));
			start.setNext(end);
			start.setPrev(current);
			branches.add(start);
		}
		current.addChildren(branches);
		JunctionNum ++;
	}
	
	public int getJunctionNum() {
		return JunctionNum;
	}
	
	public void addFirstNodeToBranch(String JunctionEndNum,int branch,String name,CircuitComponent c) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		setCurrentNode(end.prev().getChildren().get(branch));
		addSingleNewNode(name,c);
	}
	
	public void removeParallelSection(String JunctionEndNum) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		if(end == outputStartingNode) {
			outputStartingNode = null;
		}
		else if(end == outputEndingNode) {
			outputEndingNode = null;
		}
		CircuitNode prev = end.prev();
		CircuitNode next = end.next();
		prev.setNext(next);
		if(next.getComponent().getType().equals("JunctionEnd")) {
			double identification = next.getComponent().getValue();
			CircuitNode temp = end;
			while(!temp.getComponent().getType().equals("JunctionStart")) {
				temp = temp.prev();
			}
			if(temp.getComponent().getValue() != identification) {
				next.setPrev(prev);
			}
		}
		else {
			next.setPrev(prev);
		}
		Vector<CircuitNode> children = prev.getChildren();
		for(int i = 0;i < children.size();++i) {
			CircuitNode start = children.get(i);
			start = start.next();
			while(start != end) {
				if(start.getComponent().getType().equals("JunctionEnd")) {
					String num = start.getName().substring(3);
					start = start.next();
					removeParallelSection(num);
				}
				else {
					String name = start.getName();
					start = start.next();
					deleteSingleNode(name);
				}
			}
		}
		prev.removeChildren();
		if(end.isBranchStart()) {
			prev.addChildren(end.getChildren());
			for(CircuitNode c: prev.getChildren()) {
				c.setPrev(prev);
			}
		}
		CircuitMap.remove("end" + JunctionEndNum);
	}
	
	public void removeSingleBranch(String JunctionEndNum,int toRemove) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		CircuitNode start = end.prev().getChildren().get(toRemove);
		start = start.next();
		while(start != end) {
			if(start.getComponent().getType().equals("JunctionEnd")) {
				String num = start.getName().substring(3);
				start = start.next();
				removeParallelSection(num);
			}
			else {
				String name = start.getName();
				start = start.next();
				deleteSingleNode(name);
			} 
		}
		end.prev().removeBranch(toRemove);
	}
	
	public void addSingleBranch(String JunctionEndNum) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		double value = end.prev().getChildren().get(0).getComponent().getValue();
		CircuitNode newNode = new CircuitNode(new JunctionStart(value));
		newNode.setNext(end);
		newNode.setPrev(end.prev());
		end.prev().addBranch(newNode);
	}
	
	//only called when curr is a junction end
	public ComplexNumber calculateParallelImpedance(CircuitNode node,double frequency){
		CircuitNode prev = node.prev();
		JunctionEnd je = (JunctionEnd) node.getComponent();
		je.reset();
		boolean isEmpty = true;
		for(int i = 0;i < prev.getChildren().size();++i) {
			CircuitNode start = prev.getChildren().get(i);
			if(start.next() == node) {}
			else {
				isEmpty = false;
				ComplexNumber branchImpedance = new ComplexNumber(0,0);
				while(start != node) {
					if(start.getComponent().getType().equals("JunctionEnd")) {
						branchImpedance.add(calculateParallelImpedance(start,frequency));
						start = start.next();
					}
					else {
						branchImpedance.add(start.getComponent().calculateImpedance(frequency));
						start = start.next();
					}
				}
				je.updateImpedance(branchImpedance);
			}
		}
		if(isEmpty) return new ComplexNumber(0,0);
		else return je.calculateImpedance(frequency);
		
	}
	
	public ComplexNumber calculateTotalImpedance(double frequency) {
		if(pureCapacitive() && frequency == 0) {
			return new ComplexNumber(0,Integer.MIN_VALUE);
		}
		else {
			ComplexNumber ans = new ComplexNumber(0,0);
			CircuitNode curr = outputStartingNode;
			while(curr != outputEndingNode.next()) {
				if(curr.getComponent().getType().equals("JunctionEnd")) {
					ans.add(calculateParallelImpedance(curr,frequency));
					curr = curr.next();
				}
				else {
					ans.add(curr.getComponent().calculateImpedance(frequency));
					curr = curr.next();
				}
			}
			
			return ans;
		}
	}
	
	public double calculateVoltagePercentage(double frequency) {
	
			ComplexNumber sectionImpedance = calculateTotalImpedance(frequency);
			CircuitNode curr = start0;
			ComplexNumber temp = new ComplexNumber (0,0);
			while(curr != end0) {
				if(curr.getComponent().getType().equals("JunctionEnd")) {
					temp.add(calculateParallelImpedance(curr,frequency));
					curr = curr.next();
				}
				else {
					temp.add(curr.getComponent().calculateImpedance(frequency));
					curr = curr.next();
				}
			}
			double a = sectionImpedance.getRealPart();
			double b = sectionImpedance.getImaginaryPart();
			double c = temp.getRealPart();
			double d = temp.getImaginaryPart();
			
			double num = Math.sqrt(a*a + b*b);
			double den = Math.sqrt(c*c + d*d);
			
			return num/den;

	}
	
	public double calculatePhaseAngle(double frequency){
		CircuitNode curr = start0;
		ComplexNumber inputImpedance = new ComplexNumber(0,0);
		while(curr != end0) {
			if(curr.getComponent().getType().equals("JunctionEnd") && curr!= findNode("end0")) {
				inputImpedance.add(calculateParallelImpedance(curr,frequency));
				curr = curr.next();
			}
			else {
				inputImpedance.add(curr.getComponent().calculateImpedance(frequency));
				curr = curr.next();
			}
		}

		
		if(inputImpedance.getRealPart() == 0) {
			if(inputImpedance.getImaginaryPart() > 0) {
				return 1.57079576513;
			}
			else if(inputImpedance.getImaginaryPart() == 0) return 0;
			else return -1.57079576513;
		}
		
		else return Math.atan(inputImpedance.getImaginaryPart()/inputImpedance.getRealPart());
		
		
		
		
	}
	
	public String findLeadingVector(double frequency) {
		double phaseAngle = calculatePhaseAngle(frequency);
		if(phaseAngle > 0) {
			return new String("Output leads input");
		}
		
		else if(phaseAngle < 0) {
			return new String("Input leads output");
		}
		
		else return new String("Input in phase with output");
	}
	
	public Vector<String> viewCircuit(){
		Vector<String> result = new Vector<String>();
		for(CircuitNode node = start0;node != end0; node = node.next()){
			if(node == start0) {
				result.add("Function generator");
				
			}
			
			else if(node.getComponent().getType().equals("JunctionEnd")) {
				if(node == outputStartingNode) {
					result.add("||");
				}
				result.add("Parallel Section " + (int)node.getComponent().getValue());
				if(node == outputEndingNode) {
					result.add("||");
				}
			}
			
			else if(node == outputEndingNode && node == outputStartingNode) {
				result.add("||");
				result.add(node.getName());
				result.add("||");
			}
			
			else if(node == outputStartingNode) {
				result.add("||");
				result.add(node.getName());

			}
			else if(node == outputEndingNode) {
				result.add(node.getName());
				result.add("||");
			}
			
			else {
				result.add(node.getName());
			}
		}
		result.add("Back to function generator");
		
		return result;
	}
	
	//assuming name is a branch end
	public Vector<String> viewBranch(String JunctionEndNum,int branch){
		Vector<String> result = new Vector<String>();
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		CircuitNode start = end.prev().getChildren().get(branch);
		result.add("Branch start");
		start = start.next();
		while(start != end) {
			if(start.getComponent().getType().equals("JunctionEnd")) {
				result.add("Parallel Section " + (int)start.getComponent().getValue());
				start = start.next();
			}
			
			else {
				result.add(start.getName());
				start = start.next();
			}
		}
		result.add("Branch End");
		return result;
	}
	
	
}
