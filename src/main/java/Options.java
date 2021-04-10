import java.util.ArrayList;

public class Options{
	
	private ArrayList<String> soilTypes;
	private ArrayList<String> sunLevels;
	private ArrayList<String> moistures;
	
	public Options(ArrayList<String> st, ArrayList<String> sl, ArrayList<String> m) {
		this.soilTypes = st;
		this.sunLevels = sl;
		this.moistures = m;
	}
	
	public String toString() {
		
		String str = soilTypes.toString() + sunLevels.toString() + moistures.toString();
		return str;
	}

	public ArrayList<String> getSoilTypes() {
		return soilTypes;
	}

	public void setSoilTypes(ArrayList<String> soilTypes) {
		this.soilTypes = soilTypes;
	}

	public ArrayList<String> getSunLevels() {
		return sunLevels;
	}

	public void setSunLevels(ArrayList<String> sunLevels) {
		this.sunLevels = sunLevels;
	}

	public ArrayList<String> getMoistures() {
		return moistures;
	}

	public void setMoistures(ArrayList<String> moistures) {
		this.moistures = moistures;
	}
	
	
	
	
	
	
}