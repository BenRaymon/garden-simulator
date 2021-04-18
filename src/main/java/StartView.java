import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;

import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartView extends View{

	private Button newGarden;
	private Button loadGarden;
	private Scene scene;
	private GridPane base;
	private Controller controller;
	private final double SPACING = 10;
	private final double BUTTON_H = 50;
	private final double BUTTON_W = 150;
	
	
	GridPane tempImages = new GridPane();
	int tempNum = 0;
	
	public StartView(Stage stage, Controller c) {
		controller = c;
		base = new GridPane();
		base.setHgap(SPACING);
		base.setVgap(SPACING);
		base.setAlignment(Pos.CENTER);
		
		
		//Buttons for load and new gardens
		HBox buttons = new HBox();
		buttons.setSpacing(100);
		newGarden = new Button("Create New Garden");
		newGarden.setMinHeight(BUTTON_H);
		newGarden.setMinWidth(BUTTON_W);
		newGarden.setOnMouseClicked(controller.getNewGardenOnClickHandler());
		loadGarden = new Button("Load Garden");
		loadGarden.setMinHeight(BUTTON_H);
		loadGarden.setMinWidth(BUTTON_W);
		buttons.getChildren().add(newGarden);
		buttons.getChildren().add(loadGarden);
		base.add(buttons, 0, 0);

		//create a temporary vbox for the name and button
		pageTitle = new Text("START");
		VBox temp = new VBox(5);
		temp.getChildren().add(pageTitle);
		temp.setAlignment(Pos.CENTER);
		base.add(temp, 0, 1);
		tempImages.setVgap(SPACING);
		tempImages.setHgap(SPACING);
		base.add(tempImages, 0, 3);
		
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Button getNewGarden() {
		return newGarden;
	}
	
	public Button getLoadGarden() {
		return loadGarden;
	}
	
}