import static org.junit.Assert.*;

import org.junit.Test;

public class ReportModelTest extends ReportModel {

	
	@Test
	public void testGenerateReport() {
		assertEquals(generateReport(),0);
	}
	
	@Test
	public void testGenerateLepChart() {
		assertEquals(generateLepChart(),0);
	}

	@Test
	public void testGeneratePlantChart() {
		assertEquals(generatePlantChart(),0);
	}
	
	@Test
	public void testGenerateSummary() {
		assertEquals(generateSummary(),0);
	}
}
