import java.util.ArrayList;

import java.io.Serializable;

public class Lep implements Serializable {
	
	private String lepFamily;
	private String lepName;
	private String hostPlantFamily;
	private String hostPlantName;
	private String country;
	
	
	
	public Lep(String tempLepFamily, String tempLepName, String tempHostPlantF, String tempHostPlantN, String tempCountry) {
		lepFamily = tempLepFamily;
		lepName = tempLepName;
		hostPlantFamily = tempHostPlantF;
		hostPlantName = tempHostPlantN;
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



	public String getHostPlantFamily() {
		return hostPlantFamily;
	}



	public void setHostPlantFamily(String host) {
		hostPlantFamily = host;
	}


	public String getHostPlantName() {
		return hostPlantName;
	}


	public void setHostPlantName(String host) {
		hostPlantName = host;
	}

	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}
	
}