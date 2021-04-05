import java.util.*;

public abstract class Model {
	
	Garden ourGarden;
	
	HashMap<String, Plant> allPlants = new HashMap<String, Plant>();
	
	HashMap<String, Lep> allLeps = new HashMap<String, Lep>();
	
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