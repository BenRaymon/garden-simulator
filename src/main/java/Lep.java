public class Lep {
	private String commonName;
	private String scientificName;
	private String color;
	
	
	public Lep(String commonName, String scientificName, String color) {
		super();
		this.commonName = commonName;
		this.scientificName = scientificName;
		this.color = color;
	}
	
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getScientificName() {
		return scientificName;
	}
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}