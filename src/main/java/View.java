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
	
	
	//TEMPORARY FOR CHANGING PAGES IN PRE-ALPHA
	Text pageTitle;
	View next;
	View back;
	public abstract Scene getScene();
	// END TEMP CODE
	
	public static ConcurrentHashMap<String, Image> getImages() {
		return plantImages;
	}
	
	public static void setSize(double width, double height) {
		WINDOW_HEIGHT = height;
		WINDOW_WIDTH = width;
	}
	
}