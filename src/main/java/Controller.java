import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Controller extends Application{
	Model model;
	ArrayList<View> pageViews = new ArrayList<View>();
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		//create a start and plot design page
		StartView startView = new StartView(stage);
		pageViews.add(startView);
		PlotDesignView plotDesignView = new PlotDesignView(stage);
		pageViews.add(plotDesignView);
		GardenEditorView gardenEditorView = new GardenEditorView(stage);
		pageViews.add(gardenEditorView);
		CompPlantsView compPlantsView = new CompPlantsView(stage);
		pageViews.add(compPlantsView);
		ShoppingListView shopView = new ShoppingListView(stage);
		pageViews.add(shopView);
		ReportView reportView = new ReportView(stage);
		pageViews.add(reportView);
		
		//set the first scene to start
		stage.setScene(startView.getScene());
		
		//LOAD PLANT INFO
		model = new StartModel();
		((StartModel) model).loadAllPlants();
		startView.loadImages();
		
		//TEMPORARY TO SWITCH FROM PAGE TO PAGE IN PRE-ALPHA
		//set page switch buttons on each page
		for(int i = 0; i < pageViews.size(); i++) {
			pageViews.get(i).next = pageViews.get((i+1) % (pageViews.size()));
			
			if( i > 0) 
				pageViews.get(i).back = pageViews.get((i-1) % pageViews.size());
			
			final int x = i; 
			pageViews.get(i).nextPage.setOnMouseClicked(event ->{
				stage.setScene(pageViews.get(x).next.getScene());
			});
			
			pageViews.get(i).backPage.setOnMouseClicked(event ->{
				stage.setScene(pageViews.get(x).back.getScene());
			});
		}
		// END TEMP CODE
		
		
		
		//Button functionality for startView Create New Garden
		startView.getNewGarden().setOnMouseClicked(event ->{
			stage.setScene(plotDesignView.getScene());
			model = new PlotDesignModel();
		});
		
		//plotdesignview drawPlot button listener
		plotDesignView.getDrawPlot().setOnMouseClicked(event ->{
			//get Options values and create a new plot with these options
			double sunlight = plotDesignView.getSlider("Sun").getValue();
			double soiltype = plotDesignView.getSlider("Soil").getValue();
			double moisture = plotDesignView.getSlider("Moisture").getValue();
			Options o = new Options(sunlight, soiltype, moisture);
			((PlotDesignModel)model).newPlot(o);
		});
		
		//plotdesignview scene drag listener
		plotDesignView.getScene().setOnMousePressed(event->{
        	System.out.println("Mouse pressed");
        	//start drawing a plot
        	plotDesignView.getGC().beginPath();
        	plotDesignView.getGC().lineTo(event.getX() - 195, event.getY());
        	plotDesignView.getGC().stroke();
        	//get index of the plot we are adding right now
        	int index = ((PlotDesignModel)model).getNumPlots() - 1; 
        	//create a point for the plot
        	Point p = new Point(event.getX() - 195, event.getY());
        	//add coordinate to plot
        	((PlotDesignModel)model).addCoordToPlot(index, p);
        });
		
		plotDesignView.getScene().setOnMouseDragged(event->{
        	System.out.println("Mouse Dragged");
        	//Draw the line as the mouse is dragged
        	plotDesignView.getGC().lineTo(event.getX() - 195, event.getY());
        	plotDesignView.getGC().stroke();	
        	plotDesignView.getGC().fill();
        	//get index of the plot we are adding right now
        	int index = ((PlotDesignModel)model).getNumPlots() - 1; 
        	//create a point for the plot
        	Point p = new Point(event.getX() - 195, event.getY());
        	//add coordinate to plot
        	((PlotDesignModel)model).addCoordToPlot(index, p);
        });

	}
	
	public static void main(String[] args) {
		System.out.println("in main 1");
		launch(args);
		
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