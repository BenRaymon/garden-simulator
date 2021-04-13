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

	@Override
	public Scene getScene() {
		return scene;
	}

}
