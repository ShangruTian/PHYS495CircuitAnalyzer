package circuit_logic;

public class Inductor extends CircuitComponent{
	public Inductor(double value) {
		super("Inductor",value);
	}

	public ComplexNumber calculateImpedance(double frequency) {
		return new ComplexNumber(0,frequency * value * Math.PI * 2);
	}
	
}
