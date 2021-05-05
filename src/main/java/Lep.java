import java.util.ArrayList;

import java.io.Serializable;

public class Lep implements Serializable {
	
	private String lepFamily;
	private String lepName;
	private String hostPlant;
	private String country;
	
	
	
	public Lep(String tempLepFamily, String tempLepName, String tempHostPlant) {
		lepFamily = tempLepFamily;
		lepName = tempLepName;
		hostPlant = tempHostPlant;
		
		
		
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



	public String getHostPlant() {
		return hostPlant;
	}



	public void addHostPlant(String host) {
		hostPlant = host;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}
	
}