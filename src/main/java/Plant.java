import java.util.ArrayList;

public class Plant{
	
	Options options;
	double spreadRadius;
	ArrayList<Lep> lepsSupported;
	String color;
	String size;
	double cost;
	String scientificName;
	String commonName;
	Point position;
	
	public Plant(Options options, double spreadRadius, ArrayList<Lep> lepsSupported, String color, String size,
			double cost, String scientificName, String commonName, Point position) {
		super();
		this.options = options;
		this.spreadRadius = spreadRadius;
		this.lepsSupported = lepsSupported;
		this.color = color;
		this.size = size;
		this.cost = cost;
		this.scientificName = scientificName;
		this.commonName = commonName;
		this.position = position;
	}
	
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	public double getSpreadRadius() {
		return spreadRadius;
	}
	public void setSpreadRadius(double spreadRadius) {
		this.spreadRadius = spreadRadius;
	}
	public ArrayList<Lep> getLepsSupported() {
		return lepsSupported;
	}
	public void setLepsSupported(ArrayList<Lep> lepsSupported) {
		this.lepsSupported = lepsSupported;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getScientificName() {
		return scientificName;
	}
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	
}