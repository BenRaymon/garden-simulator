import java.util.*;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
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
	private Controller controller;
	private BorderPane base;
	private VBox leftVBox;
	private VBox rightVBox;
	private VBox bottomVBox;
	
	public ShoppingListView(Stage stage, Controller c) {
		System.out.println("In shopping list view");
		controller = c;
		
		base = new BorderPane();
		leftVBox = this.createLeftVBox();
		rightVBox = this.createRightVBox();
		bottomVBox = this.createBottomVBox();
		
		addGardenButton();
		generateShoppingListData();
		addPlaceHolderText();
		
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
	}
	
	private void generateShoppingListData() {
		
	}
	
	public VBox createBottomVBox() {
		VBox bottom_vbox = new VBox(2);
		bottom_vbox.setAlignment(Pos.BASELINE_CENTER);
		bottom_vbox.setStyle("-fx-background-color: aqua");
		bottom_vbox.setMinWidth(800);
		base.setBottom(bottom_vbox);
		return bottom_vbox;
	
	}
	
	public VBox createLeftVBox(){
		VBox left_vbox = new VBox(3);
		left_vbox.setAlignment(Pos.TOP_CENTER);
		left_vbox.setStyle("-fx-background-color: #42f58d");
		//left_grid.setGridLinesVisible(true);
		left_vbox.setMinWidth(400);
		base.setLeft(left_vbox);
		return left_vbox;
	}
	
	public VBox createRightVBox(){
		VBox right_vbox = new VBox(3);
		right_vbox.setAlignment(Pos.TOP_CENTER);
		right_vbox.setStyle("-fx-background-color: #42adf5");
		//left_grid.setGridLinesVisible(true);
		right_vbox.setMinWidth(400);
		base.setRight(right_vbox);
		return right_vbox;
	}
	
	public void addGardenButton() {
		Button toGarden = new Button("Garden Editor");
		toGarden.setOnMouseClicked(controller.getToGardenOnClickHandler());
		bottomVBox.getChildren().add(toGarden);
	}
	
	private void addPlaceHolderText() {
		Text fakeplant1 = new Text("1x Testing1");
		Text fakeplant2 = new Text("3x Testing2");
		Text fakeplant3 = new Text("3x Testing3");
		Text fakeplant4 = new Text("7x Testing4");
		Separator leftSep = new Separator();
		leftSep.setMaxWidth(300);
		leftSep.setHalignment(HPos.CENTER);
		Text fakeTotal = new Text("Total: 14 Perenials");
		leftVBox.getChildren().add(fakeplant1);
		leftVBox.getChildren().add(fakeplant2);
		leftVBox.getChildren().add(fakeplant3);
		leftVBox.getChildren().add(fakeplant4);
		leftVBox.getChildren().add(leftSep);
		leftVBox.getChildren().add(fakeTotal);
		
		Text fakeCost1 = new Text("Cost: 50");
		Text fakeCost2 = new Text("Cost: 150");
		Text fakeCost3 = new Text("Cost: 150");
		Text fakeCost4 = new Text("Cost: 350");
		Separator rightSep = new Separator();
		rightSep.setMaxWidth(300);
		rightSep.setHalignment(HPos.CENTER);
		Text fakeBudget = new Text("Budget: $1000");
		Text fakeCost = new Text("Total Cost: $700");
		rightVBox.getChildren().add(fakeCost1);
		rightVBox.getChildren().add(fakeCost2);
		rightVBox.getChildren().add(fakeCost3);
		rightVBox.getChildren().add(fakeCost4);
		rightVBox.getChildren().add(rightSep);
		rightVBox.getChildren().add(fakeBudget);
		rightVBox.getChildren().add(fakeCost);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void display(String title, double budget, double cost, int plantCount, HashMap plantsInGarden) {
		
	}
	
	
}