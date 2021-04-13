import java.util.HashMap;

import javafx.scene.image.Image;

public class BackgroundLoaderController {
	private Thread thread;
	private HashMap<String, Image> plant_images;
	private HashMap<String, Plant> all_plants;
	
	public BackgroundLoaderController(HashMap<String, Image> pi, HashMap<String, Plant> ap) {
		// Get references to the hashmaps for loading
		this.plant_images = pi;
		this.all_plants = ap;
	}
	
	
}
