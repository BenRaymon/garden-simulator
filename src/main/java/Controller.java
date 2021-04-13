import java.util.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Controller extends Application{
	BackgroundLoaderController backgroundLoader;
	
	Model model;
	View view;
	Stage stage;
	ArrayList<View> pageViews = new ArrayList<View>();
	SplashView splashView;
	StartView startView;
	PlotDesignView plotDesignView;
	GardenEditorView gardenEditorView;
	CompPlantsView compPlantsView;
	ShoppingListView shopView;
	ReportView reportView;
	
	@Override
	public void start(Stage s) throws Exception {
		this.stage = s;
		
		// Initialize the background loader controller with the image and plant maps to be populated from disk
		backgroundLoader = new BackgroundLoaderController(View.getImages(), Model.getPlants());
		
		// Create the views
		splashView = new SplashView(stage, this);
		startView = new StartView(stage, this);
		plotDesignView = new PlotDesignView(stage, this);
		gardenEditorView = new GardenEditorView(stage, this);
		compPlantsView = new CompPlantsView(stage, this);
		shopView = new ShoppingListView(stage, this);
		reportView = new ReportView(stage, this);
		
		// Add views to the ArrayList of views
		pageViews.add(startView);
		pageViews.add(plotDesignView);
		pageViews.add(gardenEditorView);
		pageViews.add(compPlantsView);
		pageViews.add(shopView);
		pageViews.add(reportView);
		
		//set the first scene to start
		//stage.setScene(startView.getScene());
		stage.setScene(splashView.getScene());
		
		// Load assets in the background in SplashModel via BackgroundLoader
		backgroundLoader.loadData();
		backgroundLoader.loadImages();
		
		// Wait for loading to complete
		// Will probably try to make some kind of loading bar for beta/full release
		while(!backgroundLoader.isCompleted());
		
		//model = new SplashModel();
		//((SplashModel) model).loadAssets();
		//splashView.loadImages();
		
		
		//LOAD PLANT INFO
		model = new StartModel();
//		((StartModel) model).loadAllPlants();
//		startView.loadImages();
		
	}
	
	public static void main(String[] args) {
		System.out.println("in main 1");
		launch(args);
		
	}
	
	//Button functionality for toGarden
	public EventHandler getToGardenOnClickHandler() {
		return (event -> {
			pageViews.set(2,new GardenEditorView(stage, this));
			stage.setScene(pageViews.get(2).getScene());
			model = new PlotDesignModel();
		});
	}
	
	//Button functionality for startView Create New Garden
	public EventHandler getNewGardenOnClickHandler() {
		return (event -> {
			stage.setScene(plotDesignView.getScene());
			model = new PlotDesignModel();
		});
		
	}
	
	public EventHandler getToShoppingListOnClickHandler() {
		return (event -> {
			System.out.println("ShoppingList button handler");
			pageViews.set(4,new ShoppingListView(stage, this));
			stage.setScene(pageViews.get(4).getScene());
			model = new ShoppingListModel();
		});
	}
	
	//Hanlder for the DrawPlot button in PlotDesignnView
	public EventHandler getDrawPlotHandler() {
		return (event -> {
			PlotDesignView plv = (PlotDesignView)pageViews.get(1);
			//get Options values and create a new plot with these options
			double sunlight = plv.getSlider("Sun").getValue();
			double soiltype = plv.getSlider("Soil").getValue();
			double moisture = plv.getSlider("Moisture").getValue();
			Options o = new Options(sunlight, soiltype, moisture);
			((PlotDesignModel)model).newPlot(o);
		});
	}
	
	//Handler for detecting start of drag for drawing plot
	public EventHandler getDrawPlotDragDetected() {
		return (event->{
        	System.out.println("Mouse pressed");
        	
			PlotDesignView plv = ((PlotDesignView)pageViews.get(1));
			MouseEvent me = (MouseEvent)event;
        	//start drawing a plot
        	plv.getGC().beginPath();
        	plv.getGC().lineTo(me.getX() - 195, me.getY());
        	plv.getGC().stroke();
        	//get index of the plot we are adding right now
        	int index = ((PlotDesignModel)model).getNumPlots() - 1; 
        	//create a point for the plot
        	Point p = new Point(me.getX() - 195, me.getY());
        	//add coordinate to plot
        	((PlotDesignModel)model).addCoordToPlot(index, p);
        });
	}
	
	//Handler for while plot is being drawn
	public EventHandler getDrawPlotDragged() {
		return (event->{
			System.out.println("Mouse Dragged");
			PlotDesignView plv = ((PlotDesignView)pageViews.get(1));
			MouseEvent me = (MouseEvent)event;
        	//Draw the line as the mouse is dragged
        	plv.getGC().lineTo(me.getX() - 195, me.getY());
        	plv.getGC().stroke();	
        	
        	//get index of the plot we are adding right now
        	int index = ((PlotDesignModel)model).getNumPlots() - 1; 
        	//create a point for the plot
        	Point p = new Point(me.getX() - 195, me.getY());
        	//add coordinate to plot
        	((PlotDesignModel)model).addCoordToPlot(index, p);
        });
	}
	
	//Hanlder for when plot is done being drawn
	public EventHandler getOnDrawPlotDone() {
		return (event->{
			System.out.println("Mouse Released");
			PlotDesignView plv = ((PlotDesignView)pageViews.get(1));
			PlotDesignModel plm = (PlotDesignModel) model;
			MouseEvent me = (MouseEvent)event;
        	//Close the path
			
        	plv.getGC().closePath();
        	plv.getGC().fill();
        	plv.getGC().beginPath();
        	
        	
        	//get index of the plot we are adding right now
        	int index = plm.getNumPlots() - 1; 
        	//create a point for the plot
        	Point p = new Point(me.getX() - 195, me.getY());
        	//add coordinate to plot
        	plm.addCoordToPlot(index, p);
        });
	}
	
	//Handler for image being dragged
	public EventHandler getOnImageDraggedHandler() {
		return (event -> {
			System.out.println("On dragged (drag detected handler)");
			ImageView iv = (ImageView)event.getSource();
			Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
			
			ClipboardContent content = new ClipboardContent();
			content.putImage(iv.getImage());
			db.setContent(content);
			
			if (((GardenEditorView)pageViews.get(2)).getBase().getChildren().contains(event.getSource())) {
				iv.setImage(null);
			}
			event.consume();
		});
	}
	
	//Handler for dragOver
	public EventHandler getOnDragOverHandler() {
		return (event -> {
			System.out.println("On drag over");
			((DragEvent) event).acceptTransferModes(TransferMode.ANY);
			event.consume();
		});
	}
	
	//Handler for dropping image into plot
	public EventHandler getOnDragDroppedHandler() {
		return (event -> {
			System.out.println("On drag dropped");
			DragEvent drag = (DragEvent) event;
			Dragboard db = drag.getDragboard();
			((GardenEditorView)pageViews.get(2)).createNewImageInBase(drag, ((Image)db.getContent(DataFormat.IMAGE)));
//			model.setX(event.getSceneX());
//			model.setY(event.getSceneY());
			drag.setDropCompleted(true);
			drag.consume();
		});
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