import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class StartModel extends Model{
	
	public boolean loadAllPlants() {
		
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
	    
		if(getPlants().size() == numLines - 1)
			return true;
		else
			return false;
	}
	
	public void loadPlant(String line) {
		if(line.contains("commonName"))
			return;
		
		//split csv line into array of strings
		String[] words = line.split(",");
		//load options for sunlevels, soiltypes and moistures
		
		
		//create an options for the plant
		Options op = new Options(new int[3],new int[3],new int[3]);
		//create plant instance
		Plant addPlant = new Plant(words[0], words[1], words[2], words[3], words[4], Double.parseDouble(words[5]), Double.parseDouble(words[6]), 
							Integer.parseInt(words[7]), Integer.parseInt(words[8]),op, Double.parseDouble(words[9]), Integer.parseInt(words[10]), 
							words[11].charAt(0));
		//add plant to the static hashmap
		getPlants().put(addPlant.getCommonName(), addPlant);
		
		//DEBUG
		System.out.println(addPlant.toString() + "||" + op.toString());
		System.out.println(getPlants().size());
	}
	
	
	
}