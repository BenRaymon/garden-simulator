import java.util.*;

public abstract class Model {
	
	private Garden ourGarden;
	
	private static HashMap<String, Plant> allPlants = new HashMap<String, Plant>();
	
	public HashMap getPlants() {
		return allPlants;
	}
	
	public int update() {
		return 0;
	}
	
	public int serializeData() {
		return 0;
	}
	
	public int saveData() {
		return 0;
	}
	
	public int loadData() {
		return 0;
	}
	
}