import java.util.ArrayList;

public class Options{
	
	private int[] soilTypes;
	private int[] sunLevels;
	private int[] moistures;
	
	public Options(int[] st, int[] sl, int[] m) {
		this.soilTypes = st;
		this.sunLevels = sl;
		this.moistures = m;
	}
	
	public Options(double st, double sl, double m) {
		soilTypes = new int[3];
		sunLevels = new int[3];
		moistures = new int[3];
		
	}
	
	public String toString() {
		
		String str = soilTypes.toString() + sunLevels.toString() + moistures.toString();
		return str;
	}

	public int[] getSoilTypes() {
		return soilTypes;
	}

	public void setSoilTypes(int[] soilTypes) {
		this.soilTypes = soilTypes;
	}

	public int[] getSunLevels() {
		return sunLevels;
	}

	public void setSunLevels(int[] sunLevels) {
		this.sunLevels = sunLevels;
	}

	public int[] getMoistures() {
		return moistures;
	}

	public void setMoistures(int[] moistures) {
		this.moistures = moistures;
	}

	
	
	
	
	
	
	
}