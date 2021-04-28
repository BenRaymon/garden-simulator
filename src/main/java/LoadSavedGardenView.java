import javafx.geometry.Insets;
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
	private GridPane base;
	private Controller controller;
	private Button to_garden_editor = new Button("Load Selection");
	private Button delete_garden = new Button("Delete Selection");
	private Button to_home = new Button("Back to Home");
	private VBox list_box;
	private Label label = new Label("Saved Gardens, pick a garden to load");
	private ObservableList<String> garden_names = FXCollections.observableArrayList();
	private ListView<String> listView;
	
	public LoadSavedGardenView(Stage stage, ArrayList<Garden> saved_g, Controller c) {
		setObservableList(saved_g);
		this.controller = c;
		base = new GridPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);
		
		listView = new ListView<String>(garden_names);
		listView.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		to_garden_editor.setOnMouseClicked(controller.loadSelectedGardenHandler());
		delete_garden.setOnMouseClicked(controller.deleteSelectedGardenHandler());
		to_home.setOnMouseClicked(controller.fromLoadToHome());
		list_box = createVBox();
		
		GridPane sideBySide = new GridPane();
		VBox buttonBox = new VBox(10);
		buttonBox.getChildren().add(to_garden_editor);
		buttonBox.getChildren().add(delete_garden);
		buttonBox.getChildren().add(to_home);
		sideBySide.add(list_box,0,0);
		sideBySide.add(buttonBox,1,0);
		
		base.add(sideBySide,0,0);
		base.setStyle("-fx-background-color: BEIGE");
		
		
		//base.add(to_garden_editor, 0, 10);
		//base.add(delete_garden, 1, 10);
		//base.add(to_home, 2, 10);
		//base.add(list_box, 0, 20);
		
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}
	
	public VBox createVBox() {
		VBox vbox_g = new VBox(10);
		Insets inset = new Insets(5, 5, 5, 50);
		vbox_g.setPadding(inset);
		vbox_g.getChildren().addAll(label, listView);
		vbox_g.setStyle("-fx-background-color: BEIGE");
		return vbox_g;
	}
	
	public void setObservableList(ArrayList<Garden> list_g) {
		// get the names of saved gardens into our observable list
		ObservableList<String> tmp = FXCollections.observableArrayList();
		for(Garden g : list_g) {
			tmp.add(g.name);
		}
		
		garden_names = tmp;
	}
	
	public ListView<String> getListView() {
		return listView;
	}
	
	public Scene getScene() {
		return scene;
	}
}
