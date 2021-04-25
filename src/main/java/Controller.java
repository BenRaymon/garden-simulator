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
import javafx.scene.control.ListView;



public class Controller extends Application{
	View view;
	Stage stage;
	SplashView splashView;
	StartView startView;
	PlotDesignView plotDesignView;
	GardenEditorView gardenEditorView;
	CompPlantsView compPlantsView;
	ShoppingListView shopView;
	ReportView reportView;
	Garden garden;
	
	@Override
	public void start(Stage s) throws Exception {
		this.stage = s;
		
		// Create the views
		splashView = new SplashView(stage, this);
		
		//set the first scene to splash
		stage.setScene(splashView.getScene());
		
		// BackgroundLoader loads the data and images in concurrently whilst showing a splash screen
		// It then goes to the start screen when it is finished
		BackgroundLoaderController loadData = new BackgroundLoaderController(View.getImages(), Garden.getAllPlants(), this);
		garden = new Garden();
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
	}
	
	//Button functionality for startView Create New Garden
	public EventHandler getNewGardenOnClickHandler() {
		return (event -> {
			stage.setScene(plotDesignView.getScene());
		});
	}
	
	public EventHandler getToShoppingListOnClickHandler() {
		return (event -> {
			stage.setScene(shopView.getScene());
		});
	}
	
	public EventHandler getToReportOnClickHandler() {
		return (event -> {
			stage.setScene(reportView.getScene());
		});
	}
	
	public EventHandler getToCompareOnClickHandler() {
		return (event -> {
			stage.setScene(compPlantsView.getScene());
		});
	}
	
	//Handler for the DrawPlot button in PlotDesignnView
	public EventHandler getDrawPlotHandler() {
		return (event -> {
			//get Options values and create a new plot with these options
			double sunlight = plotDesignView.getSunlightSlider();
			double soiltype = plotDesignView.getSoilSlider();
			double moisture = plotDesignView.getMoistureSlider();
			Options o = new Options(soiltype, sunlight, moisture);
			//create a new plot in the garden
			garden.newPlot(o);
			//allow drawing
			plotDesignView.allowDrawing();
		});
	}
	
	//Handler for detecting start of drag for drawing plot
	public EventHandler getDrawPlotDragDetected() {
		return (event->{
        	System.out.println("Mouse pressed");
			MouseEvent me = (MouseEvent)event;
			plotDesignView.startDrawingPlot(me);
			
        });
	}
	
	//Handler for while plot is being drawn
	public EventHandler getDrawPlotDragged() {
		return (event->{
			System.out.println("Mouse Dragged");
			MouseEvent me = (MouseEvent)event;
			plotDesignView.drawPlot(me);
        });
	}
	
	//Hanlder for when plot is done being drawn
	public EventHandler getOnDrawPlotDone() {
		return (event->{
			System.out.println("Mouse Released");
			MouseEvent me = (MouseEvent)event;
        	
        	//add coords to the plot in the garden
			if(plotDesignView.getCanDraw()) {
	        	garden.addCoordsToPlot(plotDesignView.getCoords());
	        	//set fill color based on soil type
	        	int plotIndex = garden.getNumPlots() - 1;
	        	plotDesignView.setFillColor(garden.getPlotOptions(plotIndex));
	        	//fill the plot
	        	plotDesignView.fillPlot(me);
			}
        });
	}
	
	//Button functionality for toGarden
	public EventHandler getToGardenOnClickHandler() {
		return (event -> {
			stage.setScene(gardenEditorView.getScene());
			System.out.println(garden.getPlots());
			for (Plot p : garden.getPlots()) {
				gardenEditorView.setFillColor(p.getOptions());
				gardenEditorView.drawPlot(p.getCoordinates());
			}
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
			//Possibly refactor this if statement
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
			gardenEditorView.creatNewImageInBase_withParams(drag,db);
			drag.setDropCompleted(true);
			drag.consume();
		});
	}
	
