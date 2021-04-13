import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CompPlantsView extends View{

	private Image plantImageA;
	private Image plantImageB;
	private Text plantSummaryA;
	private Text plantSummaryB;
	private Scene scene;
	private Controller controller;
	
	public CompPlantsView(Stage stage, Controller c) {
		this.controller = c;
		GridPane base = new GridPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);
		
		//create a temporary vbox for the name and button
		pageTitle = new Text("Compare Plants");
		VBox temp = new VBox(5);
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
	
}