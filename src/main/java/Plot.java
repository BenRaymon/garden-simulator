import java.util.*;

public class Plot{
	private HashMap <String, Plant> recommendedPlants;
	private HashMap <String, Plant> plantsInPlot;
	private Options options;
	private ArrayList<Point> coordinates;
	
	public Plot(Options o) {
		this.recommendedPlants = new HashMap<String, Plant>();
		this.plantsInPlot = new HashMap<String, Plant>();
		this.options = o;
		this.coordinates = new ArrayList<Point>();
	}
	
	
	public void addCoordinate(Point point) {
		coordinates.add(point);
	}
	
	public void removeCoordinate(Point point) {
		coordinates.remove(point);
	}
	
	public ArrayList<Point> getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(ArrayList<Point> points) {
		this.coordinates = points;
	}
	
	public void addRecommendedPlant(String n, Plant p) {
		recommendedPlants.put(n, p);
	}
	
	public void removeRecommendedPlant(String n) {
		// remove does return the object, may change this function
		// to return it later
		recommendedPlants.remove(n);
	}
	
	public HashMap<String, Plant> getRecommendedPlants() {
		return recommendedPlants;
	}
	
	public void addPlantToPlot(String n, Plant p) {
		plantsInPlot.put(n, p);
	}
	
	public void removePlantFromPlot(String n) {
		// remove does return the object, may change this function
		// to return it later
		plantsInPlot.remove(n);
	}
	
	public HashMap<String, Plant> getPlantsInPlot() {
		return plantsInPlot;
	}

	
	public void setOptions(Options o) {
		options = o;
	}
	
	public Options getOptions() {
		return options;
	}
	
	public boolean checkSpread() {
		
		return true;
	}
	
	
	
	public boolean movePlant() {
		
		
		return true;
		
	}
	
	

	
	
	
	
}