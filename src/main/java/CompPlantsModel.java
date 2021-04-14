import java.util.HashMap;

public class CompPlantsModel extends Model{
	
	Plant plantA;
	Plant plantB;
	private static HashMap<String, Plant> allPlants;

	
	public int compare() {
		return 0;
	}
	
	public Plant getPlant(String s) {
		allPlants = getPlants();
		return allPlants.get(s);
	}
	
	public String getInfo(String s) {
		Plant p = getPlant(s);
		return p.toString();
	}
	
	
}