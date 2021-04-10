import java.util.ArrayList;

public class Plant{
	
	Options options;
	double spreadRadius;
	int lepsSupported;
	String color;
	String size;
	double cost;
	String scientificName;
	String commonName;
	Point position;
	
	public Plant(Options options, double spreadRadius, int lepsSupported, String color, String size,
			double cost, String scientificName, String commonName, Point position) {
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
	
	public Plant() {
		
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
	public int getLepsSupported() {
		return lepsSupported;
	}
	
	public void setLepsSupported(int ls) {
		this.lepsSupported = ls;
	}
	public void incrementLepsSupportedByOne() {
		this.lepsSupported++;
	}
	public void incrementLepsSupportedByX(int x) {
		this.lepsSupported += x;
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
		return this.position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	
}