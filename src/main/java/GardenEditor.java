import java.util.*;
import java.lang.*;

public class GardenEditor {
	
	private static Plant currentlySelectedPlant;
	private static double cx, cy, scale, left, right, top, bottom;
	private static double CANVAS_WIDTH, CANVAS_HEIGHT;
	private static double SCALE_BUFFER = 100, BUFFER = 10;
	
	/**
	 * Set the currentlySelectedPlant for while dragging a plant from the plant list to a plot
	 * @param plantName the name of the plant to select from the garden
	 * @param pos the current position of the plant in the garden
	 * @return none
	 */
	public static void setSelectedPlant(String plantName, Point pos) {
		Plant p = Garden.getPlant(plantName);
		p.setPosition(pos);
		currentlySelectedPlant = Garden.getPlant(plantName);
	}
	
	/**
	 * Set the currentlySelectedPlant for while dragging a plant from the plant list to a plot
	 * @param plant the plant to select 
	 * @return none
	 */
	public static void setSelectedPlant(Plant p) {
		currentlySelectedPlant = p;
	}
	
	/**
	 * Gets the currently selected plant
	 * @return the plant object representing the image that is currently being dragged
	 */
	public static Plant getSelectedPlant() {
		return currentlySelectedPlant;
	}
	
	/**
	 * Creates a list of sorted plant names based on a recommended plant list and sort criteria
	 * @param recommendedPlants the original map of all the recommended plants that should be shown
	 * @param sortCriteria a string representing how the plants should be shorted
	 * @return the list of sorted plant names
	 */
	public static ArrayList<String> sortRecommendedPlants(HashMap<String, Plant> recommendedPlants, String sortCriteria) {
		
		//make an array of plants from the recommended vales
		Plant[] plants = recommendedPlants.values().toArray(new Plant[0]);
		//blank list of plant names
		ArrayList<String> plantNames = new ArrayList<String>();
		
		//If the users chooses to sort by butterflies
		if(sortCriteria.equals("Butterfly Count")) {
			//sort by descending butterfly count by using the ButterflyComparator
			Arrays.sort(plants, Plant.ButterflyComparator);
			//add the names of the sorted list into the list of plant names
			for (Plant p : plants) {
				plantNames.add(p.getScientificName());
			}
			return plantNames;
		}
		
		//If the users chooses to sort by cost
		if(sortCriteria.equals("Cost")) {
			//sort by descending cost by using the CostComparator
			Arrays.sort(plants, Plant.CostComparator);
			//add the names of the sorted list into the list of plant names
			for (Plant p : plants) {
				plantNames.add(p.getScientificName());
			}
			return plantNames;
		}
		
		//If the users chooses to sort by spread radius
		if(sortCriteria.equals("Spread Radius")) {
			//sort by ascending spread radius by using the SpreadComparator
			Arrays.sort(plants, Plant.SpreadComparator);
			//add the names of the sorted list into the list of plant names
			for (Plant p : plants) {
				plantNames.add(p.getScientificName());
			}
			return plantNames;
		}
		
		//If the users chooses to sort by size
		if(sortCriteria.equals("Plant Size")) {
			//sort by ascending size by using the SizeComparator
			Arrays.sort(plants, Plant.SizeComparator);
			//add the names of the sorted list into the list of plant names
			for (Plant p : plants) {
				plantNames.add(p.getScientificName());
			}
			return plantNames;
		}
		return null;
	}
	
	/**
	 * Transform a plot to fit in the desired canvas. Transform includes
	 * scaling a plot up and smoothing the edges of a rough shape
	 * 
	 * @param plots an array list of plots that are in the garden
	 * @param canvasWidth the width of the canvas to scale to
	 * @param canvasHeight the height of the canvas to scale to
	 * @param ppf is the current scale of the garden, current pixels per foot
	 * @return pixels per foot, a value representing the resulting scale of the garden
	 */
	public static double transformPlots(ArrayList<Plot> plots, double canvasWidth, double canvasHeight, double ppf) {
		//reset values for left, right, top and bottom
		left = canvasWidth; right = 0; top = canvasHeight; bottom = 0;
		
		CANVAS_HEIGHT = canvasHeight;
		CANVAS_WIDTH = canvasWidth;
		//TOP_HEIGHT = top;
		calculatePlotBoundaries(plots);
		ppf *= calculateScale();
		scalePlots(plots);
		recenterGarden(plots);
		
		return ppf;
	}
	
