import java.util.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Garden implements Serializable {
	String name; // name for the garden that will be used to get it from the list of saved gardens
	private double spent;
	private double budget;
	private ArrayList<Plot> plots;
	private int lepsSupported;
	private ArrayList<Plant> plantsInGarden;
	private HashMap<String, Plant> recommendedPlants;
	private static ConcurrentHashMap<String, Plant> allPlants = new ConcurrentHashMap<String, Plant>();
	private static ConcurrentHashMap<String, Lep> allLeps = new ConcurrentHashMap<String, Lep>();
	private static ConcurrentHashMap<String, Set<Lep>> lepsByPlant = new ConcurrentHashMap<String, Set<Lep>>();
	private double pixelsPerFoot ;
	
	/**
	 * No-arg constructor for Garden
	 * @return none
	 */
	public Garden() {
		spent = 0;
		budget = 0;
		lepsSupported = 0;
		plantsInGarden = new ArrayList<Plant>();
		plots = new ArrayList<Plot>();
		recommendedPlants = new HashMap<String, Plant>();
		pixelsPerFoot = 0;
	}
	
	/**
	 * Main constructor for Garden
	 * @param name name of garden
	 * @param spent how much of the budget is spent
	 * @param budget how much budget the user has
	 * @param plots ArrayList of plots in the garden
	 * @param lepsSupported how many leps the garden supports
	 * @param plants ArrayList of plants in the garden
	 * @param pixelsperfoot pixels per feet for scaling
	 * @return none
	 */
	public Garden(String name, double spent, double budget, ArrayList<Plot> plots, int lepsSupported, ArrayList<Plant> plants, double pixelsperfoot) {
		this.name = name;
		this.spent = spent;
		this.budget = budget;
		this.plots = plots;
		this.lepsSupported = lepsSupported;
		this.plantsInGarden = plants;
		this.pixelsPerFoot = pixelsperfoot;
	}
	
	/**
	 * Get a plant from a plot with the plot index
	 * @param plotNum index of the plot
	 * @param pos Point in which to get the plant from the plot
	 * @return Plant from the plot
	 */
	public Plant getPlant(int plotNum, Point pos) {
		return plots.get(plotNum).getPlant(pos);
	}
	
	/**
	 * Get all the plants in the garden
	 * @return ArrayList of all plants in garden
	 */
	public ArrayList<Plant> getPlantsInGarden() {
		return plantsInGarden;
	}
	
	/**
	 * Get how many plots in the garden
	 * @return the size of the plot array
	 */
	public int getNumPlots() {
		return plots.size();
	}
	
	/**
	 * Get all plots in the garden
	 * @return ArrayList of plots
	 */
	public ArrayList<Plot> getPlots() {
		return plots;
	}
	
	/**
	 * Create a new plot
	 * @param o an instance of Options to create a plot
	 * @return none
	 */
	public void newPlot(Options o) {
		plots.add(new Plot(o));
	}
	
	/**
	 * Get the settings of a plot
	 * @param plotIndex the index of the plot
	 * @return the settings
	 */
	public Options getPlotOptions(int plotIndex) {
		return plots.get(plotIndex).getOptions();
	}
	
	/**
	 * Get the amount spent in the garden so far
	 * @return how much the user spent as a double
	 */
	public double getSpent() {
		return spent;
	}
	
	/**
	 * Set the spent variable
	 * @param spent double of how much is spent
	 * @return none
	 */
	public void setSpent(double spent) {
		this.spent = spent;
	}
	
	/**
	 * Update how much the user has spent so far
	 * @param delta integer of how much more was spent
	 * @return none
	 */
	public void updateSpent(int delta) {
		this.spent += delta;
	}
	
	/**
	 * Get the budget
	 * @return the users budget as a double
	 */
	public double getBudget() {
		return budget;
	}
	
	/**
	 * Set the budget
	 * @param budget the budget the user has set
	 * @return none
	 */
	public void setBudget(double budget) {
		this.budget = budget;
	}

	/**
	 * Get how many leps the garden supports
	 * @return number of leps supported by the garden
	 */
	public int getLepsSupported() {
		return lepsSupported;
	}
	
	/**
	 * Set the recommended plants list
	 * @param rec the hash map to set as the new recommended plants list
	 * @return none
	 */
	public void setRecommendedPlants(HashMap<String, Plant> rec) {
		this.recommendedPlants = rec;
	}

	/**
	 * Get the current list of recommended plants
	 * @return recommended plants hashmap
	 */
	public HashMap<String, Plant> getRecommendedPlants() {
		return recommendedPlants;
	}
	
	/**
	 * Add the coordinates drawn to the newest plot
	 * @param points an array list of points for setting the size of the plot
	 * @return none
	 */
	public void addCoordsToPlot(ArrayList<Point> points) {
		plots.get(plots.size() - 1).setCoordinates(points);
	}
	
	/**
	 * Add a new plant to the specified plot
	 * @param index the plot index
	 * @param point the location of the new plant
	 * @param p the plant to add to the plot
	 * @return none
	 */
	public  void addPlantToPlot(int index, Point point, Plant p) {
		plots.get(index).addPlant(point, p);
		plantsInGarden.add(p);
		this.spent += p.getCost();
		lepsSupported += p.getLepsSupported();
	}
	
	/**
	 * Remove a plant at a specified location from the specific plot
	 * @param index the plot index where the plant is
	 * @param point the location of the plant
	 * @return none
	 */
	public  void removePlantFromPlot(int index, Point point) {
		this.spent -= plots.get(index).getPlant(point).getCost();
		this.lepsSupported -= plots.get(index).getPlant(point).getLepsSupported();
		plantsInGarden.remove(plots.get(index).getPlant(point));
		plots.get(index).removePlant(point);
	}
	
	/**
	 * Check to see if the specified plant is in the plot provided
	 * @param index plot index
	 * @param p Plant that is being checked
	 * @return true/false whether or not the plant is in the plot
	 */
	public boolean isPlantInPlot(int index, Plant p) {
		try {
			return plots.get(index).getPlantsInPlot().containsValue(p);
		} catch (Exception e) {
			System.out.println("Plant is not in plot");
		}
		return false;
	}
	
	/**
	 * Get the updated garden data for the shopping list such as common name, scientific name, cost, and how many of them are in the garden
	 * @return a hashmap of PlantShoppingListData for the shopping list to use
	 */
	public HashMap<String, PlantShoppingListData> generateShoppingListData() {
		HashMap<String, PlantShoppingListData> psld = new HashMap<String, PlantShoppingListData>();
		
		Iterator itr = plantsInGarden.iterator();
		while (itr.hasNext()) {
			Plant p = (Plant)itr.next();
			if (psld.containsKey(p.getCommonName())) {
				psld.get(p.getCommonName()).updateCount(1);
				psld.get(p.getCommonName()).updateCost(p.getCost());
			} else {
				psld.put(p.getCommonName(), new PlantShoppingListData(1, p.getCost(), p.getCommonName(), p.getScientificName()));
			}
		}
		
		return psld;
	}
	
	/**
	 * Set the scale of the garden
	 * @param scale the new pixelsperfoot scale
	 * @return none
	 */
	public void setScale(double scale) {
		pixelsPerFoot = scale;
	}
	
	/**
	 * Get the current garden scale
	 * @return the scale as a double
	 */
	public double getScale() {
		return pixelsPerFoot;
	}
	/**
	 * Get the gardens name
	 * @return the gardens name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set a new name for the garden
	 * @return none
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Get all the plants in the garden
	 * @return a ConcurrentHashMap of all the plants
	 */
	public static ConcurrentHashMap<String, Plant> getAllPlants() {
		return allPlants;
	}
	
	/**
	 * Get a plant by name
	 * @return the plant
	 */
	public static Plant getPlant(String name) {
		return allPlants.get(name);
	}
	
	//TODO
	public static ConcurrentHashMap<String, Set<Lep>> getLepsByPlant(){
		return lepsByPlant;
	}
	//TODO
	public static ConcurrentHashMap<String, Lep> getAllLeps(){
		return allLeps;
	}
}
