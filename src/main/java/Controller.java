import java.util.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.concurrent.ConcurrentHashMap;




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
	LearnMoreView learnMoreView;
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
		View.setSize(stage.getWidth(), stage.getHeight());
		
		loadStartScreen();
		garden = new Garden();
	}
	
	public static void main(String[] args) {
		// BackgroundLoader loads the data and images in concurrently whilst showing a splash screen
		// It then goes to the start screen when it is finished
		 
		
		BackgroundLoader backgroundLoader = new BackgroundLoader("bkgloader", View.getImages(),View.getLepImages(), Garden.getAllPlants(), Garden.getAllLeps());
		
		System.out.println("Printing out all leps as a test");
		
		backgroundLoader.start();
		try {
			backgroundLoader.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		launch(args);	
	}
	
	
	/**
	 * Create views for all views in the program
	 * Load saved gardens
	 * Set the start screen to the main into screen
	 */
	public void loadStartScreen() {
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
		learnMoreView = new LearnMoreView(stage, this);
		
		//set the scene and model to Start
		stage.setScene(startView.getScene());
		
		//Populates reportView Plants List
		
	}
	
	public EventHandler getLearnMoreOnClickHandler() {
		return (event->{
			stage.setScene(learnMoreView.getScene());
		});
	}
	
	
	/**
	 * Returns the event handler for clicking the new garden button
	 * @return event handler
	 */
	public EventHandler getNewGardenOnClickHandler() {
		return (event -> {
			stage.setScene(plotDesignView.getScene());
			if(garden.getPlots().size() > 0) {
				for (Plot p : garden.getPlots()) {
					//change the coordinates in the current list to reflect the original coordinates
					//cannot just do p.setCoordinates because the point objects are still the same
					//need to deep copy the point values to the current list of coordinates
					ArrayList<Point> origCoords = p.getOriginalCoordinates();
					ArrayList<Point> currentCoords = new ArrayList<Point>();
					for(Point point:origCoords) {
		        		currentCoords.add(new Point(point.getX(), point.getY()));	
					}
					p.setCoordinates(currentCoords);
					plotDesignView.setFillColor(p.getOptions());
					plotDesignView.drawPlot(p.getOriginalCoordinates());
				}
			}
		});
	}
	
	/**
	 * Returns the event handler for shopping list button clicked
	 * @return event handler
	 */
	public EventHandler getToShoppingListOnClickHandler() {
		return (event -> {
			stage.setScene(shopView.getScene());
			shopView.setShoppingListData(garden.generateShoppingListData(), garden.getBudget());
		});
	}
	
	/**
	 * Returns the event handler for report button clicked
	 * @return event handler
	 */
	public EventHandler getToReportOnClickHandler() {
		return (event -> {
			stage.setScene(reportView.getScene());
		});
	}
	
	/**
	 * Returns the event handler for compare plants button clicked
	 * @return event handler
	 */
	public EventHandler getToCompareOnClickHandler() {
		return (event -> {
			stage.setScene(compPlantsView.getScene());
			
			compPlantsView.clearPlantList();
			for(String key: garden.getAllPlants().keySet()) {
				compPlantsView.setPlantList(garden.getAllPlants().get(key).getScientificName());
			}
		});
	}
	
	//TODO: JAAVADOX
	public void setUpGardenEditor() {
		//create list of plots for recommended plant list selection
		gardenEditorView.setPlotBoxes(garden.getPlots());

		//get the current canvas size
		double h = gardenEditorView.getCanvasHeight();
		double w = gardenEditorView.getCanvasWidth();
		//transform plots to and calculate scale 
		double pixelsPerFoot = GardenEditor.transformPlots(garden.getPlots(), w, h, garden.getScale());
		//set the new scale in both the model and the view
		garden.setScale(pixelsPerFoot);
		gardenEditorView.setScale(pixelsPerFoot);
		//smooth the plots in the model and fill them based on soil type
		for (Plot p : garden.getPlots()) {
			p.setCoordinates(p.filterCoords(5));
			p.setCoordinates(GardenEditor.smooth(p.getCoordinates(), 0.3, 20));
			gardenEditorView.setFillColor(p.getOptions());
			if (p.getPlantsInPlot().size() == 0) 
				gardenEditorView.drawPlot(p.getCoordinates(), null);
			else
				gardenEditorView.drawPlot(p.getCoordinates(), p.getPlantsInPlot());
		}
		//update leps supported
		gardenEditorView.updateGardenCounts(garden.getLepsSupported(), garden.getPlantsInGarden().size(), garden.getSpent());
		
		//Recommended plant list stuff
		if(garden.getPlots().get(0).getRecommendedPlants() == null) {
			//create recommended list for the first plot
			garden.getPlots().get(0).createRecommendedPlants();
		}
		//get the recommended plant list for plot 0 and set it as the Rec list for the garden
		HashMap<String, Plant> recommendedPlants  = garden.getPlots().get(0).getRecommendedPlants();
		garden.setRecommendedPlants(recommendedPlants);
		//make an array list of all the plant names for the view
		ArrayList<String> recommendedPlantNames = new ArrayList<String>();
		recommendedPlantNames.addAll(recommendedPlants.keySet());
		//set plant images based on list of plant names
		gardenEditorView.setPlantImages(recommendedPlantNames);
	}
	
	/**
	 * Returns the event handler for set dimensions button clicked
	 * @return event handler
	 */
	public EventHandler drawPlotGrid() {
		return (event->{
			double pixelsPerFoot = plotDesignView.drawGrid();
			garden.setScale(pixelsPerFoot);
			//draw plots if there are any
			if(garden.getPlots() != null && garden.getPlots().size() > 0) {
				for (Plot plot : garden.getPlots()) {
					plotDesignView.setFillColor(plot.getOptions());
					plotDesignView.drawPlot(plot.getCoordinates());
				}
			}
		});
	}
	
	/**
	 * Returns the change listener for plot design height change
	 * @return change listener
	 */
	public ChangeListener<Object> getPDHeightChangeListener() {
		return ((observable, oldValue, newValue) -> {
				plotDesignView.heightChanged(newValue);
				double pixelsPerFoot = plotDesignView.drawGrid();
				garden.setScale(pixelsPerFoot);
		});
	}
	

	/**
	 * Returns the change listener for plot design width change
	 * @return change listener
	 */
	public ChangeListener<Object> getPDWidthChangeListener() {
		return ((observable, oldValue, newValue) -> {
				plotDesignView.widthChanged(newValue);
				double pixelsPerFoot = plotDesignView.drawGrid();
				garden.setScale(pixelsPerFoot);
		});
	}

	/**
	 * Returns the event handler for draw plot button in plotdesignview
	 * @return event handler
	 */
	public EventHandler getDrawPlotHandler() {
		return (event -> {
			//get Options values and create a new plot with these options
			double sunlight = plotDesignView.getSunlightSlider();
			double soiltype = plotDesignView.getSoilSlider();
			double moisture = plotDesignView.getMoistureSlider();
			Options o = new Options(soiltype, sunlight, moisture);
			//create a new plot in the garden
			garden.newPlot(o);
			//allow drawing and show the redraw button
			plotDesignView.allowDrawing();
			plotDesignView.showRedrawButton();
		});
	}
	
	/**
	 * TODO 
	 */
	public EventHandler getRedrawPlotHandler() {
		return (event -> {
			//let the user redraw a plot with whatever options are currently selected
			double sunlight = plotDesignView.getSunlightSlider();
			double soiltype = plotDesignView.getSoilSlider();
			double moisture = plotDesignView.getMoistureSlider();
			Options o = new Options(soiltype, sunlight, moisture);
			//don't create a new plot, just reset the coordinates
			int plotIndex = garden.getNumPlots() - 1;
			garden.getPlots().get(plotIndex).setCoordinates(new ArrayList<Point>());
			garden.getPlots().get(plotIndex).setOptions(o);
			//allow drawing
			plotDesignView.allowDrawing();
			
			//redraw the current plots without the one you want to redraw
			plotDesignView.drawGrid();
			for (Plot plot : garden.getPlots()) {
				plotDesignView.setFillColor(plot.getOptions());
				plotDesignView.drawPlot(plot.getCoordinates());
			}
		});
	}

	/**
	 * Returns the event handler for initiating drawing a plot
	 * @return event handler
	 */
	public EventHandler getDrawPlotDragDetected() {
		return (event->{
        	System.out.println("Draw Plot Mouse Pressed");
			MouseEvent me = (MouseEvent)event;
			plotDesignView.startDrawingPlot(me);
			
        });
	}
	
	/**
	 * Returns the event handler for while the plot is being drawn, while mouse is being dragged
	 * @return event handler
	 */
	public EventHandler getDrawPlotDragged() {
		return (event->{
			System.out.println("Draw Plot Mouse Dragged");
			MouseEvent me = (MouseEvent)event;
			plotDesignView.drawPlot(me);
        });
	}
	
	/**
	 * Returns the event handler for when the plot is done drawing. On drag complete
	 * @return event handler
	 */	
	public EventHandler getOnDrawPlotDone() {
		return (event->{
			System.out.println("Draw Plot Mouse Released");
			MouseEvent me = (MouseEvent)event;
        	
        	//add coords to the plot in the garden
			if(plotDesignView.getCanDraw()) {
	        	garden.addCoordsToPlot(plotDesignView.getCoords());
	        	//save the coordinates from the plot design view in a new list of original coordinates
	        	//need to do a deep copy of the point objects in the PDV list to prevent referencing the same object
	        	ArrayList<Point> origCoords = new ArrayList<Point>();
	        	for (Point p : plotDesignView.getCoords()) {
	        		origCoords.add(new Point(p.getX(), p.getY()));
	        	}
	        	garden.getPlots().get(garden.getPlots().size()-1).setOriginalCoordinates(origCoords);
	        	//set fill color based on soil type
	        	int plotIndex = garden.getNumPlots() - 1;
	        	plotDesignView.setFillColor(garden.getPlotOptions(plotIndex));
	        	//fill the plot
	        	plotDesignView.fillPlot(me);
			}
        });
	}
	
	/** TODO JAVADOC REWRITE
	 * Returns the event handler for the to garden button in plotdesignview
	 * Transform plots to fit in the gardeneditor, set a new scale, smooth the newly scaled plots
	 * Update information in right bar with garden info and send a recommended plant list based on plot
	 * @return event handler
	 */
	public EventHandler getToGardenOnClickHandler() {
		return (event -> {
			//set the budget in the garden to the current budget in the plotdesignview
			garden.setBudget(plotDesignView.getBudget());
			//set new scene to gardeneditorview
			stage.setScene(gardenEditorView.getScene());
			
			//update budget in the view
			gardenEditorView.setBudget(garden.getBudget());
			
			//i need to clear the garden editor first
			gardenEditorView.clearCanvas();
			//

			setUpGardenEditor();
			
		});
	}
		
	public EventHandler getToGardenOnClickHandler2() {
		return (event -> {
			stage.setScene(gardenEditorView.getScene());
			
		});
	}
	/**
	 * Returns the event handler for clicking an image in the garden editor
	 * Updates plant info in gardeneditorview
	 * @return event handler
	 */
	public EventHandler getOnImageEnteredInfo() {
		return (event-> {
			System.out.println("In Image Clicked on Handler");
			Circle plantCirc = (Circle)event.getSource();
			Image plantImage = ((ImagePattern)plantCirc.getFill()).getImage();
			gardenEditorView.setPlantInfoImage(plantImage);
			String plant = gardenEditorView.getPlantName(plantImage);
			Plant selectedPlant = garden.getPlant(plant);
			/*
			ConcurrentHashMap <String, Set<Lep>> allLeps = Garden.getAllLeps();
			for(String key : allLeps.keySet()) {
				System.out.println(allLeps.get(key).toString());
			}
			*/
			System.out.println("Printing plant value: " + plant);
			System.out.println(garden.getAllLeps().get(plant));
			gardenEditorView.setPlantInfo(selectedPlant,garden.getAllLeps().get(plant));
			
		});
	}
	
	
	/**
	 * Returns the event handler for initiating dragging a plant from the recommended bar to a plot
	 * @return event handler
	 */
	public EventHandler getOnImageDraggedHandler() {
		return (event -> {
			System.out.println("On dragged (drag detected plant image handler)");
			
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
			int plotNum = GardenEditor.inPlot(pos, garden.getPlots(), gardenEditorView.getTopBar(), gardenEditorView.getLeftBar());
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

	/**
	 * Returns the event handler for while the plant/image is being dragged
	 * @return event handler
	 */
	public EventHandler getOnDragOverHandler() {
		return (event -> {
			System.out.println("On drag over (while dragging image)");
			((DragEvent) event).acceptTransferModes(TransferMode.ANY);
			event.consume();
		});
	}
	
	/**
	 * Returns the event handler for when the image is dropped, drag complete
	 * @return event handler
	 */
	public EventHandler getOnDragDroppedHandler() {
		return (event -> {
			System.out.println("On drag dropped (drop plant image)");
			DragEvent drag = (DragEvent) event;
			Dragboard db = drag.getDragboard();
			
			//check for valid placement (right now only checks if in a plot)
			Point pos = new Point(drag.getX(), drag.getY());
			int plotNum = GardenEditor.inPlot(pos, garden.getPlots(), gardenEditorView.getTopBar(), gardenEditorView.getLeftBar());
			if(plotNum == -1) { 
				
				Plant plant = GardenEditor.getSelectedPlant();
				plotNum = GardenEditor.inPlot(plant.getPosition(), garden.getPlots(), gardenEditorView.getTopBar(), gardenEditorView.getLeftBar());
				garden.removePlantFromPlot(plotNum,plant.getPosition());
				gardenEditorView.updateGardenCounts(garden.getLepsSupported(), garden.getPlantsInGarden().size(), garden.getSpent());
				drag.consume();
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
			gardenEditorView.updateGardenCounts(garden.getLepsSupported(), garden.getPlantsInGarden().size(), garden.getSpent());
			System.out.println(garden.getPlots().get(plotNum).getPlantsInPlot().size());

			drag.setDropCompleted(true);
			drag.consume();
		});
	}
	
	/**
	 * Returns event handler for changing selection of plots in the checkbox
	 * This method updates the recommended plants list in the view based on which boxes are checked
	 * @return event handler
	 */
	public EventHandler getSelectPlotCheckboxHander() {
		return (event -> {
			int plotIndex = gardenEditorView.getPlotIndex(event);
			ArrayList<Integer> plotSelections = gardenEditorView.getSelections();
			//Construct the recommended plant list if this plot does not have one already
			if(garden.getPlots().get(plotIndex).getRecommendedPlants() == null) {
				garden.getPlots().get(plotIndex).createRecommendedPlants();
			}
			//new blank recommendedPlant list
			HashMap<String, Plant> recommendedPlants = new HashMap<String, Plant>();
			int index = 0; //index of plot as it iterates thru them
			for(Integer selection : plotSelections) {
				//if the checkbox is checked, add the recommended plants for this plot to the list
				if (selection == 1)
					recommendedPlants.putAll(garden.getPlots().get(index).getRecommendedPlants());
				index++;
			}
			//set the recommended plant list for the garden
			garden.setRecommendedPlants(recommendedPlants);
			//set the final list of recommended plants in the view based on the selected checkboxes
			ArrayList<String> plantNames = new ArrayList<String>();
			plantNames.addAll(recommendedPlants.keySet());
			gardenEditorView.setPlantImages(plantNames);
		});
	}
	
	/**
	 * Returns the change listener that activates when an item is selected in the sort by dropdown
	 * This method sorts the recommended plants based on the selected option
	 * @return ChangeListener
	 */
	public ChangeListener getSortByHandler() {
		return ((ov, t, t1) -> {
			//get the hashmap of recommended plants from the model
			HashMap<String, Plant> recommendedPlants = garden.getRecommendedPlants();
			//sort the plans based on the chosen option, returns the list of sorted names
			ArrayList<String> recommendedPlantNames = GardenEditor.sortRecommendedPlants(recommendedPlants, (String)t1);
			//use the list of sorted names to display the newly sorted list of recommended plants
			gardenEditorView.setPlantImages(recommendedPlantNames);
		});
	}
	
	/**
	 * Event handler that gets text from search bar in compPlantsView, searches for plant from all loaded plants in model,
	 * and sends info gotten from compPlants to compPlantsView on button Click (Right Button Click).
	 * @param None
	 * @return None
	 */
	public EventHandler RightPlantButtonClickedHandler() {
		return (event ->{
			
			//compPlantsView.setBLeps(compPlants.getLeps());
			ListView<String> tempList = compPlantsView.getplantList();
			//TextField temp = compPlantsView.getTextBox();
			String plantInfo = "Plant A\n";		
			plantInfo = plantInfo + CompPlants.getInfo(tempList.getSelectionModel().getSelectedItem());
			Text tempText = compPlantsView.getRightBody();
			//tempText.setText(plantInfo);
			tempText.setText(tempList.getSelectionModel().getSelectedItem());
			
			//Sets right image
			compPlantsView.setRightImage(tempList.getSelectionModel().getSelectedItem());
			//Setting plant A variables in compPlantView to plantInfo
			
			//compPlantsView.setALeps(CompPlants.getLepInfo(temp.getText()));
			//compPlantsView.setAName(temp.getText());

		});
		
	}
	
	/**
	 * Event handler that gets text from search bar in compPlantsView, searches for plant from all loaded plants in model,
	 * and sends info gotten from compPlants to compPlantsView on button Click (Left Button Click).
	 * @param None
	 * @return None
	 */
	public EventHandler LeftPlantButtonClickedHandler() {
		return (event ->{
			ListView<String> tempList = compPlantsView.getplantList();
			//TextField temp = compPlantsView.getTextBox();
			String plantInfo = "Plant B\n";		
			plantInfo = plantInfo + CompPlants.getInfo(tempList.getSelectionModel().getSelectedItem());
			//String plantInfo = CompPlants.getInfo(temp.getText());
			Text tempText = compPlantsView.getLeftBody();
			//tempText.setText(plantInfo);
			tempText.setText(tempList.getSelectionModel().getSelectedItem());
			//Sets left image
			compPlantsView.setLeftImage(tempList.getSelectionModel().getSelectedItem());

			//Setting plant B variables in compPlantView to plantInfo
			
			//compPlantsView.setBLeps(CompPlants.getLepInfo(temp.getText()));
			//compPlantsView.setBName(temp.getText());
		});
		
	}
	
	
	/**
	 * Event handler for listView in compPlantView. 
	 * On list click, this handler finds the element clicked on, gets the coresponding information from CompPlants, 
	 * and sends the gotten info to compPlantsView.
	 * @param None
	 * @return None
	 */
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
		});
	}
	

	//TODO : javadoc
	public EventHandler getDisplayGridlinesHandler() {
		return (event ->{
			//change the value of showGridLines in plotdesignview
			plotDesignView.flipShowGridLines();
			//clear canvas / calculate scale / draw grid lines if applicable
			plotDesignView.drawGrid();
			//draw all current plots
			for (Plot plot : garden.getPlots()) {
				plotDesignView.setFillColor(plot.getOptions());
				plotDesignView.drawPlot(plot.getCoordinates());
			}
		});
	}
	
	
	/**
	 * Called from the Garden Editor View when the user clicks the save button.
	 * creates a copy of the current garden and if a unique name is given it will save the
	 * current garden as a unique entry in the list of saved gardens but if no name or the same
	 * name is given it will overwrite the loaded garden.
	 * 
	 * @param none
	 * @return event - creates and saves copy garden.
	 * */
	public EventHandler SaveButtonClickedHandler() {
		return (event -> {
			System.out.println("Save Button clicked");
			
			try {
				// set the garden name
				TextField tmp = gardenEditorView.getGardenName();
				String name_g = tmp.getText();
				int tmp_leps = garden.getLepsSupported();
				double tmp_spent = garden.getSpent();
				double tmp_budget = garden.getBudget();
				double tmp_scale = garden.getScale();
				ArrayList<Plot> tmp_plots = garden.getPlots();
				ArrayList<Plant> tmp_plants = garden.getPlantsInGarden();
				
				if(name_g == "") {
					//System.out.println("empty name");
					Garden tmp_g = new Garden(garden.getName(), tmp_spent, tmp_budget, tmp_plots, tmp_leps, tmp_plants, tmp_scale);
					savedGardens.add(tmp_g);
					gardenSaverLoader.deleteGarden(garden.getName(), savedGardens);
					gardenSaverLoader.saveGarden(savedGardens);
					
				} else {
					Garden tmp_g = new Garden(name_g, tmp_spent, tmp_budget, tmp_plots, tmp_leps, tmp_plants, tmp_scale);
					savedGardens.add(tmp_g);
					gardenSaverLoader.saveGarden(savedGardens);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				savedGardens = gardenSaverLoader.loadGardenList();
				for(Garden g : savedGardens) {
					System.out.println(g.getName());
					for(Plant p : g.getPlantsInGarden())
						System.out.println(p.getScientificName());
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Sets the scene to the load menu when the Load Garden button is clicked
	 * on the home screen.
	 * 
	 * @param none
	 * @return event - loads the view for the load screen
	 * */
	public EventHandler getLoadGardenViewOnClickHandler() {
		return (event -> {
			loadSavedGardenView = new LoadSavedGardenView(stage, savedGardens, this);
		
			stage.setScene(loadSavedGardenView.getScene());
		});
	}
	
	
	/**
	 * Loads the garden picked by the user on the load menu. Uses the string highlighted
	 * in the ListView to find the garden in list of saved gardens. Once it has the garden
	 * it draws the plots in the garden and every plant in each plot in the Garden Editor
	 * 
	 * @param none
	 * @return event - loads and displays the chosen garden.
	 * */
	public EventHandler loadSelectedGardenHandler() {
		return (event -> {
			ListView<String> tmp = loadSavedGardenView.getListView();
			String curr_g = tmp.getSelectionModel().getSelectedItem();
			garden = gardenSaverLoader.loadPickedGarden(curr_g, savedGardens);
			
			stage.setScene(gardenEditorView.getScene());
			
			setUpGardenEditor();
		});
	}
	
	/**
	 * Deletes the garden selected by the user in the load menu. Uses the string from the listView
	 * to find the right garden, removes it from the arrayList of saved gardens, and then saves the arraylist
	 * to overwrite the old list.
	 * 
	 * @param none
	 * @return event - deletes the selected garden
	 * */
	
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
	
	/**
	 * Takes the user from the load menu to the home screen if they click the back button.
	 * 
	 * @param - none
	 * @return event - sets the stage back to the home view.
	 * */
	public EventHandler fromLoadToHome() {
		return (event -> {
			System.out.println("from load to home");
			
			stage.setScene(startView.getScene());
		});
	}
	
	
	/**
	 * Handles the report generation when the generate report button is clicked in ReportView.
	 * This will send different pieces of information to reportView depending on the reportView checkBoxes. 
	 * @param None
	 * @return None
	 */
	public EventHandler generateReportHandler() {
		return(event -> {
			System.out.println("Generate report button pushed");
			//Gets vlaues of checkboxes
			boolean perennialDiversityOptionFlag = reportView.getPerennialDiversityOption().isSelected();
			boolean budgetFlag = reportView.getBudgetOption().isSelected();
			//boolean tableFlag = reportView.get
			
			if(perennialDiversityOptionFlag) {
			HashMap<String, PlantShoppingListData> tempGardenData = garden.generateShoppingListData();
			
			//Adds plant info to reportGardenPieGraph
			Iterator itr = tempGardenData.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry element = (Map.Entry)itr.next();
				PlantShoppingListData v = (PlantShoppingListData)element.getValue();
				reportView.addItemToPieGraph(v.getCommonName(),v.getCount());
			}
			
			reportView.addGardenPieGraph();
			}
			if(budgetFlag) {
				reportView.addBudgetBox(garden.getSpent(),garden.getBudget());
			}
			reportView.showReport();
			
		});
		
	}
	/**
	 * Event Handler for opening a webpage from Learn More Hyperlinks
	 * @return event
	 */
	public EventHandler onClickedWebpageHandler() {
		return (event -> {
			learnMoreView.launchWebpage(event.getSource());
		});
	}
	
	public EventHandler lepPopUpHandler() {
		return (event->{
			gardenEditorView.createLepPopUp(event.getSource());
		});
	}
	
	public EventHandler closePopUp() {
		return (event->{
			gardenEditorView.closeLepWindow();
		});
	}
}