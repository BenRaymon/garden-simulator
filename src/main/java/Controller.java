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
		//create a start and plot design page
		StartView start = new StartView(stage);
		PlotDesignView plt = new PlotDesignView(stage);
		//set the first scene to start
		stage.setScene(start.getScene());
		
		//TEMPORARY 
		start.nextPage.setOnMouseClicked(event -> {
			System.out.println("start next - goto plot design");
			stage.setScene(plt.getScene());
		});
		
		//TEMPORARY
		plt.backPage.setOnMouseClicked(event -> {
			System.out.println("plot design back - goto start page");
			stage.setScene(start.getScene());
		});
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