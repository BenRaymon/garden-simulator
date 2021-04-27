
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import com.sun.tools.javac.Main;

import javafx.scene.image.Image;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


public class BackgroundImageLoader extends Thread {
	private Thread thread;
	private String threadName;
	private ConcurrentHashMap<String, Image> plant_images;
	
	/**
	 * Constructor for data loading
	 * @param name name of the thread
	 * @param pi static concurrenthashmap from the view to load plant images to
	 * @return none
	 */
	public BackgroundImageLoader(String name, ConcurrentHashMap<String, Image> pi) {
		this.threadName = name;
		this.plant_images = pi;
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
		LoadImages();
	}
	
	/**
	 * This loads the image in as an InputStream
	 * @param fileName filename for the image
	 * @return InputStream version of the image
	 */
	public InputStream getFile(String fileName) {
		return BackgroundImageLoader.class.getResourceAsStream("images/"+fileName);
	}
	
	/**
	 * This opens all the images and loads them into the map
	 * @return none
	 */
	public void LoadImages() {
		File dir = Paths.get("src/main/resources/images").toFile().getAbsoluteFile();
		File[] directoryListing = dir.listFiles();
		int x = 0;
		if (directoryListing != null) {
			for (File child : directoryListing) {
				Image image = new Image(getFile(child.getName()));
				String name = child.getName().replace(".jpg", "");
				synchronized(plant_images) {
					plant_images.put(name, image);
				}
			}
		}
		System.out.println(plant_images.size());
	}
}
