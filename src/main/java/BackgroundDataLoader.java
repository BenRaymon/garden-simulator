import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
public class BackgroundDataLoader extends Thread {
	private Thread thread;
	private String threadName;
	// Reference to our model's plant map
	private ConcurrentHashMap<String, Plant> allPlants;
	private ConcurrentHashMap<String, Set<Lep>> lepsByPlant;
	private ConcurrentHashMap<String, Lep> allLeps;
	
	/**
	 * Constructor for data loading plants
	 * @param name name of the thread
	 * @param allPlants static concurrenthashmap from the model to load plant data to
	 * @return none
	 */
	public BackgroundDataLoader(String name, ConcurrentHashMap<String, Plant> allPlants) {
		this.threadName = name;
		this.allPlants = allPlants;
	}
	
	/**
	 * Constructor for data loading leps
	 * @param name name of the thread
	 * @param lepsByPlant static concurrenthashmap for holding list of leps per plant 
	 * @params allLeps a static concurrenthashmap for all leps
	 * @return none
	 */
	public BackgroundDataLoader(String name, ConcurrentHashMap<String, Set<Lep>> lepsByPlant, ConcurrentHashMap<String, Lep> allLeps) {
		this.threadName = name;
		this.lepsByPlant = lepsByPlant;
		this.allLeps = allLeps;
	}
	
	/**
	 * Start method for the thread
	 * @return none
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	/**
	 * The run function for the thread, begins executing here.
	 * @return none
	 */
	public void run() {
		if(lepsByPlant == null) {
		LoadPlantData();
		}
		else {
			LoadLepData();
		}
	}
	
	/**
	 * This opens the data file for plant data and ensures all 366 are loaded
	 * @return none
	 */
	private void LoadPlantData() {
		//load data file and create a list of lines
		File plantData = Paths.get("src/main/resources/data.csv").toFile().getAbsoluteFile();
		BufferedReader br;
		int numLines = 0;
		try {
			br = new BufferedReader(new FileReader(plantData));
		    String line;
		    while ((line = br.readLine()) != null) {
		        loadPlant(line);
		        numLines++;
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		if (allPlants.size() == 366)
			System.out.println("All 366 plants loaded sucessfully in the background");
	}
	
	private void LoadLepData() {
		File plantData = Paths.get("src/main/resources/leps_data_vbon.csv").toFile().getAbsoluteFile();
		BufferedReader br;
		int numLines = 0;
		try {
			br = new BufferedReader(new FileReader(plantData));
		    String line;
		    while ((line = br.readLine()) != null) {
		        loadLep(line);
		        numLines++;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadLep(String line) {
		if(line.contains("Lep ID")) {
			return;
		}
		//split csv line into array of strings.
		String[] words = line.split(",");
		
		Lep addLep = null;
		//check if the lep has already been seen
		if(allLeps.containsKey(words[2])) {
			//add the family and name to the sets of families/names that this lep feeds off of
			Lep currLep = allLeps.get(words[2]);
			currLep.addHostFamily(words[3]);
			currLep.addHostName(words[4]);
			currLep.addCountry(words[5]);
			addLep = currLep;
		}
		else {
			//make a new lep and add it to the master list
			Lep newLep = new Lep(words[1],words[2],words[3], words[4], words[5]);
			allLeps.put(words[2], newLep);
			addLep = newLep;
		}
		
		//if the set of leps for this plant does not exist yet, make one
		if(lepsByPlant.get(words[4]) == null) {
			Set<Lep> lepSet = new HashSet<Lep>();
			lepSet.add(addLep);
			lepsByPlant.put(words[4], lepSet);
		}
		//if the set has been made already, add the new lep to the set
		else {
			Set<Lep> lepSet = lepsByPlant.get(words[4]);
			lepSet.add(addLep);
			lepsByPlant.put(words[4],lepSet);
		}
		
		
		
	}
	/**
	 * This function loads the plant data from the csv into a Plant object
	 * @param line line in the csv file of data
	 * @return none
	 */
	public void loadPlant(String line) {
		if(line.contains("commonName"))
			return;
		
		//split csv line into array of strings
		String[] words = line.split(",");
		
		//create an options for the plant
		int[] sl = new int[] {0,0,0};
		int[] st = new int[] {0,0,0};
		int[] m = new int[] {0,0,0};
		for (int i = 12; i < words.length; i++) {
			words[i] = words[i].replace("\"", "");
			switch(words[i].trim()) {
				case "Shade":
					sl[0] = 1;
					break;
				case "Partial Sun":
					sl[1] = 1;
					break;
				case "Full Sun":
					sl[2] = 1;
					break;
				case "Dry":
					m[0] = 1;
					break;
				case "Moist":
					m[1] = 1;
					break;
				case "Wet":
					m[2] = 1;
					break;
				case "Clay":
					st[0] = 1;
					break;
				case "Loamy":
					st[1] = 1;
					break;
				case "Sandy":
					st[2] = 1;
					break;
			}
		}
		
		Options op = new Options(st,sl,m);
		//create plant instance
		Plant addPlant = new Plant(words[0], words[1], words[2], words[3], words[4], Double.parseDouble(words[5]), Double.parseDouble(words[6]), 
							Double.parseDouble(words[7]), Double.parseDouble(words[8]),op, Double.parseDouble(words[9]), Integer.parseInt(words[10]), 
							words[11].charAt(0));
		//add plant to the static hashmap
		synchronized(allPlants) {
			allPlants.put(addPlant.getScientificName(), addPlant);	
		}
	}
}
