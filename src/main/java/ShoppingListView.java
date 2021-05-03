import java.util.*;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
	//private GridPane base;
	private GridPane base;
	private HBox center;
	private VBox leftVBox;
	private VBox rightVBox;
	private VBox bottomVBox;
	
	/**
	 * Constructor for ShoppingListView
	 * @param stage
	 * @param c
	 */
	public ShoppingListView(Stage stage, Controller c) {
		controller = c;
		
		base = new GridPane();
		//base = new BorderPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);
		
		center = new HBox();
		//base.setCenter(center);
		
		leftVBox = this.createLeftVBox();
		rightVBox = this.createRightVBox();
		bottomVBox = this.createBottomVBox();
		
		//HBox center_of_hbox = new HBox();
		//center_of_hbox.getChildren().add(leftVBox);
		//center_of_hbox.getChildren().add(rightVBox);
		//center.getChildren().add(center_of_hbox);
		
		base.add(center, 0, 10);
		//addPlaceHolderText();
		
		// get button styles
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(buttonStyle);
		stage.setScene(scene);
        stage.show();
	}
	
	/**
	 * Sets the data in the Shopping List
	 * @param psld
	 * @param budget
	 */
	public void setShoppingListData(HashMap<String, PlantShoppingListData> psld, double budget) {
		double cost = 0;
		// First, clear the 2 VBoxes
		leftVBox.getChildren().clear();
		rightVBox.getChildren().clear();
		bottomVBox.getChildren().clear();
		
		// Now, re-populate the VBoxes with the new data
		// Key doesnt matter here, just the value
		Iterator itr = psld.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry element = (Map.Entry)itr.next();
			PlantShoppingListData v = (PlantShoppingListData)element.getValue();
			cost += v.getCost();
			addDataToLeftBox(v);
			addDataToRightBox(v);
		}
		
		Separator leftSep = new Separator();
		leftSep.setMaxWidth(300);
		leftSep.setHalignment(HPos.CENTER);
		Separator rightSep = new Separator();
		rightSep.setMaxWidth(300);
		rightSep.setHalignment(HPos.CENTER);
		leftVBox.getChildren().add(leftSep);
		rightVBox.getChildren().add(rightSep);
		
		Text budgetText = new Text("Budget: " + budget);
		budgetText.setFont(Font.font(15));
		Text costText = new Text("Cost: " + cost);
		costText.setFont(Font.font(15));
		rightVBox.getChildren().add(budgetText);
		rightVBox.getChildren().add(costText);
		rightVBox.setMargin(costText, new Insets(0,0,0,15));
		rightVBox.setMargin(budgetText, new Insets(0,0,0,15));
		Text costBudget;
		if(budget - cost > 0) {
			costBudget = new Text("You went " + (budget-cost) + " under budget");
		}
		else if(budget - cost == 0) {
			costBudget = new Text("You used your entire budget!");
		}
		else {
			costBudget = new Text("You went " + (cost-budget) + " over budget");
		}
		costBudget.setFont(Font.font(20));
		bottomVBox.getChildren().add(costBudget);
		bottomVBox.setMargin(costBudget, new Insets(-20,20,20,20));
		addGardenButton();

	}
	
	/**
	 * Adds the data to the left Box
	 * @param data
	 */
	private void addDataToLeftBox(PlantShoppingListData data) {
		Text text = new Text(data.getCount() + "x " + data.getCommonName());
		text.setFont(Font.font(15));
		leftVBox.getChildren().add(text);
		leftVBox.setMargin(text, new Insets(0,15,0,0));
	}
	
	private void addDataToRightBox(PlantShoppingListData data) {
		Text text = new Text("$" + data.getCost());
		text.setFont(Font.font(15));
		rightVBox.getChildren().add(text);
		rightVBox.setMargin(text, new Insets(0,0,0,15));
	}
	
	public VBox createBottomVBox() {
		VBox bottom_vbox = new VBox(2);
		bottom_vbox.setAlignment(Pos.BOTTOM_CENTER);
		//bottom_vbox.setStyle("-fx-background-color: aqua");
		bottom_vbox.setMinWidth(WINDOW_WIDTH / 2);
		//center.getChildren().add(bottom_vbox);
		base.add(bottom_vbox, 0, 15);
		//base.setBottom(bottom_vbox);
		return bottom_vbox;
	
	}
	
	/**
	 * Creates the left VBox
	 * @return left_vbox
	 */
	public VBox createLeftVBox(){
		VBox left_vbox = new VBox(2);
		left_vbox.setAlignment(Pos.TOP_RIGHT);
		//left_vbox.setStyle("-fx-background-color: red");
		left_vbox.setMinWidth(WINDOW_WIDTH / 2);
		center.getChildren().add(left_vbox);
		return left_vbox;
	}
	
	/**
	 * Creates the right VBox
	 * @return right_vbox
	 */
	public VBox createRightVBox(){
		VBox right_vbox = new VBox(2);
		right_vbox.setAlignment(Pos.CENTER_LEFT);
		//right_vbox.setStyle("-fx-background-color: #42f58d");
		//left_grid.setGridLinesVisible(true);
		right_vbox.setMinWidth(WINDOW_WIDTH / 2);
		center.getChildren().add(right_vbox);
		return right_vbox;
	}
	
	/**
	 * Creates and adds button to Garden Editor
	 */
	public void addGardenButton() {
		Button toGarden = new Button("Back to Garden Editor");
		toGarden.setOnMouseClicked(controller.getToGardenOnClickHandler2());
		bottomVBox.getChildren().add(toGarden);
	}
	
	/**
	 * Getter for scene
	 */
	public Scene getScene() {
		return scene;
	}
	
}