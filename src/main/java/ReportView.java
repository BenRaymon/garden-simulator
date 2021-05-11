import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private CheckBox tableOption;
	private CheckBox lepListOption;
	private Button generateButton;
	private ScrollPane sp;
	private GridPane plantData;
	private TableView plantTable;
	private ObservableList<PieChart.Data> plantsInGardenPieChartData = FXCollections.observableArrayList();
	private ObservableList<String> plantsInGarden = FXCollections.observableArrayList();
	private ObservableList<String> lepsInGarden = FXCollections.observableArrayList();
	private ListView <String> lepList;
	private ListView <String> plantList;
	
	
	/**
	 * Constructor
	 * @param stage the stage for the program
	 * @param c the controller instance
	 */
	public ReportView(Stage stage, Controller c) {
		this.controller = c;
		base = new BorderPane();
		reportGrid = new GridPane();
		base.setCenter(reportGrid);
		
		toGardenEditor = new Button("Garden Editor");
		toGardenEditor.setOnMouseClicked(controller.getToGardenOnClickHandler2());		
		//Instantiates Scroll Pane
		sp = new ScrollPane();
		
		plantData = new GridPane();
		plantData.setHgap(10);
		plantData.setVgap(10);

		//Instantiates pieChart
		ObservableList<PieChart.Data> plantsInGardenPieChartData = FXCollections.observableArrayList();
		ObservableList<Plant> plantsInGardenTableData = FXCollections.observableArrayList();

		//Sets up checkboxes
		Text optionTitle = new Text("Report Options");
		reportGrid.add(optionTitle,0,0);
		perennialDiversityOption = new CheckBox("Perennial Diversity");
		reportGrid.add(perennialDiversityOption,0,1);
		budgetOption = new CheckBox("Budget");
		reportGrid.add(budgetOption,0,2);
		tableOption = new CheckBox("List Supported Plants");
		lepListOption = new CheckBox("List Supported Leps");
		reportGrid.add(lepListOption,0,3);
		//reportGrid.add(tableOption,0,3);
		generateButton = new Button("Generate Report");
		
		
		//Sets button handler
		generateButton.setOnMouseClicked(c.generateReportHandler());
		reportGrid.add(generateButton,0,4);
		
		// get button styles
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		MenuBox menu = new MenuBox(c, "report");
		base.setTop(menu);
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(buttonStyle);
        stage.setScene(scene);
        stage.show();
	}
	
	/**
	 * Sets the scroll pane to the gridPane plantData, and adds the scroll pane to reportGrid. 
	 * @param None
	 * @return None
	 */
	public void showReport() {
		//Clears out content in reportGrid, and adds Scroll Pane
		reportGrid.getChildren().clear();
		sp.setContent(plantData);
		reportGrid.add(sp,0,0);
	}
	
	/**
	 * Creates an instance of a pie chart, using the data found in the plantsInGardenPieChartData observable list. 
	 * Adds this to the plantData Scroll Pane.
	 * @param None
	 * @return None 
	 */
	public void addGardenPieGraph() {
		PieChart plantChart = new PieChart(plantsInGardenPieChartData);
		plantChart.setTitle("Perennial Diversity");
		plantData.add(plantChart,1,1);
	}
	
	/**
	 * Adds the budget and spent values to a gridpane, and adds the gridPane to the plantData gridPane. 
	 * @param spent
	 * @param budget
	 * @return None
	 */
	public void addBudgetBox(double spent, double budget) {
		GridPane budgetGrid = new GridPane();
		budgetGrid.setHgap(10);
		budgetGrid.setVgap(10);
		budgetGrid.setGridLinesVisible(true);
		budgetGrid.setAlignment(Pos.CENTER);
		Text spentText = new Text("Spent: " + spent);
		Text budgetText = new Text("Budget: " + budget);

		budgetGrid.add(budgetText,0,0);
		budgetGrid.add(spentText,1,0);
		//budgetGrid.setHgap(10);
		plantData.add(budgetGrid,1,0);
	}
	
	/**
	 * Adds a pieChart.Data() object to the observable arrayList addItemToPieGraph using the plantName and plantNum parameters. 
	 * @param plantName
	 * @param plantNum
	 * @return None
	 */
	public void addItemToPieGraph(String plantName, int plantNum) {
		plantsInGardenPieChartData.add(new PieChart.Data(plantName,plantNum));
	}
	
	
	public void addLep(String lepName) {
		if(!lepsInGarden.contains(lepName)) {
		lepsInGarden.add(lepName);
		}		
	}
	
	public void addLepList() {
		lepList = new ListView<String>();
		lepList.setItems(lepsInGarden);
		System.out.println("Adding lepList to plantData");
		plantData.add(lepList,2,2);
	}
	
	public void addPlant(String plant) {
		plantsInGarden.add(plant);
	}
	public void addPlantList() {
		plantList = new ListView<String>();
		plantList.setItems(plantsInGarden);
		plantData.add(plantList,0,2);
	}
	
	/**
	 * Getter for Scene.
	 *@return scene
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Getter for PerennialDiversityOption checkbox.
	 * @return perennialDiversityOption
	 */
	public CheckBox getPerennialDiversityOption() {
		return perennialDiversityOption;
	}
	
	/**
	 * Getter for BudgetOption checkbox. 
	 * @return budgetOption
	 */
	public CheckBox getBudgetOption() {
		return budgetOption;
	}
	
	public CheckBox getLepListOption() {
		return lepListOption;
	}
	
}