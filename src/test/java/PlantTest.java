import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PlantTest {
	Options op = new Options(new int[] {1,1,1}, new int[] {0,1,1}, new int[] {1,0,0});
	Plant p = new Plant("name", "sciName", "genera", "fam", "blue", 1.0, 5.0,69,80, op, 600.0, 5, 't');
	
	@Test
	public void testToString() {
		String str = "name\nsciName\nfam\nblue\n1.0\n5.0\n69.0\n80.0\n";
		assertEquals(p.toString(), str);
	}
	@Test
	public void testGetOptions() {
		assertEquals(1, p.getOptions().getSoilTypes()[0]);
		assertEquals(0, p.getOptions().getSunLevels()[0]);
		assertEquals(1, p.getOptions().getMoistures()[0]);

		assertEquals(1, p.getOptions().getSoilTypes()[1]);
		assertEquals(1, p.getOptions().getSunLevels()[1]);
		assertEquals(0, p.getOptions().getMoistures()[1]);

		assertEquals(1, p.getOptions().getSoilTypes()[2]);
		assertEquals(1, p.getOptions().getSunLevels()[2]);
		assertEquals(0, p.getOptions().getMoistures()[2]);
	}
	
	@Test
	public void testSetOptions() {
		p.setOptions(new Options(1.0, 2.0, 1.0));
		assertEquals(1, p.getOptions().getSoilTypes()[0]);
		assertEquals(1, p.getOptions().getSunLevels()[1]);
		assertEquals(1, p.getOptions().getMoistures()[0]);

		assertEquals(0, p.getOptions().getSoilTypes()[1]);
		assertEquals(0, p.getOptions().getSunLevels()[0]);
		assertEquals(0, p.getOptions().getMoistures()[1]);

		assertEquals(0, p.getOptions().getSoilTypes()[2]);
		assertEquals(0, p.getOptions().getSunLevels()[2]);
		assertEquals(0, p.getOptions().getMoistures()[2]);
	}
	
	@Test
	public void testGetSpreadRadius() {
		assertEquals(69, p.getSpreadRadiusLower(), 0);
		assertEquals(80, p.getSpreadRadiusUpper(), 0);
	}
	
	
	@Test
	public void testSetSpreadRadius() {
		p.setSpreadRadiusLower(0);
		p.setSpreadRadiusUpper(10);
		assertEquals(0, p.getSpreadRadiusLower(),0);
		assertEquals(10, p.getSpreadRadiusUpper(),0);
	}
	
	@Test
	public void testGetLepsSupported() {
		assertEquals(5, p.getLepsSupported());
	}
	
	@Test
	public void testSetLepsSupported() {
		p.setLepsSupported(8);
		assertEquals(8, p.getLepsSupported());
	}
	
	@Test
	public void testGetColor() {
		assertEquals("blue", p.getColor());
	}
	
	@Test
	public void testSetColor() {
		p.setColor("red");
		assertEquals("red", p.getColor());
	}
	
	@Test
	public void testGetSize() {
		assertEquals(1.0, p.getSizeLower(), 0.5);
		assertEquals(5.0, p.getSizeUpper(), 0.5);
	}
	
	@Test
	public void testSetSize() {
		p.setSizeLower(2);
		p.setSizeUpper(10);
		assertEquals(2.0, p.getSizeLower(), 0.5);
		assertEquals(10.0, p.getSizeUpper(), 0.5);
	}
	
	@Test
	public void testGetCost() {
		assertEquals(600.0, p.getCost(), 0.1);
	}
	
	@Test
	public void testSetCost() {
		p.setCost(55.5);
		assertEquals(55.5, p.getCost(), 0.1);
	}
	
	@Test
	public void testGetScientificName() {
		assertEquals("sciName", p.getScientificName());
	}
	
	@Test
	public void testSetScientificName() {
		p.setScientificName("scientificHamburger");
		assertEquals("scientificHamburger", p.getScientificName());
	}
	
	@Test
	public void testGetCommonName() {
		assertEquals("name", p.getCommonName());
	}
	
	@Test
	public void testSetCommonName() {
		p.setCommonName("hamburger");
		assertEquals("hamburger", p.getCommonName());
	}
	
	@Test
	public void testGetPosition() {
		assertEquals(0, p.getPosition().getX(), 0);
		assertEquals(0, p.getPosition().getY(), 0);
	}
	
	@Test
	public void testSetPosition() {
		p.setPosition(new Point(3.0, 4.0));
		assertEquals(3.0, p.getPosition().getX(), 0.1);
		assertEquals(4.0, p.getPosition().getY(), 0.1);
	}
	
	@Test
	public void testGetTypeAndSetType() {
		assertEquals(p.getType(), 't');
		p.setType('h');
		assertEquals(p.getType(), 'h');
	}
	
	@Test
	public void testGetAndSetGenera() {
		assertEquals(p.getGenera(), "genera");
		p.setGenera("Test Genus");
		assertEquals(p.getGenera(), "Test Genus");
	}
	
	@Test
	public void testGetAndSetFamily() {
		assertEquals(p.getFamily(), "fam");
		p.setFamily("Test Family");
		assertEquals(p.getFamily(), "Test Family");
	}
	
	@Test
	public void testEquals() {
		Plant pNew = new Plant();
		p.setPosition(new Point(10,10));
		pNew.setPosition(new Point(10,10));
		assertTrue(p.equals(pNew));
		pNew.setPosition(new Point(0,0));
		assertFalse(p.equals(pNew));
	}
	
	@Test
	public void testClone() {
		Plant clone = p.clone();
		assertEquals(p.getCommonName(), clone.getCommonName());
		assertEquals(p.getScientificName(), clone.getScientificName());
		assertEquals(p.getColor(), clone.getColor());
		assertEquals(p.getFamily(), clone.getFamily());
		assertEquals(p.getSpreadRadiusLower(), clone.getSpreadRadiusLower(), 0);
		assertEquals(p.getSizeUpper(), clone.getSizeUpper(), 0);
		assertEquals(p.getType(), clone.getType());
		assertEquals(p.getClass(), clone.getClass());
		assertTrue(p.equals(clone)); 
		
		p.setColor("new color");
		assertFalse(p.getColor().equals(clone.getColor()));
	}
	

}
