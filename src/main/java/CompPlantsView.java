import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CompPlantsView extends View {


	private ImageView imageViewA;
	private ImageView imageViewB;
	private Label plantSummaryA;
	private Label plantSummaryB;
	private Scene scene;
	private Controller controller;
	private ListView<String> list;
	private ObservableList<String> items;
	private BorderPane base;
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private XYChart.Series series1;
	private XYChart.Series series2;
	private BarChart<String, Number> bc;
	
	// for the carousel
	private HashMap<String, Plant> recPlants;
	private HashMap<Image, String> recommendedPlantImages;
	private ObservableList<String> recList;
	private ListView<VBox> top;
	private VBox container;
	
	// Hbox holds plant data
	private HBox plantDataHbox;
	private GridPane left;
	private GridPane right;
	private GridPane center = new GridPane();
	private ObservableList<String> plantsList;
	private ListView<String> plantsListView;

	
	/**
	 * Creates the compPlantsView.
	 * 
	 * @param Stage      stage
	 * @param Controller c
	 */
	public CompPlantsView(Stage stage, Controller c) {
		this.controller = c;
		container = new VBox();
		left = new GridPane();
		right = new GridPane();
		
		right.setHgap(10);
		right.setVgap(10);
		left.setHgap(10);
		left.setVgap(10);
		
		right.setMinWidth(300);
		left.setMinWidth(300);
		
		base = new BorderPane();
		center.setHgap(10);
		center.setVgap(10);
		center.setAlignment(Pos.TOP_CENTER);
		center.setPadding(new Insets(0,1,2,3));


		// Creating table
		items = FXCollections.observableArrayList("General Info", "Lep Compare", "Radius Compare", "Size Compare");
		list = new ListView<String>();
		list.setItems(items);
		list.setMaxSize(500, 190);
		list.setOnMouseClicked(controller.listClickedHandler());

		// Setting plant Summary Values
		plantSummaryA = new Label("Plant summary A");
		plantSummaryB = new Label("Plant summary B");

		Button leftPlantButton = new Button("Left Plant");
		Button rightPlantButton = new Button("Right Plant");

		plantsList = FXCollections.observableArrayList("Test Plant A", "Test Plant B", "Test Plant C");
		plantsListView = new ListView<String>();
		plantsListView.setItems(plantsList);
		plantsListView.setPrefHeight(300);

		rightPlantButton.setOnMouseClicked(c.RightPlantButtonClickedHandler());
		leftPlantButton.setOnMouseClicked(c.LeftPlantButtonClickedHandler());
	
		imageViewA = new ImageView();
		imageViewB = new ImageView();
		
		imageViewA.setFitHeight(200);
		imageViewA.setFitWidth(200);
		imageViewA.setPreserveRatio(true);
		
		imageViewB.setFitHeight(200);
		imageViewB.setFitWidth(200);
		imageViewB.setPreserveRatio(true);
		
		
		MenuBox menu = new MenuBox(c, "comp");
		container.getChildren().add(menu);
		left.add(plantSummaryA, 2, 3);
		right.add(plantSummaryB, 2, 3);
//		center.add(leftPlantButton, 0, 3, 1, 1);
//		center.add(rightPlantButton, 2, 3, 1, 1);
		
		center.add(list, 1, 2, 1, 1);
		
		right.add(rightPlantButton, 2, 6);
		left.add(leftPlantButton, 2, 6);
		
		right.setStyle("-fx-background-color: "+darkGreen);
		left.setStyle("-fx-background-color: "+darkGreen);
		
		//Adds image Views
		right.add(imageViewB, 2, 9);
		left.add(imageViewA, 2, 9);
		
		GridPane.setValignment(rightPlantButton, VPos.CENTER);
		GridPane.setValignment(leftPlantButton, VPos.CENTER);
		GridPane.setValignment(plantSummaryA, VPos.CENTER);
		GridPane.setValignment(plantSummaryB, VPos.CENTER);
		GridPane.setValignment(imageViewB, VPos.CENTER);
		GridPane.setValignment(imageViewA, VPos.CENTER);
		//Adds Lists
		//center.add(plantsListView, 1, 2, 1, 1);
	
		base.setLeft(left);
		base.setRight(right);
		
		// get button styles
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		String scrollStyle = getClass().getResource("scrollbars.css").toExternalForm();
		String labelStyle = getClass().getResource("labels.css").toExternalForm();
		String smallStyle = getClass().getResource("smallview.css").toExternalForm();
		list.getStylesheets().add(smallStyle);
		plantSummaryA.getStylesheets().add(labelStyle);
		plantSummaryB.getStylesheets().add(labelStyle);
		
		base.setCenter(center);
		base.setTop(container);

		base.setStyle("-fx-background-color: "+offWhite);
		
		// create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(buttonStyle);
		scene.getStylesheets().add(scrollStyle);
		//scene.getStylesheets().add(labelStyle);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Scene getter.
	 * 
	 * @return Scene scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * RightTextBox (plantSummaryB) getter.
	 * 
	 * @return String plantSummaryB text
	 */
	public String getRightBody() {
		return plantSummaryB.getText();
	}

	/**
	 * LeftTextBox (plantSummaryA) getter.
	 * 
	 * @return String plantSummrayA text
	 */
	public String getLeftBody() {
		return plantSummaryA.getText();
	}

	/**
	 * RightTextBox (plantSummaryB) setter.
	 * 
	 * @param s string to set text of 
	 */
	public void setRightBody(String s) {
		plantSummaryB.setText(s);
	}

	/**
	 * LeftTextBox (plantSummaryA) setter.
	 * 
	 * @param s string to set text of
	 */
	public void setLeftBody(String s) {
		plantSummaryA.setText(s);
	}
	
	/**
	 * ListView getter.
	 * 
	 * @return ListView<String> list
	 */
	public ListView<String> getListView() {
		return list;
	}

	/**
	 * LepCompare setter.
	 * 
	 * @param plantA
	 * @param plantB
	 */
	public void setLepCompare(Plant plantA, Plant plantB) {
		center.getChildren().remove(bc);
		center.getChildren().remove(plantDataHbox);
		
		
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Plant names");
		yAxis.setLabel("# of Leps");
		
		series1 = new XYChart.Series();
		series1.getData().add(new XYChart.Data(plantA.getScientificName(), plantA.getLepsSupported()));
		series1.getData().add(new XYChart.Data(plantB.getScientificName(), plantB.getLepsSupported()));
		
		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Leps Supported");
		bc.getData().addAll(series1);
		center.add(bc, 1, 5, 1, 1);
	}

	/**
	 * RadiusCompare setter.
	 * 
	 * @param plantA
	 * @param plantB
	 */
	public void setRadiusCompare(Plant plantA, Plant plantB) {
		center.getChildren().remove(bc);
		center.getChildren().remove(plantDataHbox);
		
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Plant names");
		yAxis.setLabel("Plant Spread (in feet)");
	
		series1 = new XYChart.Series();
		series2 = new XYChart.Series();
		series1.setName("Spread (Lower Bound)");
		series2.setName("Spread (Upper Bound)");
	
		series1.getData().add(new XYChart.Data(plantA.getScientificName(), plantA.getSpreadRadiusLower()));
		series1.getData().add(new XYChart.Data(plantB.getScientificName(), plantB.getSpreadRadiusLower()));
	
		series2.getData().add(new XYChart.Data(plantA.getScientificName(), plantA.getSpreadRadiusUpper()));
		series2.getData().add(new XYChart.Data(plantB.getScientificName(), plantB.getSpreadRadiusUpper()));
	
		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Plant Spread");
		bc.getData().addAll(series1, series2);
		center.add(bc, 1, 5, 1, 1);
	}

	/**
	 * SizeCompare Setter.
	 * 
	 * @param plantA
	 * @param plantB
	 */
	public void setSizeCompare(Plant plantA, Plant plantB) {
		center.getChildren().remove(bc);
		center.getChildren().remove(plantDataHbox);

		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Plant names");
		yAxis.setLabel("Plant Size (in feet)");

		series1 = new XYChart.Series();
		series2 = new XYChart.Series();
		series1.setName("Size (Lower Bound)");
		series2.setName("Size (Upper Bound)");

		series1.getData().add(new XYChart.Data(plantA.getScientificName(), plantA.getSizeLower()));
		series1.getData().add(new XYChart.Data(plantB.getScientificName(), plantB.getSizeLower()));
	
		series2.getData().add(new XYChart.Data(plantA.getScientificName(), plantA.getSizeUpper()));
		series2.getData().add(new XYChart.Data(plantB.getScientificName(), plantB.getSizeUpper()));

		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Plant Size");
		bc.getData().addAll(series1, series2);
		center.add(bc, 1, 5, 1, 1);
	}

	/**
	 * GeneralInfoCompare setter.
	 * 
	 * @param plantA
	 * @param plantB
	 */
	public void setGeneralInfoComapre(Plant plantA, Plant plantB) {
		System.out.println("Inside general info compare");
		center.getChildren().remove(bc);
		center.getChildren().remove(plantDataHbox);

		plantDataHbox = new HBox();
		plantDataHbox.setSpacing(10);
		Text plantInfoLabel = new Text(
				"--Common Name--\n--Scientific Name--\n--Family--\n--Color--\n--Lower Size(In feet)--\n--Upper Size(In feet)--\n--Lower Radius(In feet)--\n--Upper Radius(In feet)--");

		Text plantInfoA = new Text(plantA.toString());
		Text plantInfoB = new Text(plantB.toString());
		plantDataHbox.getChildren().addAll(plantInfoA, plantInfoLabel, plantInfoB);

		center.add(plantDataHbox, 1, 5, 1, 1);

	}

	/**
	 * add plant to plant list
	 * 
	 * @param s string for plant name
	 */
	public void setPlantList(String p) {
		plantsList.add(p);
	}

	/**
	 * clears the list of plants
	 */
	public void clearPlantList() {
		plantsList.clear();
	}
	
	/**
	 * plant list getter
	 * 
	 * @return plantsListView
	 */
	public ListView<String> getplantList() {
		return plantsListView;
	}
	
	/**
	 * left plant image setter.
	 * 
	 * @param s string for plant name
	 */
	public void setLeftImage(String s) {
		Image tempImg = getImages().get(s);
		imageViewA.setImage(tempImg);
	}
	
	/**
	 * right plant image setter.
	 * 
	 * @param s string for plant name
	 */
	public void setRightImage(String s) {
		Image tempImg = getImages().get(s);
		imageViewB.setImage(tempImg);
	}
	
	public String getSelectedPlant() {
		VBox tmp = top.getSelectionModel().getSelectedItem();
		return ((Text) tmp.getChildren().get(1)).getText();
	}
	
	/**
	 * Draws images on top grid
	 * @param plantNames
	 */
	public void setPlantImages(ArrayList<String> plantNames){
		//reset the map of current recommended images
		recommendedPlantImages = new HashMap<Image, String>();
		//make a list to hold the circle objects for each plant 
		ArrayList<VBox> recommendedPlantCircs = new ArrayList<VBox>();
		//master static list of all images from the abstract view
		ConcurrentHashMap<String, Image> allImages = View.getImages();
		
		//iterate through every string in the list of plant names to add
		for (String name : plantNames) {
			try {
				//create a circle object with the corresponding plant image
				Image image = allImages.get(name);
				recommendedPlantImages.put(image, name);
				Circle circ = new Circle(40);
				VBox holder = new VBox();
		        circ.setFill(new ImagePattern(image));
		        
		        holder.setAlignment(Pos.CENTER);
		        holder.getChildren().add(circ);
		        Text nameText = new Text(name);
		        nameText.setTextAlignment(TextAlignment.CENTER);
		        holder.getChildren().add(nameText);
		        recommendedPlantCircs.add(holder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//convert the array list into a backing list for the list view
		//add list of recommended plant circles to the listview and add the listview to the base border pane 
		ObservableList<VBox> backingList = FXCollections.observableArrayList(recommendedPlantCircs);
		if(container.getChildren().contains(top))
			container.getChildren().remove(top);
		top = new ListView<>(backingList);
		top.setOrientation(Orientation.HORIZONTAL);
		top.setMaxHeight(125);
		String topStyle = getClass().getResource("listview.css").toExternalForm();
		top.getStylesheets().add(topStyle);
		container.getChildren().add(top);
		
	}
}
