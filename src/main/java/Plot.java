import java.util.*;
import java.io.Serializable;

public class Plot implements Serializable {
	private HashMap <String, Plant> recommendedPlants;
	private HashMap <Point, Plant> plantsInPlot;
	private Options options;
	private ArrayList<Point> coordinates;
	
	/**
	 * Plot constructor with passing options in
	 * @param o an instance of an options
	 * @return none
	 */
	public Plot(Options o) {
		this.recommendedPlants = null;
		this.plantsInPlot = new HashMap<Point, Plant>();
		this.options = o;
		this.coordinates = null;
	}
	
	/**
	 * shrinks the plot coords so the smooth function can grow it by 20x
	 * @param num shrink factor
	 * @return the filtered arraylist of points
	 */
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
	
	/**
	 * add a coordinate to the plots list of coords
	 * @param point point to add
	 * @return none
	 */
	public void addCoordinate(Point point) {
		coordinates.add(point);
	}
	/**
	 * remove the coordinate from the plot
	 * @param point the coordinate to remove
	 * @return none
	 */
	public void removeCoordinate(Point point) {
		coordinates.remove(point);
	}
	/**
	 * get the coordinates that house this plot
	 * @return the coordinate arraylist
	 */
	public ArrayList<Point> getCoordinates() {
		return coordinates;
	}
	/**
	 * set the coordinate arraylist to specified coords
	 * @param points the arraylist of coords
	 * @return none
	 */
	public void setCoordinates(ArrayList<Point> points) {
		this.coordinates = points;
	}
	
	/**
	 * add a plant to the recommended plants of the plot
	 * @param n the name of a plant
	 * @param p the plant
	 * @return none
	 */
	public void addRecommendedPlant(String n, Plant p) {
		recommendedPlants.put(n, p);
	}
	/**
	 * remove a plant with the passed in name from recommended plants
	 * @param n the name of the plant
	 * @return none
	 */
	public void removeRecommendedPlant(String n) {
		// remove does return the object, may change this function
		// to return it later
		recommendedPlants.remove(n);
	}
	/**
	 * get the recommended plants of the plot
	 * @return hashmap of name, plant
	 */
	public HashMap<String, Plant> getRecommendedPlants() {
		return recommendedPlants;
	}
	/**
	 * create the hashmap of recommended plants based on user defined options
	 * @return none
	 */
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
	/**
	 * add a plant to the plot by coord
	 * @param pos the coordinate of the placed plant
	 * @param p the plant added
	 */
	public void addPlant(Point pos, Plant p) {
		p.setPosition(pos);
		plantsInPlot.put(pos, p);
	}
	/**
	 * remove a plant from the plot
	 * @param pos the coordinate of the plant to remove
	 */
	public void removePlant(Point pos) {
		// remove does return the object, may change this function
		// to return it later
		plantsInPlot.remove(pos);
	}
	/**
	 * get a plant from the plot by coordinate
	 * @param pos the coordinate
	 * @return the plant specified
	 */
	public Plant getPlant(Point pos) {
		return plantsInPlot.get(pos);
	}
	
	/**
	 * get all the plants in the plot
	 * @return the hashmap of plants in the plot and their coordinate
	 */
	public HashMap<Point, Plant> getPlantsInPlot() {
		return plantsInPlot;
	}

	/**
	 * set the options with new options
	 * @param o the new options
	 * @return none
	 */
	public void setOptions(Options o) {
		options = o;
	}
	/**
	 * get the options of the plot
	 * @return the options
	 */
	public Options getOptions() {
		return options;
	}
	/**
	 * set the recommended plants to a predefined hashmap
	 * @param rec the defined map of plants
	 * @return none
	 */
	public void setRecommended(HashMap<String,Plant> rec) {
		this.recommendedPlants = rec;
	}
	/**
	 * set the plantsinplot to a predefined hashmap
	 * @param list the defined map of plants
	 * @return none
	 */
	public void setPlantsinPlot(HashMap<Point,Plant> list) {
		this.plantsInPlot = list;
	}
	

	
	
	
	
}