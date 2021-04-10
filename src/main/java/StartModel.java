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
		
		//File file = new File(getClass().getClassLoader().getResource("report.csv").getFile());
		File test = Paths.get("src/main/resources/result.csv").toFile().getAbsoluteFile();
		System.out.println(test);
		
		try (BufferedReader br = new BufferedReader(new FileReader(test))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        loadPlant(line);
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return true;
	}
	
	public void loadPlant(String line) {
		System.out.println("Line:  " + line);
	}
	
	
	
}