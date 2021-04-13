import java.util.HashMap;

import javafx.scene.image.Image;

public class BackgroundLoaderController {
	private HashMap<String, Image> plant_images;
	private HashMap<String, Plant> all_plants;
	private boolean dataCompleted;
	private boolean imagesCompleted;
	
	public BackgroundLoaderController(HashMap<String, Image> pi, HashMap<String, Plant> ap) {
		// Get references to the hashmaps for loading
		this.plant_images = pi;
		this.all_plants = ap;
		this.dataCompleted = false;
		this.imagesCompleted = false;
	}
	
	public void loadData() {
		BackgroundDataLoader bdl = new BackgroundDataLoader("DataThread", all_plants);
		// Start the data thread
		bdl.start();
		// When it finishes, set the dataCompleted flag to true
		try {
			bdl.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataCompleted = true;
	}
	
	public void loadImages() {
		BackgroundImageLoader bil = new BackgroundImageLoader("ImageThread", plant_images);
		bil.start();
		// When it finishes, set the dataCompleted flag to true
		try {
			bil.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imagesCompleted = true;
	}
	
	public boolean isCompleted() {
		return dataCompleted && imagesCompleted;
	}
}
