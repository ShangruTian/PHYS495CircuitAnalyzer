package circuit_logic;

public class Capacitor extends CircuitComponent{
	public Capacitor(double value) {
		super("Capacitor",value);
	}
	public ComplexNumber calculateImpedance(double frequency) {
		if(frequency == 0) return new ComplexNumber(0,Integer.MIN_VALUE);
		return new ComplexNumber(0,(-1/(frequency * value * Math.PI * 2)));
	}
}
