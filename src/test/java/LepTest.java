import static org.junit.Assert.*;

import org.junit.Test;

public class LepTest {
	Lep l = new Lep("taco", "scientificTaco", "blue");
	
	@Test
	public void testGetCommonName() {
		assertEquals("taco", l.getCommonName());
	}
	
	@Test
	public void testSetCommonName() {
		l.setCommonName("hamburger");
		assertEquals("hamburger", l.getCommonName());
	}
	
	@Test
	public void testGetScientificName() {
		assertEquals("scientificTaco", l.getScientificName());
	}
	
	@Test
	public void testSetScientificName() {
		l.setScientificName("scientificHamburger");
		assertEquals("scientificHamburger", l.getScientificName());
	}
	
	@Test
	public void testGetColor() {
		assertEquals("blue", l.getColor());
	}
	
	@Test
	public void testSetColor() {
		l.setColor("red");
		assertEquals("red", l.getColor());
	}

}
