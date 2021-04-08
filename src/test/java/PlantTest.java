import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PlantTest {
	Plant p = new Plant(new Options("taco", 1, 2), 69.0, 5, "blue", "large", 600.0, "scientificTaco", "taco", new Point(1.0, 2.0));
	
	@Test
	public void testGetOptions() {
		assertEquals("taco", p.getOptions().getSoilType());
		assertEquals(1, p.getOptions().getSunLevel());
		assertEquals(2, p.getOptions().getSunLevel());
	}
	
	@Test
	public void testSetOptions() {
		p.setOptions(new Options("newTaco", 3, 4));
		assertEquals("newTaco", p.getOptions().getSoilType());
		assertEquals(3, p.getOptions().getSunLevel());
		assertEquals(4, p.getOptions().getSunLevel());
	}
	
	@Test
	public void testGetSpreadRadius() {
		assertEquals(69.0, p.getSpreadRadius(), 0.5);
	}
	
	
	@Test
	public void testSetSpreadRadius() {
		p.setSpreadRadius(75.0);
		assertEquals(75.0, p.getSpreadRadius(), 0.5);
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
	public void testIncrementLepsSupportedByOne() {
		p.setLepsSupported(8);
		p.incrementLepsSupportedByOne();
		assertEquals(9, p.getLepsSupported());
	}
	
	@Test
	public void testIncrementLepsSupportedByX() {
		p.setLepsSupported(9);
		p.incrementLepsSupportedByX(6);
		assertEquals(15, p.getLepsSupported());
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
		assertEquals("large", p.getSize());
	}
	
	@Test
	public void testSetSize() {
		p.setSize("pequeno");
		assertEquals("pequeno", p.getSize());
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
		assertEquals("scientificTaco", p.getScientificName());
	}
	
	@Test
	public void testSetScientificName() {
		p.setScientificName("scientificHamburger");
		assertEquals("scientificHamburger", p.getScientificName());
	}
	
	@Test
	public void testGetCommonName() {
		assertEquals("taco", p.getCommonName());
	}
	
	@Test
	public void testSetCommonName() {
		p.setCommonName("hamburger");
		assertEquals("hamburger", p.getCommonName());
	}
	
	@Test
	public void testGetPosition() {
		assertEquals(1.0, p.getPosition().getX(), 0.1);
		assertEquals(2.0, p.getPosition().getY(), 0.1);
	}
	
	@Test
	public void testSetPosition() {
		p.setPosition(new Point(3.0, 4.0));
		assertEquals(1.0, p.getPosition().getX(), 0.1);
		assertEquals(2.0, p.getPosition().getY(), 0.1);
	}

}
