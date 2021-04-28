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
	
	
	/**
	 * Constructor to create the LoadSavedGardenView. Makes and places all elements of the
	 * load screen
	 * 
	 * @param stage - the stage initialized by the controller
	 * @param saved_g - the list of saved gardens
	 * @param c - the controller
	 * */
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
	
	/**
	 * Creates the vbox that holds the listView of saved gardens. The user can click one
	 * of the entries in the list and then load that selected entry with the load button.
	 * 
	 * @param none
	 * @return VBox - the created VBox with the label and listView added to it
	 * */
	public VBox createVBox() {
		VBox vbox_g = new VBox(10);
		Insets inset = new Insets(5, 5, 5, 50);
		vbox_g.setPadding(inset);
		vbox_g.getChildren().addAll(label, listView);
		vbox_g.setStyle("-fx-background-color: BEIGE");
		return vbox_g;
	}
	
	/**
	 * Fills the observable list with the names of thegardens in the saved list after 
	 * it's loaded into the application
	 * 
	 * @param list_g - the list of saved gardens
	 * @return void
	 * */
	public void setObservableList(ArrayList<Garden> list_g) {
		// get the names of saved gardens into our observable list
		ObservableList<String> tmp = FXCollections.observableArrayList();
		Iterator garden_itr = list_g.iterator();
		while(garden_itr.hasNext()) {
			Garden g = (Garden) garden_itr.next();
			tmp.add(g.name);
		}
		
		garden_names = tmp;
	}
	
	/**
	 * Gets the listView so that the name of the selected garden can be use to load the right
	 * garden
	 * 
	 * @param none
	 * @return ListView - the listview containing the garden names
	 * */
	public ListView<String> getListView() {
		return listView;
	}
	
	/**
	 * Gets the scene so that we can display the load screen when user clicks
	 * the load menu button on the home screen.
	 * 
	 * @param none
	 * @return Scene - the scene of this view
	 * */
	public Scene getScene() {
		return scene;
	}
}
