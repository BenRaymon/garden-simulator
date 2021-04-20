public class GardenEditor {
	
	private static Plant currentlySelectedPlant;
	
	Plot currentPlot;
	
	String sortBy;
	
	Garden garden;
	
	public static void setSelectedPlant(String plantName) {
		currentlySelectedPlant = Garden.getPlant(plantName);
	}
	
	public static Plant getSelectedPlant() {
		return currentlySelectedPlant;
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