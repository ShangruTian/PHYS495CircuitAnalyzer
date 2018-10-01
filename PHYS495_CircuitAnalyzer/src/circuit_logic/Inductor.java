package circuit_logic;

public class Inductor extends CircuitComponent{
	public Inductor(float value) {
		super("Inductor",value);
	}

	public ComplexNumber calculateImpedance(double frequency) {
		return new ComplexNumber((float)0,(float)frequency * value);
	}
	
}
