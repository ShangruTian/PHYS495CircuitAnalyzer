package circuit_logic;

public class ComplexNumber {
	private float real_part;
	private float imaginary_part;
	
	public ComplexNumber(float real, float img) {
		this.real_part = real;
		this.imaginary_part = img;
	}
	
	public float getRealPart() {
		return this.real_part;
	}
	
	public float getImaginaryPart() {
		return this.imaginary_part;
	}
	
	public void add(ComplexNumber c) {
		this.real_part += c.getRealPart();
		this.imaginary_part += c.getImaginaryPart();
	}
	
	public void subtract(ComplexNumber c) {
		this.real_part -= c.getRealPart();
		this.imaginary_part -= c.getImaginaryPart();
	}
	
	public void multiply(ComplexNumber c) {
		float newRealPart = this.real_part * c.getRealPart() - (this.imaginary_part * c.getImaginaryPart());
		float newImagingaryPart = this.real_part * c.getImaginaryPart() + this.imaginary_part * c.getRealPart();
		this.real_part = newRealPart;
		this.imaginary_part = newImagingaryPart;
	}
	
	public void divide(ComplexNumber c) {
		float commonDenominator = c.getRealPart() * c.getRealPart() + c.getImaginaryPart() * c.getImaginaryPart();
		float newRealPart = this.real_part * c.getRealPart() + this.imaginary_part * c.getImaginaryPart();
		float newImaginaryPart = this.imaginary_part * c.getRealPart() - this.real_part * c.getImaginaryPart();
		this.real_part = newRealPart/commonDenominator;
		this.imaginary_part = newImaginaryPart/commonDenominator;
	}
}
