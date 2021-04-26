import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Garden implements Serializable{
	String name; // name for the garden that will be used to get it from the list of saved gardens
	private double spent;
	private double budget;
	private ArrayList<Plot> plots;
	private int lepsSupported;
	private ArrayList<Plant> plantsInGarden;
	private static HashMap<String, Plant> allPlants = new HashMap<String, Plant>();
	
	public Garden() {
		spent = 0;
		budget = 0;
		lepsSupported = 0;
		plantsInGarden = new ArrayList<Plant>();
		plots = new ArrayList<Plot>();
	}
	
	public Plant getPlant(int plotNum, Point pos) {
		return plots.get(plotNum).getPlant(pos);
	}
	
	public int getNumPlots() {
		return plots.size();
	}
	
	public Plot getPlot(int plotIndex) {
		return plots.get(plotIndex);
	}
	
	public Options getPlotOptions(int plotIndex) {
		return plots.get(plotIndex).getOptions();
	}
	
	public double getSpent() {
		return spent;
	}
	
	public void setSpent(double spent) {
		this.spent = spent;
	}
	
	public double getBudget() {
		return budget;
	}
	
	public void setBudget(double budget) {
		this.budget = budget;
	}
	
	public void addPlot(Plot p) {
		plots.add(p);
	}
	
	public void loadPlants() {
		
	}
	
	public void loadPlots() {
		
	}
	
	public void updateSpent(int delta) {
		this.spent += delta;
	}
	
	public ArrayList<Plot> getPlots() {
		return plots;
	}

	public int getLepsSupported() {
		return lepsSupported;
	}
	
	public boolean addCoordsToPlot(ArrayList<Point> points) {
		plots.get(plots.size() - 1).setCoordinates(points);
		if (plots.get(plots.size() - 1) != null)
			return true;
		else
			return false;
	}
	
	public void newPlot(Options o) {
		plots.add(new Plot(o));
	}
	
	public  void addPlantToPlot(int index, Point point, Plant p) {
		plots.get(index).addPlant(point, p);
	}
	
	public  void removePlantFromPlot(int index, Point point) {
		plots.get(index).removePlant(point);
	}
	
	public boolean isPlantInPlot(int index, Plant p) {
		try {
			return plots.get(index).getPlantsInPlot().containsValue(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Plant> getPlantsInGarden() {
		return plantsInGarden;
	}
	
	public static HashMap<String, Plant> getAllPlants() {
		return allPlants;
	}
	
	public static Plant getPlant(String name) {
		return allPlants.get(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
}
