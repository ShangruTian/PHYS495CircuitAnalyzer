package circuit_logic;

public class JunctionEnd extends CircuitComponent{
	private ComplexNumber numerator;
	private ComplexNumber denominator;
	public JunctionEnd(double value) {
		super("JunctionEnd",value);
		numerator = new ComplexNumber(1,0);
		denominator = new ComplexNumber(0,0);
		
	}
	
	public void updateImpedance(ComplexNumber c) {
		numerator.multiply(c);
		denominator.add(c);
	}

	public ComplexNumber calculateImpedance(double frequency) {
		if(denominator.getRealPart() == 0 && denominator.getImaginaryPart() ==0) {
			return new ComplexNumber (0,0);
		}
		numerator.divide(denominator);
		return numerator;
	}

}
