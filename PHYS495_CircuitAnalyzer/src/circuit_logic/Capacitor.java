package circuit_logic;

public class Capacitor extends CircuitComponent{
	public Capacitor(float value) {
		super("Capacitor",value);
	}
	public ComplexNumber calculateImpedance(double frequency) {
		if(frequency == 0) return new ComplexNumber((float)0,(float)Integer.MAX_VALUE);
		return new ComplexNumber((float)0,(float) (-1/(frequency * value)));
	}
}
