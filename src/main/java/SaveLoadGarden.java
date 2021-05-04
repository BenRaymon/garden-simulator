import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SaveLoadGarden {
	
	/**
	 * Save a list of gardens to savedGardens.dat so they can be loaded and edited later.
	 * This gets called every time the user clicks the save button in the Garden Editor.
	 * @param g - ArrayList of gardens to write the save file
	 * @return void
	 * @throws ClassNotFoundException if saving the garden fails
	 */

	public void saveGarden(ArrayList<Garden> g) throws ClassNotFoundException {
		
		try {
			// check for file, if it exists get the save file from resources, else create it
			File f = new File("src/main/resources/savedGardens.dat");
			
			if(!f.exists()) {
				f.createNewFile();
			}
			
			// make the output streams for the file and garden
			FileOutputStream f_out = new FileOutputStream(f, false); // append is false to keep one arraylist in save file
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
	
	/**
	 * Load in the list of gardens saved into savedGardens.dat. This is called once at the start
	 * of the program and never again during that instance of the program.
	 * @param none
	 * @return ArrayList of gardens that were saved into savedGardens.dat
	 * @throws ClassNotFoundException if reading in the list of gardens fails
	 */
	public ArrayList<Garden> loadGardenList() throws ClassNotFoundException {
		
		ArrayList<Garden> ret_gardens = new ArrayList<Garden>();
		
		try {
			// get the save file from resources
			File f = new File("src/main/resources/savedGardens.dat");
			
			// check for empty file to avoid EOFException
			if(f.length() != 0) {
				// open the file and object input streams to read the gardens into the app
				FileInputStream f_in = new FileInputStream(f);
				ObjectInputStream o_in = new ObjectInputStream(f_in);
				
				// read gardens into our return variable and type case appropriately
				ret_gardens = (ArrayList<Garden>) o_in.readObject();
				
				// close the input streams
				o_in.close();
				f_in.close();
			} else {
				// if file is empty, tell me
				System.out.println("File is empty...");
			}
			
		} catch (FileNotFoundException e) {
		      System.out.println("File not found");
	    } catch (IOException e) {
	    	  System.out.println("Can't initialize stream");
	    	  e.printStackTrace();
	    }
		return ret_gardens;
	}
	
	/**
	 * Deletes a specified garden from the ArrayList of saved gardens. If the garden isn't found, return null.
	 * This is called when the user deletes a garden from the load screen.
	 * @param g_name - the name of the garden we want to delete
	 * @param list - the list of saved gardens that we want to remove a garden from
	 * @return the new list without the garden we wanted to remove or just the unedited list.
	 */
	public ArrayList<Garden> deleteGarden(String g_name, ArrayList<Garden> list) {
		// find and delete the garden
		Garden ret_g = null;
		
		for(Garden g : list) {
			if(g.getName() == g_name) {
				ret_g = g;
				break;
			}
		}
		
		list.remove(ret_g);
		return list;
	}
	
	/**
	 * Loads a specified garden from the ArrayList of saved gardens. This is called when
	 * the user loads a garden from the load screen.
	 * @param g_name - the name of the garden we want to load
	 * @param list - the list of saved gardens that we want to get a garden from
	 * @return if the garden was found we return it, otherwise we return null
	 */
	public Garden loadPickedGarden(String g_name, ArrayList<Garden> list) {
		// find the garden by iterating over the given list and finding the element
		// with a name that matches the given name.
		Garden ret_g = null;
		
		for(Garden g : list) {
			if(g.getName() == g_name) {
				ret_g = g;
				break;
			}
		}
		
		return ret_g;
	}
}
