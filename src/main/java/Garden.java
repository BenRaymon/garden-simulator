import java.util.ArrayList;

public class Garden{
	private float spent;
	private float budget;
	private ArrayList<Plot> plots;
	private ArrayList<Lep> lepsSupported;
	private ArrayList<Plant> plantsInGarden;
	
	public float getSpent() {
		return spent;
	}
	
	public void setSpent(float spent) {
		this.spent = spent;
	}
	
	public float getBudget() {
		return budget;
	}
	
	public void setBudget(float budget) {
		this.budget = budget;
	}
	
	public void newPlot() {
		plots.add(new Plot());
	}
	
	public void loadPlants() {
		
	}
	
	public void loadLeps() {
		
	}
	
	public void loadPlots() {
		
	}
	
	public void updateSpent(int delta) {
		this.spent += delta;
	}
	
	public ArrayList<Plot> getPlots() {
		return plots;
	}

	public ArrayList<Lep> getLepsSupported() {
		return lepsSupported;
	}
	
	public ArrayList<Plant> getPlantsInGarden() {
		return plantsInGarden;
	}
	
	
	
}
