import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
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
	private CheckBox budgetOption;
	private CheckBox option3;

	private Button generateButton;
	
	private ScrollPane sp;
	private GridPane plantData;
	private TableView plantTable;
	
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
		
		plantData = new GridPane();
		//Instantiates pieChart
		ObservableList<PieChart.Data> plantsInGardenPieChartData = FXCollections.observableArrayList();
		
		
		//Sets up checkboxes
		Text optionTitle = new Text("Report Options");
		reportGrid.add(optionTitle,0,0);
		perennialDiversityOption = new CheckBox("Perennial Diversity");
		reportGrid.add(perennialDiversityOption,0,1);
		budgetOption = new CheckBox("budget");
		reportGrid.add(budgetOption,0,2);
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
		sp.setContent(plantData);
		reportGrid.add(sp,0,0);
	}
	
	public void addGardenPieGraph() {
		PieChart plantChart = new PieChart(plantsInGardenPieChartData);
		plantChart.setTitle("Perennial Diversity");
		plantData.add(plantChart,0,0);
		
		
	}
	public void addBudgetBox(double spent, double budget) {
		GridPane budgetGrid = new GridPane();
		Text spentText = new Text("Spent: " + spent);
		Text budgetText = new Text("Budget: " + budget);

		budgetGrid.add(budgetText,0,0);
		budgetGrid.add(spentText,1,0);
		//budgetGrid.setHgap(10);
		plantData.add(budgetGrid,0,1);

	}
	
	public void addTable() {
		//Instantiates plantTable
		plantTable = new TableView();
		//Creates columns 
		TableColumn commonNameCol = new TableColumn("Common Name");
        TableColumn scientificNameCol = new TableColumn("Scientific Name");
        TableColumn amountCol = new TableColumn("Perennial Amount");
        
        plantTable.getColumns().addAll(commonNameCol, scientificNameCol, amountCol);


	}
	
	
	public void addItemToPieGraph(String plantName, int plantNum) {
		plantsInGardenPieChartData.add(new PieChart.Data(plantName,plantNum));
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
	
	public CheckBox getBudgetOption() {
		return budgetOption;
	}
	
	
}