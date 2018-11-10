package circuit_logic;

import java.util.Vector;

public class CircuitNode {
	private CircuitComponent component;
	private Vector<CircuitNode> children;
	private CircuitNode next;
	private CircuitNode prev;
	
	public CircuitNode(CircuitComponent c) {
		this.component = c;
		children = new Vector<CircuitNode>();
		next = null;
	}
	
	public ComplexNumber calculateImpedance(double frequency) {
		return component.calculateImpedance(frequency);
	}
	
	public void addChildren(Vector<CircuitNode> c) {
		this.children = c;
	}
	
	public boolean isBranchStart() {
		return children != null;
	}
	
	public void removeChildren() {
		children = null;
	}
	
	public Vector<CircuitNode> getChildren(){
		return children;
	}
	
	public void setNext(CircuitNode node) {
		this.next = node;
	}
	
	public CircuitNode next() {
		return this.next;
	}
	
	public void setPrev(CircuitNode node) {
		this.prev = node;
	}
	
	public CircuitNode prev() {
		return this.prev;
	}
	
	public void addBranch(CircuitNode node) {
		children.add(node);
	}
	
	public void removeBranch(int index) {
		children.remove(index);
	}
	
	public void changeComponent (CircuitComponent newComponent) {
		this.component = newComponent;
	}
	
	public CircuitComponent getComponent() {
		return this.component;
	}

}
