import java.util.ArrayList;
import java.util.Comparator;
import java.io.Serializable;

public class Plant implements Serializable, Comparable<Plant>{
	
	
	private Point position;
	private String commonName;
	private String scientificName;
	private String family;
	private String color;
	private double sizeLower;
	private double  sizeUpper;
	private double spreadRadiusLower;
	private double spreadRadiusUpper;
	private Options options;
	private double cost;
	private int lepsSupported;
	private char type;
	private String genera;
	
	/**
	 * Creates an instance of plant. 
	 * @param commonName
	 * @param scientificName
	 * @param genera
	 * @param family
	 * @param color
	 * @param sizeLower
	 * @param sizeUpper
	 * @param spreadRadiusLower
	 * @param spreadRadiusUpper
	 * @param options
	 * @param cost
	 * @param lepsSupported
	 * @param type
	 * @return None
	 */
	public Plant(String commonName, String scientificName, String genera, String family, String color, double sizeLower, double sizeUpper, 
			double spreadRadiusLower, double spreadRadiusUpper, Options options, double cost, int lepsSupported, char type) {
		this.commonName = commonName;
		this.scientificName = scientificName;
		this.genera = genera;
		this.family = family;
		this.color = color;
		this.sizeLower = sizeLower;
		this.sizeUpper = sizeUpper;
		this.spreadRadiusLower = spreadRadiusLower;
		this.spreadRadiusUpper = spreadRadiusUpper;
		this.options = options;
		this.lepsSupported = lepsSupported;
		this.cost = cost;
		this.type=type;
		this.position = new Point(0,0);
	}

	public Plant() {
		
	}
	
	/**
	 * Clones a copy of the plant in the static list, so a copy can be used in the garden.
	 * @return Plant p
	 */
	public Plant clone() {
		Plant p = new Plant();
		p.commonName = commonName;
		p.scientificName = scientificName;
		p.genera = genera;
		p.family = family;
		p.color = color;
		p.sizeLower = sizeLower;
		p.sizeUpper = sizeUpper;
		p.spreadRadiusLower = spreadRadiusLower;
		p.spreadRadiusUpper = spreadRadiusUpper;
		p.options = options;
		p.lepsSupported = lepsSupported;
		p.cost = cost;
		p.type=type;
		p.position = position;
		return p;
	}

	/**
	 * Creates a plant toString. 
	 * @param None
	 * @return String toString
	 */
	public String toString() {
		String temp = commonName + "\n";
		temp = temp + scientificName + "\n";
		temp = temp + family + "\n";
		temp = temp + color + "\n";
		temp = temp + sizeLower + "\n";
		temp = temp + sizeUpper + "\n";
		temp = temp + spreadRadiusLower + "\n";
		temp = temp + spreadRadiusUpper + "\n";
		return temp;
	}


	/**
	 *Position getter. 
	 * @return Point position
	 */
	public Point getPosition() {
		return position;
	}



	/**
	 * Position setter.
	 * @param Point position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * Genera setter
	 * @return String genera
	 */
	public String getGenera() {
		return genera;
	}



	/**
	 * Genera setter.
	 * @param String genera
	 */
	public void setGenera(String genera) {
		this.genera = genera;
	}


	/**
	 * Common Name getter.
	 * @return String commonName
	 */
	public String getCommonName() {
		return commonName;
	}



	/**
	 * Common Name setter.
	 * 
	 * @param String commonName
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}



	/**
	 * ScientificName getter. 
	 * @return String scientificName
	 */
	public String getScientificName() {
		return scientificName;
	}



