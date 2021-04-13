import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class ShoppingListModelTest {
	
	Options op = new Options(new int[] {1,1,1}, new int[] {0,1,1}, new int[] {1,0,0});
	Plant p = new Plant("name", "sciName", "genera", "fam", "blue", 1.0, 5.0,69,80, op, 600.0, 5, 't');
	HashMap<String, Plant> plants = new HashMap<String, Plant>();
	
	@Test
	public void testgenerateTotalCost() {
		//fail("Not yet implemented");
		ShoppingListModel testModel = new ShoppingListModel();
		plants.put("Test", p);
		assertEquals(testModel.generateTotalCost(plants), 600.0, 0.5);
	}

}
