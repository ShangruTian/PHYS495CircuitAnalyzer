package circuit_logic;

public class ComplexNumber {
	private double real_part;
	private double imaginary_part;
	
	public ComplexNumber(double real, double img) {
		this.real_part = real;
		this.imaginary_part = img;
	}
	
	public double getRealPart() {
		return this.real_part;
	}
	
	public double getImaginaryPart() {
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
		double newRealPart = this.real_part * c.getRealPart() - (this.imaginary_part * c.getImaginaryPart());
		double newImagingaryPart = this.real_part * c.getImaginaryPart() + this.imaginary_part * c.getRealPart();
		this.real_part = newRealPart;
		this.imaginary_part = newImagingaryPart;
	}
	
	public void divide(ComplexNumber c) {
		double commonDenominator = c.getRealPart() * c.getRealPart() + c.getImaginaryPart() * c.getImaginaryPart();
		double newRealPart = this.real_part * c.getRealPart() + this.imaginary_part * c.getImaginaryPart();
		double newImaginaryPart = this.imaginary_part * c.getRealPart() - this.real_part * c.getImaginaryPart();
		this.real_part = newRealPart/commonDenominator;
		this.imaginary_part = newImaginaryPart/commonDenominator;
	}
}
