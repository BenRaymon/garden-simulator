import java.util.*;

public class PlotDesignModel extends Model {
	
	private ArrayList<Plot> plots = new ArrayList<Plot>();
	private int numPlots = 0;
	
	Options currentSelection;
	
	public int getNumPlots() {
		return numPlots;
	}
	
	public ArrayList<Plot> getPlots(){
		return plots;
	}
	
	public int addCoordToPlot(int index, Point p) {
		plots.get(index).addCoordinate(p);
		return 0;
	}
	
	public int buildPlot() {
		return 0;
	}
	
	public int newPlot(Options o) {
		plots.add(new Plot(o));
		numPlots++;
		return 0;
	}
	
	public int updateMoisture() {
		return 0;
	}
	
	public int updateSunlight() {
		return 0;
	}
	
	public int updateSoil() {
		return 0;
	}
	
}