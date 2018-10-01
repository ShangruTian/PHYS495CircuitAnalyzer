package circuit_logic;
//base class for circuit components, resistors,capacitors should extend
//on this class

public abstract class CircuitComponent {
	protected float value;
	protected String type;
	
	public CircuitComponent(String type, float value) {
		this.type = type;
		this.value = value;
	}
	
	public CircuitComponent(String type) {
		this.type = type;
	}
	
	public double getValue() {
		return value;
	}
	
	public String getType() {
		return type;
	}
	
	public void setNewValue(float newValue) {
		this.value = newValue;
	}
	
	public abstract ComplexNumber calculateImpedance(double frequency);
	
}