	/**
	 * ScientificName setter. 
	 * @param String scientificName
	 */
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}



	/**
	 * Family getter. 
	 * @return String family
	 */
	public String getFamily() {
		return family;
	}



	/**
	 * Family setter. 
	 * @param String family
	 */
	public void setFamily(String family) {
		this.family = family;
	}



	/**
	 * Color getter. 
	 * @return String color
	 */
	public String getColor() {
		return color;
	}



	/**
	 * Color setter. 
	 * @param String color
	 */
	public void setColor(String color) {
		this.color = color;
	}



	/**
	 * SizeLower getter. 
	 * @return double sizeLower
	 */
	public double getSizeLower() {
		return sizeLower;
	}



	/**
	 * SizeLower setter. 
	 * @param int sizeLower
	 */
	public void setSizeLower(int sizeLower) {
		this.sizeLower = sizeLower;
	}



	/**
	 * SizeUpper getter. 
	 * @return double sizeUpper
	 */
	public double getSizeUpper() {
		return sizeUpper;
	}



	/**
	 * SizeUpper setter. 
	 * @param int sizeUpper
	 */
	public void setSizeUpper(int sizeUpper) {
		this.sizeUpper = sizeUpper;
	}



	/**
	 * SpreadRadiusLower getter. 
	 * @return double spreadRadiusLower
	 */
	public double getSpreadRadiusLower() {
		return spreadRadiusLower;
	}



	/**
	 * SpreadRadiusLower setter
	 * @param double spreadRadiusLower
	 */
	public void setSpreadRadiusLower(double spreadRadiusLower) {
		this.spreadRadiusLower = spreadRadiusLower;
	}



	/**
	 * SpreadRadiusUpper getter. 
	 * @return double spreadRadiusUpper
	 */
	public double getSpreadRadiusUpper() {
		return spreadRadiusUpper;
	}



	/**
	 * SpreadRadiusUpper setter. 
	 * @param double spreadRadiusUpper
	 */
	public void setSpreadRadiusUpper(double spreadRadiusUpper) {
		this.spreadRadiusUpper = spreadRadiusUpper;
	}



	/**
	 * Options getter. 
	 * @return Options options
	 */
	public Options getOptions() {
		return options;
	}

	//TODO
	public String getSoilTypes() {
		String soils = "";
		int[] soilTypes = options.getSoilTypes();
		if (soilTypes[0] == 1) {
			soils += "Clay, ";
		} 
		if (soilTypes[1] == 1) {
			soils += "Loam, ";
		}
		if (soilTypes[2] == 1) {
			soils += "Sand, ";
		}
		soils = soils.substring(0, soils.length() - 2);
		return soils;
	}
	
	//TODO
	public String getSunlightLevels() {
		String sunlight = "";
		int[] sunlevels = options.getSunLevels();
		if (sunlevels[0] == 1) {
			sunlight += "Shade, ";
		} 
		if (sunlevels[1] == 1) {
			sunlight += "Partial Sun, ";
		}
		if (sunlevels[2] == 1) {
			sunlight += "Full Sun, ";
		}
		sunlight = sunlight.substring(0, sunlight.length() - 2);
		return sunlight;
	}
		
	//TODO
	public String getMoistures() {
		String moistures = "";
		int[] moistureLevels = options.getSoilTypes();
		if (moistureLevels[0] == 1) {
			moistures += "Dry, ";
		} 
		if (moistureLevels[1] == 1) {
			moistures += "Moist, ";
		}
		if (moistureLevels[2] == 1) {
			moistures += "Wet, ";
		}
		moistures = moistures.substring(0, moistures.length() - 2);
		return moistures;
	}


	/**
	 * Options setter. 
	 * @param Options options
	 */
	public void setOptions(Options options) {
		this.options = options;
	}



	/**
	 * Cost getter. 
	 * @return double cost
	 */
	public double getCost() {
		return cost;
	}



	/**
	 * Cost setter. 
	 * @param double cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}



	/**
	 * LepsSupported getter. 
	 * @return int lepsSupported
	 */
	public int getLepsSupported() {
		return lepsSupported;
	}



	/**
	 * LepsSupported setter. 
	 * @param int lepsSupported
	 */
	public void setLepsSupported(int lepsSupported) {
		this.lepsSupported = lepsSupported;
	}



	/**
	 * Type getter.
	 * @return char type
	 */
	public char getType() {
		return type;
	}



	/**
	 * Type setter. 
	 * @param char type
	 */
	public void setType(char type) {
		this.type = type;
	}
	
	/**
	 * Checks if a plant passed in is equal to the plant the method was called from.
	 * @param Object o
	 * @return boolean
	 */
	@Override
	//two plants are considered equal (as of right now) if they're locations are the same

	public boolean equals(Object o) {
		if (o instanceof Plant) {
			Plant p = (Plant)o;
			return p.getPosition().equals(this.position);
		} else return false;
	}
	
	/**
	 * Returns x position of the plant object this is called from. 
	 * @return int hashCode
	 */
	@Override
	public int hashCode() {
		return (int)(this.getPosition().getX());
	}

	
	public static Comparator<Plant> ButterflyComparator = new Comparator<Plant>() {
		
		public int compare(Plant p1, Plant p2) {
			return p2.getLepsSupported() - p1.getLepsSupported();
		}
	};

	public static Comparator<Plant> SpreadComparator = new Comparator<Plant>() {
		
		public int compare(Plant p1, Plant p2) {
			if(p1.getSpreadRadiusLower() == 0) {
				return -1;
			}
			if(p2.getSpreadRadiusLower() == 0) {
				return 1;
			}
			return (int) (p1.getSpreadRadiusLower() - p2.getSpreadRadiusLower())*10;
		}
	};
	
	public static Comparator<Plant> CostComparator = new Comparator<Plant>() {
		
		public int compare(Plant p1, Plant p2) {
			return (int) (p2.getCost() - p1.getCost());
		}
	};
	
	public static Comparator<Plant> SizeComparator = new Comparator<Plant>() {
			
		public int compare(Plant p1, Plant p2) {
			return (int) ((p1.getSizeLower() - p2.getSizeLower())*10);
		}
	};
	
	@Override
	public int compareTo(Plant o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}