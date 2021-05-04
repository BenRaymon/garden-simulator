import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MenuBox {
	Button plot_d;
	Button editor;
	Button report;
	Button shopping_l;
	Button load;
	Button comp_p;
	GridPane container;
	
	public MenuBox() {
		
		// make the pane
		container = new GridPane();
		createPane(container, "darkgrey");
		container.setMinHeight(100);
		
		// make the buttons
		load = new Button("Load");
		plot_d = new Button("Plot Designer");
		editor = new Button("Garden Editor");
		report = new Button("Report");
		shopping_l = new Button("Shopping List");
		comp_p = new Button("Compare Plants");
		
		container.add(load, 0, 0);
		container.add(plot_d, 1, 0);
		container.add(editor, 2, 0);
		container.add(report, 3, 0);
		container.add(shopping_l, 4, 0);
		container.add(comp_p, 5, 0);
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
}
