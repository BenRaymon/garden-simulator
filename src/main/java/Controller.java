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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;



public class Controller extends Application{
	Model model;
	View view;
	Stage stage;
	SplashView splashView;
	StartView startView;
	PlotDesignView plotDesignView;
	GardenEditorView gardenEditorView;
	CompPlantsView compPlantsView;
	ShoppingListView shopView;
	ReportView reportView;
	CompPlantsModel compPlantsModel;
	
	@Override
	public void start(Stage s) throws Exception {
		this.stage = s;
		
		//Creates an instance of compPlantsModel
		compPlantsModel = new CompPlantsModel();
		
		// Create the views
		splashView = new SplashView(stage, this);
		
		//set the first scene to start
		//stage.setScene(startView.getScene());
		stage.setScene(splashView.getScene());
		
		// SplashModel loads the data and images in concurrently whilst showing a splash screen
		// It then goes to the start screen when it is finished
		model = new SplashModel(this);
	}
	
	public static void main(String[] args) {
		System.out.println("in main 1");
		launch(args);
		
	}
	
	//Start the main program
	public void loadStartScreen() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Create all other views after the screen is loaded
		startView = new StartView(stage, this);
		plotDesignView = new PlotDesignView(stage, this);
		gardenEditorView = new GardenEditorView(stage, this);
		compPlantsView = new CompPlantsView(stage, this);
		shopView = new ShoppingListView(stage, this);
		reportView = new ReportView(stage, this);
		
		//set the scene and model to Start
		stage.setScene(startView.getScene());
		model = new StartModel();
	}
	
	//Button functionality for toGarden
	public EventHandler getToGardenOnClickHandler() {
		return (event -> {
			//pageViews.set(2,new GardenEditorView(stage, this));
			stage.setScene(gardenEditorView.getScene());
			Garden garden = new Garden(((PlotDesignModel)model).getPlots());
			model = new GardenEditorModel(garden);
			GardenEditorModel gm = (GardenEditorModel)model;
			for (Plot p : gm.getGarden().getPlots()) {
				gardenEditorView.drawPlot(p.getCoordinates());
			}
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
			//pageViews.set(4,new ShoppingListView(stage, this));
			stage.setScene(shopView.getScene());
			model = new ShoppingListModel();
		});
	}
	
	public EventHandler getToReportOnClickHandler() {
		return (event -> {System.out.println("Report button handler");
		stage.setScene(reportView.getScene());
		model = new ReportModel();
		});
	}
	
	public EventHandler getToCompareOnClickHandler() {
		return (event -> {System.out.println("Compare button handler");
		stage.setScene(compPlantsView.getScene());
		model = new CompPlantsModel();
			});
	}
	
	//Hanlder for the DrawPlot button in PlotDesignnView
	public EventHandler getDrawPlotHandler() {
		return (event -> {
			//get Options values and create a new plot with these options
			double sunlight = plotDesignView.getSlider("Sun").getValue();
			double soiltype = plotDesignView.getSlider("Soil").getValue();
			double moisture = plotDesignView.getSlider("Moisture").getValue();
			System.out.println("SOIL TYPE");
			System.out.println(soiltype);
			Options o = new Options(soiltype, sunlight, moisture);
			((PlotDesignModel)model).newPlot(o);
		});
	}
	
	//Handler for detecting start of drag for drawing plot
	public EventHandler getDrawPlotDragDetected() {
		return (event->{
        	System.out.println("Mouse pressed");
			MouseEvent me = (MouseEvent)event;
        	//start drawing a plot
			plotDesignView.getGC().beginPath();
			plotDesignView.getGC().lineTo(me.getX() - 195, me.getY());
			plotDesignView.getGC().stroke();
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
			MouseEvent me = (MouseEvent)event;
        	//Draw the line as the mouse is dragged
			plotDesignView.getGC().lineTo(me.getX() - 195, me.getY());
			plotDesignView.getGC().stroke();	
        	
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
			PlotDesignModel plm = (PlotDesignModel) model;
			MouseEvent me = (MouseEvent)event;
        	//Close the path
			plotDesignView.getGC().closePath();
			int[]soils = plm.getPlots().get(plm.getNumPlots()-1).getOptions().getSoilTypes();
			System.out.println("PRINTING HERE");
			for (int element: soils) {
				System.out.println(element);
			}
			if (soils[0] == 1) {
				plotDesignView.getGC().setFill(Color.CHOCOLATE);
			}
			if (soils[1] == 1) {
				plotDesignView.getGC().setFill(Color.SADDLEBROWN);
			}
			if (soils[2] == 1) {
				plotDesignView.getGC().setFill(Color.TAN);
			}
				
        	plotDesignView.getGC().fill();
        	plotDesignView.getGC().beginPath();
        	
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
			
			if (gardenEditorView.getBase().getChildren().contains(event.getSource())) {
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
			gardenEditorView.createNewImageInBase(drag, ((Image)db.getContent(DataFormat.IMAGE)));
//			model.setX(event.getSceneX());
//			model.setY(event.getSceneY());
			drag.setDropCompleted(true);
			drag.consume();
		});
	}
	
	//Searches for plant from all loaded plants, and displays info on button click
	public EventHandler RightPlantButtonClickedHandler() {
		return (event ->{
			System.out.println("ComparePlantsLeftButton Clicked.");
			//TextBox temp = CompPlantsView.getTextBox();
			//String plantInfo = CompPlantsModel.getInfo(temp);
			
			//System.out.println("Before TextField declaration");
			TextField temp = compPlantsView.getTextBox();
			//System.out.println("Before plantInfo declaration");
			
			String plantInfo = compPlantsModel.getInfo(temp.getText());
			//System.out.println("Before tempText declaration");
			Text tempText = compPlantsView.getRightBody();
			//System.out.println("Before setText");
			tempText.setText(plantInfo);
			
		});
		
	}
	
	//Searches for plant from all loaded plants, and displays info on button click
	public EventHandler LeftPlantButtonClickedHandler() {
		return (event ->{
			System.out.println("ComparePlantsLeftButton Clicked.");
			//TextBox temp = CompPlantsView.getTextBox();
			//String plantInfo = CompPlantsModel.getInfo(temp);
			
			//System.out.println("Before TextField declaration");
			TextField temp = compPlantsView.getTextBox();
			//System.out.println("Before plantInfo declaration");
			
			String plantInfo = compPlantsModel.getInfo(temp.getText());
			//System.out.println("Before tempText declaration");
			Text tempText = compPlantsView.getLeftBody();
			//System.out.println("Before setText");
			tempText.setText(plantInfo);
			
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