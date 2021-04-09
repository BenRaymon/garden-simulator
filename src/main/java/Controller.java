import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Controller extends Application{
	Model model;
	View view;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		setView(new StartView(stage));
		
	}
	
	public static void main(String[] args) {
		System.out.println("in main");
		launch(args);
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public boolean drawPlot() {
		return true;
	}
	
	public boolean dragPlant() {
		return true;
	}



	
	
}