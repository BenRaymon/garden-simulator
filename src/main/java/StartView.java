import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartView extends View{

	private Button newGarden;
	private Button loadGarden;
	
	public StartView(Stage stage) {
		StackPane base = new StackPane();
		
		//create a temporary group for the name and button
		Group temp = new Group();
		Button nextPage = new Button("Next Page");
		Text title = new Text("START");
		
		temp.getChildren().add(nextPage);
		temp.getChildren().add(title);
		
		StackPane.setAlignment(temp, Pos.CENTER);
		base.getChildren().add(temp);
		
		//create and set scene with base StackPane
		Scene scene = new Scene(base, 800, 800);
        stage.setScene(scene);
        stage.show();
	}
	
	public boolean loadImages() {
		return true;
	}
	
}