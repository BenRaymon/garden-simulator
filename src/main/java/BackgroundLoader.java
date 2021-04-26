import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.image.Image;

public class BackgroundLoader extends Thread {
	private Thread thread;
	private String threadName;
	private ConcurrentHashMap<String, Image> plant_images;
	private ConcurrentHashMap<String, Plant> all_plants;
	private boolean dataCompleted;
	private boolean imagesCompleted;
	
	public BackgroundLoader(String name, ConcurrentHashMap<String, Image> pi, ConcurrentHashMap<String, Plant> ap) {
		// Get references to the hashmaps for loading
		this.threadName = name;
		this.plant_images = pi;
		this.all_plants = ap;
		
		loadData();
		loadImages();
		
		System.out.println("Background Loading Complete");
	}
	
	public void start() {
		System.out.println("Starting background load process");
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	public void run() {
		loadData();
		loadImages();
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
	}
}
