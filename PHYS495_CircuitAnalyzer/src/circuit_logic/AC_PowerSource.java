package circuit_logic;

public class AC_PowerSource{
	private double peak;
	private double frequency;
	public AC_PowerSource(double peak, double frequency) {
		this.peak = peak;
		this.frequency = frequency;
	}
	
	public void setPeak(double newPeak) {
		this.peak = newPeak;
	}
	
	public void setFrequency(double newFrequecy) {
		this.frequency = newFrequecy;
	}
	
	public double getPeak() {
		return peak;
	}
	
	public double getFrequency() {
		return frequency;
	}

}
