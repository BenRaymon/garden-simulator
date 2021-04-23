import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;

public class BackgroundDataLoader extends Thread {
	private Thread thread;
	private String threadName;
	// Reference to our model's plant map
	private HashMap<String, Plant> all_plants;
	
	public BackgroundDataLoader(String name, HashMap<String, Plant> ap) {
		System.out.println("BackgroundDataLoader created with thread name: " + name);
		this.threadName = name;
		this.all_plants = ap;
	}
	
	public void start() {
		System.out.println("Starting background load process");
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	// The thread runs this function
	public void run() {
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
		all_plants.put(addPlant.getScientificName(), addPlant);
		
		//DEBUG
		System.out.println(addPlant.toString() + "||" + op.toString());
		System.out.println(all_plants.size());
	}
}
