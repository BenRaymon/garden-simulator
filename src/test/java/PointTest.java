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
	
	@Test
	public void testDistance() {
		Point a = new Point(10,10);
		Point b = new Point(0,0);
		assertEquals(a.distance(b), 14.142136, 0.2);
	}
	
	@Test
	public void testToString() {
		Point a = new Point(1,2);
		assertEquals(a.toString(), "(1.0, 2.0)");
	}
	
	@Test
	public void testEquals() {
		Point test_p = new Point(2.0, 2.0);
		Point test_p2 = new Point(2.0, 2.0);
		String test_s = new String();
		assertEquals(test_p.equals(test_p2), true);
		assertEquals(test_p.equals(test_s), false);
	}
}
