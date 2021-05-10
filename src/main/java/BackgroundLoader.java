import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.image.Image;

public class BackgroundLoader extends Thread {
	private Thread thread;
	private String threadName;
	private ConcurrentHashMap<String, Image> plantImages;
	private ConcurrentHashMap<String, Image> lepImages;
	private ConcurrentHashMap<String, Plant> allPlants;
	private ConcurrentHashMap<String, Lep> allLeps;
	private ConcurrentHashMap<String, Set<Lep>> lepsByPlant;
	private boolean dataCompleted;
	private boolean imagesCompleted;
	
	/**
	 * Constructor for the thread controller (which is also a thread)
	 * @param name name of the thread
	 * @param po static concurrenthashmap for plant images
	 * @param ap static concurrenthashmap for plant data
	 * @return none
	 */
	public BackgroundLoader(String name, ConcurrentHashMap<String, Image> pi,ConcurrentHashMap<String, Image> li, ConcurrentHashMap<String, Plant> ap, ConcurrentHashMap<String, Set<Lep>> l, ConcurrentHashMap<String, Lep> al) {
		// Get references to the hashmaps for loading
		this.threadName = name;
		this.plantImages = pi;
		this.lepImages = li;
		this.allPlants = ap;
		this.lepsByPlant = l;
		this.allLeps = al;
		loadData();
		loadImages();
		loadLeps();
		
		
		System.out.println("Background Loading Complete");
	}
	
	/**
	 * Start method for the thread
	 * @return none
	 */
	public void start() {
		System.out.println("Starting background load process");
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	/**
	 * The run function for the thread, begins executing here.
	 * @return none
	 */
	public void run() {
		loadData();
		loadImages();
		loadLeps();
	}
	
	/**
	 * This starts the BackgroundDataLoader thread for loading in plant data
	 * @return none
	 */
	public void loadData() {
		BackgroundDataLoader bdl = new BackgroundDataLoader("DataThread", allPlants);
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
	
	/**
	 * This starts the BackgroundImageLoader thread for loading in plant images
	 * @return none
	 */
	public void loadImages() {
		BackgroundImageLoader bil = new BackgroundImageLoader("ImageThread", plantImages, lepImages);
		bil.start();
		// When it finishes, set the dataCompleted flag to true
		try {
			bil.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadLeps() {
		BackgroundDataLoader bdl = new BackgroundDataLoader("Leps Thread", lepsByPlant, allLeps, 0);
		bdl.start();
		
		try {
			bdl.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
