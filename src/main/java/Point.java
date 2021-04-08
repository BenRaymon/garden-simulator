public class Point{
	
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
	
}