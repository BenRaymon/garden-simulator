import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;


// *.class.getResourceAsStream(fileName); gets you the specified files in resources
public class SaveLoadGarden {
	/*
	 * Takes as input Garden g and serializes
	 * it to save files in .dat format.
	 * */
	public void saveGarden(ArrayList<Garden> g) throws ClassNotFoundException {
		
		try {
			// get the save file from resources
			File f = new File("src/main/resources/savedGardens.dat");
			
			// make the output streams for the file and garden
			FileOutputStream f_out = new FileOutputStream(f, false); // append is false to keep save file short
			ObjectOutputStream o_out = new ObjectOutputStream(f_out);
			
			// write the garden to f_out
			o_out.writeObject(g);
			
			// close the file and object output streams
			o_out.close();
			f_out.close();
			
	      // catch exceptions and errors
		} catch (FileNotFoundException e) {
		      System.out.println("File not found");
	    } catch (IOException e) {
	    	  System.out.println("Can't initialize stream");
	    	  e.printStackTrace();
	    }
	}
	
	/*
	 * Load a perviously saved garden
	 * Takes the file name as input
	 * */
	public Garden loadGarden(String g_name) throws ClassNotFoundException {
		// declare the garden we'll return after loading in the save file
		Garden ret_g = new Garden();
		boolean flag = false; // flag to check if ret_g has been loaded properly
		
		try {
			// find the save file, if it doesn't exist, throw and error
			File f = new File("src/main/resources/savedGardens.dat");
			if(!f.exists()) {
				System.out.println("Garden not found");
			}
			
			// make the input streams for the file and garden
			FileInputStream f_in = new FileInputStream(f);
			ObjectInputStream o_in = new ObjectInputStream(f_in);
			
			// Read the garden into our return garden so we can use it in the app
			ret_g = (Garden) o_in.readObject();
			
			// close the input streams
			o_in.close();
			f_in.close();
			
			//set the flag to denote proper load
			flag = true;
			
		} catch (FileNotFoundException e) {
		      System.out.println("File not found");
	    } catch (IOException e) {
	    	  System.out.println("Can't initialize stream");
	    	  e.printStackTrace();
	    }
		// if the garden was loaded, return it, otherwise, throw error
		if(flag) {
			// return the loaded garden
			return ret_g;
		} else {
			return null;
		}
	}
	
	/*
	 * Load the collection of gardens in the save file.
	 * */
	public ArrayList<Garden> loadGardenList() throws ClassNotFoundException {
		
		ArrayList<Garden> ret_gardens = new ArrayList<Garden>();
		boolean flag = false;
		
		try {
			// get the save file from resources
			File f = new File("src/main/resources/savedGardens.dat");
			
			// open the file and object input streams to read the gardens into the app
			FileInputStream f_in = new FileInputStream(f);
			ObjectInputStream o_in = new ObjectInputStream(f_in);
			
			// read gardens into our return variable and type case appropriately
			ret_gardens = (ArrayList<Garden>) o_in.readObject();
			
			// close the input streams
			o_in.close();
			f_in.close();
			
			// set the flag to true to denote a good load
			flag = true;
			
		} catch (FileNotFoundException e) {
		      System.out.println("File not found");
	    } catch (IOException e) {
	    	  System.out.println("Can't initialize stream");
	    	  e.printStackTrace();
	    }
		// if the garden was loaded, return it, otherwise, throw error
		if(flag) {
			// return the loaded garden
			return ret_gardens;
		} else {
			return null;
		}
	}
	
	/*
	 * Load an individual garden from the saved list
	 * */
	public Garden loadPickedGarden(String g_name, ArrayList<Garden> list) {
		// find the garden by filtering a stream of saved gardens
		Garden find = list.stream().filter(s -> s.name.equals(g_name)).findFirst().orElse(null);
		if(find != null) {
			return find;
		} else {
			return null;
		}
	}
}
