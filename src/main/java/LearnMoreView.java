import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class LearnMoreView extends View{
	
	private Scene scene;
	private Controller controller;
	private BorderPane base;
	private int LEFTBAR = 225;
	private int SPACING = 5;
	double titleSize = 80;
	double subtitleSize = 32;
	double textSize = 18;
	
	/**
	 * Constructor for LearnMoreView
	 * @param stage
	 * @param c
	 */
	public LearnMoreView(Stage stage, Controller c) {
		controller = c;
		base = new BorderPane();
		scene = new Scene(base, WINDOW_WIDTH, WINDOW_HEIGHT);
		createTop();
		createLeft();
		createRight();
		createCenter();
		stage.setScene(scene);
        stage.show();
	}
	/**
	 * Creates the top VBox
	 */
	public void createTop() {
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setStyle("-fx-background-color: "+darkGreen);
		Text t = new Text("Learn More");
		t.setTextAlignment(TextAlignment.CENTER);
		t.setFont(Font.font(titleSize));
		t.setFill(Color.web(lightBlue));
		box.getChildren().add(t);
		base.setTop(box);
	}
	
	/**
	 * Creates the left panel VBoxes
	 */
	public void createLeft() {
		VBox box1 = new VBox();
		box1.setAlignment(Pos.CENTER);
		box1.setStyle("-fx-background-color: "+darkGreen);
		box1.setMinWidth(LEFTBAR);
		VBox box2 = new VBox();
		box1.getChildren().add(box2);
		box2.setBackground(new Background(new BackgroundFill(Color.web(lightBlue), CornerRadii.EMPTY, Insets.EMPTY)));
		box2.setAlignment(Pos.CENTER);
		box2.setSpacing(SPACING);
		gardenTipsText(box2);
		base.setLeft(box1);
	}
	
	/**
	 * Creates right panel VBoxes
	 */
	public void createRight() {
		VBox box1 = new VBox();
		box1.setAlignment(Pos.CENTER);
		box1.setStyle("-fx-background-color: "+darkGreen);
		box1.setMinWidth(LEFTBAR);
		VBox box2 = new VBox();
		box1.getChildren().add(box2);
		box2.setBackground(new Background(new BackgroundFill(Color.web(lightBlue), CornerRadii.EMPTY, Insets.EMPTY)));
		box2.setAlignment(Pos.CENTER);
		box2.setSpacing(SPACING);
		moreResources(box2);
		base.setRight(box1);
	}
	
	/**
	 * Writes in more resource text
	 * @param box
	 */
	public void moreResources(VBox box) {
		Text title = new Text("More Resources");
		title.setFont(Font.font(subtitleSize));
		title.setFill(Color.web(offWhite));
		box.getChildren().add(title);
		Hyperlink link1 = new Hyperlink("Mt. Cuba Center");
		link1.setTextFill(Color.web(offWhite));
		link1.setOnMouseClicked(controller.onClickedWebpageHandler());
		box.getChildren().add(link1);
		Hyperlink link2 = new Hyperlink("University of Delaware");
		link2.setTextFill(Color.web(offWhite));
		link2.setOnMouseClicked(controller.onClickedWebpageHandler());
		box.getChildren().add(link2);
		Hyperlink link3 = new Hyperlink("Delaware Native Plant Society");
		link3.setTextFill(Color.web(offWhite));
		link3.setOnMouseClicked(controller.onClickedWebpageHandler());
		box.getChildren().add(link3);
	}
	
	/**
	 * Launches webpages with resources 
	 */
	public void launchWebpage(Object source) {
		Hyperlink link = (Hyperlink) source;
		Desktop d = Desktop.getDesktop();
		if (link.getText().equals("Mt. Cuba Center")){
			try {
				d.browse(new URI("https://mtcubacenter.org/"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (link.getText().equals("University of Delaware")) {
			try {
				d.browse(new URI("https://www.udel.edu/academics/colleges/canr/cooperative-extension/fact-sheets/native-plants-for-delaware-landscapes/"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (link.getText().equals("Delaware Native Plant Society")) {
			try {
				d.browse(new URI("https://delawarenativeplants.org/"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes garden tips text
	 * @param box
	 */
	public void gardenTipsText(VBox box){
		Text title = new Text("Gardening Tips");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(Font.font(subtitleSize));
		title.setFill(Color.web(offWhite));
		box.getChildren().add(createGardenText(""));
		box.getChildren().add(title);
		box.getChildren().add(createGardenText("Choose the right plants"));
		box.getChildren().add(createGardenText("Start Small"));
		box.getChildren().add(createGardenText("Use Spacing"));
		box.getChildren().add(createGardenText("Mix It Up"));
		box.getChildren().add(createGardenText(""));
		
	}
	
	
	/**
	 * Helper function for creating garden tip text
	 * @param phrase
	 * @return
	 */
	public Text createGardenText(String phrase) {
		Text t = new Text(phrase);
		t.setTextAlignment(TextAlignment.CENTER);
		t.setFont(Font.font(textSize));
		t.setFill(Color.web(offWhite));
		return t;
	}
	
	/**
	 * Creates the center VBoxes
	 */
	public void createCenter() {
		VBox box1 = new VBox();
		box1.setAlignment(Pos.CENTER);
		box1.setStyle("-fx-background-color: " + darkGreen);
		VBox box2 = new VBox();
		box1.getChildren().add(box2);
		box2.setBackground(new Background(new BackgroundFill(Color.web(lightBlue), CornerRadii.EMPTY, Insets.EMPTY)));
		box2.setAlignment(Pos.CENTER);
		box2.setSpacing(SPACING);
		box2.setMaxWidth(WINDOW_WIDTH/2);
		createCenterText(box2);
		base.setCenter(box1);
	}
	
	/**
	 * Adds the center panel vbox text
	 * @param box
	 */
	public void createCenterText(VBox box){
		Text title = new Text("Our Mission");
		title.setFont(Font.font(subtitleSize));
		title.setFill(Color.web(offWhite));
		box.getChildren().add(title);
		try {
			String content = Files.readString
					(Path.of("src/main/resources/LearnMoreCenterText.txt"), StandardCharsets.US_ASCII);
			Text body = new Text(content);
			body.setFont(Font.font(textSize));
			body.setFill(Color.web(offWhite));
			box.getChildren().add(body);
			Text buffer = new Text("");
			box.getChildren().add(buffer);
		} catch (IOException e) {
			System.out.println("IN CATCH");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Getter for scene
	 * @return scene
	 */
	@Override
	public Scene getScene() {
		return scene;
	}
}