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
	private ConcurrentHashMap<String, Plant> all_plants;
	private ConcurrentHashMap<String, Set<Lep>> allLeps;
	
	/**
	 * Constructor for data loading
	 * @param name name of the thread
	 * @param all_plants2 static concurrenthashmap from the model to load plant data to
	 * @return none
	 */
	public BackgroundDataLoader(String name, ConcurrentHashMap<String, Plant> all_plants2) {
		System.out.println("BackgroundDataLoader created with thread name: " + name);
		this.threadName = name;
		this.all_plants = all_plants2;
	}
	
	public BackgroundDataLoader(String name, ConcurrentHashMap<String, Set<Lep>> Lep,int i) {
		System.out.println("BackgroundDataLoader created with thread name: " + name);
		this.threadName = name;
		this.allLeps = Lep;
	}
	
	/**
	 * Start method for the thread
	 * @return none
	 */
	public void start() {
		System.out.println("Starting background load process");
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
		if(allLeps == null) {
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
	    
		if (all_plants.size() == 366)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
			//System.out.println("All Leps loaded sucessfully in the background");
	

	}
	
	public void loadLep(String line) {
		//System.out.println("In loadLeps");
		if(line.contains("Lep ID")) {
			return;
		}
		//split csv line into array of strings.
		String[] words = line.split(",");
		//System.out.println("First: " + words[0] + " Second: " + words[1] + " Third: " + words[2] + " Fourth: " + words[3] + " Fifth: " + words[4] + " Sixth: " + words[5]);
		
		if(allLeps.get(words[4]) == null) {
			
		
		Lep addLep = new Lep(words[1],words[2],words[3]);
		Set<Lep> lepSet = new HashSet<Lep>();
		lepSet.add(addLep);
		allLeps.put(words[4], lepSet);
		System.out.println(words[4]);
		}
		else {
			Set<Lep> lepSet = allLeps.get(words[4]);
			Lep addLep = new Lep(words[1],words[2],words[3]);
			lepSet.add(addLep);
			allLeps.put(words[4],lepSet);
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
		//load options for sunlevels, soiltypes and moistures
		
		
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
		synchronized(all_plants) {
			all_plants.put(addPlant.getScientificName(), addPlant);	
		}
		
		
		//DEBUG
		System.out.println(addPlant.toString() + "||" + op.toString());
		System.out.println(all_plants.size());
	}
}
