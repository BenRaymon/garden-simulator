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
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	private GridPane right, left;
	private ListView<Circle> top;
	private BorderPane base;
	private Scene scene;
	private Button toShoppingList;
	private Button saveGarden; // save the garden to a .dat file
	private GraphicsContext gc;
	private int imageInc = 1;
	private TextField garden_name; // name the garden (used to load garden)
	private VBox plantBox;
	private Text lepCount = new Text("0");
	private Text plantCount = new Text("0");
	private Text budgetText = new Text();
	
	private double LEFTBAR = 350;
	private double RIGHTBAR = 250;
	private double TOPBAR = 125, BOTTOM = 100;
	private double SPACING = 10;
	private double SCALE = 10;
	private double CANVAS_WIDTH = WINDOW_WIDTH - LEFTBAR - RIGHTBAR;
	private double CANVAS_HEIGHT = WINDOW_HEIGHT - TOPBAR - BOTTOM;
	
	private double budget;
	private double budgetLeft;
	
	
	/**
	 * Construct for for GardenEditorView
	 * @param stage the javafx stage for the view
	 * @param c reference to the controller
	 */
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
		createLeft();
		setPlantInfo(null, null);
		GridPane bottom = createBottom();
		addPageButtons(bottom);
		
	
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
        stage.show();
	}
	
	
	public void setPlotBoxes(ArrayList<Plot> plots) {
		VBox plotSelectors = new VBox();
		int index = 0;
		for (Plot plot : plots) {
			CheckBox plotCheck = new CheckBox("Plot " + index++);
			plotSelectors.getChildren().add(plotCheck);
		}
		plantBox.getChildren().add(plotSelectors);
	}

	
	/**
	 * Draws plots on the canvas
	 * @param ArrayList points
	 */
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
	
	/**
	 * Sets the fill color for the plots drawn
	 * @param Options o 
	 */
	public void setFillColor(Options o) {
		int[] soil = o.getSoilTypes();
		if(soil[0] == 1) 
			gc.setFill(Color.GREY);
		else if (soil[1] == 1) 
			gc.setFill(Color.SADDLEBROWN);
		else if (soil[2] == 1) 
			gc.setFill(Color.SANDYBROWN);
	}
	
	
	/**
	 * Creates and sets the budget text in right panel
	 * @return none
	 */
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
	
	/**
	 * Creates and sets the number of plants text
	 */
	public void createPlantText() {
		Text plantLabel = new Text("Number of Plants");
		plantLabel.setFont(Font.font(20));
		GridPane.setHalignment(plantLabel, HPos.CENTER);
		right.add(plantLabel, 0, 2);
		
		plantCount.setFont(Font.font(32));
		GridPane.setHalignment(plantCount, HPos.CENTER);
		right.add(plantCount, 0, 3);
	}
	
	/**
	 * Creates and sets the number of leps text
	 */
	public void createLepText() {
		Text lepLabel = new Text("Number of Lep");
		lepLabel.setFont(Font.font(20));
		GridPane.setHalignment(lepLabel, HPos.CENTER);
		right.add(lepLabel, 0, 4);
		
		lepCount.setFont(Font.font(32));
		GridPane.setHalignment(lepCount, HPos.CENTER);
		right.add(lepCount, 0, 5);
	}
	
	/**
	 * Creates the entire right text fields
	 */
	public void createRightText() {
		createBudgetText();
		createPlantText();
		createLepText();
	}
	
	/**
	 * Draws images on top grid
	 * @param keys
	 */
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
	
	/**
	 * Sets all the plants info on left pane
	 * @param plant
	 */
	public void setPlantInfo(Plant plant, ArrayList<Plot> plots) {
		if (plant == null) {
			Text t = new Text("Click a Plant to See it's Info");
			left.add(t, 0, 0);
		}
		else
		{
			//clear current children
			left.getChildren().clear();
			Image plantImage = View.getImages().get(plant.getScientificName());
			setPlantInfoImage(plantImage);
			addNames(plant);
			addLeps(plant);
			addType(plant);
			addSize(plant);
			addCost(plant);
			addColor(plant);
		}
	}
	
	
	/**
	 * Sets the plant image on left pane
	 * @param plantImg
	 */
	public void setPlantInfoImage(Image plantImg) {

		if(plantBox.getChildren().size() > 1)
			plantBox.getChildren().remove(1);

		ImageView plantIV = new ImageView();
		plantIV.setImage(plantImg);
		plantIV.setFitWidth(LEFTBAR);
		plantIV.setFitHeight(300);
		plantIV.setPreserveRatio(true);
		plantBox.setAlignment(Pos.CENTER);
		plantBox.getChildren().add(plantIV);
	}
	
	/**
	 * Adds the plant's scientific and common name on left pane
	 * Also add the genus and family
	 * @param Plant
	 */
	public void addNames(Plant plant){
		Text commonName = new Text(plant.getCommonName());
		Text commonText = new Text("Common Name:");
		left.add(commonText, 0, 1);
		left.add(commonName, 1, 1);
		Text scienceText = new Text("Scientific Name:");
		Text scienceName = new Text(plant.getScientificName());
		left.add(scienceText, 0, 2);
		left.add(scienceName, 1, 2);
		Text familyText = new Text("Family:");
		Text familyName = new Text(plant.getFamily());
		left.add(familyText, 0, 3);
		left.add(familyName, 1, 3);
		Text genusText = new Text("Genus:");
		Text genusName = new Text(plant.getGenera());
		left.add(genusText, 0, 4);
		left.add(genusName, 1, 4);
		
	}
	
	/**
	 * Add the number of leps supported on left pane
	 * @param plant
	 */
	public void addLeps(Plant plant) {
		Text lepText = new Text("Number of Leps Supported:");
		Text lepNum = new Text(String.valueOf(plant.getLepsSupported()));
		left.add(lepText, 0, 5);
		left.add(lepNum, 1, 5);
	}
	
	/**
	 * Adds the plant's type on left pane
	 * @param plant
	 */
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
		
		left.add(typeText, 0, 6);
		left.add(typeName, 1, 6);
	}
	
	/**
	 * Add the cost of this plant on left pane
	 * @param plant
	 */
	public void addCost(Plant plant) {
		Text costText = new Text("Plant Cost:");
		Text costNum = new Text("$" + String.valueOf(plant.getCost()));
		left.add(costText, 0, 7);
		left.add(costNum, 1, 7);
	}
	
	/**
	 * Adds the plant's size on left pane
	 * @param plant
	 */
	public void addSize(Plant plant) {
		Text sizeText = new Text("Size Range (ft):");
		Text sizeRange = new Text(String.valueOf(plant.getSizeLower())+ "-" + String.valueOf(plant.getSizeUpper()));
		left.add(sizeText, 0, 8);
		left.add(sizeRange, 1, 8);
	}
	
	
	/**
	 * Adds the plant's color on the left pane
	 * @param plant
	 */
	public void addColor(Plant plant) {
		Text colorText = new Text("Color:");
		Text colorStr = new Text(String.valueOf(plant.getColor()));
		left.add(colorText, 0, 9);
		left.add(colorStr, 1, 9);
	}

	
	/**
	 * Creates the right pane
	 */
	public void createRight() {
		right = new GridPane();
		createPane(right, "darkseagreen");
		right.setMinWidth(RIGHTBAR);
		right.setAlignment(Pos.CENTER);
		base.setRight(right);
		createRightText();
	}
	
	/**
	 * Creates the left pane
	 */
	public void createLeft() {
		VBox leftBase = new VBox();
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
	
	/**
	 * Creates the bottom pane
	 */
	public GridPane createBottom() {
		GridPane bottom = new GridPane();
		createPane(bottom, "darkgrey");
		bottom.setMinHeight(BOTTOM);
		base.setBottom(bottom);
		return bottom;
	}
	
	/**
	 * Helper method for generally used pane creation
	 * @param pane
	 * @param color
	 */
	public void createPane(GridPane pane, String color) {
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: " + color);
		pane.setHgap(SPACING);
		pane.setVgap(SPACING);
	}
	
	/**
	 * Updates the budget, leps, and plant numbers
	 * @param leps
	 * @param plants
	 * @param spent
	 */
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
	
	/**
	 * Adds the page buttons
	 */
	public void addPageButtons(GridPane bottom) {
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
	
	
	/**
	 * Sets remaining budget
	 * @param remaining
	 */
	public void setBudgetLeft(double remaining) {
		budgetLeft = remaining;
		createRightText();
	}
	
	/**
	 * Getter for shoppign list button
	 * @return shopping list button
	 */
	public Button getToShoppingListButton() {
		return this.toShoppingList;
	}
	
	/**
	 * Getter for base pane
	 * @return base 
	 */
	public BorderPane getBase() {
		return this.base;
	}
	
	/**
	 * Getter for scene
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Creates new image in base for drag and drop
	 * @param event
	 * @param db
	 * @param radius
	 */
	public void createNewImageInBase(DragEvent event, Dragboard db, double radius) {
		System.out.println("Creating new gardeneditorview image");
		Circle circ = new Circle(event.getX(), event.getY(), radius*SCALE);
        circ.setFill(new ImagePattern(selectedPlant));
        circ.setOnDragDetected(controller.getOnImageDraggedHandler());
    	base.getChildren().add(circ);
	}
	
	/**
	 * Adds the plant image to base for drag and drop
	 * @param pos
	 * @param img_v
	 * @param radius
	 * @return base added children
	 */
	public boolean addPlantImageToBase(Point pos, Image img_v, double radius) {
		Circle circ = new Circle(pos.getX(), pos.getY(), radius*SCALE);
        circ.setFill(new ImagePattern(img_v));
        circ.setOnDragDetected(controller.getOnImageDraggedHandler());
    	return base.getChildren().add(circ);
	}
	
	/**
	 * Checks for event source in mouse event
	 * @param event
	 * @return boolean checking for source in base
	 */
	public boolean hasChild(MouseEvent event) {
		return base.getChildren().contains(event.getSource());
	}
	
	/**
	 * Getter for plant name based on image
	 * @param im
	 * @return plant name
	 */
	public String getPlantName(Image im) {
		return recommendedPlantImages.get(im);
	}
	
	/**
	 * Getter for plant name based on event
	 * @param e 
	 * @return plant name
	 */
	public String getPlantName(Event e) {
		Circle plantCirc = (Circle)e.getSource();
		Image plantImage = ((ImagePattern)plantCirc.getFill()).getImage();
		return recommendedPlantImages.get(plantImage);
	}
	
	/**
	 * sets selected plant
	 * @param im
	 */
	public void setSelectedPlantImage(Image im) {
		selectedPlant = im; 
	}
	
	/**
	 * Getter for Graphics Context
	 * @return
	 */
	public GraphicsContext getGC() {
		return gc;
	}
	
	/**
	 * Getter for canvas height
	 * @return canvas height
	 */
	public double getCanvasHeight() {
		return CANVAS_HEIGHT;
	}
	
	/**
	 * Getter for canvas width
	 * @return canvas width
	 */
	public double getCanvasWidth() {
		return CANVAS_WIDTH;
	}

	/**
	 * Getter for garden name
	 * @return garden name
	 */
	public TextField getGardenName() {
		return garden_name;
	}
	
	/**
	 * Sets Garden scale
	 * @param scale
	 */
	public void setScale(double scale) {
		SCALE = scale;
	}
	
	
	/**
	 * Getter for Top Panel Height
	 * @return double 
	 */
	public double getTopBar() {
		return TOPBAR;
	}
	
	/**
	 * Getter for Left Panel Width
	 * @return double
	 */
	public double getLeftBar() {
		return LEFTBAR;
	}
	
	/**
	 * Setter for the total budget
	 * @param double total budget 
	 */
	public void setBudget(double b) {
		this.budget = b;
	}
}