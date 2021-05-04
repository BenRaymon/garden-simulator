import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MenuBox extends Pane{
	Button plot_d;
	Button editor;
	Button report;
	Button shopping_l;
	Button load;
	Button comp_p;
	GridPane container;
	
	public MenuBox(Controller controller) {
		
		// make the pane
		container = new GridPane();
		createPane(container, "darkgrey");
		container.setMinHeight(30);
		
		// make the buttons
		load = new Button("Load");
		plot_d = new Button("Plot Designer");
		editor = new Button("Garden Editor");
		report = new Button("Report");
		shopping_l = new Button("Shopping List");
		comp_p = new Button("Compare Plants");
		
		plot_d.setOnMouseClicked(controller.getNewGardenOnClickHandler());
		editor.setOnMouseClicked(controller.getToGardenOnClickHandler2());
		shopping_l.setOnMouseClicked(controller.getToShoppingListOnClickHandler());
		report.setOnMouseClicked(controller.getToReportOnClickHandler());
		comp_p.setOnMouseClicked(controller.getToCompareOnClickHandler());
		
		
		container.add(load, 0, 0);
		container.add(plot_d, 1, 0);
		container.add(editor, 2, 0);
		container.add(report, 3, 0);
		container.add(shopping_l, 4, 0);
		container.add(comp_p, 5, 0);
		
		getChildren().add(container);
		getStylesheets().add(getClass().getResource("menubox.css").toExternalForm());
	}
	
	/**
	 * Helper method for generally used pane creation
	 * @param pane
	 * @param color
	 */
	public void createPane(GridPane pane, String color) {
		pane.setAlignment(Pos.TOP_LEFT);
		pane.setStyle("-fx-background-color: " + color);
		pane.setHgap(10);
		pane.setVgap(10);
	}
	
	public MenuBar createMenuBar() {
		MenuBar menu;
		
		Menu load = new Menu("Load");
		Menu plot = new Menu("Plot Designer");
		Menu edit = new Menu("Garden Editor");
		Menu report = new Menu("Report");
		Menu shop = new Menu("Shopping List");
		Menu comp = new Menu("Compare Plants");
		
		menu = new MenuBar(load, plot, edit, report, shop, comp);
		//menu.getStylesheets().add(getClass().getResource("menu.css").toExternalForm());
		
		return menu;
	}
	
	public GridPane getContainer() {
		return container;
	}
}
