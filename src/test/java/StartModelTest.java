import static org.junit.Assert.*;

import org.junit.Test;

public class StartModelTest extends StartModel{

	@Test
	public void testLoadAllPlants() {
		assertEquals(loadAllPlants(),true);
	}

}
