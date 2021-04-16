import java.util.HashMap;

public class CompPlants {
	
	public static String getInfo(String s) {
		Plant p = Garden.getPlant(s);
		return p.toString();
	}
	
	public static String moreLeps(Plant A, Plant B) {
		if (A.getLepsSupported() > B.getLepsSupported())
			return A.getScientificName();
		else if(B.getLepsSupported() > A.getLepsSupported())
			return B.getScientificName();
		else return "Equal";
	}
	
}