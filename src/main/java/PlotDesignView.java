import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
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
	private GridPane left_grid;
	private Canvas drawArea;
	private VBox box;
	private Slider moisture, soilType, sunlight;
	private boolean canDraw;
	private final double LEFTBAR = 325;
	private final double SPACING = 10;
	private double canvasWidth = WINDOW_WIDTH - LEFTBAR;
	private double canvasHeight = WINDOW_HEIGHT;
	private int scaleIndex = 50;
	
	public PlotDesignView(Stage stage, Controller c) {

		controller = c;
		base = new BorderPane();
		coords = new ArrayList<Point>();
		canDraw = false;
		
		
		box = new VBox();
		base.setCenter(box);
		box.setMinWidth(canvasWidth);
		box.setMinHeight(canvasHeight);

		drawArea = new Canvas(canvasWidth, canvasHeight);
		gc = drawArea.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		
		box.getChildren().add(drawArea);
		
		createLeftGrid();
		
		// add the drawplot button
		drawPlot = new Button("Draw Plot");
		drawPlot.setOnMouseClicked(controller.getDrawPlotHandler());
		left_grid.add(drawPlot, 0, 17);
		toGarden = new Button("To Garden");
		toGarden.setOnMouseClicked(controller.getToGardenOnClickHandler());
		left_grid.add(toGarden, 0, 19);
		
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
		canvasHeight = (double)windowHeight;
		drawArea.setHeight(canvasHeight);
		
		gc = drawArea.getGraphicsContext2D();
		//drawGrid();
	}
	
	public void widthChanged(Object windowWidth) {
		WINDOW_WIDTH =(double) windowWidth;
		canvasWidth = (double)windowWidth - LEFTBAR;
		drawArea.setWidth(canvasWidth);
		
		gc = drawArea.getGraphicsContext2D();
		//drawGrid();
	}
	
	
	/**
	 * Creates the text fields and labels for inputing all grid dimension sizes
	 * @param none
	 * @return none
	 */
	public void inputDimensions() {
		Label widthText = new Label("Garden width");
		Label heightText = new Label("Garden height");
		Label boxHeightText = new Label("Grid Height");
		Label boxWidthText = new Label("Grid Width");
		Label budgetText = new Label("Budget");
		gridSize = new Label("Grid Size");
		widthInput = new TextField();
		widthInput.setText("" + 100);
		heightInput = new TextField();
		heightInput.setText("" + 100);
		boxHeightInput = new TextField();
		boxHeightInput.setText("" + 10);
		boxWidthInput = new TextField();
		budgetInput = new TextField();
		boxWidthInput.setText("" + 10);
		
		drawDimensions = new Button("Set Dimensions");
		drawDimensions.setOnMouseClicked(controller.drawPlotGrid());
		
		VBox dimensions = new VBox();
		dimensions.getChildren().add(widthText);
		dimensions.getChildren().add(widthInput);
		dimensions.getChildren().add(heightText);
		dimensions.getChildren().add(heightInput);
		dimensions.getChildren().add(boxWidthText);
		dimensions.getChildren().add(boxWidthInput);
		dimensions.getChildren().add(boxHeightText);
		dimensions.getChildren().add(boxHeightInput);
		dimensions.getChildren().add(budgetText);
		dimensions.getChildren().add(budgetInput);
		dimensions.getChildren().add(drawDimensions);
		
		Insets margin = new Insets(10,0,10,0);
		Iterator<Node> it = dimensions.getChildren().iterator();
		while(it.hasNext()) {
			dimensions.setMargin(it.next(), margin);
		}
		
		left_grid.add(dimensions, 0, 1);
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
	 * Creates the left grid pane in the border pane base
	 * @param none
	 * @return none
	 */
	public void createLeftGrid() {
		left_grid = new GridPane();
		left_grid.setAlignment(Pos.CENTER);
		left_grid.setStyle("-fx-background-color: darkseagreen");
		left_grid.setMinWidth(LEFTBAR);
		left_grid.setHgap(SPACING);
		left_grid.setVgap(SPACING);
		
		inputDimensions();
		createSliders();
		
		base.setLeft(left_grid);
	}
	
	public void createSliders() {
		sunlight = new Slider(1, 3, 0);
		soilType = new Slider(1, 3, 0);
		moisture = new Slider(1, 3, 0);
		sliderStandards(sunlight);
		sliderStandards(soilType);
		sliderStandards(moisture);
		
		Text sunlightText = new Text("Sunlight Level");
		Text soilTypeText = new Text("Soil Type");
		Text moistureText = new Text("Moisture Level");
		
		VBox sliders = new VBox();
		sliders.getChildren().add(sunlightText);
		sliders.getChildren().add(sunlight);
		sliders.getChildren().add(moistureText);
		sliders.getChildren().add(moisture);
		sliders.getChildren().add(soilTypeText);
		sliders.getChildren().add(soilType);
		
		Insets margins = new Insets(10,0,10,0);
		Insets margin = new Insets(10,0,10,0);
		Iterator<Node> it = sliders.getChildren().iterator();
		while(it.hasNext()) {
			sliders.setMargin(it.next(), margin);
		}
		
		left_grid.add(sliders, 0, 10);
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
			gc.lineTo(me.getX()- LEFTBAR - 5, me.getY());
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
			gc.lineTo(me.getX()- LEFTBAR - 5, me.getY());
			gc.stroke();	
			//add the point to a coordinate list
			coords.add(new Point(me.getX() , me.getY()));
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