import java.io.Serializable;

public class Point implements Serializable{
	
	private double xCord;
	private double yCord;
	
	public Point(){
		xCord = 0;
		yCord = 0;
	}
	
	public Point(double x, double y) {
		xCord=x;
		yCord=y;
	}
	
	public double getX() {
		return xCord;
	}
	
	public double getY() {
		return yCord;
	}
	
	public void setX(double x) {
		xCord = x;
	}
	
	public void setY(double y) {
		yCord = y;
	}
	
	public boolean isValid(){
		return true;
	}
	
	public boolean checkBoundry() {
		return true;
	}
	
	public String toString() {
		return "("+ xCord + ", " + yCord + ")";
	}
	
	@Override
	//two points are equal if the x and y coordinates are the same
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point)o;
			System.out.println("IN EQUALS");
			return this.xCord == p.getX() && this.yCord == p.getY();
		}
		else return false;
	}
	
	@Override
	public int hashCode() {
		return (int)(this.getX());
	}
	
}