import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GardenEditorView extends View{

	private Controller controller;
	private ArrayList<ImageView> imageViewsForPlantsInGarden;
	private HashMap<String, Image> recommendedPlantImages;
	private ListView recommendedPlants;
	private Image selectedPlant;
	private Text selectedPlantInfo;
	private Text lepCount;
	private Text plantCount;
	private Text budget;
	private GridPane top;
	private BorderPane base;
	private Scene scene;
	private Button toShoppingList;
	private GraphicsContext gc;
	private GridPane right;
	private int imageInc = 0;
	
	public GardenEditorView(Stage stage, Controller c) {
		imageViewsForPlantsInGarden = new ArrayList<ImageView>();
		controller = c;
		base = new BorderPane();
		base.setOnDragOver(controller.getOnDragOverHandler());
		base.setOnDragDropped(controller.getOnDragDroppedHandler());
		
		Canvas drawArea = new Canvas(600,650);
		gc = drawArea.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		base.setCenter(drawArea);
		
		createRight();
		createRightText();
		
		createTop();
		setPlantImages();
		
		GridPane left_pane = createLeft();
		setPlantInfo(left_pane);
		
		GridPane bottom_pane = createBottom();
		addPageButtons(bottom_pane);
	
		//create and set scene with base
		scene = new Scene(base, 800, 800);
        stage.setScene(scene);
        stage.show();
	}
	
	public void drawPlot(ArrayList<Point> points) {
		
		double[] xcords = new double[points.size()];
		double[] ycords = new double[points.size()];
		int i = 0;
		for(Point p : points) {
			xcords[i] = p.getX();
			ycords[i++] = p.getY() - 150;
		}
		gc.fillPolygon(xcords, ycords, i);
		
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
		recommendedPlantImages = getImages();
		recommendedPlantImages.forEach((key,value) -> {
			ImageView temp = new ImageView((Image)value);
	        temp.setPreserveRatio(true);
	        temp.setFitHeight(100);
	        temp.setFitWidth(100);
	        temp.setOnDragDetected(controller.getOnImageDraggedHandler());
	        top.add(temp, imageInc, 0);
	        imageInc++;
		});
		base.setTop(top);
		
	}
	
	public void createRight() {
		right = new GridPane();
		right.setAlignment(Pos.CENTER_RIGHT);
		right.setStyle("-fx-background-color: darkseagreen");
		right.setGridLinesVisible(true);
		right.setHgap(0);
		right.setVgap(0);
		base.setRight(right);
		Text t = new Text("Test");
		
	}
	
	
	public void createTop() {
	    top = new GridPane();
		top.setAlignment(Pos.TOP_CENTER);
		top.setStyle("-fx-backgorund-color: darkseagreen");
		top.setGridLinesVisible(true);
		top.setHgap(10);
		top.setVgap(10);
		base.setTop(top);
	}
	
	
	public GridPane createLeft() {
		GridPane left_grid = new GridPane();
		left_grid.setAlignment(Pos.CENTER_LEFT);
		left_grid.setStyle("-fx-background-color: darkseagreen");
		left_grid.setGridLinesVisible(true);
		left_grid.setHgap(10);
		left_grid.setVgap(10);
		base.setLeft(left_grid);
		return left_grid;	
	}
	
	public GridPane createBottom() {
		GridPane bottom_grid = new GridPane();
		bottom_grid.setAlignment(Pos.CENTER_LEFT);
		bottom_grid.setStyle("-fx-background-color: darkgrey");
		bottom_grid.setGridLinesVisible(true);
		bottom_grid.setHgap(10);
		bottom_grid.setVgap(10);
		base.setBottom(bottom_grid);
		return bottom_grid;	
	}
	
	public void addPageButtons(GridPane bottom) {
		toShoppingList = new Button("Shopping List");
		toShoppingList.setOnMouseClicked(controller.getToShoppingListOnClickHandler());
		bottom.add(toShoppingList, 2, 0);
		
		Button toReport = new Button("Report");
		toReport.setOnMouseClicked(controller.getToReportOnClickHandler());
		bottom.add(toReport, 3, 0);
		
		Button toComp = new Button("Compare");
		toComp.setOnMouseClicked(controller.getToCompareOnClickHandler());
		bottom.add(toComp, 4, 0);
		
	}
	
	public void setPlantInfo(GridPane leftPane) {
		Text t = new Text("Plant Info Here");
		leftPane.add(t, 0, 0);
		
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
	
	public void createNewImageInBase(DragEvent event, Image i) {
		System.out.println("Creating new gardeneditorview image");
		ImageView iv = new ImageView();
    	iv.setImage(i);
    	iv.setPreserveRatio(true);
    	iv.setFitHeight(100);
    	iv.setX(event.getX());
		iv.setY(event.getY());
		iv.setOnDragDetected(controller.getOnImageDraggedHandler());
		// right here problem
		//imc.setHandlerForClick(iv);
		imageViewsForPlantsInGarden.add(iv);
    	base.getChildren().add(iv);
	}
	
	
}