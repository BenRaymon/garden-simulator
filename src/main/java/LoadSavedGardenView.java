import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoadSavedGardenView extends View {
	private Scene scene;
	private BorderPane base;
	private Label label = new Label("Saved Gardens");
	private ObservableList<String> garden_names;
	private ListView<String> listView;
	
	public LoadSavedGardenView(Stage stage, ArrayList<Garden> saved_g, Controller c) {
		// get the names of saved gardens into our observable list
		for(Garden g : saved_g) {
			garden_names.add(g.name);
		}
		
		base = new BorderPane();
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		listView = new ListView<String>(garden_names);
		listView.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
	}
	
	public VBox createVBox() {
		VBox vbox_g = new VBox(10);
		//vbox_g.setPadding(new Insets(5, 5, 5, 50));
		vbox_g.getChildren().addAll(label, listView);
		vbox_g.setStyle("-fx-background-color: BEIGE");
		return vbox_g;
	}
	
	public Scene getScene() {
		return scene;
	}
}
