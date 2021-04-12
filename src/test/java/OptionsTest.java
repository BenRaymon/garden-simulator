import static org.junit.Assert.*;
import org.junit.Test;

public class OptionsTest{
	
	Options test = new Options(new int[] {1,0,0}, new int[] {0,1,1}, new int[] {0,0,1});
	
	@Test
	public void testGetSoilType() {
		assertEquals(test.getSoilTypes()[0],1);
		assertEquals(test.getSoilTypes()[1],0);
		assertEquals(test.getSoilTypes()[2],0);
	}
	
	@Test
	public void testSetSoilType() {
		test.setSoilTypes(new int[] {0,0,1});
		assertEquals(test.getSoilTypes()[0],0);
		assertEquals(test.getSoilTypes()[1],0);
		assertEquals(test.getSoilTypes()[2],1);
	}
	
	@Test
	public void testGetSunLevel() {
		assertEquals(test.getSunLevels()[0],0);
		assertEquals(test.getSunLevels()[1],1);
		assertEquals(test.getSunLevels()[2],1);
	}
	
	@Test
	public void testSetSunLevel() {
		test.setSunLevels(new int[] {1,0,0});
		assertEquals(test.getSunLevels()[0],1);
		assertEquals(test.getSunLevels()[1],0);
		assertEquals(test.getSunLevels()[2],0);
	}
	
	@Test
	public void testGetMoisture() {
		assertEquals(test.getMoistures()[0],0);
		assertEquals(test.getMoistures()[1],0);
		assertEquals(test.getMoistures()[2],1);
	}
	
	@Test
	public void testSetMoisture() {
		test.setMoistures(new int[] {1,1,0});
		assertEquals(test.getMoistures()[0],1);
		assertEquals(test.getMoistures()[1],1);
		assertEquals(test.getMoistures()[2],0);
	}
	
}