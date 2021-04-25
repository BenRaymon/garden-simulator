import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GardenEditorView extends View{

	private Controller controller;
	private ArrayList<ImageView> imageViewsForPlantsInGarden;
	private HashMap<Image, String> recommendedPlantImages;
	private ListView recommendedPlants;
	private Image selectedPlant;
	private Text selectedPlantInfo;
	private Text lepCount;
	private Text plantCount;
	private Text budget;
	private GridPane top, bottom, right, left;
	private BorderPane base;
	private Scene scene;
	private Button toShoppingList;
	private Button saveGarden; // save the garden to a .dat file
	private GraphicsContext gc;
	private int imageInc = 0;
	private TextField garden_name; // name the garden (used to load garden)
	
	private double LEFTBAR = 200;
	private double RIGHTBAR = 150;
	private double TOPBAR = 100, BOTTOM = 30;
	private double SPACING = 10;
	private double SCALE = 10;
	private double CANVAS_WIDTH = WINDOW_WIDTH - LEFTBAR - RIGHTBAR;
	private double CANVAS_HEIGHT = WINDOW_HEIGHT - TOPBAR - BOTTOM;
	
	
	public GardenEditorView(Stage stage, Controller c) {
		imageViewsForPlantsInGarden = new ArrayList<ImageView>();
		controller = c;
		base = new BorderPane();
		base.setOnDragOver(controller.getOnDragOverHandler());
		base.setOnDragDropped(controller.getOnDragDroppedHandler());
		
		Canvas drawArea = new Canvas(WINDOW_WIDTH - LEFTBAR - RIGHTBAR, WINDOW_HEIGHT - TOPBAR - BOTTOM);
		gc = drawArea.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		base.setCenter(drawArea);
		
		//TEMP
		drawArea.setOnMouseClicked(event -> {
			System.out.println(((MouseEvent) event).getSceneX());
			System.out.println(((MouseEvent) event).getSceneY());
			
		});
		
		createRight();
		createRightText();
		createTop();
		setPlantImages();
		createLeft();
		setPlantInfo();
		createBottom();
		addPageButtons();
	
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
	}
	
	public void drawPlot(ArrayList<Point> points) {
		
		double[] xcords = new double[points.size()];
		double[] ycords = new double[points.size()];
		int i = 0;
		for(Point p : points) {
			xcords[i] = (p.getX());
			ycords[i++] = (p.getY());
		}
		gc.fillPolygon(xcords, ycords, i);
		//gc.strokePolygon(xcords, ycords, i);
	}
	
	public void setFillColor(Options o) {
		int[] soil = o.getSoilTypes();
		if(soil[0] == 1) 
			gc.setFill(Color.GREY);
		else if (soil[1] == 1) 
			gc.setFill(Color.SADDLEBROWN);
		else if (soil[2] == 1) 
			gc.setFill(Color.SANDYBROWN);
	}
	
	public void createRightText() {
		Text t0 = new Text("Remaining Budget");
		Text t00 = new Text("$#");
		right.add(t0, 0, 0);
		right.add(t00, 0, 1);
		Text t1 = new Text("Number of Plants");
		right.add(t1, 0, 2);
		Text t2 = new Text("# of Plants");
		right.add(t2, 0, 3);
		Text t3 = new Text("Number of Lep");
		right.add(t3, 0, 4);
		Text t4 = new Text("# of Leps");
		right.add(t4, 0, 5);
	}
	
	//Supposed to draw images in the top grid pane
	public void setPlantImages(){
		System.out.println(getImages().size());
		recommendedPlantImages = new HashMap<Image, String>();
		getImages().forEach((key,value) -> {
			//ImageView temp = new ImageView((Image)value);
	        //temp.setPreserveRatio(true);
	        //temp.setFitHeight(100);
	        //temp.setFitWidth(100);
	        //temp.setOnDragDetected(controller.getOnImageDraggedHandler());
	        //top.add(temp, imageInc, 0);
			if(imageInc < 10) {
				recommendedPlantImages.put(value, key);
				
		        Circle circ = new Circle(50);
		        circ.setFill(new ImagePattern((Image)value));
		        circ.setOnDragDetected(controller.getOnImageDraggedHandler());
		        top.add(circ, imageInc, 0);
		        imageInc++;
			}
	        
		});
		base.setTop(top);
		
	}
	
	public void createRight() {
		right = new GridPane();
		createPane(right, "darkseagreen");
		right.setMinWidth(RIGHTBAR);
		base.setRight(right);
	}
	
	public void createLeft() {
		left  = new GridPane();
		createPane(left, "darkseagreen");
		left.setMinWidth(LEFTBAR);
		base.setLeft(left);
	}
	
	public void createTop() {
	    top = new GridPane();
		createPane(top, "white");
		top.setMinHeight(TOPBAR);
		top.setMaxWidth(600);
		base.setTop(top);
	}
	
	public void createBottom() {
		bottom = new GridPane();
		createPane(bottom, "darkgrey");
		bottom.setMinHeight(BOTTOM);
		base.setBottom(bottom);
	}
	
	public void createPane(GridPane pane, String color) {
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: " + color);
		pane.setGridLinesVisible(true);
		pane.setHgap(SPACING);
		pane.setVgap(SPACING);
	}
	
	
	
	
	public void addPageButtons() {
		toShoppingList = new Button("Shopping List");
		toShoppingList.setOnMouseClicked(controller.getToShoppingListOnClickHandler());
		bottom.add(toShoppingList, 2, 0);
		
		Button toReport = new Button("Report");
		toReport.setOnMouseClicked(controller.getToReportOnClickHandler());
		bottom.add(toReport, 3, 0);
		
		Button toComp = new Button("Compare");
		toComp.setOnMouseClicked(controller.getToCompareOnClickHandler());
		bottom.add(toComp, 4, 0);
		
		garden_name = new TextField();
		bottom.add(garden_name, 5, 0);
		
		saveGarden = new Button("Save");
		saveGarden.setOnMouseClicked(controller.SaveButtonClickedHandler());
		bottom.add(saveGarden, 6, 0);
	}
	
	public void setPlantInfo() {
		Text t = new Text("Plant Info Here");
		left.add(t, 0, 0);
	}
	
	public Button getToShoppingListButton() {
		return this.toShoppingList;
	}
	
	public GridPane getTop() {
		return this.top;
	}
	
	public BorderPane getBase() {
		return this.base;
	}
	
	public ArrayList<ImageView> getImageViewsForPlantsInGarden() {
		return this.imageViewsForPlantsInGarden;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void updateValues() {
		
	}
	
	public void updateInfo() {
		
	}
	
	public void createNewImageInBase(DragEvent event, Dragboard db, double radius) {
		System.out.println("Creating new gardeneditorview image");
		//Image i = (Image)db.getContent(DataFormat.IMAGE);
		//ImageView iv = new ImageView();
    	//iv.setImage(i);
    	//iv.setPreserveRatio(true);
    	//iv.setFitHeight(100);
    	//iv.setX(event.getX());
		//iv.setY(event.getY());
		//iv.setOnDragDetected(controller.getOnImageDraggedHandler());
		// right here problem
		//imc.setHandlerForClick(iv);
		Circle circ = new Circle(event.getX(), event.getY(), radius*SCALE);
        circ.setFill(new ImagePattern(selectedPlant));
        circ.setOnDragDetected(controller.getOnImageDraggedHandler());
		//imageViewsForPlantsInGarden.add(iv);
    	base.getChildren().add(circ);
	}
	
	public boolean hasChild(MouseEvent event) {
		return base.getChildren().contains(event.getSource());
	}
	
	public String getPlantName(Image im) {
		return recommendedPlantImages.get(im);
	}
	
	public void setSelectedPlantImage(Image im) {
		selectedPlant = im; 
	}
	
	public GraphicsContext getGC() {
		return gc;
	}
	
	public double getCanvasHeight() {
		return CANVAS_HEIGHT;
	}
	public double getCanvasWidth() {
		return CANVAS_WIDTH;
	}
	public double getTopHeight() {
		return TOPBAR;
	}
	public TextField getGardenName() {
		return garden_name;
	}
	
}