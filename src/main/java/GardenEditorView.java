import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GardenEditorView extends View {

	private Controller controller;
	private HashMap<Image, String> recommendedPlantImages;
	private Image selectedPlant;
	private GridPane right, left;
	private ListView<Circle> top;
	private BorderPane base;
	private ComboBox sortBy;
	private Scene scene;
	private GraphicsContext gc;
	private TextField gardenName; // name the garden (used to load garden)
	private VBox plantBox, plotSelectors;
	private Text lepCount = new Text("0");
	private Text plantCount = new Text("0");
	private Text budgetText = new Text();
	private VBox container;
	
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
		
		
		createRight();
		createLeft();
		setPlantInfo(null);
		//createBottom();
		//addPageButtons();
		
		// get button and scroll bar styles
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		String scrollBarStyle = getClass().getResource("scrollbars.css").toExternalForm();
		String textStyle = getClass().getResource("text.css").toExternalForm();
		// add save inputs to menu for the editor, add menu to the container
		gardenName = new TextField();
		gardenName.setPromptText("Name your Garden");
		Button saveGarden = new Button("Save");
		saveGarden.setOnMouseClicked(controller.SaveButtonClickedHandler());
		
		MenuBox menu = new MenuBox(c);
		menu.getContainer().add(gardenName, 9, 0);
		menu.getContainer().add(saveGarden, 10, 0);
		container = new VBox( menu, base);
		
		//create and set scene with base
		scene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(buttonStyle);
		scene.getStylesheets().add(scrollBarStyle);
		scene.getStylesheets().add(textStyle);
		//scene.getStylesheets().add(menuStyle);
		stage.setScene(scene);
        stage.show();
	}
	
	
	/**
	 * Creates the a vbox of checkboxes for every plot in the garden
	 * @param plots a list of all the plots in the garden
	 */
	public void setPlotBoxes(ArrayList<Plot> plots) {
		plotSelectors = new VBox();
		Insets margins = new Insets(5,5,5,15);
		plotSelectors.getChildren().add(new Text("Select plots"));
		int index = 1;
		for (Plot plot : plots) {
			CheckBox plotCheck = new CheckBox("Plot " + index++);
			plotCheck.setOnAction(controller.getSelectPlotCheckboxHander());
			plotSelectors.getChildren().add(plotCheck);
		}
		
		for(Node x : plotSelectors.getChildren()) {
			plotSelectors.setMargin(x, margins);
		}
		
		((CheckBox) plotSelectors.getChildren().get(1)).setSelected(true);
		//plantBox.getChildren().add(plotSelectors);
		right.add(plotSelectors, 0, 1);
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
	 * Add the plot selector and sortby components to the right side bar
	 */
	public void createSort() {
		plotSelectors = new VBox();
		right.add(plotSelectors, 0, 1); //checkbox for plots to show recommended plants for
		VBox sort = new VBox();
		ComboBox sortBy = new ComboBox();
		Text sortLabel = new Text("Sort Recommended Plants By: ");
		sortBy.valueProperty().addListener(controller.getSortByHandler());
		sortBy.getItems().addAll("Butterfly Count", "Plant Size");
		sort.getChildren().add(sortLabel);
		sort.getChildren().add(sortBy);
		right.add(sort, 0, 2); //dropdown for sorting criteria
	}
	
	/**
	 * Creates and sets the budget text in right panel
	 */
	public void createBudgetText(){
		Text budgetLabel = new Text("Remaining Budget");
		budgetLabel.setFont(Font.font(20));
		budgetLabel.getStyleClass().add("editor-t");
		GridPane.setHalignment(budgetLabel, HPos.CENTER);
		right.add(budgetLabel, 0, 4);
		
		
		budgetText.setText(String.valueOf(budgetLeft));
		budgetText.setFont(Font.font(32));
		GridPane.setHalignment(budgetText, HPos.CENTER);
		right.add(budgetText, 0, 5);
	}
	
	/**
	 * Creates and sets the number of plants text
	 */
	public void createPlantText() {
		Text plantLabel = new Text("Number of Plants");
		plantLabel.setFont(Font.font(20));
		plantLabel.getStyleClass().add("editor-t");
		GridPane.setHalignment(plantLabel, HPos.CENTER);
		right.add(plantLabel, 0, 6);
		
		plantCount.setFont(Font.font(32));
		GridPane.setHalignment(plantCount, HPos.CENTER);
		right.add(plantCount, 0, 7);
	}
	
	/**
	 * Creates and sets the number of leps text
	 */
	public void createLepText() {
		Text lepLabel = new Text("Number of Lep");
		lepLabel.setFont(Font.font(20));
		lepLabel.getStyleClass().add("editor-t");
		GridPane.setHalignment(lepLabel, HPos.CENTER);
		right.add(lepLabel, 0, 8);
		
		lepCount.setFont(Font.font(32));
		lepCount.getStyleClass().add("editor-t");
		GridPane.setHalignment(lepCount, HPos.CENTER);
		right.add(lepCount, 0, 9);
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
	 * @param plantNames
	 */
	public void setPlantImages(ArrayList<String> plantNames){
		recommendedPlantImages = new HashMap<Image, String>();
		ArrayList<Circle> recommendedPlantCircs = new ArrayList<Circle>();
		
		ConcurrentHashMap<String, Image> allImages = View.getImages();
		
		Iterator<String> it = plantNames.iterator();
		
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
	public void setPlantInfo(Plant plant) {
		if (plant == null) {
			Text t = new Text("Click a Plant to See it's Info");
			t.getStyleClass().add("editor-t");
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
			//left.add(sortBy,0,10);
		}
	}
	
	
	/**
	 * Sets the plant image on left pane
	 * @param plantImg
	 */
	public void setPlantInfoImage(Image plantImg) {

		if(plantBox.getChildren().size() > 0 && plantBox.getChildren().get(0) instanceof ImageView) {
			plantBox.getChildren().remove(0);
		}

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
		commonName.getStyleClass().add("editor-t");
		commonText.getStyleClass().add("editor-t");
		left.add(commonText, 0, 1);
		left.add(commonName, 1, 1);
		Text scienceText = new Text("Scientific Name:");
		Text scienceName = new Text(plant.getScientificName());
		scienceText.getStyleClass().add("editor-t");
		scienceName.getStyleClass().add("editor-t");
		left.add(scienceText, 0, 2);
		left.add(scienceName, 1, 2);
		Text familyText = new Text("Family:");
		Text familyName = new Text(plant.getFamily());
		familyText.getStyleClass().add("editor-t");
		familyName.getStyleClass().add("editor-t");
		left.add(familyText, 0, 3);
		left.add(familyName, 1, 3);
		Text genusText = new Text("Genus:");
		Text genusName = new Text(plant.getGenera());
		genusText.getStyleClass().add("editor-t");
		genusName.getStyleClass().add("editor-t");
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
		lepText.getStyleClass().add("editor-t");
		lepNum.getStyleClass().add("editor-t");
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
		typeText.getStyleClass().add("editor-t");
		typeName.getStyleClass().add("editor-t");
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
		costText.getStyleClass().add("editor-t");
		costNum.getStyleClass().add("editor-t");
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
		sizeText.getStyleClass().add("editor-t");
		sizeRange.getStyleClass().add("editor-t");
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
		colorText.getStyleClass().add("editor-t");
		colorStr.getStyleClass().add("editor-t");
		left.add(colorText, 0, 9);
		left.add(colorStr, 1, 9);
	}

	
	/**
	 * Creates the right pane
	 */
	public void createRight() {
		right = new GridPane();
		createPane(right, "#678B5E");
		right.setMinWidth(RIGHTBAR);
		right.setAlignment(Pos.TOP_CENTER);
		base.setRight(right);

		RowConstraints row00 = new RowConstraints();
	    row00.setPercentHeight(5);
		RowConstraints row0 = new RowConstraints();
	    row0.setPercentHeight(20);
		RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(5);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(10);
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(5);
	    RowConstraints row4 = new RowConstraints();
	    row4.setPercentHeight(5);
	    RowConstraints row5 = new RowConstraints();
	    row5.setPercentHeight(5);
	    RowConstraints row6 = new RowConstraints();
	    row6.setPercentHeight(5);
	    RowConstraints row7 = new RowConstraints();
	    row7.setPercentHeight(5);
	    RowConstraints row8 = new RowConstraints();
	    row8.setPercentHeight(5);
	    RowConstraints row9 = new RowConstraints();
	    row9.setPercentHeight(5);
	    //RowConstraints row10 = new RowConstraints();
	    //row10.setPercentHeight(20);
	    right.getRowConstraints().addAll(row00,row0,row1,row2,row3,row4,row5,row6,row7,row8,row9);
	    right.setMaxHeight(CANVAS_HEIGHT);
	    
	    for(RowConstraints row: right.getRowConstraints()) {
	    	row.setVgrow(Priority.ALWAYS);
	    }
		
	    AnchorPane.setTopAnchor(right, 0.0);
        AnchorPane.setBottomAnchor(right, 0.0);
        AnchorPane.setLeftAnchor(right, 0.0);
        AnchorPane.setRightAnchor(right, 0.0);

		createSort();
		createRightText();
	}
	
	/**
	 * Creates the left pane
	 */
	public void createLeft() {
		VBox leftBase = new VBox();
		leftBase.setStyle("-fx-background-color: #678B5E");
		left  = new GridPane();
		plantBox = new VBox();
		createPane(left, "#678B5E");
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
		Button toShoppingList = new Button("Shopping List");
		toShoppingList.setOnMouseClicked(controller.getToShoppingListOnClickHandler());
		bottom.add(toShoppingList, 2, 0);
		
		Button toReport = new Button("Report");
		toReport.setOnMouseClicked(controller.getToReportOnClickHandler());
		bottom.add(toReport, 3, 0);
		
		Button toComp = new Button("Compare");
		toComp.setOnMouseClicked(controller.getToCompareOnClickHandler());
		bottom.add(toComp, 4, 0);
		
		gardenName = new TextField();
		bottom.add(gardenName, 5, 0);
		
		Button saveGarden = new Button("Save");
		saveGarden.setOnMouseClicked(controller.SaveButtonClickedHandler());
		bottom.add(saveGarden, 6, 0);
	}
	
	/**
	 * returns the index of the plot that the checkbox represents
	 * @param event which should represent the checkbox that was changed
	 * @return index of plot
	 */
	public int getPlotIndex(Event e) {
		if (e.getSource() instanceof CheckBox) {
            CheckBox chk = (CheckBox) e.getSource();
            return Integer.parseInt(chk.getText().substring(5)) - 1;
        }
		else
			return -1;
	}
	
	/**
	 * get the values of the current plot selections
	 * @return an array list of 1s and 0s representing if the recommended plants for each plot should be shown
	 */
	public ArrayList<Integer> getSelections() {
		
		ArrayList<Integer> selections = new ArrayList<Integer>(); 
		
		//box of checkboxes
		for(Node n : plotSelectors.getChildren()) {
			if (n instanceof CheckBox) {
				CheckBox chk = (CheckBox) n;
				if(chk.isSelected())
					selections.add(1);
				else
					selections.add(0);
			}
		}
		
		return selections;
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
		return gardenName;
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