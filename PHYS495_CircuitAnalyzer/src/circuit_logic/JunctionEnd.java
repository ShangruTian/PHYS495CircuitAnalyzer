package circuit_logic;

public class JunctionEnd extends CircuitComponent{
	private ComplexNumber numerator;
	private ComplexNumber denominator;
	public JunctionEnd(float value) {
		super("JunctionEnd",value);
		numerator = new ComplexNumber((float)1,(float)0);
		denominator = new ComplexNumber((float)1,(float)0);
		
	}
	
	public void updateImpedance(ComplexNumber c) {
		numerator.multiply(c);
		denominator.add(c);
	}

	public ComplexNumber calculateImpedance(double frequency) {
		numerator.divide(denominator);
		return numerator;
	}

}
