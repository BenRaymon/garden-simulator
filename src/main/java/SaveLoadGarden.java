import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;


public class SaveLoadGarden implements Serializable {
	
	/*
	 * Takes as input Garden g and serializes
	 * it to save files in .dat format.
	 * */
	public void saveGarden(Garden g) throws ClassNotFoundException {
		
		try {
			// initialize file f, if it doesn't exist, make it
			File f = new File("save files/garden2.dat");
			if(!f.exists()) {
				f.createNewFile();
			}
			
			// make the output streams for the file and garden
			FileOutputStream f_out = new FileOutputStream(f);
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
	public Garden loadGarden(String f_name) throws ClassNotFoundException {
		// TODO: implement the method you buffoon
		// declare the garden we'll return after loading in the save file
		Garden ret_g = new Garden();
		boolean flag = false; // flag to check if ret_g has been loaded properly
		
		try {
			// find the save file, if it doesn't exist, throw and error
			File f = new File("save files/" + f_name);
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
}
