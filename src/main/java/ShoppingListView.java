import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShoppingListView extends View{
	
	private Text title;
	private Text budget;
	private Text cost;
	private Text plantCount;
	private ListView plants;
	private Scene scene;
	
	public ShoppingListView(Stage stage) {
		GridPane base = new GridPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);
		
		//create a temporary vbox for the name and button
		nextPage = new Button("Next Page");
		backPage = new Button("Back Page");
		pageTitle = new Text("Shopping List");
		VBox temp = new VBox(5);
		temp.getChildren().add(nextPage);
		temp.getChildren().add(backPage);
		temp.getChildren().add(pageTitle);
		temp.setAlignment(Pos.CENTER);
		base.add(temp, 0, 1);
		
		//create and set scene with base
		scene = new Scene(base, 800, 800);
        stage.setScene(scene);
        stage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void display(String title, double budget, double cost, int plantCount, HashMap plantsInGarden) {
		
	}
	
	
}