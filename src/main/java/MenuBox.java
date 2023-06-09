import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MenuBox extends Pane {
	private Button plotD;
	private Button editor;
	private Button report;
	private Button shoppingL;
	private Button load;
	private Button compP;
	private Button learn;
	private GridPane container;
	static int MENU_HEIGHT = 60;
	
	/**
	 * Constructor that makes a MenuBox instance. Appears at the top of every
	 * view, has buttons that allow for easy traversal of the views.
	 * 
	 * @param controller - the controller to link buttons to appropriate event handlers
	 * @return none
	 * */
	public MenuBox(Controller controller, String current_t) {
		
		// make the pane
		container = new GridPane();
		createPane(container, "#E2E4E0");
		container.setMinHeight(60);
		container.setMinWidth(View.WINDOW_WIDTH);
		
		// make the buttons
		load = new Button("Load Menu");
		plotD = new Button("Plot Designer");
		editor = new Button("Garden Editor");
		report = new Button("Report");
		shoppingL = new Button("Shopping List");
		compP = new Button("Compare Plants");
		learn = new Button("Learn More");
		
		load.setOnMouseClicked(controller.getLoadGardenViewOnClickHandler());
		plotD.setOnMouseClicked(controller.getToPlotDesignHandler());
		editor.setOnMouseClicked(controller.getToGardenOnClickHandler2());
		shoppingL.setOnMouseClicked(controller.getToShoppingListOnClickHandler());
		report.setOnMouseClicked(controller.getToReportOnClickHandler());
		compP.setOnMouseClicked(controller.getToCompareOnClickHandler());
		learn.setOnMouseClicked(controller.getLearnMoreOnClickHandler());
		
		container.add(load, 0, 0);
		container.add(plotD, 1, 0);
		container.add(editor, 2, 0);
		container.add(report, 3, 0);
		container.add(shoppingL, 4, 0);
		container.add(compP, 5, 0);
		container.add(learn, 6, 0);
		
		enableButtons();
		disableButton(current_t);
		
		getChildren().add(container);
		getStylesheets().add(getClass().getResource("menubox.css").toExternalForm());
	}
	
	/**
	 * Helper method for generally used pane creation
	 * @param pane
	 * @param color
	 */
	public void createPane(GridPane pane, String color) {
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: " + color);
		pane.setHgap(10);
		pane.setVgap(10);
	}
	
	/**
	 * Getter that returns the container pane that holds the buttons.
	 * Used to add or take things away from the container.
	 * 
	 * @param none
	 * @return container - the container pane that holds buttons
	 * */
	public GridPane getContainer() {
		return container;
	}
	
	/**
	 * Getter for EditorButton()
	 * @return editor
	 */
	public Button getEditorButton() {
		return editor;
	}
	
	/**
	 * disable buttons
	 * @param s - name of the button
	 * @return non
	 * */
	public void disableButton(String s) {
		switch(s) {
		case "load":
			load.setDisable(true);
			break;
		case "plotD":
			plotD.setDisable(true);
			break;
		case "editor":
			editor.setDisable(true);
			break;
		case "report":
			report.setDisable(true);
			break;
		case "shopping":
			shoppingL.setDisable(true);
			break;
		case "comp":
			compP.setDisable(true);
			break;
		case "learn":
			learn.setDisable(true);
			break;
		default:
			break;
		}
	}
	
	/**
	 * enable buttons
	 * @param none
	 * @return non
	 * */
	public void enableButtons() {
		for(Node n : container.getChildren()) {
			if(n instanceof Button) {	
				Button b = (Button) n;
				b.setDisable(false);
			}
		}
	}
}
