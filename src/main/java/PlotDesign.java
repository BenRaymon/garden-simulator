import java.util.*;

public class PlotDesign {

	
	public static int addCoordsToPlot(int index, ArrayList<Plot> plots, ArrayList<Point> points) {
		plots.get(index).setCoordinates(points);
		return 0;
	}
	
	
	public static int newPlot(Options o, ArrayList<Plot> plots) {
		plots.add(new Plot(o));
		return 0;
	}
	
}