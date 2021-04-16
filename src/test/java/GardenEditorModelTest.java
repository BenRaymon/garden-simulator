import static org.junit.Assert.*;
import org.junit.Test;

public class GardenEditorModelTest {
	
	@Test
	public void testselectPlant() {
		//fail("Not yet implemented");
		GardenEditor testModel = new GardenEditor();
		assertEquals(testModel.selectPlant(),null);
	}
	
	@Test
	public void testplacePlant() {
		//fail("Not yet implemented");
		GardenEditor testModel = new GardenEditor();
		assertEquals(testModel.placePlant(),0);
	}

	@Test
	public void testisValidPlacement() {
		//fail("Not yet implemented");
		Point testPoint = new Point();
		GardenEditor testModel = new GardenEditor();
		assertEquals(testModel.isValidPlacement(testPoint),0);
	}

	@Test
	public void testupdateStats() {
		//fail("Not yet implemented");
		GardenEditor testModel = new GardenEditor();
		assertEquals(testModel.updateStats(),0);
	}

	@Test
	public void testsearch() {
		//fail("Not yet implemented");
		GardenEditor testModel = new GardenEditor();
		assertEquals(testModel.search(),0);
	}

	@Test
	public void testsetPos() {
		//fail("Not yet implemented");
		Point testPoint = new Point();
		GardenEditor testModel = new GardenEditor();
		assertEquals(testModel.setPos(testPoint),0);
	}

}
