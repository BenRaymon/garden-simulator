import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

public class Lep implements Serializable {
	
	private String lepFamily;
	private String lepName;
	private Set<String> hostPlantFamily;
	private Set<String> hostPlantName;
	private Set<String> countries;
	
	
	
	public Lep(String tempLepFamily, String tempLepName, String tempHostPlantF, String tempHostPlantN, String tempCountry) {
		lepFamily = tempLepFamily;
		lepName = tempLepName;
		hostPlantFamily = new HashSet<String>();
		hostPlantFamily.add(tempHostPlantF);
		hostPlantName = new HashSet<String>();
		hostPlantName.add(tempHostPlantN);
		countries = new HashSet<String>();
		countries.add(tempCountry);
	}

	/**
	 * Add Lep Host Family
	 * @param family
	 */
	public void addHostFamily(String family) {
		hostPlantFamily.add(family);
	}
	
	/**
	 * add Lep Host Name
	 * @param name
	 */
	public void addHostName(String name) {
		hostPlantName.add(name);
	}

	/**
	 * Getter Lep Family()
	 * @return
	 */
	public String getLepFamily() {
		return lepFamily;
	}

	/**
	 * Setter for Lep Family()
	 * @param lepFamily
	 */
	public void setLepFamily(String lepFamily) {
		this.lepFamily = lepFamily;
	}

	/**
	 * Getter for LepName
	 * @return lepName
	 */
	public String getLepName() {
		return lepName;
	}

	/**
	 * Setter for Lep Name
	 * @param lepName
	 */
	public void setLepName(String lepName) {
		this.lepName = lepName;
	}

	/**
	 * Getter for Lep Countries
	 * @return
	 */
	public Set<String> getCountries() {
		return countries;
	}

	/**
	 * Adds lep country
	 * @param country
	 */
	public void addCountry(String country) {
		countries.add(country);
	}
	
	/**
	 * Getter for Host Plant family
	 * @return
	 */
	public Set<String> getFamilies(){
		return hostPlantFamily;
	}
	
	/**
	 * Getter for Host Plant Name
	 * @return
	 */
	public Set<String> getNames(){
		return hostPlantName;
	}
	
	/**
	 * Setter for Host Plant Family
	 * @param families
	 */
	public void setLepFamilies(HashSet<String> families) {
		this.hostPlantFamily = families;
	}
}