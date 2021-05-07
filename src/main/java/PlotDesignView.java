import java.util.ArrayList;
import java.util.Iterator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PlotDesignView extends View {

	private Image hideImg, showImg;
	private Button toggleGridLines;
	private TextField widthInput;
	private TextField heightInput;
	private TextField boxHeightInput;
	private TextField boxWidthInput;
	private TextField budgetInput;
	private Scene scene;
	private BorderPane base;
	private Button drawDimensions;
	private GraphicsContext gc;
	private Controller controller;
	private ArrayList<Point> coords;
	private GridPane left_grid;
	private Canvas drawArea;
	private VBox box;
	private HBox drawRedrawPlot;
	private Slider moisture, soilType, sunlight;
	private boolean canDraw;
	private final double LEFTBAR = 325;
	private final double SPACING = 10;
	private double CANVAS_WIDTH = WINDOW_WIDTH - LEFTBAR;
	private double CANVAS_HEIGHT = WINDOW_HEIGHT - MenuBox.MENU_HEIGHT;
	private int scaleIndex = 50;
	private boolean showGridLines = true;
	
	/**
	 * Constructor
	 * @param stage the stage
	 * @param c the controller
	 */
	public PlotDesignView(Stage stage, Controller c) {

		controller = c;
		base = new BorderPane();
		coords = new ArrayList<Point>();
		canDraw = false;
		
		
		box = new VBox();
		base.setCenter(box);
		box.setMinWidth(CANVAS_WIDTH);
		box.setMinHeight(CANVAS_HEIGHT);

		drawArea = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		gc = drawArea.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		
		box.getChildren().add(drawArea);
		
		createLeftGrid();
		
		// add the drawplot button
		drawRedrawPlot = new HBox();
		Button drawPlot = new Button("Draw Plot");
		drawPlot.setPrefWidth(LEFTBAR*0.6);
		drawPlot.setOnMouseClicked(controller.getDrawPlotHandler());
		drawRedrawPlot.getChildren().add(drawPlot);
		drawRedrawPlot.setMargin(drawPlot, new Insets(10, 5, 0, 5));
		left_grid.add(drawRedrawPlot, 0, 3);
		
		VBox toGardenHolder = new VBox(); //need the vbox for alignment
		toGardenHolder.setAlignment(Pos.CENTER);
		Button toGarden = new Button("To Garden");
		toGarden.setMinWidth(LEFTBAR*0.6);
		toGarden.setOnMouseClicked(controller.getToGardenOnClickHandler());
		toGardenHolder.getChildren().add(toGarden);
		left_grid.add(toGardenHolder, 0, 4);
		
		// get button styles
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		
		//get slider styles
		String sliderStyle = getClass().getResource("sliders.css").toExternalForm();

		// get text styles
		String textStyle = getClass().getResource("labels.css").toExternalForm();
		
		MenuBox menu = new MenuBox(c);
		menu.getEditorButton().setOnMouseClicked(controller.getToGardenOnClickHandler());
		//make show/hide button
		toggleGridLines = new Button();
		toggleGridLines.setOnMouseClicked(controller.getDisplayGridlinesHandler());
		//eye image for button
		hideImg = new Image(getClass().getResourceAsStream("hide.png"));
		showImg = new Image(getClass().getResourceAsStream("show.png"));
        ImageView imgView = new ImageView(hideImg);
        imgView.setFitHeight(25);
        imgView.setFitWidth(25);
        //make the image white like the rest of the text
        ColorAdjust whiteout = new ColorAdjust();
        whiteout.setBrightness(1.0);
        imgView.setEffect(whiteout);
        toggleGridLines.setGraphic(imgView);
        menu.getContainer().add(toggleGridLines, 9, 0);
		base.setTop(menu);
		
		
		
		// create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.setOnDragDetected(controller.getDrawPlotDragDetected());
		scene.setOnMouseDragged(controller.getDrawPlotDragged());
		scene.setOnMouseDragReleased(controller.getOnDrawPlotDone());
		scene.setOnMouseReleased(controller.getOnDrawPlotDone());
		scene.getStylesheets().add(buttonStyle);
		scene.getStylesheets().add(sliderStyle);
		scene.getStylesheets().add(textStyle);
		stage.setScene(scene);
		stage.show();
	
		scene.widthProperty().addListener(controller.getPDWidthChangeListener());
		scene.heightProperty().addListener(controller.getPDHeightChangeListener());
	}
	
	public void heightChanged(Object windowHeight) {
		WINDOW_HEIGHT = (double) windowHeight;
		CANVAS_HEIGHT = (double)windowHeight - MenuBox.MENU_HEIGHT;
		drawArea.setHeight(CANVAS_HEIGHT);
		
		gc = drawArea.getGraphicsContext2D();
		//drawGrid();
	}
	
	public void widthChanged(Object windowWidth) {
		WINDOW_WIDTH =(double) windowWidth;
		CANVAS_WIDTH = (double)windowWidth - LEFTBAR;
		drawArea.setWidth(CANVAS_WIDTH);
		
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
		widthText.setMinWidth(LEFTBAR*0.6);
		Label heightText = new Label("Garden height");
		heightText.setMinWidth(LEFTBAR*0.6);
		Label boxHeightText = new Label("Grid Height");
		boxHeightText.setMinWidth(LEFTBAR*0.6);
		Label boxWidthText = new Label("Grid Width");
		boxWidthText.setMinWidth(LEFTBAR*0.6);
		Label budgetText = new Label("Budget");
		budgetText.setMinWidth(LEFTBAR*0.6);
		Label gridSize = new Label("Grid Size");
		widthInput = new TextField();
		widthInput.setMaxWidth(LEFTBAR*0.6);
		widthInput.setText("" + 100);
		heightInput = new TextField();
		heightInput.setMaxWidth(LEFTBAR*0.6);
		heightInput.setText("" + 100);
		boxHeightInput = new TextField();
		boxHeightInput.setMaxWidth(LEFTBAR*0.6);
		boxHeightInput.setText("" + 10);
		boxWidthInput = new TextField();
		boxWidthInput.setMaxWidth(LEFTBAR*0.6);
		budgetInput = new TextField();
		budgetInput.setMaxWidth(LEFTBAR*0.6);
		budgetInput.setText("0");
		boxWidthInput.setText("" + 10);
		
		Button drawDimensions = new Button("Set Dimensions");
		drawDimensions.setOnMouseClicked(controller.drawPlotGrid());
		
		VBox budget = new VBox();
		budget.setAlignment(Pos.CENTER);
		budget.getChildren().add(budgetText);
		budget.getChildren().add(budgetInput);
		
		
		VBox dimensions = new VBox();
		dimensions.setAlignment(Pos.CENTER);
		dimensions.getChildren().add(widthText);
		dimensions.getChildren().add(widthInput);
		dimensions.getChildren().add(heightText);
		dimensions.getChildren().add(heightInput);
		dimensions.getChildren().add(boxWidthText);
		dimensions.getChildren().add(boxWidthInput);
		dimensions.getChildren().add(boxHeightText);
		dimensions.getChildren().add(boxHeightInput);
		dimensions.getChildren().add(drawDimensions);
		
		Insets margin = new Insets(5,0,5,0);
		Iterator<Node> it = dimensions.getChildren().iterator();
		while(it.hasNext()) {
			dimensions.setMargin(it.next(), margin);
		}
		dimensions.setMargin(dimensions.getChildren().get(0), new Insets(20,0,5,0)); //give 20 pixel gap
		budget.setMargin(budgetText, margin);
		budget.setMargin(budgetInput, margin);

		left_grid.add(dimensions, 0, 0);
		left_grid.add(budget, 0, 1);
	}
	
	
	/**
	 * Responsible for drawing the scaling grid lines on the canvas
	 * @return a scale value calculated based on grid lines and pixel distances
	 */
	public double drawGrid() {
		
		gc.clearRect(0, 0, drawArea.getWidth(), drawArea.getHeight());
		gc.setLineWidth(1);
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
		
		
		double perciseWidth = CANVAS_WIDTH/(width/boxWidth);
		double perciseHeight = CANVAS_HEIGHT/(height/boxHeight);
		int heightInc = (int)CANVAS_HEIGHT/(height/boxHeight);
		int widthInc = (int)CANVAS_WIDTH/(width/boxWidth);
		
		double widthBorder = perciseWidth - widthInc;
		
		double heightBorder = perciseHeight - heightInc;
		
		heightBorder *= height/boxHeight;
		widthBorder *= width/boxWidth;
		
		drawArea.setWidth(CANVAS_WIDTH - widthBorder + 2);
		drawArea.setHeight(CANVAS_HEIGHT - heightBorder + 1);
		double pixelsPerFoot = (double)widthInc / boxWidth;
		
		//do all scale calculations but only show if supposed to
		if(showGridLines) {
			for (double x = 1; x <= CANVAS_WIDTH + widthBorder; x+=widthInc) {
				gc.moveTo(x, 0);
				gc.lineTo(x, CANVAS_HEIGHT);
				gc.stroke();
			}
			
			for (double y = 0; y <= CANVAS_HEIGHT + heightBorder; y+=heightInc) {
				gc.moveTo(0,y);
				gc.lineTo(CANVAS_WIDTH,y);
				gc.stroke();
			}
			gc.closePath();
			gc.beginPath();
		}
		
		return pixelsPerFoot;
	}
	
	/**
	 * Draws plots on the canvas
	 * TODO: javadoc
	 * @param points an array lit of coordinates to draw
	 */
	public void drawPlot(ArrayList<Point> points) {
		//split the array list of points into two arrays of doubles
		//(fillPolygon method requires 2 arrays of doubles)
		double[] xcords = new double[points.size()];
		double[] ycords = new double[points.size()];
		int i = 0;
		for(Point p : points) {
			xcords[i] = (p.getX() - LEFTBAR - 5);
			ycords[i++] = (p.getY() - MenuBox.MENU_HEIGHT);
		}
		gc.fillPolygon(xcords, ycords, i);
		
	}
		

	/**
	 * Creates the left grid pane in the border pane base
	 * @param none
	 * @return none
	 */
	public void createLeftGrid() {
		left_grid = new GridPane();
		left_grid.setAlignment(Pos.CENTER);
		left_grid.setStyle("-fx-background-color: #678B5E");
		left_grid.setMinWidth(LEFTBAR);
		left_grid.setHgap(SPACING);
		left_grid.setVgap(SPACING);
		
		inputDimensions();
		createSliders();
		
		RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(35);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(10);
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(30);
	    RowConstraints row4 = new RowConstraints();
	    row4.setPercentHeight(7);
	    RowConstraints row5 = new RowConstraints();
	    row5.setPercentHeight(7);
	    RowConstraints row6 = new RowConstraints();
	    row6.setPercentHeight(11);
	    left_grid.getRowConstraints().addAll(row1,row2,row3,row4,row5, row6);
		
		base.setLeft(left_grid);
	}
	
	/**
	 * create the sliders for sunlight, soiltype and moisture
	 */
	public void createSliders() {
		sunlight = new Slider(1, 3, 0);
		soilType = new Slider(1, 3, 0);
		moisture = new Slider(1, 3, 0);
		sliderStandards(sunlight);
		sliderStandards(soilType);
		sliderStandards(moisture);
		
		// makes the soiltype slider display Clay Loam and Sand instead of 1 2 and 3
        soilType.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 2) return "Clay";
                if (n < 3) return "Loam";

                return "Sand";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Clay":
                        return 0d;
                    case "Loam":
                        return 1d;

                    default:
                        return 3d;
                }
            }
        });
        
		Label sunlightText = new Label("Sunlight Level");
		sunlightText.setMinWidth(LEFTBAR*0.6);
		Label soilTypeText = new Label("Soil Type");
		soilTypeText.setMinWidth(LEFTBAR*0.6);
		Label moistureText = new Label("Moisture Level");
		moistureText.setMinWidth(LEFTBAR*0.6);
		
		VBox sliders = new VBox();
		sliders.setAlignment(Pos.CENTER);
		sliders.getChildren().add(sunlightText);
		sliders.getChildren().add(sunlight);
		sliders.getChildren().add(moistureText);
		sliders.getChildren().add(moisture);
		sliders.getChildren().add(soilTypeText);
		sliders.getChildren().add(soilType);

		Insets margin = new Insets(5,0,5,0);
		Iterator<Node> it = sliders.getChildren().iterator();
		while(it.hasNext()) {
			sliders.setMargin(it.next(), margin);
		}
		
		left_grid.add(sliders, 0, 2);
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
		slider.setMaxWidth(LEFTBAR*0.6);
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
	 * TODO javadox
	 * Getter for canvas height
	 * @return canvas height
	 */
	public double getLeftbarWidth() {
		return LEFTBAR;
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
			gc.lineTo(me.getX()- LEFTBAR - 5, me.getY()- MenuBox.MENU_HEIGHT);
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
			gc.setLineWidth(5);
			gc.lineTo(me.getX()- LEFTBAR - 5, me.getY() - MenuBox.MENU_HEIGHT);
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
	
	//TODO javadoc
	public void showRedrawButton() {
		if(drawRedrawPlot.getChildren().size() > 1) {
			return;
		}
		Button redrawPlot = new Button("Redraw");
		redrawPlot.setPrefWidth(LEFTBAR*0.45);
		redrawPlot.setOnMouseClicked(controller.getRedrawPlotHandler());
		((Button)drawRedrawPlot.getChildren().get(0)).setText("New Plot");
		((Button)drawRedrawPlot.getChildren().get(0)).setPrefWidth(LEFTBAR*0.45);
		drawRedrawPlot.getChildren().add(redrawPlot);
		drawRedrawPlot.setMargin(redrawPlot, new Insets(10, 5, 0, 5));
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
	 * @return boolean
	 */
	public boolean getCanDraw() {
		return canDraw;
	}
	
	/**
	 * Changes the value of the showGridLines variable and changes the image in the menu button 
	 */
	public void flipShowGridLines() {
		showGridLines = !showGridLines;
		if(showGridLines)
			((ImageView)toggleGridLines.getGraphic()).setImage(hideImg);
		else
			((ImageView)toggleGridLines.getGraphic()).setImage(showImg);
	}
	
	/** 
	 * Returns the budget input value
	 * @return the text in the budget input box
	 */
	public double getBudget() {
		return Double.parseDouble(budgetInput.getText());
	}
}