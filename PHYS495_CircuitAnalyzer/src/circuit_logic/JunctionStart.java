package circuit_logic;

public class JunctionStart extends CircuitComponent{
	
	public JunctionStart (double value) {
		super("JunctionStart",value);
	}


	public ComplexNumber calculateImpedance(double frequency) {
		return new ComplexNumber(0,0);
	}

}
