import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;


public class CompPlantsView extends View{

	private Image plantImageA;
	private Image plantImageB;
	private Text plantSummaryA;
	private Text plantSummaryB;
	private Scene scene;
	private Controller controller;
	private TextField plantNameInput;
	private Button toGardenEditor;
	
	public CompPlantsView(Stage stage, Controller c) {
		this.controller = c;
		GridPane base = new GridPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);
		
		//button to go back to the garden editor
		toGardenEditor = new Button("Garden Editor");
		toGardenEditor.setOnMouseClicked(controller.getToGardenOnClickHandler());
		base.add(toGardenEditor, 0, 10);
		
		
		//Setting plant Summary Values
		plantSummaryA = new Text("Plant summary A");
		plantSummaryB = new Text("Plant summary B");
		
		Button leftPlantButton = new Button("Left Plant");
		Button rightPlantButton = new Button("Right Plant");
		
		plantNameInput = new TextField();
		
		rightPlantButton.setOnMouseClicked(c.RightPlantButtonClickedHandler());
		leftPlantButton.setOnMouseClicked(c.LeftPlantButtonClickedHandler());
		
		GridPane center = new GridPane();
		
		base.add(plantSummaryA,0,0,1,1);
		base.add(plantSummaryB,1,0,1,1);
		base.add(leftPlantButton, 0, 1,1,1);
		base.add(rightPlantButton, 1, 1,1,1);
		base.add(plantNameInput, 0, 2, 1, 1);

		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public TextField getTextBox() {
		return plantNameInput;
	}
	
	public void setRightTextBox(String s) {
		plantSummaryB.setText(s);
	}
	
	public Text getRightBody() {
		return plantSummaryB;
	}
	
	public Text getLeftBody() {
		return plantSummaryA;
	}
}