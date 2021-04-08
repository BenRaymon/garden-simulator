import static org.junit.Assert.*;
import org.junit.Test;

public class OptionsTest{
	
	Options test = new Options("a",2,3);
	
	@Test
	public void testGetSoilType() {
		assertEquals(test.getSoilType(),"a");
	}
	
	@Test
	public void testSetSoilType() {
		test.setSoilType("b");
		assertEquals(test.getSoilType(), "b");
	}
	
	@Test
	public void testGetSunLevel() {
		assertEquals(test.getSunLevel(),2);
	}
	
	@Test
	public void testSetSunLevel() {
		test.setSunLevel(1);
		assertEquals(test.getSunLevel(),1);
	}
	
	@Test
	public void testGetMoisture() {
		assertEquals(test.getMoisture(),3);
	}
	
	@Test
	public void testSetMoisture() {
		test.setMoisture(2);
		assertEquals(test.getMoisture(),2);
	}
	
}