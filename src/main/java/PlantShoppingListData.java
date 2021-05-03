public class PlantShoppingListData {
	private int count;
	private double cost;
	private String common_name;
	private String scientific_name;
	
	/**
	 * Constructor for PlantShoppingListData
	 * @param count
	 * @param cost
	 * @param cname
	 * @param sname
	 */
	public PlantShoppingListData(int count, double cost, String cname, String sname) {
		super();
		this.count = count;
		// Cost is the cost of the entire stack of plants here, not a single one.
		// So it is price x count
		this.cost = cost;
		this.common_name = cname;
		this.scientific_name = sname;
	}
	
	/**
	 * Getter for plant count
	 * @return count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Setter for plant count
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * Updates plant count
	 * @param up
	 */
	public void updateCount(int up) {
		this.count += up;
	}
	
	/**
	 * Getter for plant cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Setter for plant cost
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * Updates cost
	 * @param up
	 */
	public void updateCost(double up) {
		this.cost += up;
	}
	
	/**
	 * Getter for plant common name
	 * @return common_name
	 */
	public String getCommonName() {
		return common_name;
	}
	
	/**
	 * Setter for common name
	 * @param common_name
	 */
	public void setCommonName(String common_name) {
		this.common_name = common_name;
	}
	
	/**
	 * Getter for scientific name
	 * @return scientific_name
	 */
	public String getScientificName() {
		return scientific_name;
	}
	
	/**
	 * Setter for scientific name
	 * @param scientific_name
	 */
	public void setScientificName(String scientific_name) {
		this.scientific_name = scientific_name;
	}
	
}
