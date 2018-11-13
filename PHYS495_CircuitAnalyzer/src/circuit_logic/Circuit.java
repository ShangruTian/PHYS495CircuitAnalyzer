package circuit_logic;

import java.util.HashMap;
import java.util.Vector;

public class Circuit {
	private CircuitNode start0;
	private CircuitNode end0;
	private CircuitNode current;
	private CircuitNode outputStartingNode;
	private static int JunctionNum;
	private HashMap<String,CircuitNode> CircuitMap;
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
		JunctionNum ++;
	}
	
	public HashMap<String,CircuitNode> getMap(){
		return CircuitMap;
	}
	
	public void setOutput(CircuitNode node) {
		this.outputStartingNode = node;
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
		else if(current.next().getComponent().getType() == "JunctionEnd") {
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
		
		else if(toDelete.next().getComponent().getType() == "JunctionEnd") {
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
		current.next().setPrev(end);
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
	
	public void addFirstNodeToBranch(String JunctionEndNum,int branch,String name,CircuitComponent c) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		setCurrentNode(end.prev().getChildren().get(branch));
		addSingleNewNode(name,c);
	}
	
	public void removeParallelSection(String JunctionEndNum) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		CircuitNode prev = end.prev();
		CircuitNode next = end.next();
		prev.setNext(next);
		next.setPrev(prev);
		Vector<CircuitNode> children = prev.getChildren();
		for(int i = 0;i < children.size();++i) {
			CircuitNode start = children.get(i);
			start = start.next();
			while(start != end) {
				if(start.getComponent().getType() == "JunctionEnd") {
					removeParallelSection(Double.toString(start.getComponent().getValue()));
					start = start.next();
				}
				else {
					CircuitMap.remove(start.getName());
				}
			}
		}
		prev.removeChildren();
		CircuitMap.remove("end" + JunctionEndNum);
	}
	
	public void removeSingleBranch(String JunctionEndNum,int toRemove) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		CircuitNode start = end.prev().getChildren().get(toRemove);
		start = start.next();
		while(start != end) {
			if(start.getComponent().getType() == "JunctionEnd") {
				removeParallelSection(Double.toString(start.getComponent().getValue()));
				start = start.next();
			}
			else {
				CircuitMap.remove(start.getName());
			}
		}
		end.prev().removeBranch(toRemove);
	}
	
	public void addSingleBranch(String JunctionEndNum) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		float value = end.prev().getChildren().get(0).getComponent().getValue();
		CircuitNode newNode = new CircuitNode(new JunctionStart(value));
		newNode.setNext(end);
		newNode.setPrev(end.prev());
		end.prev().getChildren().addElement(newNode);
	}
	
	//only called when curr is a junction end
	public ComplexNumber calculateParallelImpedance(CircuitNode node,double frequency){
		CircuitNode prev = node.prev();
		JunctionEnd je = (JunctionEnd) node.getComponent();
		boolean isEmpty = true;
		for(int i = 0;i < prev.getChildren().size();++i) {
			CircuitNode start = prev.getChildren().get(i);
			if(start.next() == node) {}
			else {
				isEmpty = false;
				ComplexNumber branchImpedance = new ComplexNumber(0,0);
				while(start != node) {
					if(start.getComponent().getType()=="JunctionEnd") {
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
		ComplexNumber ans = new ComplexNumber(0,0);
		CircuitNode curr = outputStartingNode;
		while(curr != end0) {
			if(curr.getComponent().getType() == "JunctionEnd") {
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
	
	public double calculatePhaseAngle(double frequency){
		ComplexNumber outputImpedance = calculateTotalImpedance(frequency);
		CircuitNode curr = start0.next();
		ComplexNumber inputImpedance = new ComplexNumber(0,0);
		while(curr != outputStartingNode) {
			if(curr.getComponent().getType() == "JunctionEnd") {
				inputImpedance.add(calculateParallelImpedance(curr,frequency));
				curr = curr.next();
			}
			else {
				inputImpedance.add(curr.getComponent().calculateImpedance(frequency));
				curr = curr.next();
			}
		}
		
		inputImpedance.add(outputImpedance);
		double a = inputImpedance.getRealPart() * outputImpedance.getRealPart() + inputImpedance.getImaginaryPart() + outputImpedance.getImaginaryPart();
		double b = Math.sqrt(inputImpedance.getRealPart() * inputImpedance.getRealPart() + inputImpedance.getImaginaryPart() * inputImpedance.getImaginaryPart());
		double c = Math.sqrt(outputImpedance.getRealPart() * outputImpedance.getRealPart() + outputImpedance.getImaginaryPart() * outputImpedance.getImaginaryPart());
		double cosineValue = a/(b * c);
		
		return Math.acos(cosineValue);
		
		
		
	}
	
	public Vector<String> viewCircuit(){
		Vector<String> result = new Vector<String>();
		for(CircuitNode node = start0;node != end0; node = node.next()){
			if(node == start0) {
				result.add("Circuit Start");
			}
			
			else if(node.getComponent().getType() == "JunctionEnd") {
				result.add("Parallel Section " + node.getComponent().getValue());
			}
			
			else if(node == outputStartingNode) {
				result.add("(Output terminal)");
				result.add(node.getName());
			}
			else {
				result.add(node.getName());
			}
		}
		result.add("Circuit End");
		
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
			if(start.getComponent().getType() == "JunctionEnd") {
				result.add("Parallel Section " + start.getComponent().getValue());
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
