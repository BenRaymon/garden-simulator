import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class BackgroundLoaderTest {
	// Pass in the concurrent hashmaps needed
	static BackgroundLoader bl = null;
	@BeforeClass
	public static void SetupandRunLoaders() {
		bl = new BackgroundLoader("test", View.getImages(), Garden.getAllPlants());
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBackgroundDataLoaderOutput() {
		// Make sure all plants are loaded
		assertEquals(368, Garden.getAllPlants().size());
	}
	
	@Test
	public void testBackgroundImageLoaderOutput() {
		assertEquals(367, View.getImages().size());
	}
}
