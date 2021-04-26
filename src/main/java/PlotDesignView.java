import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlotDesignView extends View {

	private Slider sunlight;
	private Slider moisture;
	private Slider soilType;
	private Button drawPlot;
	private TextField budget;
	private TextField widthInput;
	private TextField heightInput;
	private TextField boxHeightInput;
	private TextField boxWidthInput;
	private TextField budgetInput;
	private Label gridSize;
	private Scene scene;
	private BorderPane base;
	private Button toGarden;
	private Button drawDimensions;
	private GraphicsContext gc;
	private Controller controller;
	private ArrayList<Point> coords;
	private GridPane left_grid, bottom;
	private Canvas drawArea;
	private VBox box;
	private boolean canDraw;
	private final double LEFTBAR = 325;
	private final double SPACING = 10;
	private final double BOTTOM_HEIGHT = 100;
	private double canvasWidth = WINDOW_WIDTH - LEFTBAR;
	private double canvasHeight = WINDOW_HEIGHT - BOTTOM_HEIGHT;
	private int scaleIndex = 50;
	
	public PlotDesignView(Stage stage, Controller c) {

		controller = c;
		base = new BorderPane();
		coords = new ArrayList<Point>();
		canDraw = false;
		
		

		// create Canvas
		box = new VBox();
		base.setCenter(box);
		box.setMinWidth(canvasWidth);
		box.setMinHeight(canvasHeight);

		drawArea = new Canvas(canvasWidth, canvasHeight);
		gc = drawArea.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		
		box.getChildren().add(drawArea);

		// Create left side bar with sliders and buttons
		createLeftGrid();
		createSunSlider();
		createMoistureSlider();
		createSoilSlider();

		// create last and next page buttons
		createBottom();
		inputDimensions();
		
		
		// add the drawplot button
		drawPlot = new Button("Draw Plot");
		drawPlot.setOnMouseClicked(controller.getDrawPlotHandler());
		left_grid.add(drawPlot, 0, 30);
		toGarden = new Button("To Garden");
		toGarden.setOnMouseClicked(controller.getToGardenOnClickHandler());
		left_grid.add(toGarden, 0, 50);
		
		// create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.setOnDragDetected(controller.getDrawPlotDragDetected());
		scene.setOnMouseDragged(controller.getDrawPlotDragged());
		scene.setOnMouseDragReleased(controller.getOnDrawPlotDone());
		scene.setOnMouseReleased(controller.getOnDrawPlotDone());
		stage.setScene(scene);
		stage.show();
	
		scene.widthProperty().addListener(controller.getPDWidthChangeListener());
		scene.heightProperty().addListener(controller.getPDHeightChangeListener());
	}
	
	public void heightChanged(Object windowHeight) {
		WINDOW_HEIGHT = (double) windowHeight;
		canvasHeight = (double)windowHeight - BOTTOM_HEIGHT;
		drawArea.setHeight(canvasHeight);
		
		gc = drawArea.getGraphicsContext2D();
		drawGrid();
	}
	
	public void widthChanged(Object windowWidth) {
		WINDOW_WIDTH =(double) windowWidth;
		canvasWidth = (double)windowWidth - LEFTBAR;
		drawArea.setWidth(canvasWidth);
		
		gc = drawArea.getGraphicsContext2D();
		drawGrid();
	}
	
	
	/**
	 * Creates the text fields and labels for inputing all grid dimension sizes
	 * @param none
	 * @return none
	 */
	public void inputDimensions() {
		Label widthText = new Label("Garden width");
		Label heightText = new Label("Garden height");
		Label boxHeightText = new Label("Unit height");
		Label boxWidthText = new Label("Unit width");
		Label budgetText = new Label("Budget");
		gridSize = new Label("Grid Size");
		widthInput = new TextField();
		heightInput = new TextField();
		boxHeightInput = new TextField();
		boxWidthInput = new TextField();
		budgetInput = new TextField();
		
		drawDimensions = new Button("Set Dimensions");
		drawDimensions.setOnMouseClicked(controller.drawPlotGrid());
		left_grid.add(widthText, 0, 35);
		left_grid.add(widthInput, 1, 35);
		left_grid.add(heightText, 0, 37);
		left_grid.add(heightInput, 1, 37);
		left_grid.add(boxHeightText, 0, 40);
		left_grid.add(boxHeightInput, 1, 40);
		left_grid.add(boxWidthText, 0, 43);
		left_grid.add(boxWidthInput, 1, 43);
		left_grid.add(budgetText, 0, 46);
		left_grid.add(budgetInput, 1, 46);
		left_grid.add(drawDimensions, 1, 50);
	}
	
	
	/**
	 * Responsible for drawing the scaling grid lines on the canvas
	 * @param none
	 * @return double FIX ME
	 */
	public double drawGrid() {
		
		gc.clearRect(0, 0, drawArea.getWidth(), drawArea.getHeight());
		
		int width = Integer.parseInt(widthInput.getText());
		int height = Integer.parseInt(heightInput.getText());
		int boxHeight = Integer.parseInt(boxHeightInput.getText());
		int boxWidth = Integer.parseInt(boxWidthInput.getText());
		
		if (boxWidth > width) {
			System.out.println("Impossible size");
		}
		
		if (boxHeight > height) {
			System.out.println("Impossible size");
		}
		
		
		double perciseWidth = canvasWidth/(width/boxWidth);
		double perciseHeight = canvasHeight/(height/boxHeight);
		int heightInc = (int)canvasHeight/(height/boxHeight);
		int widthInc = (int)canvasWidth/(width/boxWidth);
		
		double widthBorder = perciseWidth - widthInc;
		
		double heightBorder = perciseHeight - heightInc;
		
		heightBorder *= height/boxHeight;
		widthBorder *= width/boxWidth;
		
		drawArea.setWidth(canvasWidth - widthBorder + 2);
		drawArea.setHeight(canvasHeight - heightBorder + 1);
		double pixelsPerFoot = (double)widthInc / boxWidth;
		
		for (double x = 1; x <= canvasWidth + widthBorder; x+=widthInc) {
			gc.moveTo(x, 0);
			gc.lineTo(x, canvasHeight);
			gc.stroke();
		}
		
		for (double y = 0; y <= canvasHeight + heightBorder; y+=heightInc) {
			gc.moveTo(0,y);
			gc.lineTo(canvasWidth,y);
			gc.stroke();
		}
		gc.closePath();
		gc.beginPath();
		
		return pixelsPerFoot;
	}
		
	/**
	 * Creates the bottom panel of the border pane
	 * @param none
	 * @return none
	 */
	public void createBottom() {
		bottom = new GridPane();
		bottom.setAlignment(Pos.TOP_CENTER);
		bottom.setStyle("-fx-background-color: darkgrey");
		bottom.setGridLinesVisible(true);
		bottom.setHgap(SPACING);
		bottom.setVgap(SPACING);
		bottom.setMinHeight(BOTTOM_HEIGHT);
		base.setBottom(bottom);
		Text t = new Text("Testing");
		bottom.add(t, 0, 0);
		
	}

	/**
	 * Creates the left grid pane in the border pane base
	 * @param none
	 * @return none
	 */
	public void createLeftGrid() {
		left_grid = new GridPane();
		left_grid.setAlignment(Pos.TOP_CENTER);
		left_grid.setStyle("-fx-background-color: darkseagreen");
		// left_grid.setGridLinesVisible(true);
		left_grid.setMinWidth(LEFTBAR);
		left_grid.setHgap(SPACING);
		left_grid.setVgap(SPACING);
		base.setLeft(left_grid);
	}
	
	/**
	 * Creates the standard form for the sliders used
	 * @param slider
	 * @return none
	 */
	public Slider sliderStandards(Slider slider) {
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setSnapToTicks(true);
		slider.setMinorTickCount(0);
		return slider;

	}

	/**
	 * Creates and places the Sunlight slider
	 * @param none
	 * @return none
	 */
	public void createSunSlider() {
		sunlight = new Slider(1, 3, 0);
		Text t = new Text("Sunlight Level");
		sliderStandards(sunlight);
		left_grid.add(t, 0, 0);
		left_grid.add(sunlight, 0, 1);

	}

	/**
	 * Creates and places the Moisture slider
	 * @param none
	 * @return none
	 */
	public void createMoistureSlider() {
		moisture = new Slider(1, 3, 0);
		sliderStandards(moisture);
		Text t = new Text("Moisture Level");
		left_grid.add(t, 0, 9);
		left_grid.add(moisture, 0, 10);
	}

	/**
	 * Creates and places the Soil Type slider
	 * @param none
	 * @return none
	 */
	public void createSoilSlider() {
		soilType = new Slider(1, 3, 0);
		sliderStandards(soilType);
		Text t = new Text("Soil Type");
		left_grid.add(t, 0, 19);
		left_grid.add(soilType, 0, 20);
	}

	/**
	 * Getter for the current scene
	 * @param none
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}
	

	/**
	 * Method used in the Controller Sunlight Slider eventListener 
	 * @param none
	 * @return current value of sunlight slider
	 */
	public double getSunlightSlider() {
		return sunlight.getValue();
	}

	/**
	 * Method used in the Controller Soil Type Slider eventListener
	 * @param none
	 * @return current value of soil type slider
	 */
	public double getSoilSlider() {
		return soilType.getValue();
	}

	/**
	 * Method used in the Controller Moisture Slider eventListener
	 * @param none
	 * @return current value of moisture slider
	 */
	public double getMoistureSlider() {
		return moisture.getValue();
	}
	
	/**
	 * Method to detect start drawing plots on the canvas
	 * @param me
	 * @return none
	 */
	public void startDrawingPlot(MouseEvent me) {
		if(canDraw) {
			//start drawing a plot
			gc.beginPath();
			gc.lineTo(me.getX() - LEFTBAR, me.getY());
			gc.stroke();
			//add the point to a coordinate list
			coords.add(new Point(me.getX(), me.getY()));
		}
	}
	
	/**
	 * Continually draws user made lines on canvas
	 * @param none
	 * @return none
	 */
	public void drawPlot(MouseEvent me) {
		if(canDraw) {
			//Draw the line as the mouse is dragged
			gc.lineTo(me.getX() - LEFTBAR - 5, me.getY());
			gc.stroke();	
			//add the point to a coordinate list
			coords.add(new Point(me.getX(), me.getY()));
		}
	}
	
	/**
	 * Fills the outline created for each plot
	 * @param me
	 * @return none
	 */
	public void fillPlot(MouseEvent me) {
		//Close the path
		gc.closePath();
    	gc.fill();
    	gc.beginPath();
    	//reset the array list to draw the next plot
		coords = new ArrayList<Point>();
		//prevent user from drawing another plot before hitting button again
		preventDrawing();
	}
	
	/**
	 * Sets fill color based on the current soil type
	 * @param o
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
	 * Returns the coordinates drawn on to the canvas 
	 * @param none
	 * @return none
	 */
	public ArrayList<Point> getCoords(){
		return coords;
	}
	
	/**
	 * Allows the user to draw on the canvas after hitting Draw Plot button
	 * @param none
	 * @return none
	 */
	public void allowDrawing() {
		canDraw = true;
	}
	
	/**
	 * Prevents the user from drawing on the canvas before 
	 * @param none
	 * @return none
	 */
	public void preventDrawing() {
		canDraw = false;
	}
	
	/**
	 * Returns the canDraw variable
	 * @param none
	 * @return none
	 */
	public boolean getCanDraw() {
		return canDraw;
	}
	
	public double getBudget() {
		return Double.parseDouble(budgetInput.getText());
	}
}