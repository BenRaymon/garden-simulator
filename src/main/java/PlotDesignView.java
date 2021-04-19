import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlotDesignView extends View {

	private Slider sunlight;
	private Slider moisture;
	private Slider soilType;
	private Button drawPlot;
	private TextField budget;
	private Scene scene;
	private BorderPane base;
	private Button toGarden;
	private GraphicsContext gc;
	private Controller controller;
	private ArrayList<Point> coords;
	private boolean canDraw;
	private final double LEFTBAR = 200;
	private final double SPACING = 10;
	
	public PlotDesignView(Stage stage, Controller c) {

		controller = c;
		base = new BorderPane();
		coords = new ArrayList<Point>();
		canDraw = false;
		
		// create last and next page buttons
		GridPane bottom = createBottom();

		// create Canvas
		// FIXME Draw on Canvas
		Canvas drawArea = new Canvas(WINDOW_WIDTH - LEFTBAR, WINDOW_HEIGHT);
		gc = drawArea.getGraphicsContext2D();
		// gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		base.setCenter(drawArea);

		toGarden = new Button("To Garden");
		toGarden.setOnMouseClicked(controller.getToGardenOnClickHandler());

		// Create Sliders
		GridPane left_grid = createLeftGrid();
		createSunSlider(left_grid);
		createMoistureSlider(left_grid);
		createSoilSlider(left_grid);

		left_grid.add(toGarden, 0, 45);

		// add the drawplot button
		drawPlot = new Button("Draw Plot");
		drawPlot.setOnMouseClicked(controller.getDrawPlotHandler());
		left_grid.add(drawPlot, 0, 30);

		// create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.setOnDragDetected(controller.getDrawPlotDragDetected());
		scene.setOnMouseDragged(controller.getDrawPlotDragged());
		scene.setOnMouseDragReleased(controller.getOnDrawPlotDone());
		scene.setOnMouseReleased(controller.getOnDrawPlotDone());
		stage.setScene(scene);
		stage.show();
	}

	public GridPane createBottom() {
		GridPane bottom = new GridPane();
		bottom.setAlignment(Pos.BOTTOM_CENTER);
		bottom.setStyle("-fx-background-color: darkgrey");
		bottom.setGridLinesVisible(true);
		bottom.setHgap(SPACING);
		bottom.setVgap(SPACING);
		base.setBottom(bottom);
		return bottom;
	}

	public GridPane createLeftGrid() {
		GridPane left_grid = new GridPane();
		left_grid.setAlignment(Pos.CENTER);
		left_grid.setStyle("-fx-background-color: darkseagreen");
		// left_grid.setGridLinesVisible(true);
		left_grid.setMinWidth(LEFTBAR);
		left_grid.setHgap(SPACING);
		left_grid.setVgap(SPACING);
		base.setLeft(left_grid);
		return left_grid;
	}

	public Slider sliderStandards(Slider slider) {
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setSnapToTicks(true);
		slider.setMinorTickCount(0);
		return slider;

	}

	public void createSunSlider(GridPane left_pane) {
		// Also putting this in a V-box because that's what Ben did
		sunlight = new Slider(1, 3, 0);
		Text t = new Text("Sunlight Level");
		sliderStandards(sunlight);
		left_pane.add(t, 0, 0);
		left_pane.add(sunlight, 0, 1);

	}

	public void createMoistureSlider(GridPane left_pane) {
		moisture = new Slider(1, 3, 0);
		sliderStandards(moisture);
		Text t = new Text("Moisture Level");
		left_pane.add(t, 0, 9);
		left_pane.add(moisture, 0, 10);
	}

	public void createSoilSlider(GridPane left_pane) {
		soilType = new Slider(1, 3, 0);
		sliderStandards(soilType);
		Text t = new Text("Soil Type");
		left_pane.add(t, 0, 19);
		left_pane.add(soilType, 0, 20);
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
			gc.lineTo(me.getX() - 195, me.getY());
			gc.stroke();	
			//add the point to a coordinate list
			coords.add(new Point(me.getX() - 195, me.getY()));
		}
	}
	public void fillPlot(MouseEvent me) {
		//Close the path
		gc.closePath();
		//TODO: add code to set the color of the plot. make it a function in view
		//fillPlot(options O) <-- fill in view based on the options
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