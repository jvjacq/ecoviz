import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class JUnitTests {
	@Test
	public void example() {
		//Example
		String test = "Test";
		String input = "Test";
		assertEquals(test,input);
	}
	
	@Test
	public void testPlant() {
		//Class : Plant.java
		
		int speciesid=0;
	    int plantid=1;
	    int posx=2;
	    int posy=3;
	    double height=4.1;
	    double canopyRadius=5.2;
		
		Plant plantTest = new Plant(speciesid,plantid,posx,posy,(float) height,(float) canopyRadius);
			int testSpeciesID = plantTest.getSpeciesID();
				assertEquals(testSpeciesID,speciesid);
			int testID = plantTest.getID();
				assertEquals(testID,plantid);
			int testX = plantTest.getX();
				assertEquals(testX,posx);
			int testY = plantTest.getY();
				assertEquals(testY,posy);
			double testHeight = plantTest.getHeight();
				assertEquals((int)testHeight,(int)height);	//assertEquals deprecated for double
			double testRadius = plantTest.getCanopy();
				assertEquals((int)testRadius,(int)canopyRadius);	//assertEquals deprecated for double
				
			//Test Filter Boolean:
			plantTest.toggleFilter(); //Should be setting to true
			boolean testFilter = plantTest.getFilter();
				assertEquals(testFilter,true);
	}

}
