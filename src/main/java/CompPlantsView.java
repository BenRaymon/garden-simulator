import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
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

	private Image plantImageA;
	private Image plantImageB;
	private Text plantSummaryA;
	private Text plantSummaryB;

	private int plantALeps;
	private int plantBLeps;
	private String plantAName;
	private String plantBName;

	private Scene scene;
	private Controller controller;
	private TextField plantNameInput;
	private Button toGardenEditor;
	// private TableView table;
	private ListView<String> list;
	private ObservableList<String> items;
	GridPane base;
	// For bar graph ----------
	// ** x and y axis for the bar graph. Can be whatever axis the bar graph needs.
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	// *** Can be used to represent whatever information needs to be seen on bar
	// graph
	private int dataZone1;
	private int dataZone2;
	// ** Holds the physical Data
	private XYChart.Series series1;
	private XYChart.Series series2;

	// Actual barChart
	private BarChart<String, Number> bc;
	
	//Hbox holds plant data
	HBox plantDataHbox;

	// --------------------------

	public CompPlantsView(Stage stage, Controller c) {
		this.controller = c;
		base = new GridPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);

		// button to go back to the garden editor
		toGardenEditor = new Button("Garden Editor");
		toGardenEditor.setOnMouseClicked(controller.getToGardenOnClickHandler());
		base.add(toGardenEditor, 0, 10);

		// Creating table
		items = FXCollections.observableArrayList("General Info", "Lep Compare", "Radius Compare", "Size Compare",
				"Color Compare");
		list = new ListView<String>();
		// list.setOn
		list.setItems(items);
		list.setPrefHeight(70);
		list.setOnMouseClicked(controller.listClickedHandler());

		// TableColumn plantACol = new TableColumn("Plant A");
		// TableColumn plantInfoCol = new TableColumn("Plant Info");
		// TableColumn plantBCol = new TableColumn("Plant B");
		// table.getColumns().addAll(plantACol, plantInfoCol, plantBCol);

		// Setting plant Summary Values
		plantSummaryA = new Text("Plant summary A");
		plantSummaryB = new Text("Plant summary B");

		Button leftPlantButton = new Button("Left Plant");
		Button rightPlantButton = new Button("Right Plant");

		plantNameInput = new TextField();

		rightPlantButton.setOnMouseClicked(c.RightPlantButtonClickedHandler());
		leftPlantButton.setOnMouseClicked(c.LeftPlantButtonClickedHandler());

		GridPane center = new GridPane();

		base.add(plantSummaryA, 0, 0, 1, 1);
		// base.add(list,0,0,1,1);
		base.add(plantSummaryB, 1, 0, 1, 1);
		base.add(leftPlantButton, 0, 1, 1, 1);
		base.add(rightPlantButton, 1, 1, 1, 1);
		base.add(plantNameInput, 0, 2, 1, 1);
		base.add(list, 0, 3, 1, 1);
		// base.add(bc,0,4,1,1);

		// create and set scene with base
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

	public ObservableList<String> getObservableList() {
		return items;
	}

	public ListView<String> getListView() {
		return list;
	}

	public void setInfoView() {

	}

	public void setLepCompare(String plantAName, String plantBName, int plantALeps, int plantBLeps) {

		base.getChildren().remove(bc);
		base.getChildren().remove(plantDataHbox);

		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Plant names");
		yAxis.setLabel("# of Leps");

		series1 = new XYChart.Series();
		// series2 = new XYChart.Series();
		series1.getData().add(new XYChart.Data(plantAName, plantALeps));
		series1.getData().add(new XYChart.Data(plantBName, plantBLeps));
		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Leps Supported");
		bc.getData().addAll(series1);
		base.add(bc, 0, 4, 1, 1);

	}

	public void setRadiusCompare(String plantAName, String plantBName, double plantASpreadLower,
			double plantASpreadUpper, double plantBSpreadLower, double plantBSpreadUpper) {
		base.getChildren().remove(bc);
		base.getChildren().remove(plantDataHbox);

		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Plant names");
		yAxis.setLabel("Plant Spread (in *put units here*)");

		series1 = new XYChart.Series();
		series2 = new XYChart.Series();
		series1.setName("Spread (Lower Bound)");
		series2.setName("Spread (Upper Bound)");

		series1.getData().add(new XYChart.Data(plantAName, plantASpreadLower));
		series1.getData().add(new XYChart.Data(plantBName, plantBSpreadLower));

		series2.getData().add(new XYChart.Data(plantAName, plantASpreadUpper));
		series2.getData().add(new XYChart.Data(plantBName, plantBSpreadUpper));

		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Plant Spread");
		bc.getData().addAll(series1, series2);
		base.add(bc, 0, 4, 1, 1);

	}

	public void setSizeCompare(String plantAName, String plantBName, double plantASizeLower, double plantASizeUpper,
			double plantBSizeLower, double plantBSizeUpper) {
		base.getChildren().remove(bc);
		base.getChildren().remove(plantDataHbox);
		
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Plant names");
		yAxis.setLabel("Plant Size (in *put units here*)");

		series1 = new XYChart.Series();
		series2 = new XYChart.Series();
		series1.setName("Size (Lower Bound)");
		series2.setName("Size (Upper Bound)");

		series1.getData().add(new XYChart.Data(plantAName, plantASizeLower));
		series1.getData().add(new XYChart.Data(plantBName, plantBSizeLower));

		series2.getData().add(new XYChart.Data(plantAName, plantASizeUpper));
		series2.getData().add(new XYChart.Data(plantBName, plantBSizeUpper));

		bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Plant Size");
		bc.getData().addAll(series1, series2);
		base.add(bc, 0, 4, 1, 1);

	}
	
	public void setGeneralInfoComapre(String aDescription, String bDescription) {
		System.out.println("Inside general info compare");
		base.getChildren().remove(bc);
		base.getChildren().remove(plantDataHbox);
		
		plantDataHbox = new HBox();
		plantDataHbox.setSpacing(10);
		Text plantInfoLabel = new Text("--Common Name--\n--Scientific Name--\n--Family--\n--Color--\n--Lower Size(In ? Units)--\n--Upper Size(In ? Units)--\n--Lower Radius(In ? Units)--\n--Upper Radius(In ? Units)--");
		
		Text plantInfoA = new Text(aDescription);
		Text plantInfoB = new Text(bDescription);
		plantDataHbox.getChildren().addAll(plantInfoA,plantInfoLabel,plantInfoB);
		
		
		base.add(plantDataHbox,0,4,1,1);
		
	}

	public void setAName(String n) {
		plantAName = n;
	}

	public void setBName(String n) {
		plantBName = n;
	}
}