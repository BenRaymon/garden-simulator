import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.util.concurrent.ConcurrentHashMap;
//import javafx.collections.*;
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
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class ReportView extends View {

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
	private ListView<String> lepList = new ListView<String>();
	private ListView<String> plantList = new ListView<String>();

	/**
	 * Constructor
	 * 
	 * @param stage the stage for the program
	 * @param c     the controller instance
	 */
	public ReportView(Stage stage, Controller c) {
		this.controller = c;
		base = new BorderPane();
		reportGrid = new GridPane();
		base.setCenter(reportGrid);

		toGardenEditor = new Button("Garden Editor");
		toGardenEditor.setOnMouseClicked(controller.getToGardenOnClickHandler2());
		// Instantiates Scroll Pane
		sp = new ScrollPane();

		plantData = new GridPane();
		plantData.setHgap(10);
		plantData.setVgap(10);

		// Instantiates pieChart
		ObservableList<PieChart.Data> plantsInGardenPieChartData = FXCollections.observableArrayList();
		ObservableList<Plant> plantsInGardenTableData = FXCollections.observableArrayList();

		// Sets up checkboxes
		Text optionTitle = new Text("Report Options");
		reportGrid.add(optionTitle, 0, 0);
		perennialDiversityOption = new CheckBox("Perennial Diversity");
		reportGrid.add(perennialDiversityOption, 0, 1);
		budgetOption = new CheckBox("Budget");
		reportGrid.add(budgetOption, 0, 2);
		tableOption = new CheckBox("List Supported Plants");
		lepListOption = new CheckBox("List Supported Leps");
		reportGrid.add(lepListOption, 0, 3);
		// reportGrid.add(tableOption,0,3);
		generateButton = new Button("Generate Report");

		// Sets event Handler
		plantList.setOnMouseClicked(c.plantListClicked());

		// Sets button handler
		generateButton.setOnMouseClicked(c.generateReportHandler());
		reportGrid.add(generateButton, 0, 4);

		// get button styles
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		MenuBox menu = new MenuBox(c, "report");
		base.setTop(menu);
		// create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(buttonStyle);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Sets the scroll pane to the gridPane plantData, and adds the scroll pane to
	 * reportGrid.
	 * 
	 * @param None
	 * @return None
	 */
	public void showReport() {
		// Clears out content in reportGrid, and adds Scroll Pane
		reportGrid.getChildren().clear();
		sp.setContent(plantData);
		reportGrid.add(sp, 0, 0);
	}

	/**
	 * Creates an instance of a pie chart, using the data found in the
	 * plantsInGardenPieChartData observable list. Adds this to the plantData Scroll
	 * Pane.
	 * 
	 * @param None
	 * @return None
	 */
	public void addGardenPieGraph() {
		PieChart plantChart = new PieChart(plantsInGardenPieChartData);
		plantChart.setTitle("Perennial Diversity");
		plantData.add(plantChart, 1, 1);
	}

	/**
	 * Adds the budget and spent values to a gridpane, and adds the gridPane to the
	 * plantData gridPane.
	 * 
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

		budgetGrid.add(budgetText, 0, 0);
		budgetGrid.add(spentText, 1, 0);
		// budgetGrid.setHgap(10);
		plantData.add(budgetGrid, 1, 0);
	}

	/**
	 * Adds a pieChart.Data() object to the observable arrayList addItemToPieGraph
	 * using the plantName and plantNum parameters.
	 * 
	 * @param plantName
	 * @param plantNum
	 * @return None
	 */
	public void addItemToPieGraph(String plantName, int plantNum) {
		plantsInGardenPieChartData.add(new PieChart.Data(plantName, plantNum));
	}

	public void addLep(String lepName) {
		if (!lepsInGarden.contains(lepName)) {
			lepsInGarden.add(lepName);
		}
	}

	public void addLepList() {
		lepList = new ListView<String>();
		lepList.setItems(lepsInGarden);
		System.out.println("Adding lepList to plantData");
		Text lepText = new Text("Leps in Garden");
		lepList.setOnMouseClicked(controller.lepListClicked());
		plantData.add(lepText, 2, 2);
		plantData.add(lepList, 2, 3);
	}

	public void addPlant(String plant) {
		plantsInGarden.add(plant);
	}

	public void addPlantList() {
		plantList = new ListView<String>();
		plantList.setItems(plantsInGarden);
		Text plantText = new Text("Plants in Garden");
		plantList.setOnMouseClicked(controller.plantListClicked());
		plantData.add(plantText, 0, 2);
		plantData.add(plantList, 0, 3);
	}

	/**
	 * Getter for Scene.
	 * 
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Getter for PerennialDiversityOption checkbox.
	 * 
	 * @return perennialDiversityOption
	 */
	public CheckBox getPerennialDiversityOption() {
		return perennialDiversityOption;
	}

	/**
	 * Getter for BudgetOption checkbox.
	 * 
	 * @return budgetOption
	 */
	public CheckBox getBudgetOption() {
		return budgetOption;
	}

	public CheckBox getLepListOption() {
		return lepListOption;
	}

	public ListView<String> getPlantListView() {
		return plantList;
	}

	public ListView<String> getLepView() {
		return lepList;
	}

	public void openPlantListPopUp(MouseEvent a, ConcurrentHashMap<String,Plant> plantHash) {
		System.out.println("Inside openPlantListPopUp");
		ListView temp = (ListView)a.getSource();
		String plantName = (String)temp.getSelectionModel().getSelectedItem();
		System.out.println(plantName);
		
		Stage popupPlant = new Stage();
		ImageView plantImgV = new ImageView();
		Image plantImg = View.getImages().get(plantName);
		plantImgV.setImage(plantImg);
		Plant tempPlant = plantHash.get(plantName);
		
		
		Text t = new Text("Scientific Name: " + tempPlant.getScientificName() + "\nCommon Name: "+ tempPlant.getCommonName());
		
		VBox v = new VBox();
		v.getChildren().add(plantImgV);
		v.getChildren().add(t);
		Scene popupScene = new Scene(v,plantImgV.getImage().getWidth(),plantImgV.getImage().getHeight()+50);
		popupPlant.setScene(popupScene);
		popupPlant.show();
		
		
		//popupPlant.setScene(plantImg);
	}
	
	public void openLepListPopUp(MouseEvent a, ConcurrentHashMap<String,Lep> lepHash) {
		ListView temp = (ListView)a.getSource();
		String lepName = (String)temp.getSelectionModel().getSelectedItem();
		System.out.println(lepName);
		
		Stage popupLep = new Stage();
		ImageView lepImgV = new ImageView();
		Image lepImg = View.getLepImages().get(lepName);
		
		VBox v = new VBox();
		if(lepImg != null) {
			lepImgV.setImage(lepImg);
			v.getChildren().add(lepImgV);
		}
		else {
			Text noImg = new Text("No Image Found");
			v.getChildren().add(noImg);
		}
		Text t = new Text("Lep Name: " + lepHash.get(lepName).getLepName() + "\nLep Family Name: " + lepHash.get(lepName).getLepFamily());
		
		v.getChildren().add(t);
		Scene popupScene;
		if(lepImg != null) {
		popupScene = new Scene(v,lepImgV.getImage().getWidth(),lepImgV.getImage().getHeight()+50);
		}
		else {
			popupScene = new Scene(v,500,500);
		}
		popupLep.setScene(popupScene);
		popupLep.show();
	}
}