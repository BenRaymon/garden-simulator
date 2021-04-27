import java.util.*;
import java.io.Serializable;

public class Plot implements Serializable {
	private HashMap <String, Plant> recommendedPlants;
	private HashMap <Point, Plant> plantsInPlot;
	private Options options;
	private ArrayList<Point> coordinates;
	
	public Plot(Options o) {
		this.recommendedPlants = null;
		this.plantsInPlot = new HashMap<Point, Plant>();
		this.options = o;
		this.coordinates = null;
	}
	
	public ArrayList<Point> filterCoords(int num) {
		ArrayList<Point> filtered = new ArrayList<Point>();
		int index = 0;
		for(Point p: coordinates){
			if(index++%num == 0) {
				filtered.add(p);
			}
		}
		return filtered;
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
	
	public void createRecommendedPlants() {
		recommendedPlants = new HashMap<String, Plant>();
		//iterate through every plant in the whole plant list
		Iterator<Plant> it = Garden.getAllPlants().values().iterator();
		while(it.hasNext()) {
			Plant p = it.next();
			Options o = p.getOptions();
			boolean sunlight = false, soiltype = false, moisture = false; 
			for(int i = 0; i < 3; i++) {
				if(options.getMoistures()[i] == 1 && o.getMoistures()[i] == 1) {
					moisture = true;
				}
				if(options.getSoilTypes()[i] == 1 && o.getSoilTypes()[i] == 1) {
					soiltype = true;
				}
				if(options.getSunLevels()[i] == 1 && o.getSunLevels()[i] == 1) {
					sunlight = true;
				}
			}
			if(sunlight && soiltype && moisture) {
				recommendedPlants.put(p.getScientificName(), p);
			}
		}
	}
	
	public void addPlant(Point pos, Plant p) {
		p.setPosition(pos);
		plantsInPlot.put(pos, p);
	}
	
	public void removePlant(Point pos) {
		// remove does return the object, may change this function
		// to return it later
		plantsInPlot.remove(pos);
	}
	
	public Plant getPlant(Point pos) {
		return plantsInPlot.get(pos);
	}
	
	public HashMap<Point, Plant> getPlantsInPlot() {
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