import java.util.*;

public class PlotDesignModel extends Model {
	
	private ArrayList<Plot> plots = new ArrayList<Plot>();
	
	Options currentSelection;
	
	public int addCoordToPlot() {
		return 0;
	}
	
	public int buildPlot() {
		return 0;
	}
	
	public int newPlot(Options o) {
		plots.add(new Plot(o));
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