import java.util.ArrayList;

import java.io.Serializable;

public class Lep implements Serializable {
	
	private String lepFamily;
	private String lepName;
	private String hostPlant;
	private String country;
	
	
	
	public Lep(String tempLepFamily, String tempLepName, String tempHostPlant, String tempCountry) {
		lepFamily = tempLepFamily;
		lepName = tempLepName;
		hostPlant = tempHostPlant;
		country = tempCountry;
		
		
		
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



	public void setHostPlant(String hostPlant) {
		this.hostPlant = hostPlant;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}
	
}