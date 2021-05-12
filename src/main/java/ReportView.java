import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;
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
	private ObservableList<PieChart.Data> lepsInGardenPieChartData = FXCollections.observableArrayList(); 
	private ObservableList<String> plantsInGarden = FXCollections.observableArrayList();
	private ObservableList<String> lepsInGarden = FXCollections.observableArrayList();
	private ListView<String> lepList = new ListView<String>();
	private ListView<String> plantList = new ListView<String>();
	private HashMap <String,Integer> lepCount = new HashMap<String, Integer>();

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
		reportGrid.setAlignment(Pos.CENTER);
		base.setCenter(reportGrid);
		base.setStyle("-fx-background-color: " + offWhite);

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
		plantData.getChildren().clear();
		//reportGrid = new GridPane();
		lepsInGardenPieChartData.clear();
		plantsInGardenPieChartData.clear();
		plantsInGarden.clear();
		lepsInGarden.clear();
		
		sp.setContent(plantData);
		reportGrid.add(sp, 0, 0);
		reportGrid.add(generateButton,0,1);
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
	 *  Creates an instance of a pie chart, using the data found in the
	 * lepsInGardenPieChartData observable list. Adds this to the lepData Scroll
	 * Pane.
	 * 
	 * @param None
	 * @return None
	 */
	public void addLepPieGraph() {
		PieChart lepChart = new PieChart(lepsInGardenPieChartData);
		lepChart.setTitle("Lep Diversity");
		plantData.add(lepChart,1,3);
		
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
		budgetGrid.setGridLinesVisible(false);
		
		budgetGrid.setAlignment(Pos.CENTER);
		Text spentText = new Text("Spent: " + spent);
		spentText.setStyle("-fx-font-size: 24px");
		Text budgetText = new Text("Budget: " + budget);
		budgetText.setStyle("-fx-font-size: 24px");


		budgetGrid.add(budgetText, 0, 0);
		budgetGrid.add(spentText, 1, 0);
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
	
	public void addItemToLepPieGraph(String lepName, int lepNum) {
		lepsInGardenPieChartData.add(new PieChart.Data(lepName, lepNum));
	}

	//TODO
	/**
	 * 
	 *Adds lep name to lepsInGarden observableArrayList.  
	 * @param lepName
	 */
	public void addLep(String lepName) {
		if (!lepsInGarden.contains(lepName)) {
			lepsInGarden.add(lepName);
		}
	}

	/**
	 * Adds the lepList to the plantData pane. 
	 */
	public void addLepList() {
		lepList = new ListView<String>();
		lepList.setItems(lepsInGarden);
		Text lepText = new Text("Leps in Garden");
		lepList.setOnMouseClicked(controller.lepListClicked());
		plantData.add(lepText, 2, 2);
		plantData.add(lepList, 2, 3);
	}

	/**
	 * Adds a plant name to the plantsInGarden observable array list. 
	 * @param plant
	 */
	public void addPlant(String plant) {
		plantsInGarden.add(plant);
	}

	/**
	 * Adds the plantList to the plantData pane. 
	 */
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
	 * PlantListView getter. 
	 * @return ListView<String> plantList
	 */
	public ListView<String> getPlantListView() {
		return plantList;
	}

	/**
	 * LepView getter.
	 * @return ListView<String> lepView
	 */
	public ListView<String> getLepView() {
		return lepList;
	}

	/**
	 * Opens the plantList popup based on the plant that was clicked on. 
	 * 
	 * @param a
	 * @param plantHash
	 */
	public void openPlantListPopUp(MouseEvent a, ConcurrentHashMap<String,Plant> plantHash) {
		ListView temp = (ListView)a.getSource();
		String plantName = (String)temp.getSelectionModel().getSelectedItem();
		
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
	}
	
	/**
	 * Opens the lepList popup based on the lep that was clicked on. 
	 * 
	 * @param a
	 * @param lepHash
	 */
	public void openLepListPopUp(MouseEvent a, ConcurrentHashMap<String,Lep> lepHash) {
		ListView temp = (ListView)a.getSource();
		String lepName = (String)temp.getSelectionModel().getSelectedItem();
		
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
	
	/**
	 * Adds a lepName to the lepCount hash map. If the lep name is already in the hasmap, add one to the hash map value. 
	 * @param s
	 */
	public void addToLepList(String s) {
		if(lepCount.get(s) != null) {
			lepCount.put(s,lepCount.get(s)+1);
		}
		else {
			lepCount.put(s,1);
		}
	}
	
	/**
	 * Adds elements from the hashMap lepCount to LepPieGraphData.
	 */
	public void addCountToList() {
		lepCount.forEach((k,v) ->{
			addItemToLepPieGraph(k,v);
		});
	}
	
	/**
	 * Gets generate report button. 
	 * @return Button generateReportButton
	 */
	public Button getButton() {
		return generateButton;
	}
}
