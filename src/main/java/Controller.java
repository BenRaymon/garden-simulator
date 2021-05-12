import java.util.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
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
		BackgroundLoader backgroundLoader = new BackgroundLoader("bkgloader", View.getImages(),View.getLepImages(), Garden.getAllPlants(), Garden.getLepsByPlant(), Garden.getAllLeps());
				
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
	 * Set the start screen to the main intro screen
	 */
	public void loadStartScreen() {
		try {
			savedGardens = gardenSaverLoader.loadGardenList();
			System.out.println("Load success");
		} catch (ClassNotFoundException e) {
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
	
	/**
	 * Returns the event Handler for clicking the learn more button
	 * When activated, this method opens the learn more page
	 * @return event handler
	 */
	public EventHandler getLearnMoreOnClickHandler() {
		return (event->{
			stage.setScene(learnMoreView.getScene());
		});
	}
	
	
	/**
	 * Returns the event handler for clicking the "plot design" or "new garden" buttons
	 * When activated, this method opens the plot designer page
	 * @return event handler
	 */
	public EventHandler getToPlotDesignHandler() {
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
	 * Returns the event handler for the "to garden" button
	 * When activated, this method prepares the garden for 
	 * the garden editor, draws the plots and sends the user 
	 * to the garden editor page
	 * 
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
			//clear the current canvas (prevent redrawing on top of the previous drawings)
			gardenEditorView.clearCanvas();
		
			setUpGardenEditor();
		});
	}
		
	/** 
	 * Returns the event handler for going to the garden without redrawing everything
	 * When activated, this method sends the user to the current garden editor page
	 * @return event handler
	 */
	public EventHandler getToGardenOnClickHandler2() {
		return (event -> {
			stage.setScene(gardenEditorView.getScene());
			
		});
	}
	
	/**
	 * Returns the event handler for shopping list button clicked
	 * When activated, this method opens the shopping list page
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
	 * When activated, this method opens the report page
	 * @return event handler
	 */
	public EventHandler getToReportOnClickHandler() {
		return (event -> {
			reportView = new ReportView(stage, this);
			stage.setScene(reportView.getScene());
			
		});
	}
	
	/**
	 * Returns the event handler for compare plants button clicked
	 * When activated, this method opens the compare plants page
	 * @return event handler
	 */
	public EventHandler getToCompareOnClickHandler() {
		return (event -> {
			compPlantsView.setPlantImages(garden.getRecPlantNames());
			
			stage.setScene(compPlantsView.getScene());
			
			//compPlantsView.clearPlantList();
//			for(String key: garden.getAllPlants().keySet()) {
//				compPlantsView.setPlantList(garden.getAllPlants().get(key).getScientificName());
//			}
		});
	}
	
	/**
	 * Sets up the model and the view for the garden editor page.
	 * 
	 * Resizes plots to fill the garden editor canvas, 
	 * smoothes the scaled plots, sets a value for 
	 * pixels per foot (scale), draws the plots,
	 * updates the in-garden counts, and sets the recommended plant list
	 */
	public void setUpGardenEditor() {
		//create list of plots for recommended plant list selection
		gardenEditorView.setPlotBoxes(garden.getPlots());

		//get the current canvas size
		double h = gardenEditorView.getCanvasHeight();
		double w = gardenEditorView.getCanvasWidth();
		//transform plots to and calculate scale based on the available canvas size
		double pixelsPerFoot = GardenEditor.transformPlots(garden.getPlots(), w, h, garden.getScale());
		//set the new scale in both the model and the view
		garden.setScale(pixelsPerFoot);
		gardenEditorView.setScale(pixelsPerFoot);
		//smooth the plots in the model and fill them based on soil type
		for (Plot p : garden.getPlots()) {
			p.setCoordinates(p.filterCoords(5));
			p.setCoordinates(GardenEditor.smooth(p.getCoordinates(), 0.3, 20));
			gardenEditorView.setFillColor(p.getOptions());
			//draw the plot in the view (and draw the plants in the plot if applicable) 
			if (p.getPlantsInPlot().size() == 0) 
				gardenEditorView.drawPlot(p.getCoordinates(), null);
			else
				gardenEditorView.drawPlot(p.getCoordinates(), p.getPlantsInPlot());
			// Calculate the plot boundaries for later use
			p.calculatePlotBoundaries();
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
	 * When activated, this method sets the scale of the garden based
	 * on the dimension inputs. A grid is drawn with the user's specifications
	 * 
	 * @return event handler
	 */
	public EventHandler getSetDimensionsClickedHandler() {
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
	 * When activated, this method sets a new scale based on the resulting height
	 * and redraws the grid to fit in the new canvas
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
	 * When activated, this method sets a new scale based on the resulting width
	 * and redraws the grid to fit in the new canvas
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
	 * When activated, this method creates a new plot in the garden with the 
	 * user specified soil, sunlight and moisture. 
	 * The canvas in the plotdesignview is "unlocked" so the user can draw
	 * @return event handler
	 */
	public EventHandler getDrawPlotHandler() {
		return (event -> {
			// disable buttons
			plotDesignView.disableButtons();
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
	 * Returns the event handler for clicking the redraw button
	 * When activated, this method erases the current plot and 
	 * lets the user redraw the plot. 
	 * @return event handler
	 */
	public EventHandler getRedrawPlotHandler() {
		return (event -> {
			// turn off the buttons
			plotDesignView.disableButtons();
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
	 * This method is activated when the user starts to drag 
	 * their mouse on the canvas. Begins a path and starts recording 
	 * coordinates for the plot boundaries
	 * @return event handler
	 */
	public EventHandler getDrawPlotDragDetected() {
		return (event->{
			MouseEvent me = (MouseEvent)event;
			plotDesignView.startDrawingPlot(me);
        });
	}
	
	/**
	 * Returns the event handler for while the plot is being drawn
	 * This method is activated while the user is dragging their mouse.
	 * When activated, coordinates for the plot are recorded
	 * @return event handler
	 */
	public EventHandler getDrawPlotDragged() {
		return (event->{
			MouseEvent me = (MouseEvent)event;
			plotDesignView.drawPlot(me);
        });
	}
	
	/**
	 * Returns the event handler for when the plot is done drawing.
	 * This method is activated after the user finishes drawing a plot,
	 * when the mouse/drag is released. The plot coordinates get 
	 * added to the garden and the final plot shape is filled in
	 * @return event handler
	 */	
	public EventHandler getOnDrawPlotDone() {
		return (event->{
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
			plotDesignView.enableButtons();
        });
	}
	
	
	/**
	 * Returns the event handler for selecting a plant in the garden editor
	 * This method is activated when a user clicks on or hovers over a plant image
	 * When activated, this method selects a plant to show information about in the view
	 * 
	 * @return event handler
	 */
	public EventHandler getOnImageEnteredInfo() {
		return (event-> {
			String plant = gardenEditorView.getPlantName(event);
			Plant selectedPlant = Garden.getPlant(plant);
			//update the left info bar
			gardenEditorView.setPlantInfo(selectedPlant,Garden.getLepsByPlant().get(plant), event);
			
		});
	}
	
	
	/**
	 * Returns the event handler for initiating drag and drop of a plant
	 * This method is activated when the user begins dragging a plant within the garden editor
	 * When activated, a plant is selected either from the recommended bar or within a plot
	 * and the drag and drop action is started
	 * @return event handler
	 */
	public EventHandler getOnImageDraggedHandler() {
		return (event -> {
			
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
	 * This method is activated while the user is dragging a plant in the garden editor
	 * @return event handler
	 */
	public EventHandler getOnDragOverHandler() {
		return (event -> {
			((DragEvent) event).acceptTransferModes(TransferMode.ANY);
			event.consume();
		});
	}
	
	/**
	 * Returns the event handler for when the image is dropped
	 * This method is activated when the user releases their mouse
	 * and the drag and drop is completed. 
	 * When activated, this method adds the plant to the 
	 * plot it belongs to (or removes the plant from the garden)
	 * @return event handler
	 */
	public EventHandler getOnDragDroppedHandler() {
		return (event -> {
			DragEvent drag = (DragEvent) event;
			Dragboard db = drag.getDragboard();
			
			//check for valid placement (right now only checks if in a plot)
			Point pos = new Point(drag.getX(), drag.getY());
			int plotNum = GardenEditor.inPlot(pos, garden.getPlots(), gardenEditorView.getTopBar() + MenuBox.MENU_HEIGHT, gardenEditorView.getLeftBar());
			if(plotNum == -1) { 
				Plant plant = GardenEditor.getSelectedPlant();
				plotNum = GardenEditor.inPlot(plant.getPosition(), garden.getPlots(), gardenEditorView.getTopBar() + MenuBox.MENU_HEIGHT, gardenEditorView.getLeftBar());
				garden.removePlantFromPlot(plotNum,plant.getPosition());
				gardenEditorView.updateGardenCounts(garden.getLepsSupported(), garden.getPlantsInGarden().size(), garden.getSpent());
				drag.consume();
				return;
			} 
			
			Plant selected = GardenEditor.getSelectedPlant();
			Point posOfSelectedPlant = selected.getPosition();
			int plotNumOfSelected = GardenEditor.inPlot(posOfSelectedPlant, garden.getPlots(), gardenEditorView.getTopBar(), gardenEditorView.getLeftBar());
			
			//get spread radius of the currently selected plant
			double radius = selected.getSpreadRadiusLower();
			//if radius is unknown use the lower size bound
			if(radius == 0) {
				radius =  selected.getSizeLower();
			}
			
			//Check if the selected plant was from the recommended bar or if it was in a plot
			if(garden.isPlantInPlot(plotNumOfSelected, selected)) {
				if (GardenEditor.isPlantWithinPlot(radius, garden.getScale(), garden.getPlots().get(plotNum))) {
					if (GardenEditor.canPlantBePlaced(garden.getScale(), pos, posOfSelectedPlant, radius, garden.getPlots().get(plotNum))) {
						//plants in plot are removed and added back
						garden.removePlantFromPlot(plotNumOfSelected, selected.getPosition());
						//add plant to plot ultimately also updates the position of selected to the new pos
						garden.addPlantToPlot(plotNum, pos, selected);
						//place the plant in the garden in the view
						gardenEditorView.createNewImageInBase(drag,db, radius);
					} else {
						System.out.println("Plant is too close to another");
						gardenEditorView.plantRadiusOverlapPopUp();
						//clear canvas and draw the plants again
						gardenEditorView.clearCanvas();
						for (Plot p : garden.getPlots()) {
							gardenEditorView.drawPlants(p.getPlantsInPlot());
						}
					}
				} else {
					System.out.println("Plant cannot fit into this plot, too large");
					gardenEditorView.plantTooBigPopUp();
				}
			} else {
				if (GardenEditor.isPlantWithinPlot(radius, garden.getScale(), garden.getPlots().get(plotNum))) {
					if (GardenEditor.canPlantBePlaced(garden.getScale(), pos, null, radius, garden.getPlots().get(plotNum))) {
						//plants from the recommended bar are cloned 
						//selected plant is the plant in the static allPlants hashmap
						Plant newPlant = selected.clone(); 
						selected.setPosition(new Point(0,0)); //reset the position of the plant in the allPlants list to be 0,0
						garden.addPlantToPlot(plotNum, pos, newPlant);
						//place the plant in the garden in the view
						gardenEditorView.createNewImageInBase(drag,db, radius);
					} else {
						System.out.println("Plant is too close to another");
						gardenEditorView.plantRadiusOverlapPopUp();
					}
				} else {
					System.out.println("Plant cannot fit into this plot, too large");
					gardenEditorView.plantTooBigPopUp();
				}	
			}
			gardenEditorView.updateGardenCounts(garden.getLepsSupported(), garden.getPlantsInGarden().size(), garden.getSpent());
			

			drag.setDropCompleted(true);
			drag.consume();
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
	 * Returns the change listener that activates when a plot is selected in the plots dropdown
	 * This method changes the recommended plant list to be based on the selected plot
	 * @return ChangeListener
	 */
	public ChangeListener getPlotSelectHandler() {
		return ((ov, t, t1) -> {
			//get the plot index from the string
			int plotIndex = Integer.parseInt("" + t1.toString().charAt(t1.toString().length() - 1)) - 1;
			//Construct the recommended plant list if this plot does not have one already
			if(garden.getPlots().get(plotIndex).getRecommendedPlants() == null) {
				garden.getPlots().get(plotIndex).createRecommendedPlants();
			}
			//recommended plant list of the selected plot
			HashMap<String, Plant> recommendedPlants = garden.getPlots().get(plotIndex).getRecommendedPlants();
			//set the recommended plant list for the garden
			garden.setRecommendedPlants(recommendedPlants);
			//set the final list of recommended plants in the view based on the selected plot
			ArrayList<String> plantNames = new ArrayList<String>();
			plantNames.addAll(recommendedPlants.keySet());
			gardenEditorView.setPlantImages(plantNames);
		});
	}
	
	/**
	 * Returns the event handler that sets the right-hand-side plant for comparing plants
	 * @return event handler
	 */
	public EventHandler RightPlantButtonClickedHandler() {
		return (event ->{
			String plant = compPlantsView.getSelectedPlant();

			String plantInfo = "Plant B\n";		
			plantInfo = plantInfo + CompPlants.getInfo(plant);

			compPlantsView.setRightBody(plant);
			compPlantsView.setRightImage(plant);
		});
		
	}
	
	/**
	 * Returns the event handler that sets the left-hand-side plant for comparing plants
	 * @return event handler
	 */
	public EventHandler LeftPlantButtonClickedHandler() {
		return (event ->{
			String plant = compPlantsView.getSelectedPlant();

			String plantInfo = "Plant A\n";		
			plantInfo = plantInfo + CompPlants.getInfo(plant);

			compPlantsView.setLeftBody(plant);
			compPlantsView.setLeftImage(plant);
		});
		
	}
	
	
	/**
	 * Returns the Event handler for comparison dropdown in compPlantView. 
	 * On list click, this handler finds the element clicked on, 
	 * gets the corresponding information from CompPlants, and sends the gotten info to compPlantsView.
	 * @return event handler
	 */
	public EventHandler listClickedHandler() {
		return(event ->{
			ListView<String> tempList = compPlantsView.getListView();
			String currentItem = tempList.getSelectionModel().getSelectedItem();
			
			if(currentItem == "Lep Compare") {
				String tempPlantAName = compPlantsView.getLeftBody();
				String tempPlantBName = compPlantsView.getRightBody();
				
				compPlantsView.setLepCompare(Garden.getPlant(tempPlantAName), Garden.getPlant(tempPlantBName));
			}
			else if(currentItem == "Radius Compare") {
				String tempPlantAName = compPlantsView.getLeftBody();
				String tempPlantBName = compPlantsView.getRightBody();
				
				compPlantsView.setRadiusCompare(Garden.getPlant(tempPlantAName), Garden.getPlant(tempPlantBName));
			}
			else if(currentItem == "Size Compare") {
				String tempPlantAName = compPlantsView.getLeftBody();
				String tempPlantBName = compPlantsView.getRightBody();
				
				compPlantsView.setSizeCompare(Garden.getPlant(tempPlantAName), Garden.getPlant(tempPlantBName));

			}
			else if(currentItem == "General Info") {
				String plantAName = compPlantsView.getLeftBody();
				String plantBName = compPlantsView.getRightBody();
				compPlantsView.setGeneralInfoComapre(Garden.getPlant(plantAName),Garden.getPlant(plantBName));				
			}			
		});
	}
	

	/**
	 * Returns the event handler for clicking the show / hide gridlines button
	 * When activated, this method toggles the gridlines being shown
	 * @return ChangeListener
	 */
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
	 * Returns the event handler for clicking on the save garden button
	 * This method creates a copy of the current garden and if a unique name is given 
	 * it will save the current garden as a unique entry in the list of saved gardens. 
	 * If the same name or no name is given it will overwrite the loaded garden.
	 *
	 * @return event handler
	 */
	public EventHandler SaveButtonClickedHandler() {
		return (event -> {
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
				e.printStackTrace();
			}
			
			try {
				savedGardens = gardenSaverLoader.loadGardenList();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Returns the event handler for clicking the load 
	 * garden button in the main screen. 
	 * When activated, this method sends the user 
	 * to the load gardens page
	 * 
	 * @return event handler
	 */
	public EventHandler getLoadGardenViewOnClickHandler() {
		return (event -> {
			loadSavedGardenView = new LoadSavedGardenView(stage, savedGardens, this);
			
			stage.setScene(loadSavedGardenView.getScene());
			
		});
	}
	
	
	/**
	 * Returns the event handler for selecting a garden to load.
	 * When activated, this method loads the garden picked by the user 
	 * on the load menu. The string highlighted in the ListView is used to 
	 * find the garden in list of saved gardens. Once it has the garden
	 * it draws the plots in their plants in the garden editor
	 * 
	 * @return event
	 */
	public EventHandler loadSelectedGardenHandler() {
		return (event -> {
			ListView<String> tmp = loadSavedGardenView.getListView();
			String curr_g = tmp.getSelectionModel().getSelectedItem();
			garden = gardenSaverLoader.loadPickedGarden(curr_g, savedGardens);
			gardenEditorView.clearCanvas();
			stage.setScene(gardenEditorView.getScene());
			
			setUpGardenEditor();
		});
	}
	
	/**
	 * Returns the event handler for deleting a garden. 
	 * When activated, this method deletes the garden selected 
	 * by the user in the load menu. Uses the string from the listView
	 * to find the right garden, removes it from the arrayList of saved 
	 * gardens, and then saves the arraylist to overwrite the old list.
	 *
	 * @return event
	 */
	public EventHandler deleteSelectedGardenHandler() {
		return (event -> {
			ListView<String> tmp = loadSavedGardenView.getListView();
			String curr_g = tmp.getSelectionModel().getSelectedItem();
			savedGardens = gardenSaverLoader.deleteGarden(curr_g, savedGardens);
			
			try {
				gardenSaverLoader.saveGarden(savedGardens);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			loadSavedGardenView = new LoadSavedGardenView(stage, savedGardens, this);
			//loadSavedGardenView.setObservableList(savedGardens);
			stage.setScene(loadSavedGardenView.getScene());
		});
	}
	
	/**
	 * Returns the event handler for clicking the home button
	 * When activated, this method takes the user from the load 
	 * menu to the home screen if they click the back button.
	 * 
	 * @return event
	 */
	public EventHandler fromLoadToHome() {
		return (event -> {
			stage.setScene(startView.getScene());
		});
	}
	
	
	/**
	 * Returns the event handler that handles the report generation 
	 * when the generate report button is clicked in ReportView.
	 * When activated, this method will send the proper information
	 * to display the desired report
	 * 
	 * @return event handler
	 */
	public EventHandler generateReportHandler() {
		return(event -> {
			
			reportView.showReport();
			
			HashMap<String, PlantShoppingListData> tempGardenData = garden.generateShoppingListData();
			
			//Adds plant info to reportGardenPieGraph
			Iterator itr = tempGardenData.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry element = (Map.Entry)itr.next();
				PlantShoppingListData v = (PlantShoppingListData)element.getValue();
				reportView.addItemToPieGraph(v.getCommonName(),v.getCount());
			}
			
			reportView.addGardenPieGraph();
			reportView.addLepPieGraph();

			//Garden Budget
			reportView.addBudgetBox(garden.getSpent(),garden.getBudget());

			//Lep List
			tempGardenData = garden.generateShoppingListData();
			ConcurrentHashMap<String, Set<Lep>> tempLeps =  garden.getLepsByPlant();
			tempGardenData.forEach((key,val) ->{
				String scientific = val.getScientificName();
				Set<Lep> leps = tempLeps.get(scientific);
				if(leps != null) {
				for(Lep l: leps) {
					reportView.addLep(l.getLepName());
					reportView.addToLepList(l.getLepName());
					
				}
				}
			});
			reportView.addCountToList();
			reportView.addLepList();
			
				
			//Plant List
			tempGardenData = garden.generateShoppingListData();
			tempGardenData.forEach((key,val) ->{
				String scientific = val.getScientificName();
				reportView.addPlant(scientific);
			});
			reportView.addPlantList();
			
			reportView.getButton().setDisable(true);

		});
		
	}
	
	
	/**
	 * Returns the event handler for opening a webpage from Learn More Hyperlinks
	 * When activated this method will launch the hyperlink that was clicked on
	 * @return event handler
	 */
	public EventHandler onClickedWebpageHandler() {
		return (event -> {
			learnMoreView.launchWebpage(event.getSource());
		});
	}
	
	
	/**
	 * Returns the event handler for clicking the hyperlink of a lep
	 * When activated, this method opens a pop up with the lep image and more info
	 * @return event handler
	 */
	public EventHandler lepPopUpHandler() {
		return (event->{
			gardenEditorView.lepPopUp((ActionEvent)event, Garden.getAllLeps());
		});
	}
	
	/**
	 * Event Handler called to close the pop up window
	 * @return
	 */
	public EventHandler closePopUp() {
		return (event->{
			gardenEditorView.closeLepWindow();
		});
	}
	
	//TODO javadoc
	/**Event handler to open reportView plant popup. 
	 * @return event handler
	 */
	public EventHandler plantListClicked() {
		return(event ->{
			reportView.openPlantListPopUp((MouseEvent)event, Garden.getAllPlants());
			});
	}
	
	//TODO javadoc
	/**Event handler to open reportView lep popup. 
	 * @return event handler
	 */
	public EventHandler lepListClicked() {
		return(event ->{
			reportView.openLepListPopUp((MouseEvent)event, Garden.getAllLeps());
		});
	}

}