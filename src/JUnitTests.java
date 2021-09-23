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
				assertEquals((int)testHeight,(int)height); //assertEquals deprecated for double
			double testRadius = plantTest.getCanopy();
				assertEquals((int)testRadius,(int)canopyRadius); //assertEquals deprecated for double
				
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

	@Test
	public void testSpecies() {
		//Class : Species.java
		
		String[][] SPECIES;
		int[] COLOURS;
		String commonName;
		String latinName;
		int speciesid;
		float minHeight;
		float maxHeight;
		float avgRatio;
		int numPlants;
		Plant[] plantList;

		Species testSpecies = new Species(speciesid, minHeight, maxHeight, avgRatio, numPlants);
			terrainTest.setElevations(elevations);
			Terrain.setDimX(dimX);
			Terrain.setDimY(dimY);
			terrainTest.setGridSpacing((float)gridSpacing);
			terrainTest.setLatitude((float)latitude);
			double[][] testElevations = terrainTest.getElevations(); //assertEquals deprecated for objects
				if (testElevations == elevations)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			int testDimX = Terrain.getDimX();
				assertEquals(testDimX,dimX);
			int testDimY = Terrain.getDimY();
				assertEquals(testDimY,dimY);
			double testGridSpacing = terrainTest.getGridSpacing();
				assertEquals((int)testGridSpacing,(int)gridSpacing); //assertEquals deprecated for double
			double testLatitude = terrainTest.getLatitude();
				assertEquals((int)testLatitude,(int)latitude); //assertEquals deprecated for double

			public void setCommon(String name){
				this.commonName = name;
			}
		
			public void setLatin(String name){
				this.latinName = name;
			}
		
			public void setSpeciesID(int id){
				this.speciesid = id;
			}
		
			public void setMinHeight(float minH){
				this.minHeight = minH;
			}
		
			public void setMaxHeight(float maxH){
				this.maxHeight = maxH;
			}
		
			public void setRatio(float avg){
				this.avgRatio = avg;
			}
		
			public void setNumPlants(int num){
				this.numPlants = num;
			}
		
			public void setPlantList(Plant[] list){
				this.plantList = list;
			}
		
			public static void setSpeciesList(String[][] list){
				Species.SPECIES = list;
			}
		
			public static void setColourList(int[] list){
				Species.COLOURS = list;
			}
	}

	@Test
	public void testTerrain() {
		//Class : Terrain.java
		
		double[][] elevations = new double[][] {{1.1,1.2},{1.3,1.4}};
		int dimX = 2;
		int dimY = 2;
		double gridSpacing = 2.1;
		double latitude = 3.2;

		Terrain terrainTest = new Terrain();
			terrainTest.setElevations(elevations);
			Terrain.setDimX(dimX);
			Terrain.setDimY(dimY);
			terrainTest.setGridSpacing((float)gridSpacing);
			terrainTest.setLatitude((float)latitude);
			double[][] testElevations = terrainTest.getElevations(); //assertEquals deprecated for objects
				if (testElevations == elevations)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			int testDimX = Terrain.getDimX();
				assertEquals(testDimX,dimX);
			int testDimY = Terrain.getDimY();
				assertEquals(testDimY,dimY);
			double testGridSpacing = terrainTest.getGridSpacing();
				assertEquals((int)testGridSpacing,(int)gridSpacing); //assertEquals deprecated for double
			double testLatitude = terrainTest.getLatitude();
				assertEquals((int)testLatitude,(int)latitude); //assertEquals deprecated for double
	}

	@Test
	public void testWind() {
		//Class : Wind.java
		
		final double CONVERT = 0.621371;
		double direction = 1.1;
		double speed = 2.2;
		double speedKPH = 2.2;
		double speedMPH = 1.36702;

		Wind windTest = new Wind();
			windTest.setDirection(direction);
			windTest.setSpeed(speed);
			double testDirection = windTest.getDirection();
				assertEquals((int)testDirection,(int)direction); //assertEquals deprecated for double
			double testSpeedKPH = windTest.getSpeedKPH();
				assertEquals((int)testSpeedKPH,(int)(speedKPH/2)); //assertEquals deprecated for double
			double testSpeedMPH = windTest.getSpeedMPH();
				assertEquals((int)testSpeedMPH,(int)(speedMPH/2*CONVERT)); //assertEquals deprecated for double

			//Test Metric Boolean and Speed:
			boolean testMetric1 = windTest.getMetric();

			if (testMetric1 == true)
			{
				testSpeedKPH = windTest.getSpeedKPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedKPH,(int)(testSpeed));
			}
			else
			{
				testSpeedMPH = windTest.getSpeedMPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedMPH,(int)(testSpeed));				
			}

			windTest.toggleMetric(); //Should be alternating the boolean value
			boolean testMetric2 = windTest.getMetric();
				assertEquals(testMetric1,!testMetric2);

			
			if (testMetric2 == true)
			{
				testSpeedKPH = windTest.getSpeedKPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedKPH,(int)(testSpeed));
			}
			else
			{
				testSpeedMPH = windTest.getSpeedMPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedMPH,(int)(testSpeed));				
			}
	}
}
