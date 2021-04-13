import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class SplashModel extends Model {
	private Controller controller;
	private BackgroundLoaderController backgroundLoader;
	
	public SplashModel(Controller c) {
		this.controller = c;
		// Initialize the background loader controller with the image and plant maps to be populated from disk
		backgroundLoader = new BackgroundLoaderController(View.getImages(), Model.getPlants());
		
		// Load assets in the background in SplashModel via BackgroundLoader
		backgroundLoader.loadData();
		backgroundLoader.loadImages();
		
		// Wait for loading to complete
		// Will probably try to make some kind of loading bar for beta/full release
		while(!backgroundLoader.isCompleted());

		// Tell the controller to change from the splash screen to the start screen
		controller.loadStartScreen();
	}
}
