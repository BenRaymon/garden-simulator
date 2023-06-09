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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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
	private Button learnMore;
	private Scene scene;
	private BorderPane base;
	private VBox container;
	private Controller controller;
	private GridPane titleContainer;
	private final double SPACING = 10;
	private final double BUTTON_H = 50;
	private final double BUTTON_W = 150;
	private final double BOTTOM_BAR = 250;
	private final double TEXT = 75;
	
	GridPane tempImages = new GridPane();
	int tempNum = 0;

	/**
	 * StartView Constructor
	 * @param stage
	 * @param c
	 */
	public StartView(Stage stage, Controller c) {
		controller = c;
		base = new BorderPane();
		container = new VBox();
		titleContainer = new GridPane();
		base.setStyle("-fx-background-color: " + darkGreen);
		
		Label title = new Label("OkBloomer");
		
		titleContainer.setMinHeight(100);
		titleContainer.setHgap(10);
		titleContainer.setVgap(10);
		titleContainer.add(title, 80, 2);
		base.setTop(titleContainer);
		base.setCenter(container);
		container.setMargin(title, new Insets(50,0,0,0));
		
		
		
		//Buttons for load, new gardens and learnMore
		HBox buttons = new HBox();
		buttons.setMinHeight(BOTTOM_BAR);
		buttons.setAlignment(Pos.BASELINE_CENTER);
		buttons.setSpacing(100);
		buttons.setStyle("-fx-background-color: " + darkGreen);
		newGarden = new Button("Create New Garden");
		newGarden.setMinHeight(BUTTON_H);
		newGarden.setMinWidth(BUTTON_W);
		newGarden.setOnMouseClicked(controller.getToPlotDesignHandler());
		loadGarden = new Button("Load Garden");
		loadGarden.setMinHeight(BUTTON_H);
		loadGarden.setMinWidth(BUTTON_W);
		loadGarden.setOnMouseClicked(controller.getLoadGardenViewOnClickHandler());
		learnMore = new Button("Learn More");
		learnMore.setMinHeight(BUTTON_H);
		learnMore.setMinWidth(BUTTON_W);
		learnMore.setOnMouseClicked(controller.getLearnMoreOnClickHandler());
		buttons.getChildren().add(newGarden);
		buttons.getChildren().add(loadGarden);
		buttons.getChildren().add(learnMore);
		
		Image butterfly = new Image("TitleImage.png",WINDOW_WIDTH,WINDOW_HEIGHT - 75 - BOTTOM_BAR, true, true);
		ImageView butterflyView = new ImageView(butterfly);
		VBox pictureBox = new VBox();
		pictureBox.setAlignment(Pos.CENTER);
		pictureBox.getChildren().add(butterflyView);
		pictureBox.setStyle("-fx-background-color: " + darkGreen);
		pictureBox.setMargin(butterflyView, new Insets(0,0,50,0));
		container.getChildren().add(pictureBox);
		container.getChildren().add(buttons);
		
		// get the button styles
		String labelStyle = getClass().getResource("startview.css").toExternalForm();
		String buttonStyle = getClass().getResource("buttons.css").toExternalForm();
		
		//create and set scene with base
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(buttonStyle);
		scene.getStylesheets().add(labelStyle);
        stage.setScene(scene);
        stage.show();
	}
	
	/**
	 * Getter for scence
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Getter for new Garden Button
	 * @return newGarden Button
	 */
	public Button getNewGarden() {
		return newGarden;
	}
	
	/**
	 * Getter for loadGardenButton
	 * @return loadGardenButton
	 */
	public Button getLoadGarden() {
		return loadGarden;
	}
	
}