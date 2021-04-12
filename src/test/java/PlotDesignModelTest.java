import static org.junit.Assert.*;
import org.junit.Test;

public class PlotDesignModelTest {

	PlotDesignModel testModel = new PlotDesignModel();
	
	@Test
	public void testNewPlot() {
		testModel.newPlot(new Options(1.0, 2.0, 3.0));
		assertFalse(testModel.getPlots().isEmpty());
	}
	
	
	@Test
	public void testaddCoordToPlot() {
		//fail("Not yet implemented");
		if (testModel.getNumPlots() == 0) {
			testModel.newPlot(new Options(1.0, 2.0, 3.0));
		}
		testModel.addCoordToPlot(0, new Point(1, 1));
		assertEquals(testModel.getPlots().get(0).getCoordinates().size(),1);
	}
	
	@Test
	public void testbuildPlot() {
		//fail("Not yet implemented");
		assertEquals(testModel.buildPlot(),0);
	}

	@Test
	public void testupdateMoisture() {
		//fail("Not yet implemented");
		assertEquals(testModel.updateMoisture(),0);
	}

	@Test
	public void testupdateSunlight() {
		//fail("Not yet implemented");
		assertEquals(testModel.updateSunlight(),0);
	}

	@Test
	public void testupdateSoil() {
		//fail("Not yet implemented");
		assertEquals(testModel.updateSoil(),0);
	}

}
