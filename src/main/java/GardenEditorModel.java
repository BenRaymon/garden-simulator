public class GardenEditorModel extends Model {
	
	Plant currentlySelectedPlant;
	
	Plot currentPlot;
	
	String sortBy;
	
	Garden garden;
	
	public GardenEditorModel(Garden g) {
		garden = g;
	}
	
	public Garden getGarden() {
		return garden;
	}
	
	public Plant selectPlant() {
		return null;
	}
	
	public int placePlant() {
		return 0;
	}
	
	public int isValidPlacement(Point point) {
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