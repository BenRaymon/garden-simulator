import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
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
	private static ConcurrentHashMap<String, Plant> allPlants = new ConcurrentHashMap<String, Plant>();
	private double pixelsPerFoot ;
	
	public Garden() {
		spent = 0;
		budget = 0;
		lepsSupported = 0;
		plantsInGarden = new ArrayList<Plant>();
		plots = new ArrayList<Plot>();
		pixelsPerFoot = 0;
	}
	
	public Garden(String name, double spent, double budget, ArrayList<Plot> plots, int lepsSupported, ArrayList<Plant> plants, double pixelsperfoot) {
		this.name = name;
		this.spent = spent;
		this.budget = budget;
		this.plots = plots;
		this.lepsSupported = lepsSupported;
		this.plantsInGarden = plants;
		this.pixelsPerFoot = pixelsperfoot;
	}
	
	public Plant getPlant(int plotNum, Point pos) {
		return plots.get(plotNum).getPlant(pos);
	}
	
	public ArrayList<Plant> getPlantsInGarden() {
		return plantsInGarden;
	}
	
	public int getNumPlots() {
		return plots.size();
	}
	
	public ArrayList<Plot> getPlots() {
		return plots;
	}
	
	public void newPlot(Options o) {
		plots.add(new Plot(o));
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
	
	public void updateSpent(int delta) {
		this.spent += delta;
	}
	
	public double getBudget() {
		return budget;
	}
	
	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getLepsSupported() {
		return lepsSupported;
	}
	
	public void addCoordsToPlot(ArrayList<Point> points) {
		plots.get(plots.size() - 1).setCoordinates(points);
	}
	
	public  void addPlantToPlot(int index, Point point, Plant p) {
		plots.get(index).addPlant(point, p);
		plantsInGarden.add(p);
		this.spent += p.getCost();
		lepsSupported += p.getLepsSupported();
	}
	
	public  void removePlantFromPlot(int index, Point point) {
		this.spent -= plots.get(index).getPlant(point).getCost();
		this.lepsSupported -= plots.get(index).getPlant(point).getLepsSupported();
		plantsInGarden.remove(plots.get(index).getPlant(point));
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
	
	public HashMap<String, PlantShoppingListData> generateShoppingListData() {
		HashMap<String, PlantShoppingListData> psld = new HashMap<String, PlantShoppingListData>();
		System.out.println("Plant list size is as follows:" + plantsInGarden.size());
		
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
	
	public void setScale(double scale) {
		pixelsPerFoot = scale;
	}
	
	public double getScale() {
		return pixelsPerFoot;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public static ConcurrentHashMap<String, Plant> getAllPlants() {
		return allPlants;
	}
	
	public static Plant getPlant(String name) {
		return allPlants.get(name);
	}
}
