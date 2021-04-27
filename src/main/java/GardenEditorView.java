import javafx.scene.control.TextField;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GardenEditorView extends View {

	private Controller controller;
	private HashMap<Image, String> recommendedPlantImages;
	private ArrayList<Circle> recommendedPlantCircs;
	private Image selectedPlant;
	private Text selectedPlantInfo;
	private GridPane bottom, right, left;
	private ListView<Circle> top;
	private BorderPane base;
	private Scene scene;
	private Button toShoppingList;
	private Button saveGarden; // save the garden to a .dat file
	private GraphicsContext gc;
	private int imageInc = 1;
	private TextField garden_name; // name the garden (used to load garden)
	private VBox leftBase;
	private VBox plantBox;
	private Text lepCount = new Text("0");
	private Text plantCount = new Text("0");
	private Text budgetText = new Text();
	
	private double LEFTBAR = 350;
	private double RIGHTBAR = 200;
	private double TOPBAR = 125, BOTTOM = 100;
	private double SPACING = 10;
	private double SCALE = 10;
	private double CANVAS_WIDTH = WINDOW_WIDTH - LEFTBAR - RIGHTBAR;
	private double CANVAS_HEIGHT = WINDOW_HEIGHT - TOPBAR - BOTTOM;
	
	private double budget;
	private double budgetLeft;
	
	public GardenEditorView(Stage stage, Controller c) {
		controller = c;
		base = new BorderPane();
		base.setOnDragOver(controller.getOnDragOverHandler());
		base.setOnDragDropped(controller.getOnDragDroppedHandler());
		
		Canvas drawArea = new Canvas(WINDOW_WIDTH - LEFTBAR - RIGHTBAR, WINDOW_HEIGHT - TOPBAR - BOTTOM);
		gc = drawArea.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		base.setCenter(drawArea);
		
		//TEMP
		drawArea.setOnMouseClicked(event -> {
			System.out.println(((MouseEvent) event).getSceneX());
			System.out.println(((MouseEvent) event).getSceneY());
		});
		
		createRight();
		createRightText();
		createLeft();
		setPlantInfo();
		createBottom();
		addPageButtons();
	
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
        stage.show();
	}
	
	public double getTopBar() {
		return TOPBAR;
	}
	
	public double getLeftBar() {
		return LEFTBAR;
	}
	
	public void setBudget(double b) {
		this.budget = b;
	}
	
	public void drawPlot(ArrayList<Point> points) {
		
		double[] xcords = new double[points.size()];
		double[] ycords = new double[points.size()];
		int i = 0;
		for(Point p : points) {
			xcords[i] = (p.getX());
			ycords[i++] = (p.getY());
		}
		gc.fillPolygon(xcords, ycords, i);
		//gc.strokePolygon(xcords, ycords, i);
	}
	
	public void setFillColor(Options o) {
		int[] soil = o.getSoilTypes();
		if(soil[0] == 1) 
			gc.setFill(Color.GREY);
		else if (soil[1] == 1) 
			gc.setFill(Color.SADDLEBROWN);
		else if (soil[2] == 1) 
			gc.setFill(Color.SANDYBROWN);
	}
	
	
	public void createBudgetText(){
		Text budgetLabel = new Text("Remaining Budget");
		budgetLabel.setFont(Font.font(20));
		GridPane.setHalignment(budgetLabel, HPos.CENTER);
		right.add(budgetLabel, 0, 0);
		
		
		budgetText.setText(String.valueOf(budgetLeft));
		budgetText.setFont(Font.font(32));
		GridPane.setHalignment(budgetText, HPos.CENTER);
		right.add(budgetText, 0, 1);
	}
	
	public void createPlantText() {
		Text plantLabel = new Text("Number of Plants");
		plantLabel.setFont(Font.font(20));
		GridPane.setHalignment(plantLabel, HPos.CENTER);
		right.add(plantLabel, 0, 2);
		
		plantCount.setFont(Font.font(32));
		GridPane.setHalignment(plantCount, HPos.CENTER);
		right.add(plantCount, 0, 3);
	}
	
	public void createLepText() {
		Text lepLabel = new Text("Number of Lep");
		lepLabel.setFont(Font.font(20));
		GridPane.setHalignment(lepLabel, HPos.CENTER);
		right.add(lepLabel, 0, 4);
		
		lepCount.setFont(Font.font(32));
		GridPane.setHalignment(lepCount, HPos.CENTER);
		right.add(lepCount, 0, 5);
	}
	
	public void createRightText() {
		createBudgetText();
		createPlantText();
		createLepText();
	}
	
	//Supposed to draw images in the top grid pane
	public void setPlantImages(Set<String> keys){
		recommendedPlantImages = new HashMap<Image, String>();
		recommendedPlantCircs = new ArrayList<Circle>();
		
		ConcurrentHashMap<String, Image> allImages = View.getImages();
		
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext()) {
			try {
				String name = it.next();
				Image image = allImages.get(name);
				recommendedPlantImages.put(image, name);
				
				Circle circ = new Circle(50);
		        circ.setFill(new ImagePattern(image));
		        circ.setOnDragDetected(controller.getOnImageDraggedHandler());
		        circ.setOnMouseClicked(controller.getOnImageClickedInfo());
		        recommendedPlantCircs.add(circ);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ObservableList<Circle> backingList = FXCollections.observableArrayList(recommendedPlantCircs);
		top = new ListView<>(backingList);
		top.setOrientation(Orientation.HORIZONTAL);
		top.setMaxHeight(TOPBAR);
		base.setTop(top);
	}
	
	public void setPlantInfoImage(Image plantImg) {
		plantBox.getChildren().clear();
		ImageView plantIV = new ImageView();
		plantIV.setImage(plantImg);
		plantIV.setFitWidth(LEFTBAR);
		plantIV.setFitHeight(300);
		plantIV.setPreserveRatio(true);
		plantBox.setAlignment(Pos.CENTER);
		plantBox.getChildren().add(plantIV);
	}
	
	
	public void addNames(Plant plant){
		Text commonName = new Text(plant.getCommonName());
		Text commonText = new Text("Common Name:");
		left.add(commonText, 0, 1);
		left.add(commonName, 1, 1);
		Text scienceText = new Text("Scientific Name:");
		Text scienceName = new Text(plant.getScientificName());
		left.add(scienceText, 0, 2);
		left.add(scienceName, 1, 2);
	}
	
	public void addLeps(Plant plant) {
		Text lepText = new Text("Number of Leps Supported:");
		Text lepNum = new Text(String.valueOf(plant.getLepsSupported()));
		left.add(lepText, 0, 3);
		left.add(lepNum, 1, 3);
	}
	
	public void addType(Plant plant) {
		Text typeText = new Text("Type:");
		String type = "HOLD";
		System.out.println(plant.getType());
		if (plant.getType() ==  'w'){
			type = "Woody";
		}
		else {
			type = "Herbaceous";
		}
		Text typeName = new Text(type);
		
		left.add(typeText, 0, 4);
		left.add(typeName, 1, 4);
	}
	
	public void addSize(Plant plant) {
		Text sizeText = new Text("Size Range (ft):");
		Text sizeRange = new Text(String.valueOf(plant.getSizeLower())+ "-" + String.valueOf(plant.getSizeUpper()));
		left.add(sizeText, 0, 5);
		left.add(sizeRange, 1, 5);
	}
	
	
	
	public void setPlantInfo(Plant plant) {
		System.out.println("IN SET PLANT INFO");
		left.getChildren().clear();
		addNames(plant);
		addLeps(plant);
		addType(plant);
		addSize(plant);
	}
	
	public void createRight() {
		right = new GridPane();
		createPane(right, "darkseagreen");
		right.setMinWidth(RIGHTBAR);
		right.setAlignment(Pos.CENTER);
		//right.setGridLinesVisible(true);
		base.setRight(right);
	}
	
	public void createLeft() {
		leftBase = new VBox();
		leftBase.setStyle("-fx-background-color:darkseagreen");
		left  = new GridPane();
		plantBox = new VBox();
		createPane(left, "darkseagreen");
		left.setAlignment(Pos.TOP_CENTER);
		left.setMinWidth(LEFTBAR);
		left.setMaxWidth(LEFTBAR);
		leftBase.getChildren().add(plantBox);
		leftBase.getChildren().add(left);
		base.setLeft(leftBase);
	}
	
	public void createBottom() {
		bottom = new GridPane();
		createPane(bottom, "darkgrey");
		bottom.setMinHeight(BOTTOM);
		base.setBottom(bottom);
	}
	
	public void createPane(GridPane pane, String color) {
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: " + color);
		pane.setHgap(SPACING);
		pane.setVgap(SPACING);
	}
	
	public void updatePlantLepNums(int leps, int plants, double spent){
		lepCount.setText(String.valueOf(leps));
		plantCount.setText(String.valueOf(plants));
		budgetLeft = budget - spent;
		
		if (budgetLeft < 0) {
			budgetText.setFill(Color.RED);
		}
		else{
			budgetText.setFill(Color.BLACK);
		}
		budgetText.setText(String.valueOf(budgetLeft));
	}
	
	
	public void addPageButtons() {
		toShoppingList = new Button("Shopping List");
		toShoppingList.setOnMouseClicked(controller.getToShoppingListOnClickHandler());
		bottom.add(toShoppingList, 2, 0);
		
		Button toReport = new Button("Report");
		toReport.setOnMouseClicked(controller.getToReportOnClickHandler());
		bottom.add(toReport, 3, 0);
		
		Button toComp = new Button("Compare");
		toComp.setOnMouseClicked(controller.getToCompareOnClickHandler());
		bottom.add(toComp, 4, 0);
		
		garden_name = new TextField();
		bottom.add(garden_name, 5, 0);
		
		saveGarden = new Button("Save");
		saveGarden.setOnMouseClicked(controller.SaveButtonClickedHandler());
		bottom.add(saveGarden, 6, 0);
	}
	
	public void setPlantInfo() {
		Text t = new Text("Click a Plant to See it's Info");
		left.add(t, 0, 0);
	}
	
	public void setBudgetLeft(double remaining) {
		budgetLeft = remaining;
		createRightText();
	}
	
	public Button getToShoppingListButton() {
		return this.toShoppingList;
	}
	
	public BorderPane getBase() {
		return this.base;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void updateValues() {
		
	}
	
	public void updateInfo() {
		
	}
	
	public void createNewImageInBase(DragEvent event, Dragboard db, double radius) {
		System.out.println("Creating new gardeneditorview image");
		Circle circ = new Circle(event.getX(), event.getY(), radius*SCALE);
        circ.setFill(new ImagePattern(selectedPlant));
        circ.setOnDragDetected(controller.getOnImageDraggedHandler());
    	base.getChildren().add(circ);
	}
	
	public void addPlantImageToBase(Point pos, Image img_v, double radius) {
		Circle circ = new Circle(pos.getX(), pos.getY(), radius*SCALE);
        circ.setFill(new ImagePattern(img_v));
        circ.setOnDragDetected(controller.getOnImageDraggedHandler());
    	base.getChildren().add(circ);
	}
	
	public boolean hasChild(MouseEvent event) {
		return base.getChildren().contains(event.getSource());
	}
	
	public String getPlantName(Image im) {
		return recommendedPlantImages.get(im);
	}
	
	public void setSelectedPlantImage(Image im) {
		selectedPlant = im; 
	}
	
	public GraphicsContext getGC() {
		return gc;
	}
	
	public double getCanvasHeight() {
		return CANVAS_HEIGHT;
	}
	public double getCanvasWidth() {
		return CANVAS_WIDTH;
	}
	public double getTopHeight() {
		return TOPBAR;
	}
	public TextField getGardenName() {
		return garden_name;
	}
	public void setScale(double scale) {
		SCALE = scale;
	}
}