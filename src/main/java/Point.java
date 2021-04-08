public class Point{
	
	private double xCord;
	private double yCord;
	
	public  Point(){
		xCord = 0;
		yCord = 0;
	}
	
	public double getxCord() {
		return xCord;
	}
	
	public double getyCord() {
		return yCord;
	}
	
	public void setxCord(double x) {
		xCord = x;
	}
	
	public void setyCord(double y) {
		yCord = y;
	}
	
	public boolean isValid(){
		return true;
	}
	
	public boolean checkBoundry() {
		return true;
	}
	
}