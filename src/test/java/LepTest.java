import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class LepTest {
	
	Lep testLep = new Lep(null, null, null, null, null);
	
	@Test
	public void testAddHostFamily() {
		testLep.addHostFamily("family");
		assertEquals(testLep.getFamilies().contains("family"), true);
	}
	
	@Test
	public void testaddHostName() {
		testLep.addHostName("host");
		assertEquals(testLep.getNames().contains("host"), true);
	}
	
	@Test
	public void testGetLepFamily() {
		testLep.setLepFamily("new family");
		assertEquals(testLep.getLepFamily(), "new family");
	}
	
	@Test
	public void testSetLepFamily() {
		testLep.setLepFamily("test");
		assertEquals(testLep.getLepFamily(), "test");
	}
	
	@Test
	public void testGetLepName() {
		testLep.setLepName("test");
		assertEquals(testLep.getLepName(), "test");
	}
	
	@Test
	public void testSetLepName() {
		testLep.setLepName("Harvey");
		assertEquals(testLep.getLepName(), "Harvey");
	}
	
	@Test
	public void testGetCountries() {
		testLep.addCountry("Bolivia");
		assertEquals(testLep.getCountries().contains("Bolivia"), true);
	}
	
	@Test
	public void testGetFamilies() {
		HashSet<String> testFamilies = new HashSet<String>();
		testFamilies.add("test1");
		testFamilies.add("test2");
		testLep.setLepFamilies(testFamilies);
		assertEquals(testLep.getFamilies(), testFamilies);
	}
}
