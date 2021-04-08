public class Options{
	
	private String soilType;
	private int sunLevel;
	private int moisture;
	
	public Options(String st, int sl, int m) {
		this.soilType = st;
		this.sunLevel = sl;
		this.moisture = m;
	}
	
	
	public String getSoilType() {
		return soilType;
	}
	public void setSoilType(String soilType) {
		this.soilType = soilType;
	}
	public int getSunLevel() {
		return sunLevel;
	}
	public void setSunLevel(int sunLevel) {
		this.sunLevel = sunLevel;
	}
	public int getMoisture() {
		return moisture;
	}
	public void setMoisture(int moisture) {
		this.moisture = moisture;
	}
	
	
	
}