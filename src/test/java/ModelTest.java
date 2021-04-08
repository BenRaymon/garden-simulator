import static org.junit.Assert.*;

import org.junit.Test;

public class ModelTest extends Model{

	@Test
	public void testUpdate() {
		assertEquals(update(),0);
	}
	
	@Test
	public void testLoadData() {
		assertEquals(loadData(),0);
	}
	
	@Test
	public void testSerializeData() {
		assertEquals(serializeData(),0);
	}
	
	@Test
	public void testSaveData() {
		assertEquals(saveData(),0);
	}

}
