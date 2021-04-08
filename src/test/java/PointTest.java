import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {
	
	Point testP = new Point(1.0, 1.0);

	@Test
	public void testEmptyConstructor() {
		Point testEmptyP = new Point();
		assertEquals(testEmptyP.getX(), 0, 0);
		assertEquals(testEmptyP.getY(), 0, 0);
	}
	
	@Test
	public void testGetX() {
		assertEquals(testP.getX(), 1.0, 0);
	}
	
	@Test
	public void testGetY() {
		assertEquals(testP.getY(), 1.0, 0);
	}
	
	@Test
	public void testSetX() {
		testP.setX(2.5);
		assertEquals(testP.getX(), 2.5, 0);
	}
	
	@Test
	public void testSetY() {
		testP.setY(2.5);
		assertEquals(testP.getY(), 2.5, 0);
	}
	
	@Test
	public void testIsValid() {
		assertEquals(testP.isValid(), true);
	}
	
	@Test
	public void testCheckBoundry() {
		assertEquals(testP.checkBoundry(), true);
	}
	
}
