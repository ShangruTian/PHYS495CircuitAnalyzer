package circuit_logic;

public class JunctionStart extends CircuitComponent{
	
	public JunctionStart (float value) {
		super("JunctionStart",value);
	}


	public ComplexNumber calculateImpedance(double frequency) {
		return new ComplexNumber((float)0,(float)0);
	}

}
