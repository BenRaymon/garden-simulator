import java.io.Serializable;

public class Point implements Serializable{
	
	private double xCord;
	private double yCord;
	
	/**
	 * Empty constructor for Point
	 */
	public Point(){
		xCord = 0;
		yCord = 0;
	}
	
	/**
	 * Constructor for Point
	 * @param x
	 * @param y
	 */
	public Point(double x, double y) {
		xCord=x;
		yCord=y;
	}
	
	/**
	 * Calculates the distance between the point
	 * @param p
	 * @return double distance 
	 */
	public double distance(Point p) {
		return Math.sqrt((this.xCord-p.getX()) * (this.xCord-p.getX()) + (this.yCord-p.getY()) * (this.yCord-p.getY()));
	}
	
	/**
	 * Getter for x coordinate
	 * @return x coordinate value
	 */
	public double getX() {
		return xCord;
	}
	
	/**
	 * Getter for y coordinate
	 * @return y coordinate value
	 */
	public double getY() {
		return yCord;
	}
	
	/**
	 * Setter for x coordinate 
	 * @param x
	 */
	public void setX(double x) {
		xCord = x;
	}
	
	/**
	 * Setter for y coordinate
	 * @param y
	 */
	public void setY(double y) {
		yCord = y;
	}
	
	/**
	 * Checks for valid point
	 * @return boolean 
	 */
	public boolean isValid(){
		return true;
	}
	
	/**
	 * Checks for valid boundary
	 * @return boolean
	 */
	public boolean checkBoundry() {
		return true;
	}
	
	/**
	 * Point toString
	 * @Return String
	 */
	public String toString() {
		return "("+ xCord + ", " + yCord + ")";
	}
	
	/**
	 * Point equals method
	 * @return boolean
	 */
	@Override
	//two points are equal if the x and y coordinates are the same
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point)o;
			return this.xCord == p.getX() && this.yCord == p.getY();
		}
		else return false;
	}
	
	/**
	 * Point hash code
	 * @return hash value
	 */
	@Override
	public int hashCode() {
		return (int)(this.getX());
	}
	
}