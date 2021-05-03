import java.util.ArrayList;
import java.io.Serializable;

public class Options implements Serializable {
	
	private int[] soilTypes;
	private int[] sunLevels;
	private int[] moistures;
	
	/**
	 * Constructor for arrays of values
	 * @param st
	 * @param sl
	 * @param m
	 */
	public Options(int[] st, int[] sl, int[] m) {
		this.soilTypes = st;
		this.sunLevels = sl;
		this.moistures = m;
	}
	
	/**
	 * Constructor for double values
	 * @param st
	 * @param sl
	 * @param m
	 */
	public Options(double st, double sl, double m) {
		soilTypes = new int[3];
		sunLevels = new int[3];
		moistures = new int[3];
		soilTypes[(int)st-1] = 1;
		sunLevels[(int)sl-1] = 1;
		moistures[(int)m-1] = 1;
	}
	
	/**
	 * Options toString
	 * @return String toString value
	 */
	public String toString() {
		String str = "Soil: [" + soilTypes[0] + ", " + soilTypes[1] + ", " + soilTypes[2] + "] ";
		str += "Sun: [" + sunLevels[0] + ", " + sunLevels[1] + ", " + sunLevels[2] + "]";
		str += "Moisture: [" + moistures[0] + ", " + moistures[1] + ", " + moistures[2] + "]";
		return str;
	}
	
	/**
	 * Getter for soilTypes
	 * @return soilTypes
	 */
	public int[] getSoilTypes() {
		return soilTypes;
	}

	/**
	 * Setter for soilTypes
	 * @param soilTypes
	 */
	public void setSoilTypes(int[] soilTypes) {
		this.soilTypes = soilTypes;
	}

	/**
	 * Getter for sunLevels
	 * @return sunLevels
	 */
	public int[] getSunLevels() {
		return sunLevels;
	}

	/**
	 * Setter for sunlight levels
	 * @param sunLevels
	 */
	public void setSunLevels(int[] sunLevels) {
		this.sunLevels = sunLevels;
	}

	/**
	 * Getter for Moisture levels
	 * @return moistures
	 */
	public int[] getMoistures() {
		return moistures;
	}

	/**
	 * Setter for Moisture levels
	 * @param moistures
	 */
	public void setMoistures(int[] moistures) {
		this.moistures = moistures;
	}

	
	
	
	
	
	
	
}