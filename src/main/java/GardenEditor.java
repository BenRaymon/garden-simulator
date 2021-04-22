import java.util.*;
import java.lang.*;

public class GardenEditor {
	
	private static Plant currentlySelectedPlant;
	
	Plot currentPlot;
	
	String sortBy;
	
	Garden garden;
	static double cx, cy, scale;
	
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
	
	public static void getAllPlotBoundaries(ArrayList<Plot> plots) {
		
		Iterator<Plot> it = plots.iterator();
		double left=0, right=0, top=0, bottom=0;
		
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
				
		scale = 700 / (bottom-top);
		if ((1200-200-150-100)/(right-left) < scale) {
			scale = (1200-200-150-100)/(right-left);
		}
		
		it = plots.iterator();
		while(it.hasNext()) {
			ArrayList<Point> points = it.next().getCoordinates();
			Iterator<Point> itp = points.iterator();
			while(itp.hasNext()) {
				Point p = itp.next();
				p.setX((p.getX() - cx)*scale + cx);
				p.setY((p.getY() - cy)*scale + cy);
			}
		}
		
		right = (right-cx)*scale + cx;
		left = (left-cx)*scale + cx;
		top = (top-cy)*scale + cy;
		bottom = (bottom-cy)*scale + cy;
		
		double xDist=0,yDist=0;
		
		if(left < 0) {
			xDist = left * -1 + 10;
		}
		double CANVAS_WIDTH = 1200-150-200; //totalWidth - rightbar - leftbar
		if(right > CANVAS_WIDTH) {
			xDist = -1 * (right - CANVAS_WIDTH + 10);
		}
		
		if(top < 100) { // < top heighht
			yDist = (100 - top) + 10;
		}
		double CANVAS_HEIGHT = 950 - 100; //height - top bar
		if(bottom > CANVAS_HEIGHT) {
			yDist = -1 * (bottom - CANVAS_HEIGHT + 10);
		}
		
		
		it = plots.iterator();
		while(it.hasNext()) {
			ArrayList<Point> points = it.next().getCoordinates();
			Iterator<Point> itp = points.iterator();
			while(itp.hasNext()) {
				Point p = itp.next();
				p.setX((p.getX() + xDist));
				p.setY((p.getY() + yDist));
			}
		}
		//xcords[i] = (p.getX() -cx)*GardenEditor.scale +cx - 195;
		//ycords[i++] = (p.getY() -cy)*GardenEditor.scale+cy - 100;
		
		
		
	}
	
	public static void test(Plot pl) {
		Point zero = null, one = null, two = null, three = null;

		for( int a = 0; a < 2; a ++) {
			
			ArrayList<Point> points = pl.getCoordinates();
			ArrayList<Point> np = new ArrayList<Point>();
			Iterator<Point> itp = points.iterator();
			int x = 0;
			while(itp.hasNext()) {
				Point p = itp.next();
				if(x%4 == 0)
					zero = p;
				if(x%4 == 1)
					one = p;
				if(x%4 == 2)
					two = p;
				if(x%4 == 3) {
					three = p;
					Points(np, zero, one, two, three);
				}
				x++;
			}
			pl.setCoordinates(np);
			
		}
	}
	