	/**
	 * Calculate the boundaries of the plots in your garden. 
	 * Finds the leftmost, rightmost, topmost, and bottom most coordinate in the garden.
	 * @param plots a list of plots in the garden
	 * @return void
	 */
	public static void calculatePlotBoundaries(ArrayList<Plot> plots) {
		
		Iterator<Plot> it = plots.iterator();
		while(it.hasNext()) {
			ArrayList<Point> points = it.next().getCoordinates();
			Iterator<Point> itp = points.iterator();
			while(itp.hasNext()) {
				Point p = itp.next();
				if (p.getX() < left || left == 0) {
					left = p.getX();
				}
				if (p.getX() > right) {
					right = p.getX();
				}
				if (p.getY() < top || top == 0) {
					top = p.getY();
				}
				if (p.getY() > bottom) {
					bottom = p.getY();
				}
			}
		}
		
		cx = left + ((right-left)/2);
		cy = top + ((bottom-top)/2);
		
	}
	
	/**
	 * determine if the plant that is trying to be placed can fit in the current plot based on the
	 * plants spread radius and the plots boundaries
	 * @param radius the spread radius of the plant
	 * @param plot the plot the plant is trying to be placed in
	 * @return boolean whether or not the plant can be placed
	 */
	public static boolean isPlantWithinPlot(double radius, double scale, Plot plot) {
		double rad = (radius*2)*scale;
		if (rad >= Math.abs(plot.getRight() - plot.getLeft()) || 
			rad >= Math.abs(plot.getBottom() - plot.getTop()))
			return false;
		else
			return true;
	}
	
	/**
	 * Calculate the scale factor that is needed to fit the garden in the desired canvas
	 * @return scale factor as a double
	 */
	public static double calculateScale() {
		scale = (CANVAS_HEIGHT - SCALE_BUFFER) / (bottom-top);
		if ((CANVAS_WIDTH-SCALE_BUFFER)/(right-left) < scale) {
			scale = (CANVAS_WIDTH-SCALE_BUFFER)/(right-left);
		}
		System.out.println("Scale factor: "+ scale);
		return scale;
	}
	
	/**
	 * Resize all plots in the garden according to the scale factor. 
	 * New position = (old position - center) * scale + center
	 * 
	 * @param plots all the plots in the garden
	 * @return none
	 */
	public static void scalePlots(ArrayList<Plot> plots) {
		Iterator<Plot> it = plots.iterator();
		while(it.hasNext()) {
			ArrayList<Point> points = it.next().getCoordinates();
			Iterator<Point> itp = points.iterator();
			while(itp.hasNext()) {
				Point p = itp.next();
				p.setX((p.getX() - cx)*scale + cx);
				p.setY((p.getY() - cy)*scale + cy);
			}
		}
	}
	
	/**
	 * Recenter all of the plots in the garden after the scale has been applied 
	 * @param plots a list of all the plots in the garden
	 * @return none
	 */
	private static void recenterGarden(ArrayList<Plot> plots) {
		right = (right-cx)*scale + cx;
		left = (left-cx)*scale + cx;
		top = (top-cy)*scale + cy;
		bottom = (bottom-cy)*scale + cy;
				
		double xDist=0,yDist=0;
		
		if(cx != (CANVAS_WIDTH ) / 2) 
			xDist += ((CANVAS_WIDTH )/ 2 - cx);
		
		if(cy != (CANVAS_HEIGHT - SCALE_BUFFER) / 2) 
			yDist += ((CANVAS_HEIGHT - SCALE_BUFFER) / 2 - cy);
		

		Iterator<Plot> it = plots.iterator();
		while(it.hasNext()) {
			ArrayList<Point> points = it.next().getCoordinates();
			Iterator<Point> itp = points.iterator();
			while(itp.hasNext()) {
				Point p = itp.next();
				p.setX((p.getX() + xDist));
				p.setY((p.getY() + yDist));
			}
		}
	}
	
