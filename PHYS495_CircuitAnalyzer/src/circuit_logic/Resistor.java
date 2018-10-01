package circuit_logic;

public class Resistor extends CircuitComponent{
	public Resistor(float value) {
		super("Resistor",value);
	}
	
	//units:Ohm (O),kiloOhms (K),MegaOhms(M)

	public ComplexNumber calculateImpedance(double frequency) {
		return new ComplexNumber ((float)value,(float)0);
	}
	
}
