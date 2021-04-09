import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
	
	public PlotDesignView(Stage stage) {
		GridPane base = new GridPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);
		
		//create a temporary vbox for the name and button
		nextPage = new Button("Next Page");
		backPage = new Button("Back Page");
		pageTitle = new Text("Plot Design");
		VBox temp = new VBox(5);
		temp.getChildren().add(nextPage);
		temp.getChildren().add(backPage);
		temp.getChildren().add(pageTitle);
		temp.setAlignment(Pos.CENTER);
		base.add(temp, 0, 1);
		
		//create and set scene with base
		scene = new Scene(base, 800, 800);
        stage.setScene(scene);
        stage.show();
	}
	
	public Scene getScene() {
		return scene;
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