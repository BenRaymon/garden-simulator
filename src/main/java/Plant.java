import java.util.ArrayList;

public class Plant{
	
	
	private Point position;
	private String commonName;
	private String scientificName;
	private String family;
	private String color;
	private double sizeLower;
	private double  sizeUpper;
	private int spreadRadiusLower;
	private int spreadRadiusUpper;
	private Options options;
	private double cost;
	private int lepsSupported;
	private char type;
	private String genera;
	
	public Plant(String commonName, String scientificName, String genera, String family, String color, double sizeLower, double sizeUpper, 
			int spreadRadiusLower, int spreadRadiusUpper, Options options, double cost, int lepsSupported, char type) {
		this.commonName = commonName;
		this.scientificName = scientificName;
		this.genera = genera;
		this.family = family;
		this.color = color;
		this.sizeLower = sizeLower;
		this.sizeUpper = sizeUpper;
		this.spreadRadiusLower = spreadRadiusLower;
		this.spreadRadiusUpper = spreadRadiusUpper;
		this.options = options;
		this.lepsSupported = lepsSupported;
		this.cost = cost;
		this.type=type;
		this.position = new Point(0,0);
		
	}
	
	
	
	public Plant() {
		
	}
	
	public Plant clone() {
		Plant p = new Plant();
		p.commonName = commonName;
		p.scientificName = scientificName;
		p.genera = genera;
		p.family = family;
		p.color = color;
		p.sizeLower = sizeLower;
		p.sizeUpper = sizeUpper;
		p.spreadRadiusLower = spreadRadiusLower;
		p.spreadRadiusUpper = spreadRadiusUpper;
		p.options = options;
		p.lepsSupported = lepsSupported;
		p.cost = cost;
		p.type=type;
		p.position = position;
		return p;
	}

	public String toString() {
		
		String str = commonName +"|"+scientificName+"|"+family+"|"+color+"|"+sizeLower+"|"+sizeUpper+"|"+
				spreadRadiusLower+"|"+spreadRadiusUpper+"|"+cost+"|"+lepsSupported+"|"+type;
		
		return str;
	}


	public Point getPosition() {
		return position;
	}



	public void setPosition(Point position) {
		this.position = position;
	}

	public String getGenera() {
		return genera;
	}



	public void setGenera(String genera) {
		this.genera = genera;
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



	public String getFamily() {
		return family;
	}



	public void setFamily(String family) {
		this.family = family;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}



	public double getSizeLower() {
		return sizeLower;
	}



	public void setSizeLower(int sizeLower) {
		this.sizeLower = sizeLower;
	}



	public double getSizeUpper() {
		return sizeUpper;
	}



	public void setSizeUpper(int sizeUpper) {
		this.sizeUpper = sizeUpper;
	}



	public int getSpreadRadiusLower() {
		return spreadRadiusLower;
	}



	public void setSpreadRadiusLower(int spreadRadiusLower) {
		this.spreadRadiusLower = spreadRadiusLower;
	}



	public int getSpreadRadiusUpper() {
		return spreadRadiusUpper;
	}



	public void setSpreadRadiusUpper(int spreadRadiusUpper) {
		this.spreadRadiusUpper = spreadRadiusUpper;
	}



	public Options getOptions() {
		return options;
	}



	public void setOptions(Options options) {
		this.options = options;
	}



	public double getCost() {
		return cost;
	}



	public void setCost(double cost) {
		this.cost = cost;
	}



	public int getLepsSupported() {
		return lepsSupported;
	}



	public void setLepsSupported(int lepsSupported) {
		this.lepsSupported = lepsSupported;
	}



	public char getType() {
		return type;
	}



	public void setType(char type) {
		this.type = type;
	}
	
	@Override
	//two plants are considered equal (as of right now) if they're locations are the same

	public boolean equals(Object o) {
		if (o instanceof Plant) {
			Plant p = (Plant)o;
			System.out.println("in equals");
			System.out.println(p.getPosition() + " - " + this.position);
			return p.getPosition().equals(this.position);
		} else return false;
	}
	
	@Override
	public int hashCode() {
		return (int)(this.getPosition().getX());
	}
	
	
}