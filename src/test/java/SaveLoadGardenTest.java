import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class SaveLoadGardenTest {
	public static ArrayList<Garden> gardenList = new ArrayList<Garden>();
	public static ArrayList<Garden> deleteCheck = new ArrayList<Garden>();
	public SaveLoadGarden slg = new SaveLoadGarden();
	
	@BeforeClass
	public static void setUpBeforeClass() {
		// Setup a fake garden for testing
		Garden g = new Garden("test", 100.0, 200.0, new ArrayList<Plot>(), 5, new ArrayList<Plant>(), 5.0);
		Garden g2 = new Garden("test2", 101.0, 201.0, new ArrayList<Plot>(), 6, new ArrayList<Plant>(), 6.0);
		Garden g3 = new Garden("test3", 102.0, 202.0, new ArrayList<Plot>(), 7, new ArrayList<Plant>(), 7.0);
		Garden g4 = new Garden("deleteTest", 105.0, 205.0, new ArrayList<Plot>(), 9, new ArrayList<Plant>(), 9.0);
		
		gardenList.add(g);
		gardenList.add(g2);
		gardenList.add(g3);
		
		deleteCheck.add(g);
		deleteCheck.add(g2);
		deleteCheck.add(g3);
		deleteCheck.add(g4);
	}
	
	@Test
	public void testSaveandLoadGarden() {
		// Save the garden
		try {
			slg.saveGarden(gardenList);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Now load it back in to see if the names match
		ArrayList<Garden> loaded = null;
		try {
			loaded = slg.loadGardenList();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (loaded.get(0).getName().equals("test")) {
			assertEquals(loaded.get(0).getName(), "test");
		} else if (loaded.get(0).getName().equals("test2")) {
			assertEquals(loaded.get(0).getName(), "test2");
		} else {
			assertEquals(loaded.get(0).getName(), "test3");
		}
		
	}
	
	@Test
	public void testLoadPickedGarden() {
		Garden tmp = slg.loadPickedGarden("test", gardenList);
		Garden tmp2 = slg.loadPickedGarden("deleteTest", deleteCheck);
		assertEquals(tmp, gardenList.get(0));
		assertEquals(tmp2, deleteCheck.get(3));
	}
	
	@Test
	public void testDeleteGarden() {
		deleteCheck = slg.deleteGarden("deleteTest", deleteCheck);
		assertEquals(deleteCheck, gardenList);
	}

}
