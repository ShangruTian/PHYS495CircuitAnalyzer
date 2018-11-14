package circuit_logic;

public class Resistor extends CircuitComponent{
	public Resistor(double value) {
		super("Resistor",value);
	}
	
	//units:Ohm (O),kiloOhms (K),MegaOhms(M)

	public ComplexNumber calculateImpedance(double frequency) {
		return new ComplexNumber (value,0);
	}
	
}