	public static void Points(ArrayList<Point> np, Point zero, Point one, Point two, Point three) {
		// Assume we need to calculate the control
	    // points between (x1,y1) and (x2,y2).
	    // Then x0,y0 - the previous vertex,
	    //      x3,y3 - the next one.

		double x0 = zero.getX();
		double x1 = one.getX();
		double x2 = two.getX();
		double x3 = three.getX();

		double y0 = zero.getY();
		double y1 = one.getY();
		double y2 = two.getY();
		double y3 = three.getY();
		
	    double xc1 = (x0 + x1) / 2.0;
	    double yc1 = (y0 + y1) / 2.0;
	    double xc2 = (x1 + x2) / 2.0;
	    double yc2 = (y1 + y2) / 2.0;
	    double xc3 = (x2 + x3) / 2.0;
	    double yc3 = (y2 + y3) / 2.0;

	    double len1 = Math.sqrt((x1-x0) * (x1-x0) + (y1-y0) * (y1-y0));
	    double len2 = Math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));
	    double len3 = Math.sqrt((x3-x2) * (x3-x2) + (y3-y2) * (y3-y2));

	    double k1 = len1 / (len1 + len2);
	    double k2 = len2 / (len2 + len3);

	    double xm1 = xc1 + (xc2 - xc1) * k1;
	    double ym1 = yc1 + (yc2 - yc1) * k1;

	    double xm2 = xc2 + (xc3 - xc2) * k2;
	    double ym2 = yc2 + (yc3 - yc2) * k2;
	    
	    double smooth_value = 1;

	    // Resulting control points. Here smooth_value is mentioned
	    // above coefficient K whose value should be in range [0...1].
	    double ctrl1_x = xm1 + (xc2 - xm1) * smooth_value + x1 - xm1;
	    double ctrl1_y = ym1 + (yc2 - ym1) * smooth_value + y1 - ym1;

	    double ctrl2_x = xm2 + (xc2 - xm2) * smooth_value + x2 - xm2;
	    double ctrl2_y = ym2 + (yc2 - ym2) * smooth_value + y2 - ym2;
	    
	    curve(np, zero, new Point(ctrl1_x, ctrl1_y), new Point(ctrl2_x, ctrl2_y), three);
	    
	}
	
	public static void curve(ArrayList<Point> points, Point p1, Point c1, Point c2, Point p2) {
		
		
		int NUM_STEPS = 200;
		
		double x1 = p1.getX();
		double x2 = c1.getX();
		double x3 = c2.getX();
		double x4 = p2.getX();
		
		double y1 = p1.getY();
		double y2 = c1.getY();
		double y3 = c2.getY();
		double y4 = p2.getY();
		
		double dx1 = x2 - x1;
	    double dy1 = y2 - y1;
	    double dx2 = x3 - x2;
	    double dy2 = y3 - y2;
	    double dx3 = x4 - x3;
	    double dy3 = y4 - y3;

	    double subdiv_step  = 1.0 / (NUM_STEPS + 1);
	    double subdiv_step2 = subdiv_step*subdiv_step;
	    double subdiv_step3 = subdiv_step*subdiv_step*subdiv_step;

	    double pre1 = 3.0 * subdiv_step;
	    double pre2 = 3.0 * subdiv_step2;
	    double pre4 = 6.0 * subdiv_step2;
	    double pre5 = 6.0 * subdiv_step3;

	    double tmp1x = x1 - x2 * 2.0 + x3;
	    double tmp1y = y1 - y2 * 2.0 + y3;

	    double tmp2x = (x2 - x3)*3.0 - x1 + x4;
	    double tmp2y = (y2 - y3)*3.0 - y1 + y4;

	    double fx = x1;
	    double fy = y1;

	    double dfx = (x2 - x1)*pre1 + tmp1x*pre2 + tmp2x*subdiv_step3;
	    double dfy = (y2 - y1)*pre1 + tmp1y*pre2 + tmp2y*subdiv_step3;

	    double ddfx = tmp1x*pre4 + tmp2x*pre5;
	    double ddfy = tmp1y*pre4 + tmp2y*pre5;

	    double dddfx = tmp2x*pre5;
	    double dddfy = tmp2y*pre5;

	    int step = NUM_STEPS;

	    // Suppose, we have some abstract object Polygon which
	    // has method AddVertex(x, y), similar to LineTo in
	    // many graphical APIs.
	    // Note, that the loop has only operation add!
	    while(step-- > 0)
	    {
	        fx   += dfx;
	        fy   += dfy;
	        dfx  += ddfx;
	        dfy  += ddfy;
	        ddfx += dddfx;
	        ddfy += dddfy;
	        points.add(new Point(fx, fy));
	    }
        points.add(new Point(x4, y4)); //Last step must go exactly to x4, y4
        
	}
	
	
	public GardenEditor(Garden g) {
		garden = g;
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