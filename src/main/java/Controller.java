import java.util.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
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
	LoadSavedGardenView loadSavedGardenView;
	Garden garden;
	SaveLoadGarden gardenSaverLoader = new SaveLoadGarden();
	ArrayList<Garden> savedGardens = new ArrayList<Garden>();
	//BackgroundLoader backgroundLoader;
	
	
	@Override
	public void start(Stage s) throws Exception {
		this.stage = s;
		
		// Create the views
		splashView = new SplashView(stage, this);
		
		//set the first scene to splash
		stage.setScene(splashView.getScene());
		
		stage.setMaximized(true);
		System.out.println(stage.getWidth() + " " +  stage.getHeight());
		View.setSize(stage.getWidth(), stage.getHeight());
		
		loadStartScreen();
		garden = new Garden();
	}
	
	public static void main(String[] args) {
		System.out.println("in main 1");
		// BackgroundLoader loads the data and images in concurrently whilst showing a splash screen
		// It then goes to the start screen when it is finished
		BackgroundLoader backgroundLoader = new BackgroundLoader("bkgloader", View.getImages(), Garden.getAllPlants());
		backgroundLoader.start();
		try {
			backgroundLoader.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		launch(args);	
	}
	
	
	//Start the main program
	public void loadStartScreen() {
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// load the saved garden array into savedGardens
		try {
			savedGardens = gardenSaverLoader.loadGardenList();
		
			System.out.println("Load success");		
			for(Garden g : savedGardens) {
				System.out.println(g.getName());
			}
		} catch (ClassNotFoundException e) {
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
		loadSavedGardenView = new LoadSavedGardenView(stage, savedGardens, this);
		
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
			shopView.setShoppingListData(garden.generateShoppingListData(), garden.getBudget());
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
	
	public EventHandler drawPlotGrid() {
		return (event->{
			double pixelsPerFoot = plotDesignView.drawGrid();
			garden.setScale(pixelsPerFoot);
		});
	}
	
	public ChangeListener<Object> getPDHeightChangeListener() {
		return new ChangeListener<Object>() {
			public void changed(ObservableValue observable,
					Object oldValue, Object newValue) {
				plotDesignView.heightChanged(newValue);
			}
		};
	}
	
	public ChangeListener<Object> getPDWidthChangeListener() {
		return new ChangeListener<Object>() {
			public void changed(ObservableValue observable,
					Object oldValue, Object newValue) {
				plotDesignView.widthChanged(newValue);
			}
		};
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
	
	//Handler for when plot is done being drawn
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
			double h = gardenEditorView.getCanvasHeight();
			double w = gardenEditorView.getCanvasWidth();
			double t = gardenEditorView.getTopHeight();
			double pixelsPerFoot = GardenEditor.transformPlots(garden.getPlots(), w, h, t, garden.getScale());
			garden.setScale(pixelsPerFoot);
			gardenEditorView.setScale(pixelsPerFoot);
			for (Plot p : garden.getPlots()) {
				p.setCoordinates(p.filterCoords(5));
				p.setCoordinates(GardenEditor.smooth(p.getCoordinates(), 0.3, 20));
				gardenEditorView.setFillColor(p.getOptions());
				gardenEditorView.drawPlot(p.getCoordinates());
			}
		});
	}
		
	//Handler for image being dragged
	public EventHandler getOnImageDraggedHandler() {
		return (event -> {
			System.out.println("On dragged (drag detected handler)");
			
			Circle circ = (Circle)event.getSource();
			Dragboard db = circ.startDragAndDrop(TransferMode.ANY);
			//put the image of the selected plant circle into the clipboard
			ClipboardContent content = new ClipboardContent();
			Image plantImage = ((ImagePattern)circ.getFill()).getImage();
			content.putImage(plantImage);
			db.setContent(content);
			//clear the circle if it already exits (if moving a current plant)
			if (gardenEditorView.hasChild((MouseEvent)event)) {
				circ.setFill(null);
			}
			//set selected plant image in the view 
			gardenEditorView.setSelectedPlantImage(plantImage);
			
			
			//is the plant in the plot
			Point pos = new Point(circ.getCenterX(), circ.getCenterY());
			int plotNum = GardenEditor.inPlot(pos, garden.getPlots());
			if(plotNum != -1) {
				//if the plant is in the plot make selected plant that plant
				GardenEditor.setSelectedPlant(garden.getPlant(plotNum, pos));
			} else {
				//set selected plant for the GardenEditor utility
				String plant = gardenEditorView.getPlantName(plantImage);
				GardenEditor.setSelectedPlant(plant, pos);
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
			
			//check for valid placement (right now only checks if in a plot)
			Point pos = new Point(drag.getX(), drag.getY());
			int plotNum = GardenEditor.inPlot(pos, garden.getPlots());
			if(plotNum == -1) {
				return;
			} 
			
			Plant selected = GardenEditor.getSelectedPlant();
			
			//get spread radius of the currently selected plant
			double radius = selected.getSpreadRadiusLower();
			//if radius is unknown use the lower size bound
			if(radius == 0) {
				radius =  selected.getSizeLower();
			}
			//place the plant in the garden in the view
			gardenEditorView.createNewImageInBase(drag,db, radius);
			
			//Check if the selected plant was from the recommended bar or if it was in a plot
			if(garden.isPlantInPlot(plotNum, selected)) {
				//plants in plot are removed and added back
				garden.removePlantFromPlot(plotNum, selected.getPosition());
				//add plant to plot ultimately also updates the position of selected to the new pos
				garden.addPlantToPlot(plotNum, pos, selected);
				
			} else {
				//plants from the recommended bar are cloned 
				//selected plant is the plant in the static allPlants hashmap
				Plant newPlant = selected.clone(); 
				selected.setPosition(new Point(0,0)); //reset the position of the plant in the allPlants list to be 0,0
				garden.addPlantToPlot(plotNum, pos, newPlant);
			}
			
			System.out.println(garden.getPlots().get(plotNum).getPlantsInPlot().size());
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

	public EventHandler SaveButtonClickedHandler() {
		return (event -> {
			System.out.println("Save Button clicked");
			
			try {
				// set the garden name
				TextField tmp = gardenEditorView.getGardenName();
				String name_g = tmp.getText();
				Garden tmp_g = garden;
				tmp_g.setName(name_g);
				savedGardens.add(tmp_g);
				gardenSaverLoader.saveGarden(savedGardens);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public EventHandler getLoadGardenViewOnClickHandler() {
	return (event -> {
			System.out.println("Load Screen button clicked");
		
			stage.setScene(loadSavedGardenView.getScene());
		});
	}
	
	public EventHandler loadSelectedGardenHandler() {
		return (event -> {
			ListView<String> tmp = loadSavedGardenView.getListView();
			String curr_g = tmp.getSelectionModel().getSelectedItem();
			garden = gardenSaverLoader.loadPickedGarden(curr_g, savedGardens);
			
			stage.setScene(gardenEditorView.getScene());
			System.out.println(garden.getPlots());
			double h = gardenEditorView.getCanvasHeight();
			double w = gardenEditorView.getCanvasWidth();
			double t = gardenEditorView.getTopHeight();
			double pixelsPerFoot = GardenEditor.transformPlots(garden.getPlots(), w, h, t, garden.getScale());
			garden.setScale(pixelsPerFoot);
			gardenEditorView.setScale(pixelsPerFoot);
			for (Plot p : garden.getPlots()) {
				//p.setCoordinates(p.filterCoords(20));
				//p.setCoordinates(GardenEditor.smooth(p.getCoordinates(), 0.3, 20));
				gardenEditorView.setFillColor(p.getOptions());
				gardenEditorView.drawPlot(p.getCoordinates());
				
				// iterate over the hashmap of plants from each plot and find the corresponding image
				// in the background loader. This is a weird way to do this.
				Iterator plant_itr = p.getPlantsInPlot().entrySet().iterator();
				
				while (plant_itr.hasNext()) {
					// get a map entry from the plot
					Map.Entry<Point, Plant> map_element = (Map.Entry<Point, Plant>)plant_itr.next();
					// used to determine plant image size in the plot
					double radius = map_element.getValue().getSpreadRadiusLower();
					// get position of plant
					Point tmp_pos = map_element.getValue().getPosition();
					// the image corresponding to the plot
					Image img_v = View.getImages().get(map_element.getValue().getScientificName());
					// method to add the image to the gardenEdtiorView base panel (draw duh plant)
					gardenEditorView.addPlantImageToBase(tmp_pos, img_v, radius);
				}
			}
			
		});
	}
	
	public EventHandler deleteSelectedGardenHandler() {
		return (event -> {
			System.out.println("delete button pushed");
			ListView<String> tmp = loadSavedGardenView.getListView();
			String curr_g = tmp.getSelectionModel().getSelectedItem();
			savedGardens = gardenSaverLoader.deleteGarden(curr_g, savedGardens);
			
			try {
				gardenSaverLoader.saveGarden(savedGardens);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			loadSavedGardenView = new LoadSavedGardenView(stage, savedGardens, this);
			//loadSavedGardenView.setObservableList(savedGardens);
			stage.setScene(loadSavedGardenView.getScene());
		});
	}
	
	public EventHandler fromLoadToHome() {
		return (event -> {
			System.out.println("from load to home");
			
			stage.setScene(startView.getScene());
		});
	}
}