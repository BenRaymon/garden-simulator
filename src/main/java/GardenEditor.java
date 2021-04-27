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
	 * Set the currentlySelectedPlant for while dragging a plant from the plant list to a plot
	 * @param plots an array list of plots that are in the garden
	 * @param canvasWidth the width of the canvas to scale to
	 * @param canvasHeight the height of the canvas to scale to
	 * @param top the height of the top bar used for  
	 * @return pixels per foot, a value representing the scale of the garden
	 */
	public static double transformPlots(ArrayList<Plot> plots, double canvasWidth, double canvasHeight, double ppf) {
		CANVAS_HEIGHT = canvasHeight;
		CANVAS_WIDTH = canvasWidth;
		//TOP_HEIGHT = top;
		calculatePlotBoundaries(plots);
		ppf *= calculateScale();
		scalePlots(plots);
		checkBorders(plots);
		
		return ppf;
	}
	
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
	
	public static double calculateScale() {
		scale = (CANVAS_HEIGHT - SCALE_BUFFER) / (bottom-top);
		if ((CANVAS_WIDTH-SCALE_BUFFER)/(right-left) < scale) {
			scale = (CANVAS_WIDTH-SCALE_BUFFER)/(right-left);
		}
		System.out.println("Scale factor: "+ scale);
		return scale;
	}
	
	private static void scalePlots(ArrayList<Plot> plots) {
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
	
	private static void checkBorders(ArrayList<Plot> plots) {
		right = (right-cx)*scale + cx;
		left = (left-cx)*scale + cx;
		top = (top-cy)*scale + cy;
		bottom = (bottom-cy)*scale + cy;
		
		double xDist=0,yDist=0;
		
		if(cx != CANVAS_WIDTH / 2) 
			xDist += (CANVAS_WIDTH / 2 - cx);
		
		if(cy != CANVAS_HEIGHT / 2) 
			yDist += (CANVAS_HEIGHT / 2 - cy);
		

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
	

	
	//returns the plotNum that the position falls in (-1 if out of bounds of all plots)
	public static int inPlot(Point pos, ArrayList<Plot> plots) {
		boolean inAPlot = false;
		int plotNum = 0;
		for (int i = 0; i < plots.size(); i++) {
			boolean result = inBounds(plots.get(i).getCoordinates(), pos.getX()-200, pos.getY()-100);
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
	
	//check if a Point falls within the bounds of a list of points
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
	
	
	private static ArrayList<ArrayList<Point>> getControlPoints(ArrayList<Point> coords, int N, double alpha) {
		if (alpha < 0.0 || alpha > 1.0) {
			throw new IllegalArgumentException("alpha must be a value between 0 and 1 inclusive");
		}
		  
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


	public static ArrayList<Point> smooth(ArrayList<Point> points, double alpha, int pointsPerSegment) {
	    
		int Nvertices = points.size();
		ArrayList<ArrayList<Point>> controlPoints = getControlPoints(points, Nvertices, alpha);
		
		ArrayList<Point> smoothCoords = new ArrayList<Point>();
		  
		for (int i = 0, k = 0; i < Nvertices; i++) {
		    int next = (i + 1) % Nvertices;  
		      
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
	
	
	public Plant selectPlant() {
		return null;
	}
	
	public int placePlant() {
		return 0;
	}
	
	public static int isValidPlacement(double x, double y) {
		
		return 0;
	}
	
	public int updateStats() {
		return 0;
	}
	
	public int search() {
		return 0;
	}
	
	public int setPos(Point point) {
		return 0;
	}
}