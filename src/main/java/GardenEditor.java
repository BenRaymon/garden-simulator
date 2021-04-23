import java.util.*;
import java.lang.*;

public class GardenEditor {
	
	private static Plant currentlySelectedPlant;
	private static double cx, cy, scale, left, right, top, bottom;
	private static double CANVAS_WIDTH, CANVAS_HEIGHT, TOP_HEIGHT;
	private static double SCALE_BUFFER = 100, BUFFER = 10;
	
	
	public static void setSelectedPlant(String plantName, Point pos) {
		Plant p = Garden.getPlant(plantName);
		p.setPosition(pos);
		currentlySelectedPlant = Garden.getPlant(plantName);
	}
	
	public static void setSelectedPlant(Plant p) {
		currentlySelectedPlant = p;
	}
	
	public static Plant getSelectedPlant() {
		return currentlySelectedPlant;
	}
	
	public static void transformPlots(ArrayList<Plot> plots, double canvasWidth, double canvasHeight, double top) {
		CANVAS_HEIGHT = canvasHeight;
		CANVAS_WIDTH = canvasWidth;
		TOP_HEIGHT = top;
		calculatePlotBoundaries(plots);
		calculateScale();
		scalePlots(plots);
		checkBorders(plots);
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
	
	public static void calculateScale() {
		scale = (CANVAS_HEIGHT - SCALE_BUFFER) / (bottom-top);
		if ((CANVAS_WIDTH-SCALE_BUFFER)/(right-left) < scale) {
			scale = (CANVAS_WIDTH-SCALE_BUFFER)/(right-left);
		}
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
	
	
	private static Point[][] getControlPoints(ArrayList<Point> coords, int N, double alpha) {
	      if (alpha < 0.0 || alpha > 1.0) {
	          throw new IllegalArgumentException("alpha must be a value between 0 and 1 inclusive");
	      }

	      Point[][] ctrl = new Point[N][2];

	      Point[] v = new Point[3];

	      Point[] mid = new Point[2];
	      mid[0] = new Point();
	      mid[1] = new Point();

	      Point anchor = new Point();
	      double[] vdist = new double[2];
	      double mdist;

	      v[1] = coords.get(N-1);
	      v[2] = coords.get(0);
	      mid[1].setX((v[1].getX() + v[2].getX()) / 2.0);
	      mid[1].setY((v[1].getY() + v[2].getY()) / 2.0);
	      vdist[1] = v[1].distance(v[2]);

	      for (int i = 0; i < N; i++) {
	          v[0] = v[1];
	          v[1] = v[2];
	          v[2] = coords.get((i + 1) % N);

	          mid[0].setX(mid[1].getX());
	          mid[0].setY( mid[1].getY());
	          mid[1].setX((v[1].getX() + v[2].getX()) / 2.0);
	          mid[1].setY((v[1].getY() + v[2].getY()) / 2.0);

	          vdist[0] = vdist[1];
	          vdist[1] = v[1].distance(v[2]);

	          double p = vdist[0] / (vdist[0] + vdist[1]);
	          anchor.setX(mid[0].getX() + p * (mid[1].getX() - mid[0].getX()));
	          anchor.setY(mid[0].getY() + p * (mid[1].getY() - mid[0].getY()));

	          double xdelta = anchor.getX() - v[1].getX();
	          double ydelta = anchor.getY() - v[1].getY();

	          ctrl[i][0] = new Point(
	                  alpha*(v[1].getX() - mid[0].getX() + xdelta) + mid[0].getX() - xdelta,
	                  alpha*(v[1].getY() - mid[0].getY() + ydelta) + mid[0].getY() - ydelta);

	          ctrl[i][1] = new Point(
	                  alpha*(v[1].getX() - mid[1].getX() + xdelta) + mid[1].getX() - xdelta,
	                  alpha*(v[1].getY() - mid[1].getY() + ydelta) + mid[1].getY() - ydelta);
	      }

	      return ctrl;
	}
	
	private static Point[] cubicBezier(final Point start, final Point end,
	      final Point ctrl1, final Point ctrl2, final int nv) {
	            
	      final Point[] curve = new Point[nv];
	    
	      final Point[] buf = new Point[3];
	      for (int i = 0; i < buf.length; i++) {
	          buf[i] = new Point();
	      }
	    
	      //curve[0] = new Point(start);
	      //curve[nv - 1] = new Point(end);

	      for (int i = 1; i < nv-1; i++) {
	          double t = (double) i / (nv - 1);
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

	          curve[i] = c;
	      }
	      return curve;
	  }
	
	
	public static ArrayList<Point> smooth(ArrayList<Point> points, double alpha, int pointsPerSegment) {
	    
		  int Nvertices = points.size();
	      Point[][] controlPoints = getControlPoints(points, Nvertices, alpha);
	    
	      Point[] smoothCoords = new Point[Nvertices * pointsPerSegment];
	      for (int i = 0, k = 0; i < Nvertices; i++) {
	          int next = (i + 1) % Nvertices;
	        
	          Point[] segment = cubicBezier(
	                  points.get(i), points.get(next),
	                  controlPoints[i][1], controlPoints[next][0],
	                  pointsPerSegment);
	        
	          for (int j = 0; j < pointsPerSegment; j++, k++) {
	              smoothCoords[k] = segment[j];
	          }
	      }
	      
	      ArrayList<Point> newPoints = new ArrayList<Point>();
	      
	      for (int i = 0; i < smoothCoords.length; i++) {
	    	  if (smoothCoords[i] != null) {
	    		  newPoints.add(smoothCoords[i]);
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