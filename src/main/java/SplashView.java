import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashView extends View {
	private Stage stage;
	private Controller controller;
	private StackPane background;
	private Scene scene;
	
	public SplashView(Stage s, Controller c) {
		this.stage = s;
		this.controller = c;
		
		background = new StackPane();
		background.setStyle("-fx-background-image: url(\"https://static.wikia.nocookie.net/lotr/images/e/ec/Gimli_-_FOTR.png/revision/latest?cb=20121008105956\")");
		Text loading = new Text("LOADING ASSETS");
		loading.setStyle("-fx-font-size: 50;");
		loading.setFill(Color.CRIMSON);
		loading.setX(400);
		loading.setY(400);
		background.getChildren().add(loading);
		
		scene = new Scene(background, 800, 800);
        stage.setScene(scene);
        stage.show();
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

	@Override
	public Scene getScene() {
		return scene;
	}

}
