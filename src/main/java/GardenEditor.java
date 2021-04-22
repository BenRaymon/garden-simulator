import java.util.*;

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
		System.out.println("L: " + left + " R: " + right + "T: " + top + "B: " + bottom + " " + cx + "  " + cy);
		
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
				if(p.getX() > 1100) {
					//p.setX(p.getX() - right*scale - 1100);
				}
				if(p.getY() < 100) {
					//p.setY(p.getY() + top*scale - 100);
				}
			}
		}
		//xcords[i] = (p.getX() -cx)*GardenEditor.scale +cx - 195;
		//ycords[i++] = (p.getY() -cy)*GardenEditor.scale+cy - 100;
		
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