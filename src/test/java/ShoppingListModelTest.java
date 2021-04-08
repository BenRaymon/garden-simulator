import static org.junit.Assert.*;
import org.junit.Test;

public class ShoppingListModelTest {
	
	@Test
	public void testgenerateTotalCost() {
		//fail("Not yet implemented");
		ShoppingListModel testModel = new ShoppingListModel();
		assertEquals(testModel.generateTotalCost(),0.1);
	}
	
	@Test
	public void testcountPlants() {
		//fail("Not yet implemented");
		ShoppingListModel testModel = new ShoppingListModel();
		assertEquals(testModel.countPlants(),0);
	}

}
