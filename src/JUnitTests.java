import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Point;


public class JUnitTests {
	
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

	@Test	//(Unfinished)
	public void testImagePanel(){
		ImagePanel imagePanelTest = new ImagePanel();

		Point testPoint = new Point(10,15);

		imagePanelTest.setStartPoint(testPoint);				//setStartPoint()
		assertEquals(imagePanelTest.getStartX(), testPoint.x);	//getStartX()
		assertEquals(imagePanelTest.getStartY(), testPoint.y);  //getStartY()

	}
}
