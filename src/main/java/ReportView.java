import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReportView extends View{

	private Text title;
	private Text summary; 
	private Scene scene;
	private Controller controller;
	private BorderPane base;
	private Button toGardenEditor;
	private GridPane reportGrid;
	
	private CheckBox perennialDiversityOption;
	private CheckBox option2;
	private CheckBox option3;

	private Button generateButton;
	
	private ScrollPane sp;
	
	private ObservableList<PieChart.Data> plantsInGardenPieChartData = FXCollections.observableArrayList();
	
	public ReportView(Stage stage, Controller c) {
		this.controller = c;
		base = new BorderPane();
		reportGrid = new GridPane();
		base.setTop(reportGrid);
		
		toGardenEditor = new Button("Garden Editor");
		toGardenEditor.setOnMouseClicked(controller.getToGardenOnClickHandler());
		createBottom().add(toGardenEditor, 0, 0);
//		base.setHgap(10);
//		base.setVgap(10);
		//base.setAlignment(Pos.CENTER);
		
		//Instantiates Scroll Pane
		sp = new ScrollPane();
		//Instantiates pieChart
		ObservableList<PieChart.Data> plantsInGardenPieChartData = FXCollections.observableArrayList();
		
		
		Text optionTitle = new Text("Report Options");
		reportGrid.add(optionTitle,0,0);
		perennialDiversityOption = new CheckBox("Perennial Diversity");
		reportGrid.add(perennialDiversityOption,0,1);
		option2 = new CheckBox("option2");
		reportGrid.add(option2,0,2);
		option3 = new CheckBox("option3");
		reportGrid.add(option3,0,3);
		generateButton = new Button("Generate Report");
		//Sets button handler
		generateButton.setOnMouseClicked(c.generateReportHandler());
		reportGrid.add(generateButton,0,4);
		
		
		// Create temp report
		//this.createTempReport();
		//base.add(temp, 0, 1);
		
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
	}
	
	public void showReport() {
		//Clears out content in reportGrid, and adds Scroll Pane
		reportGrid.getChildren().clear();
		reportGrid.add(sp,0,0);
	}
	
	public void addGardenPieGraph() {
		PieChart plantChart = new PieChart(plantsInGardenPieChartData);
		plantChart.setTitle("Perennial Diversity");
		sp.setContent(plantChart);
		
		
	}
	
	
	public void addItemToPieGraph(String plantName, int plantNum) {
		plantsInGardenPieChartData.add(new PieChart.Data(plantName,plantNum));
	}
	
	
	
	private void createTempReport() {
//		// Fake pie chart for leps supported
//		ObservableList<PieChart.Data> lepsSupportedPieChartData = FXCollections.observableArrayList(
//				new PieChart.Data("Lep A", 35),
//				new PieChart.Data("Lep B", 65));
//		PieChart lepChart = new PieChart(lepsSupportedPieChartData);
//		lepChart.setTitle("Leps Supported");
//		reportGrid.add(lepChart, 0, 0);
		
		// Fake pie chart for plants in garden
		ObservableList<PieChart.Data> plantsInGardenPieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Plant A", 5),
				new PieChart.Data("Plant B", 15),
				new PieChart.Data("Plant C", 20),
				new PieChart.Data("Plant D", 8),
				new PieChart.Data("Plant E", 7),
				new PieChart.Data("Plant F", 10));
		PieChart plantChart = new PieChart(plantsInGardenPieChartData);
		plantChart.setTitle("Plants In Garden");
		reportGrid.add(plantChart, 1, 0);
		
		Text budget = new Text("Budget: $1000");
		reportGrid.add(budget, 0, 2);
		Text plantCost = new Text("Plant cost: $750");
		reportGrid.add(plantCost, 0, 3);
	}
	
	public GridPane createBottom() {
		GridPane bottom_grid = new GridPane();
		bottom_grid.setAlignment(Pos.CENTER_LEFT);
		bottom_grid.setStyle("-fx-background-color: aqua");
		bottom_grid.setGridLinesVisible(true);
		bottom_grid.setHgap(10);
		bottom_grid.setVgap(10);
		base.setBottom(bottom_grid);
		return bottom_grid;	
	}
	
	public Scene getScene() {
		return scene;
	}
	
	
	public void displayReport(String title, String summary) {
		
	}
	
	public CheckBox getPerennialDiversityOption() {
		return perennialDiversityOption;
	}
	
	
}