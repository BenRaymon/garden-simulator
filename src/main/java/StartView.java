import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.sun.prism.paint.Color;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class StartView extends View {

	private Button newGarden;
	private Button loadGarden;
	private Scene scene;
	private VBox base;
	private Controller controller;
	private final double SPACING = 10;
	private final double BUTTON_H = 50;
	private final double BUTTON_W = 150;
	private final double BOTTOM_BAR = 150;
	private final double TEXT = 75;
	
	GridPane tempImages = new GridPane();
	int tempNum = 0;
	
	public StartView(Stage stage, Controller c) {
		controller = c;
		base = new VBox();
		base.setStyle("-fx-background-color: darkgrey");
		
		
		
		pageTitle = new Text("Native Garden Designer");
		pageTitle.setFont(Font.font(TEXT));
		pageTitle.minHeight(TEXT);
		pageTitle.maxHeight(TEXT);
		
		
		//Text titleBox = new VBox(5);
		//titleBox.setStyle("-fx-background-color: darkseagreen");
		//titleBox.setAlignment(Pos.CENTER);
		//titleBox.getChildren().add(pageTitle);
		base.getChildren().add(pageTitle);
		base.setAlignment(Pos.CENTER);
		base.setMargin(pageTitle, new Insets(50,0,0,0));
		
		
		
		//Buttons for load and new gardens
		HBox buttons = new HBox();
		buttons.setMinHeight(BOTTOM_BAR);
		buttons.setAlignment(Pos.BASELINE_CENTER);
		buttons.setSpacing(100);
		buttons.setStyle("-fx-background-color: darkgrey");
		newGarden = new Button("Create New Garden");
		newGarden.setMinHeight(BUTTON_H);
		newGarden.setMinWidth(BUTTON_W);
		newGarden.setOnMouseClicked(controller.getNewGardenOnClickHandler());
		loadGarden = new Button("Load Garden");
		loadGarden.setMinHeight(BUTTON_H);
		loadGarden.setMinWidth(BUTTON_W);
		loadGarden.setOnMouseClicked(controller.getLoadGardenViewOnClickHandler());
		buttons.getChildren().add(newGarden);
		buttons.getChildren().add(loadGarden);
		
		Image butterfly = new Image("TitleImage.png",WINDOW_WIDTH,WINDOW_HEIGHT - 75 - BOTTOM_BAR, true, true);
		ImageView butterflyView = new ImageView(butterfly);
		VBox pictureBox = new VBox();
		pictureBox.setAlignment(Pos.CENTER);
		pictureBox.getChildren().add(butterflyView);
		pictureBox.setStyle("-fx-background-color: darkseagreen");
		base.getChildren().add(pictureBox);
		base.getChildren().add(buttons);
		
		
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