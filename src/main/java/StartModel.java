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
		File plantData = Paths.get("src/main/resources/result.csv").toFile().getAbsoluteFile();
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
		if(line.contains("﻿"))
			return;
		
		//split csv line into array of strings
		String[] words = line.split(",");
		//load arraylists for two sunlevels, soiltypes and moistures
		ArrayList<String> sl = new ArrayList<String>();
		sl.add(words[8].replace("\"", ""));
		sl.add(words[9].replace("\"", "").trim());
		ArrayList<String> m = new ArrayList<String>();
		m.add(words[10].replace("\"", ""));
		m.add(words[11].replace("\"", "").trim());
		ArrayList<String> st = new ArrayList<String>();
		st.add(words[12].replace("\"", ""));
		st.add(words[13].replace("\"", "").trim());
		//create an options for the plant
		Options op = new Options(st, sl, m);
		//create plant instance
		Plant addPlant = new Plant(words[0], words[1], words[2], words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), 
							Integer.parseInt(words[6]), Integer.parseInt(words[7]),op, Double.parseDouble(words[14]), Integer.parseInt(words[15]), 
							words[16].charAt(0));
		//add plant to the static hashmap
		getPlants().put(addPlant.getCommonName(), addPlant);
		
		//DEBUG
		System.out.println(addPlant.toString() + "||" + op.toString());
		System.out.println(getPlants().size());
	}
	
	
	
}