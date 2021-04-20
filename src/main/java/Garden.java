import java.util.ArrayList;
import java.util.HashMap;

public class Garden{
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
	
	public boolean isValidPlacement(double x, double y) {
		
		boolean inAPlot = false;
		for (Plot p : plots) {
			boolean result = inBounds(p.getCoordinates(), x-200, y+50);
			if(result)
				inAPlot = true;
		}
		return inAPlot;
	}
	
	public boolean inBounds(ArrayList<Point> points, double x, double y) {
		boolean bounds = false;
		int i, j;
		for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
			if ((points.get(i).getY() > y) != (points.get(j).getY() > y) &&
					(x < (points.get(j).getX() - points.get(i).getX()) *(y - points.get(i).getY()) / 
            		(points.get(j).getY()-points.get(i).getY()) + points.get(i).getX())) {
				bounds = !bounds;
         	}
		}
		
		return bounds;
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
	
	public ArrayList<Plant> getPlantsInGarden() {
		return plantsInGarden;
	}
	
	public static HashMap<String, Plant> getAllPlants() {
		return allPlants;
	}
	
	public static Plant getPlant(String name) {
		return allPlants.get(name);
	}
	
	
}
