import java.util.*;

public class Plot{
	private HashMap <String, Plant> recommendedPlants;
	private HashMap <String, Plant> plantsInPlot;
	private Options options;
	private ArrayList<Point> coordinates;
	
	public Plot(HashMap<String, Plant> plants, HashMap<String, Plant> plantsPlot, Options options, ArrayList<Point> coors){
		recommendedPlants = plants;
		plantsInPlot = plantsPlot;
		this.options = options;
		coordinates = coors;
	}
	
	public Plot() {
		
	}
	
	
	public void addCoordinate(Point point) {
		//coordinates.add(point);
	}
	
	public void removeCoordinate(Point point) {
		//coordinates.remove(point);
	}
	
	public ArrayList<Point> getCoordinates() {
		return coordinates;
	}
	
	public void addRecommendedPlant(String n, Plant p) {
		//recommendedPlants.put(n, p);
	}
	
	public void removeRecommendedPlant(String n) {
		// remove does return the object, may change this function
		// to return it later
		//recommendedPlants.remove(n);
	}
	
	public HashMap<String, Plant> getRecommendedPlants() {
		return recommendedPlants;
	}
	
	public void addPlantToPlot(String n, Plant p) {
		//plantsInPlot.put(n, p);
	}
	
	public void removePlantFromPlot(String n) {
		// remove does return the object, may change this function
		// to return it later
		//plantsInPlot.remove(n);
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
		
		return false;
	}
	
	
	
	public boolean movePlant() {
		
		
		return true;
		
	}
	
	

	
	
	
	
}