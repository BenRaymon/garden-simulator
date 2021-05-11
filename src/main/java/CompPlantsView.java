import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CompPlantsView extends View {


	private ImageView imageViewA;
	private ImageView imageViewB;
	private Text plantSummaryA;
	private Text plantSummaryB;
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

	// Hbox holds plant data
	private HBox plantDataHbox;
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
		
		base = new BorderPane();
		center.setHgap(10);
		center.setVgap(10);
		center.setAlignment(Pos.CENTER);


		// Creating table
		items = FXCollections.observableArrayList("General Info", "Lep Compare", "Radius Compare", "Size Compare");
		list = new ListView<String>();
		list.setItems(items);
		list.setPrefHeight(70);
		list.setOnMouseClicked(controller.listClickedHandler());

		// Setting plant Summary Values
		plantSummaryA = new Text("Plant summary A");
		plantSummaryB = new Text("Plant summary B");

		Button leftPlantButton = new Button("Left Plant");
		Button rightPlantButton = new Button("Right Plant");

		plantsList = FXCollections.observableArrayList("Test Plant A", "Test Plant B", "Test Plant C");
		plantsListView = new ListView<String>();
		plantsListView.setItems(plantsList);
		plantsListView.setPrefHeight(100);

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
		
		
		MenuBox menu = new MenuBox(c, "comp_p");
		center.add(plantSummaryA, 0, 1, 1, 1);
		center.add(plantSummaryB, 2, 1, 1, 1);
		center.add(leftPlantButton, 0, 2, 1, 1);
		center.add(rightPlantButton, 2, 2, 1, 1);
		center.add(list, 1, 3, 1, 1);
		
		//Adds image Views
		center.add(imageViewA,0,0,1,1);
		center.add(imageViewB,2,0,1,1);
		
		//Adds Lists
		center.add(plantsListView, 1, 2, 1, 1);

		// get button styles
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		base.setCenter(center);
		base.setTop(menu);

		// create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(buttonStyle);
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
	 * @param aDescription
	 * @param bDescription
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

	//TODO
	public void setPlantList(String p) {
		plantsList.add(p);
	}

	public void clearPlantList() {
		plantsList.clear();
	}
	public ListView<String> getplantList() {
		return plantsListView;
	}
	
	public void setLeftImage(String s) {
		Image tempImg = getImages().get(s);
		imageViewA.setImage(tempImg);
	}
	
	public void setRightImage(String s) {
		Image tempImg = getImages().get(s);
		imageViewB.setImage(tempImg);
	}
}
