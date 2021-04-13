import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

public abstract class View {

	private static HashMap<String, Image> plantImages = new HashMap<String, Image>();
	
	//TEMPORARY FOR CHANGING PAGES IN PRE-ALPHA
	Text pageTitle;
	View next;
	View back;
	public abstract Scene getScene();
	// END TEMP CODE
	
	public static HashMap<String, Image> getImages() {
		return plantImages;
	}
	
}