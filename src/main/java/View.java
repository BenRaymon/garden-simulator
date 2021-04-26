import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

public abstract class View {

	private static ConcurrentHashMap<String, Image> plantImages = new ConcurrentHashMap<String, Image>();
	protected double WINDOW_WIDTH = 1920;
	protected double WINDOW_HEIGHT = 1080 - 75;
	
	
	
	//TEMPORARY FOR CHANGING PAGES IN PRE-ALPHA
	Text pageTitle;
	View next;
	View back;
	public abstract Scene getScene();
	// END TEMP CODE
	
	public static ConcurrentHashMap<String, Image> getImages() {
		return plantImages;
	}
	
}