package circuit_logic;

import java.util.HashMap;
import java.util.Vector;

public class Circuit {
	private CircuitNode start0;
	private CircuitNode end0;
	private CircuitNode current;
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
		CircuitMap.put("start0", start0);
		CircuitMap.put("end0", end0);
		JunctionNum ++;
	}
	
	public HashMap<String,CircuitNode> getMap(){
		return CircuitMap;
	}
	
	public boolean nameExist(String name) {
		return CircuitMap.containsKey(name);
	}
	
	public void setCurrentNode(CircuitNode node) {
		this.current = node;
	}
	
	public void sameComponentChangeValue(String name,CircuitComponent c) {
		CircuitMap.get(name).changeComponent(c);
	}
	
	public void changeNodeToDifferentComponent(String oldName,String newName, CircuitComponent c) {
		CircuitNode oldNode = CircuitMap.get(oldName);
		CircuitNode newNode = new CircuitNode(c);
		newNode.setPrev(oldNode.prev());
	}
	
	public void addSingleNewNode(String name,CircuitComponent c) {
		CircuitNode newNode = new CircuitNode(c);
		newNode.setPrev(current);
		newNode.setNext(current.next());
		if(current.isBranchStart()) {
			for(int i = 0;i < current.getChildren().size();++i) {
				current.getChildren().get(i).setPrev(newNode);
			}
			newNode.addChildren(current.getChildren());
			current.removeChildren();
		}
		current.setNext(newNode);
		CircuitMap.put(name, newNode);
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
			CircuitMap.remove(name);
		}
		
		else {
			toDelete.next().setPrev(toDeletePrev);
			toDeletePrev.setNext(toDelete.next());
			CircuitMap.remove(name);
		}
	}
	
	public void addParallelNodes(int numOfBranches) {
		String endName = "end" + JunctionNum;
		CircuitNode end = new CircuitNode(new JunctionEnd(JunctionNum));
		end.setPrev(current);
		CircuitMap.put(endName, end);
		Vector<CircuitNode> branches = new Vector<CircuitNode>();
		for(int i = 0;i < numOfBranches; ++i) {
			CircuitNode start = new CircuitNode(new JunctionStart(JunctionNum));
			start.setNext(end);
			start.setPrev(current);
			branches.add(start);
		}
		current.addChildren(branches);
		current.setNext(end);
	}
	
	public void removeSingleBranch(int JunctionEndNum,int toRemove) {
		CircuitNode end = CircuitMap.get("end" + JunctionEndNum);
		end.prev().removeBranch(toRemove);
	}
	
	
}
