import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartView extends View{

	private Button newGarden;
	private Button loadGarden;
	private Scene scene;
	
	Button nextPage = new Button("Next Page");
	Button backPage = new Button("Back Page");
	Text title = new Text("START");
	
	public StartView(Stage stage) {
		StackPane base = new StackPane();
		
		//create a temporary vbox for the name and button
		VBox temp = new VBox(5);
		
		temp.getChildren().add(nextPage);
		temp.getChildren().add(backPage);
		temp.getChildren().add(title);
		
		temp.setAlignment(Pos.CENTER);
		base.getChildren().add(temp);
		
		//create and set scene with base StackPane
		scene = new Scene(base, 800, 800);
        stage.setScene(scene);
        stage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public boolean loadImages() {
		return true;
	}
	
}