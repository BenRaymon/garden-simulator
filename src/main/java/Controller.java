import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Controller extends Application{
	Model model;
	View view;
	ArrayList<View> pages = new ArrayList<View>();
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		//create a start and plot design page
		StartView startView = new StartView(stage);
		pages.add(startView);
		PlotDesignView plotDesignView = new PlotDesignView(stage);
		pages.add(plotDesignView);
		GardenEditorView gardenEditorView = new GardenEditorView(stage);
		pages.add(gardenEditorView);
		CompPlantsView compPlantsView = new CompPlantsView(stage);
		pages.add(compPlantsView);
		ShoppingListView shopView = new ShoppingListView(stage);
		pages.add(shopView);
		ReportView reportView = new ReportView(stage);
		pages.add(reportView);
		
		//set the first scene to start
		stage.setScene(startView.getScene());
		
		//TEMPORARY TO SWITCH FROM PAGE TO PAGE IN PRE-ALPHA
		//set page switch buttons on each page
		for(int i = 0; i < pages.size(); i++) {
			pages.get(i).next = pages.get((i+1) % (pages.size()));
			
			if( i > 0) 
				pages.get(i).back = pages.get((i-1) % pages.size());
			
			final int x = i; 
			pages.get(i).nextPage.setOnMouseClicked(event ->{
				stage.setScene(pages.get(x).next.getScene());
			});
			
			pages.get(i).backPage.setOnMouseClicked(event ->{
				stage.setScene(pages.get(x).back.getScene());
			});
		}
		// END TEMP CODE
	}
	
	public static void main(String[] args) {
		System.out.println("in main 1");
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