	/**
	 * Check if the point falls within any of the plots in the garden
	 * @param pos coordinate of the point to check
	 * @param plots all the plots in the garden
	 * @param verticalBuffer buffer value for y coord
	 * @param horizontalBuffer buffer value for x coord
	 * @return the plot number that this coordinate belongs to,, -1 if none
	 */
	public static int inPlot(Point pos, ArrayList<Plot> plots, double verticalBuffer, double horizontalBuffer) {
		boolean inAPlot = false;
		int plotNum = 0;
		for (int i = 0; i < plots.size(); i++) {
			boolean result = inBounds(plots.get(i).getCoordinates(), pos.getX()-horizontalBuffer, pos.getY()-verticalBuffer);
			if(result) {
				inAPlot = true;
				plotNum = i;
			}
		}
		
		if(inAPlot)
			return plotNum;
		else
			return -1;
	}
	
	/**
	 * Checks if a coordinate is within the bounds of a list of points
	 * @param points the list points that is used as bounds
	 * @param x the x coordinate of the position to check
	 * @param y the y coordinate of the position to check
	 * @return true or false if the coordinate is within the bounds
	 */
	private static boolean inBounds(ArrayList<Point> points, double x, double y) {
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
	
	/**
	 * Creates a pair of control points for each coordinate in the list
	 * The control points list is used in the bezier algorithm to smooth plot edges
	 * 
	 * @param coords an array list of points used as a reference for the control points
	 * @param alpha a numeric value between zero and 1 used as a factor to generate control points
	 * @return the list of lists (list of control point pairs)
	 */
	private static ArrayList<ArrayList<Point>> getControlPoints(ArrayList<Point> coords,  double alpha) {
		if (alpha < 0.0 || alpha > 1.0) {
			throw new IllegalArgumentException("alpha must be a value between 0 and 1 inclusive");
		}
		
		int N = coords.size();
		
		ArrayList<ArrayList<Point>> ctrl = new ArrayList<ArrayList<Point>>();
		
		Point v1, v2, v3;
		
		Point mid1 = new Point();
		Point mid2 = new Point();
		  
		Point anchor = new Point();
		double vdist1, vdist2;
		double mdist;
		
		v2 = coords.get(N-1);
		v3 = coords.get(0);
		mid2.setX((v2.getX() + v3.getX()) / 2.0);
		mid2.setY((v2.getY() + v3.getY()) / 2.0);
		vdist2 = v2.distance(v3);
		
		for (int i = 0; i < N; i++) {
			v1 = v2;
		    v2 = v3;
		    v3 = coords.get((i + 1) % N);
		
		    mid1.setX(mid2.getX());
		    mid1.setY(mid2.getY());
		    mid2.setX((v2.getX() + v3.getX()) / 2.0);
		    mid2.setY((v2.getY() + v3.getY()) / 2.0);
		
		    vdist1 = vdist2;
		    vdist2 = v2.distance(v3);
		
		    double p = vdist1 / (vdist1 + vdist2);
		    anchor.setX(mid1.getX() + p * (mid2.getX() - mid1.getX()));
		    anchor.setY(mid1.getY() + p * (mid2.getY() - mid1.getY()));
		
		    double xdelta = anchor.getX() - v2.getX();
		    double ydelta = anchor.getY() - v2.getY();
		
		    ArrayList<Point> points = new ArrayList<Point>();
		    points.add(new Point(
		            alpha*(v2.getX() - mid1.getX() + xdelta) + mid1.getX() - xdelta,
		            alpha*(v2.getY() - mid1.getY() + ydelta) + mid1.getY() - ydelta));
		    points.add(new Point(
		            alpha*(v2.getX() - mid2.getX() + xdelta) + mid2.getX() - xdelta,
		            alpha*(v2.getY() - mid2.getY() + ydelta) + mid2.getY() - ydelta));
		    ctrl.add(i, points);
		      
		}
		
		return ctrl;
	}

	/**
	 * Calculates a list of coordinates following a Bezier curve between a start point and end point
	 * 
	 * @param start the start position
	 * @param end the end position
	 * @param ctrl1 the first control point
	 * @param ctrl2 the second control point 
	 * @param nv the number of verticies to add between start and end point (including the start and end point)
	 * @return list of coordinates along the Bezier curve
	 */
	private static ArrayList<Point> cubicBezier(final Point start, final Point end,
		final Point ctrl1, final Point ctrl2, final int nv) {
		        
		final ArrayList<Point> curve = new ArrayList<Point>();
		
		for (int i = 0; i < nv; i++) {
		    double t = (double) i / (nv);
		    double tc = 1.0 - t;
		
		    double t0 = tc*tc*tc;
		    double t1 = 3.0*tc*tc*t;
		    double t2 = 3.0*tc*t*t;
		    double t3 = t*t*t;
		    double tsum = t0 + t1 + t2 + t3;
		
		    Point c = new Point();
		
		    c.setX(t0*start.getX() + t1*ctrl1.getX() + t2*ctrl2.getX() + t3*end.getX());
		    c.setX(c.getX()/tsum);
		    c.setY( t0*start.getY() + t1*ctrl1.getY() + t2*ctrl2.getY() + t3*end.getY());
		    c.setY(c.getY() / tsum);
		
		    curve.add(c);
		}
		return curve;
	}


	/**
	 * Creates a new list of coordinates with a smooth exterior following a bezier curve of the original coordinates
	 * 
	 * NOTE: This algorithm and the 2 private helper methods above were based on another project
	 * http://lastresortsoftware.blogspot.com/2010/12/smooth-as.html
	 * 
	 * @param coords an array list of points that need smoothing
	 * @param alpha a numeric value between zero and 1 for the tightness of fit
	 * @param pointsPerSegment number of verticies to add between every coordinate
	 * @return a list of points for a newly smoothed plot
	 */
	public static ArrayList<Point> smooth(ArrayList<Point> points, double alpha, int pointsPerSegment) {
	    
		ArrayList<ArrayList<Point>> controlPoints = getControlPoints(points, alpha);
		
		ArrayList<Point> smoothCoords = new ArrayList<Point>();
		  
		for (int i = 0, k = 0; i < points.size(); i++) {
		    int next = (i + 1) % points.size();  
		      
		    ArrayList<Point> segment = cubicBezier(
		            points.get(i), points.get(next),
		            controlPoints.get(i).get(1), controlPoints.get(next).get(0),
		            pointsPerSegment);
		    
		    for (int j = 0; j < pointsPerSegment; j++, k++) {
		    	smoothCoords.add(segment.get(j));
		    }
		}
		  
		ArrayList<Point> newPoints = new ArrayList<Point>();
		  
		for (int i = 0; i < smoothCoords.size(); i++) {
			if (smoothCoords.get(i) != null) {
				newPoints.add(smoothCoords.get(i));
			}
		}
		  
		return newPoints;     
	}
	
	//TODO: javadoc
	public static void setScale(double s) {
		scale = s;
	}

	/**
	 * TODO update this
	 * This method figures out if a plant can be placed next to another plant based on the spread radius
	 * @param scale the scale of the garden, its a conversion from feet to pixels 
	 * @param posOfSelectedPlant position of the plant to check if it can fit
	 * @param plot the plot to get any other plants from
	 * @return true/false if plant can be placed here
	 */
	public static boolean canPlantBePlaced(double scale, Point posOfSelectedPlant, Point originalPosition, double radius, Plot plot) {
		// Base check
		if (plot.getPlantsInPlot().size() == 0)
			return true;
		for (Map.Entry<Point, Plant> entry : plot.getPlantsInPlot().entrySet()) {
			Point pos = entry.getKey();
			Plant plant = entry.getValue();
			//skip to next iteration if the plant is the same
			if(plant.getPosition().equals(originalPosition)) {
				System.out.println("SAME PLANT");
				continue;
			}
			double secondRadius = plant.getSpreadRadiusLower();
			if (secondRadius == 0)
				secondRadius = plant.getSizeLower();
			System.out.println("Distance: " + (pos.distance(posOfSelectedPlant)));
			System.out.println("Rad + scale: " + (radius * scale));
			System.out.println("2nd Rad + scale: " + (secondRadius * scale));
			System.out.println("R1 + R2: " + (radius * scale + secondRadius * scale));
			if (radius * scale + secondRadius * scale > pos.distance(posOfSelectedPlant)) {
				return false;
			}
		}
		return true;
	}
}