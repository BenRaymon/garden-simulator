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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlotDesignView extends View{
	
	private Slider sunlight;
	private Slider moisture;
	private Slider soilType; 
	private Button drawPlot;
	private TextField budget;
	private Scene scene;
	private BorderPane base;
	private GraphicsContext gc;
	
	public PlotDesignView(Stage stage) {
		base = new BorderPane();
		
		
		//create last and next page buttons
		GridPane bottom = createBottom();
		addPageButtons(bottom);
		
		//create Canvas
		//FIXME Draw on Canvas 
		Canvas drawArea = new Canvas(600,800);
		gc = drawArea.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		base.setCenter(drawArea);
		
		//Create Sliders
		GridPane left_grid = createLeftGrid();
		createSlider(left_grid, sunlight, "Sun Slider", 1);
		createSlider(left_grid, soilType, "Soil Type", 10);
		createSlider(left_grid, moisture, "Moisture", 20);
		
		//add the drawplot button
		drawPlot = new Button("Draw Plot");
		left_grid.add(drawPlot, 0, 30);
		
		//create and set scene with base
		scene = new Scene(base, 800, 800);
        stage.setScene(scene);
        stage.show();
        
        //scene.setOnMousePressed(event->{
        //	System.out.println("Mouse pressed");
        //	gc.beginPath();
        //	gc.lineTo(event.getX() - left_grid.getWidth() - 15, event.getY());
        //	gc.stroke();
        //});
        
        //scene.setOnMouseDragged(event->{
        //	System.out.println("Mouse Dragged");
        //	gc.lineTo(event.getX() - left_grid.getWidth() - 15, event.getY());
        //	gc.stroke();
        //});
	}
	
	public GridPane createBottom() {
		GridPane bottom = new GridPane();
		bottom.setAlignment(Pos.BOTTOM_CENTER);
		bottom.setStyle("-fx-background-color: aqua");
		bottom.setGridLinesVisible(true);
		bottom.setHgap(10);
		bottom.setVgap(10);
		base.setBottom(bottom);
		return bottom;
	}
	
	public void addPageButtons(GridPane bottom) {
		nextPage = new Button("Next Page");
		backPage = new Button("Back Page");
		bottom.add(nextPage,1,0);
		bottom.add(backPage,0,0);
	}
	
	public GridPane createLeftGrid(){
		GridPane left_grid = new GridPane();
		left_grid.setAlignment(Pos.CENTER);
		left_grid.setStyle("-fx-background-color: pink");
		//left_grid.setGridLinesVisible(true);
		left_grid.setMinWidth(200);
		left_grid.setHgap(10);
		left_grid.setVgap(10);
		base.setLeft(left_grid);
		return left_grid;
	}
	
	
	public Slider sliderStandards(Slider slider){
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setSnapToTicks(true);
		slider.setMinorTickCount(0);
		return slider;
		
	}
	
	public void createSlider(GridPane left_pane, Slider slider, String text, int num) {
		slider = new Slider(1,3,0);
		sliderStandards(slider);
		Text t = new Text(text);
		left_pane.add(t, 0, num-1);
		left_pane.add(slider, 0, num);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public GraphicsContext getGC() {
		return gc;
	}
	
	public Button getDrawPlot() {
		return drawPlot;
	}
	
	public Slider getSlider(String name) {
		if (name.equals("Sun")) {
			return sunlight;
		}
		else if (name.equals("Soil")) {
			return soilType;
		}
		else if (name.equals("Moisture")) {
			return moisture;
		}
		else 
			return null;
	}
	
	
	public void fillPlot(Color color) {
		
	}
	
	public void drawCoords() {
		
	}
	
	public void updateSunlightSlider() {
		
	}
	
	public void updateMoistureSlider() {
		
	}
	
	public void updateSoilSlider() {
		
	}
	
}