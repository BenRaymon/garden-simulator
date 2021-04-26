public class PlantShoppingListData {
	private int count;
	private int cost;
	private String common_name;
	private String scientific_name;
	
	public PlantShoppingListData(int count, int cost, String cname, String sname) {
		super();
		this.count = count;
		// Cost is the cost of the entire stack of plants here, not a single one.
		// So it is price x count
		this.cost = cost;
		this.common_name = cname;
		this.scientific_name = sname;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getCommonName() {
		return common_name;
	}
	public void setCommonName(String common_name) {
		this.common_name = common_name;
	}
	public String getScientificName() {
		return scientific_name;
	}
	public void setScientificName(String scientific_name) {
		this.scientific_name = scientific_name;
	}
	
}
