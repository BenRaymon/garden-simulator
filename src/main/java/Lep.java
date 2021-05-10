import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

public class Lep implements Serializable {
	
	private String lepFamily;
	private String lepName;
	private Set<String> hostPlantFamily;
	private Set<String> hostPlantName;
	private String country;
	
	
	
	public Lep(String tempLepFamily, String tempLepName, String tempHostPlantF, String tempHostPlantN, String tempCountry) {
		lepFamily = tempLepFamily;
		lepName = tempLepName;
		hostPlantFamily = new HashSet<String>();
		hostPlantFamily.add(tempHostPlantF);
		hostPlantName = new HashSet<String>();
		hostPlantName.add(tempHostPlantN);
		country = tempCountry;
	}

	
	public void addHostFamily(String family) {
		hostPlantFamily.add(family);
	}
	public void addHostName(String name) {
		hostPlantName.add(name);
	}

	public String getLepFamily() {
		return lepFamily;
	}



	public void setLepFamily(String lepFamily) {
		this.lepFamily = lepFamily;
	}



	public String getLepName() {
		return lepName;
	}



	public void setLepName(String lepName) {
		this.lepName = lepName;
	}

	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}
	
}