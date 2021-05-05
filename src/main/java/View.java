import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

public abstract class View {

	private static ConcurrentHashMap<String, Image> plantImages = new ConcurrentHashMap<String, Image>();
	protected static double WINDOW_WIDTH = 0;
	protected static double WINDOW_HEIGHT = 0;
	protected static String darkBlue = "#263C46";
	protected static String lightBlue = "#596E89";
	protected static String green = "#ACBC$40";
	protected static String darkGreen = "#678B5E";
	protected static String offWhite = "#E2E4E0";
	
	
	
	//TEMPORARY FOR CHANGING PAGES IN PRE-ALPHA
	Text pageTitle;
	View next;
	View back;
	
	/**
	 * abstract method for getting the scene
	 * @return the scene in extended views
	 */
	public abstract Scene getScene();
	// END TEMP CODE
	
	/**
	 * getter for the static map of images
	 * @return the concurrent hashmap of images
	 */
	public static ConcurrentHashMap<String, Image> getImages() {
		return plantImages;
	}
	
	/**
	 * set the window size
	 * @param width width of window
	 * @param height height of window
	 */
	public static void setSize(double width, double height) {
		WINDOW_HEIGHT = height;
		WINDOW_WIDTH = width;
	}
	
}