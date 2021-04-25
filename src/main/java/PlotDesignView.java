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
	private Scene scene;
	private BorderPane base;
	private Button toGarden;
	private Button drawDimensions;
	private GraphicsContext gc;
	private Controller controller;
	private ArrayList<Point> coords;
	private GridPane left_grid, bottom, right;
	private Canvas drawArea;
	private boolean canDraw;
	private final double LEFTBAR = 200;
	private final double SPACING = 10;
	private final double BOTTOM_HEIGHT = 100;
	private double canvasWidth = WINDOW_WIDTH;
	private double canvasHeight = WINDOW_HEIGHT;
	private int scaleIndex = 50;
	
	public PlotDesignView(Stage stage, Controller c) {

		controller = c;
		base = new BorderPane();
		coords = new ArrayList<Point>();
		canDraw = false;
		
		//circ.setFill(new ImagePattern((Image)value));
		
		
		

		// create Canvas
		
		
		
		VBox box = new VBox();
		System.out.println("Width" + box.getWidth());
		
		drawArea = new Canvas(WINDOW_WIDTH - 2 * LEFTBAR, WINDOW_HEIGHT - BOTTOM_HEIGHT);
		gc = drawArea.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		
		box.getChildren().add(drawArea);
		Image image  = new Image("https://static.wikia.nocookie.net/lotr/images/e/ec/Gimli_-_FOTR.png/revision/latest?cb=20121008105956");
		
		String cssLayout = "-fx-border-color: red;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: dashed;\n";
		
		box.setStyle(cssLayout);
		

		
		base.setCenter(box);

		// Create left side bar with sliders and buttons
		createLeftGrid();
		createSunSlider();
		createMoistureSlider();
		createSoilSlider();
		createScaleButtons();
		

		// create last and next page buttons
		createBottom();
		createRightGrid();
		inputDimensions();
		
		
		// add the drawplot button
		drawPlot = new Button("Draw Plot");
		drawPlot.setOnMouseClicked(controller.getDrawPlotHandler());
		left_grid.add(drawPlot, 0, 30);
		toGarden = new Button("To Garden");
		toGarden.setOnMouseClicked(controller.getToGardenOnClickHandler());
		left_grid.add(toGarden, 0, 45);
		
		// create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.setOnDragDetected(controller.getDrawPlotDragDetected());
		scene.setOnMouseDragged(controller.getDrawPlotDragged());
		scene.setOnMouseDragReleased(controller.getOnDrawPlotDone());
		scene.setOnMouseReleased(controller.getOnDrawPlotDone());
		stage.setScene(scene);
		stage.show();
	
		scene.widthProperty().addListener(
				new ChangeListener<Object>() {
					public void changed(ObservableValue observable,
										Object oldValue, Object newValue) {
						Double width = (Double)newValue;
						drawArea.setWidth(width);
						System.out.println("WIDTH CHANGE");
					}
				}
		);
		
		scene.heightProperty().addListener(
				new ChangeListener<Object>() {
					public void changed(ObservableValue observable,
										Object oldValue, Object newValue) {
						Double height = (Double)newValue;
						drawArea.setHeight(height - BOTTOM_HEIGHT);
					}
				});
	
	}
	
	public void inputDimensions() {
		Label widthText = new Label("Input width");
		Label heightText = new Label("Input height");
		widthInput = new TextField();
		widthInput.setOnAction(controller.getPlotWidthInput());
		heightInput = new TextField();
		heightInput.setOnAction(controller.getPlotHeightInput());
		drawDimensions = new Button("Set Dimensions");
		drawDimensions.setOnMouseClicked(controller.drawPlotGrid());
		right.add(widthText, 0, 0);
		right.add(widthInput, 1, 0);
		right.add(heightText, 0, 1);
		right.add(heightInput, 1, 1);
		right.add(drawDimensions, 0, 2);
	}
	
	public int getHeightInput() {
		int height = Integer.parseInt(heightInput.getText());
		System.out.println(height);
		return height;
	}
	
	public int getWidthInput() {
		int width = Integer.parseInt(widthInput.getText());
		System.out.print(width);
		return width;
	}
	
	public void drawGrid() {
		
		double heightScaleFactor =  (drawArea.getHeight() / getHeightInput());
		double widthScaleFactor = drawArea.getWidth() / getWidthInput();
		
		gc.clearRect(0, 0, drawArea.getWidth(), drawArea.getHeight());
		
		for (double x = 0; x < drawArea.getWidth(); x += widthScaleFactor) {
			gc.moveTo(x, 0);
			gc.lineTo(x, drawArea.getHeight());
			gc.stroke();
		}
		
		for (double y = 0; y < drawArea.getHeight(); y += heightScaleFactor) {
			gc.moveTo(0,y);
			gc.lineTo(drawArea.getWidth(),y);
			gc.stroke();
		}
		gc.closePath();
		gc.beginPath();
	}
	
	public void createRightGrid() {
		right = new GridPane();
		right.setAlignment(Pos.TOP_LEFT);
		right.setStyle("-fx-background-color: darkseagreen");
		// left_grid.setGridLinesVisible(true);
		right.setMinWidth(LEFTBAR);
		right.setHgap(SPACING);
		right.setVgap(SPACING);
		base.setRight(right);
	}
		
	
	public void createScaleButtons() {
		Button scaleUp = new Button("+");
		Button scaleDown = new Button("-");
		left_grid.add(scaleUp, 0, 50);
		left_grid.add(scaleDown, 1, 50);
		scaleUp.setOnMouseClicked(controller.scaleUpCanvas());
		scaleDown.setOnMouseClicked(controller.scaleDownCanvas());
		
	}

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

	public Slider sliderStandards(Slider slider) {
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setSnapToTicks(true);
		slider.setMinorTickCount(0);
		return slider;

	}

	public void createSunSlider() {
		sunlight = new Slider(1, 3, 0);
		Text t = new Text("Sunlight Level");
		sliderStandards(sunlight);
		left_grid.add(t, 0, 0);
		left_grid.add(sunlight, 0, 1);

	}

	public void createMoistureSlider() {
		moisture = new Slider(1, 3, 0);
		sliderStandards(moisture);
		Text t = new Text("Moisture Level");
		left_grid.add(t, 0, 9);
		left_grid.add(moisture, 0, 10);
	}

	public void createSoilSlider() {
		soilType = new Slider(1, 3, 0);
		sliderStandards(soilType);
		Text t = new Text("Soil Type");
		left_grid.add(t, 0, 19);
		left_grid.add(soilType, 0, 20);
	}

	public Scene getScene() {
		return scene;
	}
	
	// For eventListener events.
	public double getSunlightSlider() {
		return sunlight.getValue();
	}

	public double getSoilSlider() {
		return soilType.getValue();
	}

	public double getMoistureSlider() {
		return moisture.getValue();
	}
	
	
	public void startDrawingPlot(MouseEvent me) {
		if(canDraw) {
			//start drawing a plot
			gc.beginPath();
			gc.lineTo(me.getX() - 195, me.getY());
			gc.stroke();
			//add the point to a coordinate list
			coords.add(new Point(me.getX() - 195, me.getY()));
		}
	}
	
	public void drawPlot(MouseEvent me) {
		if(canDraw) {
			//Draw the line as the mouse is dragged
			gc.lineTo(me.getX() - LEFTBAR - 5, me.getY());
			gc.stroke();	
			//add the point to a coordinate list
			coords.add(new Point(me.getX() - LEFTBAR - 5, me.getY()));
		}
	}
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
	
	public void setFillColor(Options o) {
		int[] soil = o.getSoilTypes();
		if(soil[0] == 1) 
			gc.setFill(Color.GREY);
		else if (soil[1] == 1) 
			gc.setFill(Color.SADDLEBROWN);
		else if (soil[2] == 1) 
			gc.setFill(Color.SANDYBROWN);
	}
	
	public ArrayList<Point> getCoords(){
		return coords;
	}
	
	public void scaleUp() {
		System.out.println("IN SCALEUP");
		gc.clearRect(0, 0, drawArea.getWidth(), drawArea.getHeight());
		scaleIndex -= 10;
		drawGrid();
	}
	
	public void scaleDown() {
		System.out.println("IN SCALEDOWN");
		gc.clearRect(0, 0, drawArea.getWidth(), drawArea.getHeight());
		scaleIndex += 10;
		drawGrid();
	}
	
	public void allowDrawing() {
		canDraw = true;
	}
	
	public void preventDrawing() {
		canDraw = false;
	}
	
	public boolean getCanDraw() {
		return canDraw;
	}
}