import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.image.Image;

public abstract class View extends Application{

	private static HashMap<String, Image> plantImages = new HashMap<String, Image>();
	
	public HashMap getImages() {
		return plantImages;
	}
	
}