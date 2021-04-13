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
	
	GridPane tempImages = new GridPane();
	int tempNum = 0;
	
	public StartView(Stage stage) {
		base = new GridPane();
		base.setHgap(10);
		base.setVgap(10);
		base.setAlignment(Pos.CENTER);
		
		
		//Buttons for load and new gardens
		HBox buttons = new HBox();
		buttons.setSpacing(100);
		newGarden = new Button("Create New Garden");
		newGarden.setMinHeight(50);
		newGarden.setMinWidth(150);
		loadGarden = new Button("Load Garden");
		loadGarden.setMinHeight(50);
		loadGarden.setMinWidth(150);
		buttons.getChildren().add(newGarden);
		buttons.getChildren().add(loadGarden);
		base.add(buttons, 0, 0);

		//create a temporary vbox for the name and button
		nextPage = new Button("Next Page");
		backPage = new Button("Back Page");
		pageTitle = new Text("START");
		VBox temp = new VBox(5);
		temp.getChildren().add(nextPage);
		temp.getChildren().add(backPage);
		temp.getChildren().add(pageTitle);
		temp.setAlignment(Pos.CENTER);
		base.add(temp, 0, 1);
		tempImages.setVgap(10);
		tempImages.setHgap(10);
		base.add(tempImages, 0, 3);
		
		//create and set scene with base
		scene = new Scene(base, 800, 800);
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
	
	public boolean loadImages() {
		System.out.println("PLEASE ONLY SHOW ONCE");
		//load data file and create a list of lines
		File plantData = Paths.get("src/main/resources/result.csv").toFile().getAbsoluteFile();
		BufferedReader br;
		int numLines = 0;
		
		try {
			br = new BufferedReader(new FileReader(plantData));
		    String line;
		    while ((line = br.readLine()) != null) {
		        addImage(line);
		        numLines++;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	//TEMPORARY FUNCTION TO SEE HOW THE IMAGES LOOK
	
	public File getFile(String fileName) {
		return Paths.get("src/main/resources/" + fileName).toFile().getAbsoluteFile();
	}
	
	public void addImage(String line) {
		if(line.contains("﻿"))
			return;
		String words[] = line.split(",");
		String loc = words[17];
		try {
			BufferedImage image = ImageIO.read(getFile(loc));
			//COPIED THIS ONLINE
			//https://blog.idrsolutions.com/2012/11/convert-bufferedimage-to-javafx-image/
			WritableImage wr = null;
	        if (image != null) {
	            wr = new WritableImage(image.getWidth(), image.getHeight());
	            PixelWriter pw = wr.getPixelWriter();
	            for (int x = 0; x < image.getWidth(); x++) {
	                for (int y = 0; y < image.getHeight(); y++) {
	                    pw.setArgb(x, y, image.getRGB(x, y));
	                }
	            }
	        }
			getImages().put(words[0], wr);
		} catch (IOException e) {
			System.out.println("Failed to add image");
			e.printStackTrace();
		}
	}
	
}