	//Searches for plant from all loaded plants, and displays info on button click
	public EventHandler RightPlantButtonClickedHandler() {
		return (event ->{
			
			//compPlantsView.setBLeps(compPlants.getLeps());
			
			TextField temp = compPlantsView.getTextBox();
			String plantInfo = "Plant A\n";		
			plantInfo = plantInfo + CompPlants.getInfo(temp.getText());
			Text tempText = compPlantsView.getRightBody();
			//tempText.setText(plantInfo);
			tempText.setText(temp.getText());
			//Setting plant A variables in compPlantView to plantInfo
			
			//compPlantsView.setALeps(CompPlants.getLepInfo(temp.getText()));
			//compPlantsView.setAName(temp.getText());

		});
		
	}
	
	//Searches for plant from all loaded plants, and displays info on button click
	public EventHandler LeftPlantButtonClickedHandler() {
		return (event ->{
			TextField temp = compPlantsView.getTextBox();
			String plantInfo = "Plant B\n";		
			plantInfo = plantInfo + CompPlants.getInfo(temp.getText());
			//String plantInfo = CompPlants.getInfo(temp.getText());
			Text tempText = compPlantsView.getLeftBody();
			//tempText.setText(plantInfo);
			tempText.setText(temp.getText());

			//Setting plant B variables in compPlantView to plantInfo
			
			//compPlantsView.setBLeps(CompPlants.getLepInfo(temp.getText()));
			//compPlantsView.setBName(temp.getText());
		});
		
	}
	
	//Event handler for listView
	public EventHandler listClickedHandler() {
		return(event ->{
			System.out.println("In listClickedHandler");
			ListView<String> tempList = compPlantsView.getListView();
			String currentItem = tempList.getSelectionModel().getSelectedItem();
			
			if(currentItem == "Lep Compare") {
				System.out.println("Switching to Lep Compare");
				
				String tempPlantAName = compPlantsView.getLeftBody().getText();
				int tempPlantALeps = CompPlants.getLepInfo(tempPlantAName);
				String tempPlantBName = compPlantsView.getRightBody().getText();
				int tempPlantBLeps = CompPlants.getLepInfo(tempPlantBName);
				//Creates graph
				compPlantsView.setLepCompare(tempPlantAName,tempPlantBName,tempPlantALeps,tempPlantBLeps);
				//compPlantsView.setLepCompare("plant a","plant b",5,10);
			}
			else if(currentItem == "Radius Compare") {
				System.out.println("Switching to Radius Compare");
				
				String tempPlantAName = compPlantsView.getLeftBody().getText();
				double tempPlantALowerRadius = CompPlants.getLowerRadius(tempPlantAName);
				double tempPlantAUpperRadius = CompPlants.getUpperRadius(tempPlantAName);
				
				String tempPlantBName = compPlantsView.getRightBody().getText();
				double tempPlantBLowerRadius = CompPlants.getLowerRadius(tempPlantBName);
				double tempPlantBUpperRadius = CompPlants.getUpperRadius(tempPlantBName);
				
				compPlantsView.setRadiusCompare(tempPlantAName, tempPlantBName, tempPlantALowerRadius, tempPlantAUpperRadius, tempPlantBLowerRadius, tempPlantBUpperRadius);
			}
			else if(currentItem == "Size Compare") {
				System.out.println("Switching to Size Compare");
				String tempPlantAName = compPlantsView.getLeftBody().getText();
				double tempPlantALowerSize = CompPlants.getLowerSize(tempPlantAName);
				double tempPlantAUpperSize = CompPlants.getUpperSize(tempPlantAName);
				
				String tempPlantBName = compPlantsView.getRightBody().getText();
				double tempPlantBLowerSize = CompPlants.getLowerSize(tempPlantBName);
				double tempPlantBUpperSize = CompPlants.getUpperSize(tempPlantBName);
				
				compPlantsView.setSizeCompare(tempPlantAName, tempPlantBName, tempPlantALowerSize, tempPlantAUpperSize, tempPlantBLowerSize, tempPlantBUpperSize);

			}
			else if(currentItem == "General Info") {
				System.out.println("Switching to General Info");
				String infoStringA = CompPlants.getInfo(compPlantsView.getLeftBody().getText());
				String infoStringB = CompPlants.getInfo(compPlantsView.getRightBody().getText());
				compPlantsView.setGeneralInfoComapre(infoStringA,infoStringB);
				

				
			}
			//System.out.println("Selected item is: " + highlighted);
			
		});
	}



	
	
}