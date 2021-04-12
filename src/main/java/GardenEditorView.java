import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GardenEditorView extends View{

	private Controller controller;
	private ArrayList<ImageView> imageViewsForPlantsInGarden;
	private ListView recommendedPlants;
	private Image selectedPlant;
	private Text selectedPlantInfo;
	private Text lepCount;
	private Text plantCount;
	private Text budget;
	private GridPane top;
	private BorderPane base;
	private Scene scene;
	private int imageInc = 0;
	
	public GardenEditorView(Stage stage, Controller c) {
		imageViewsForPlantsInGarden = new ArrayList<ImageView>();
		controller = c;
		base = new BorderPane();
		controller.attachOnDragOverToBorderPane(base);
		controller.attachOnDragDroppedToGardenEditorBorderPane(base);
		
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
	
	
	//Supposed to draw images in the top grid pane
	public void setPlantImages(){
		System.out.println(getImages().size());
		getImages().forEach((key,value) -> {
			ImageView temp = new ImageView((Image)value);
	        temp.setPreserveRatio(true);
	        temp.setFitHeight(100);
	        temp.setFitWidth(100);
	        controller.attachImageViewDragHandler(temp);
	        top.add(temp, imageInc, 0);
	        imageInc++;
		});
		base.setTop(top);
		
	}
	
	
	public void createTop() {
	    top = new GridPane();
		top.setAlignment(Pos.TOP_CENTER);
		top.setStyle("-fx-backgorund-color: blue");
		top.setGridLinesVisible(true);
		top.setHgap(10);
		top.setVgap(10);
		base.setTop(top);
	}
	
	
	public GridPane createLeft() {
		GridPane left_grid = new GridPane();
		left_grid.setAlignment(Pos.CENTER_LEFT);
		left_grid.setStyle("-fx-background-color: pink");
		left_grid.setGridLinesVisible(true);
		left_grid.setHgap(10);
		left_grid.setVgap(10);
		base.setLeft(left_grid);
		return left_grid;	
	}
	
	public GridPane createBottom() {
		GridPane bottom_grid = new GridPane();
		bottom_grid.setAlignment(Pos.CENTER_LEFT);
		bottom_grid.setStyle("-fx-background-color: aqua");
		bottom_grid.setGridLinesVisible(true);
		bottom_grid.setHgap(10);
		bottom_grid.setVgap(10);
		base.setBottom(bottom_grid);
		return bottom_grid;	
	}
	
	public void addPageButtons(GridPane bottom) {
		nextPage = new Button("Next Page");
		backPage = new Button("Back Page");
		bottom.add(nextPage, 1, 0);
		bottom.add(backPage, 0, 0);
		
	}
	
	public void setPlantInfo(GridPane leftPane) {
		Text t = new Text("Plant Info Here");
		leftPane.add(t, 0, 0);
		
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
		controller.attachImageViewDragHandler(iv);
		// right here problem
		//imc.setHandlerForClick(iv);
		imageViewsForPlantsInGarden.add(iv);
    	base.getChildren().add(iv);
	}
	
	
}