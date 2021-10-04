import static org.junit.Assert.*;
import org.graalvm.compiler.hotspot.stubs.NewArrayStub;
import org.junit.Test;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class JUnitTests {

	@Test //Incomplete
	public void testController(){
	}

	@Test //Incomplete
	public void testFileController(){
	}

	@Test //Incomplete
	public void testFire(){
	}

	@Test //Incomplete
	public void testFireBreak(){
	}

	@Test //Incomplete
	public void testGui(){
	}

	@Test //Incomplete
	public void testImagePanel(){

		Point point = new Point(10,15);
		int XDiff = 1;
		int YDiff = 2;
		boolean dragger = true;
		boolean released = true;
		boolean zoom = true;
		double multiplier = 2.0;
		BufferedImage fire = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		BufferedImage terrain = new BufferedImage(6, 6, BufferedImage.TYPE_INT_ARGB);
		BufferedImage canopy = new BufferedImage(7, 7, BufferedImage.TYPE_INT_ARGB);
		BufferedImage undergrowth = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);

		ImagePanel testImagePanel = new ImagePanel();
			testImagePanel.setStartPoint(point);
			testImagePanel.setXDiff(XDiff);
			testImagePanel.setYDiff(YDiff);
			testImagePanel.setDragger(dragger);
			testImagePanel.setReleased(released);
			testImagePanel.setZoom(zoom);
			testImagePanel.setZoomMult(multiplier);
			testImagePanel.setFire(fire);
			testImagePanel.setTerrain(terrain);
			testImagePanel.setCanopy(canopy);
			testImagePanel.setUndergrowth(undergrowth);

			int testStartX = testImagePanel.getStartX();
				assertEquals(testStartX, point.x);
			int testStartY = testImagePanel.getStartY();
				assertEquals(testStartY, point.y);
			double testMultiplier = testImagePanel.getZoomMult(); //assertEquals deprecated for double
				assertEquals((int)testMultiplier, (int)multiplier);
			BufferedImage testFire = testImagePanel.getFire();
				if (testFire == fire)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			BufferedImage testTerrain = testImagePanel.getTerrain();
				assertEquals(testTerrain, terrain);
			BufferedImage testCanopy = testImagePanel.getCanopy();
				assertEquals(testCanopy, canopy);
			BufferedImage testUndergrowth = testImagePanel.getUndergrowth();
				assertEquals(testUndergrowth, undergrowth);

			/*
			public void deriveImg(Terrain terrain){
			public void derivePlants(){
			public void paintComponent(Graphics g){
			public void exportImage(String nm){
			public void setShowCanopy(boolean b){
			public void setShowUnderGrowth(boolean b){
			*/
		
	}

	@Test //Incomplete
	public void testMiniMap(){
	}

	@Test
	public void testPlant() {
		//Class : Plant.java
		
		int speciesID = 0;
	    int plantID = 1;
	    int posX = 2;
	    int posY = 3;
	    double height = 4.1;
	    double canopyRadius = 5.2;
		boolean canopy = true;
		boolean layer = true;
		boolean filter = true;
		
		Plant plantTest = new Plant(speciesID, plantID, posX, posY, (float)height, (float)canopyRadius, canopy);
			int testSpeciesID = plantTest.getSpeciesID();
				assertEquals(testSpeciesID,speciesID);
			int testPlantID = plantTest.getID();
				assertEquals(testPlantID,plantID);
			int testX = plantTest.getX();
				assertEquals(testX, posX);
			int testY = plantTest.getY();
				assertEquals(testY, posY);
			double testHeight = plantTest.getHeight();
				assertEquals((int)testHeight,(int)height); //assertEquals deprecated for double
			double testRadius = plantTest.getCanopy();
				assertEquals((int)testRadius,(int)canopyRadius); //assertEquals deprecated for double
			boolean testCanopy = plantTest.getFilter();
				assertEquals(testCanopy, canopy);
			boolean testLayer = plantTest.getLayer();
				assertEquals(testLayer, layer);
			boolean testFilter = plantTest.getFilter();
				assertEquals(testFilter,filter);
				
			//Test Filter Boolean:
			boolean testNewCanopy = !testCanopy;
				assertEquals(testNewCanopy,false);
			boolean testNewLayer = !testFilter;
				assertEquals(testNewLayer,false);
			plantTest.toggleFilter(); //Should be setting to false
			boolean testNewFilter = plantTest.getFilter();
				assertEquals(testNewFilter,false);
	}

	@Test //Incomplete
	public void testPlantLayer() {
		//Class : PlantLayer.java
		
		int[][][] idLocations = {{{5,5,2},{10,10,2},{15,15,2}}};
		Plant testPlant1 = new Plant(1, 1, 50, 50, (float)10, (float)10, true);
		Plant testPlant2 = new Plant(2, 2, 100, 100, (float)10, (float)10, true);
		Plant testPlant3 = new Plant(3, 3, 150, 150, (float)10, (float)10, false);
		ArrayList<Plant> plantList = new ArrayList<Plant>(Arrays.asList(testPlant1, testPlant2, testPlant3));
		Color colour1 = new Color(10, 20, 30, 1);
		Color colour2 = new Color(20, 30, 40, 1);
		Color colour3 = new Color(30, 40, 50, 1);
		Species species1 = new Species(1, "common one", "latin one", colour1);
		Species species2 = new Species(2, "common two", "latin two", colour2);
		Species species3 = new Species(3, "common three", "latin three", colour3);
		Species[] allSpecies = {species1, species2, species3};
		int numSpecies = 10;
		boolean filter = true;
		
		PlantLayer plantLayerTest = new PlantLayer();
			plantLayerTest.setNumSpecies(numSpecies);
			plantLayerTest.setFilter(filter);
			plantLayerTest.setLocations(500, 500);
			plantLayerTest.removePlant(50, 50);
			PlantLayer.setPlantList();
			PlantLayer.setAllSpecies(allSpecies);
			PlantLayer.addSpecies(new Species(0, "common name four", "latin name four", colour1));
			PlantLayer.addPlant(testPlant1);
			plantLayerTest.setLayer(idLocations);

			int[][][] testLocations = plantLayerTest.getLocations();
				if (testLocations == idLocations)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			int testNumSpecies = plantLayerTest.getNumSpecies();
				assertEquals(testNumSpecies, numSpecies);
			int[] plantAtLocation = {5,5,1,1};
			plantLayerTest.setPlantAtLocation(5, 5, 1, 1);
			int[] testPlantAtLocation = plantLayerTest.getPlantAtLocation(5, 5);
				assertArrayEquals(testPlantAtLocation, plantAtLocation);
			boolean testFilter = plantLayerTest.getFilter();
				assertEquals(testFilter, filter);
			ArrayList<Plant> testPlantList = PlantLayer.getPlantList();
				assertEquals(testPlantList, plantList);
			Species[] testAllSpecies = PlantLayer.getAllSpecies();
				if (testAllSpecies == allSpecies)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			Species testPlantLayerSpecies = PlantLayer.getSpeciesAtID(1);
				if (testPlantLayerSpecies == species1)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);

	}

	@Test
	public void testSimController() {
		//Class : SimController.java
		
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
	public void testSpecies() {
		//Class : Species.java
		
		String commonName = new String("common name");
		String latinName = new  String("latin name");
		int speciesID = 0;
		float minHeight = 10;
		float maxHeight = 20;
		float avgRatio = (float)9.0;
		int numPlants = 333;
		Plant[] canopy =
		{new Plant(1, 1, 100, 100, (float)6.9, (float)4.20, true),
		new Plant(2, 2, 200, 200, (float)11.1, (float)2.50, true),
		new Plant(3, 3, 300, 300, (float)44.4, (float)7.89, true)};
		Plant[] undergrowth =
		{new Plant(4, 4, 150, 150, (float)6.9, (float)4.20, false),
		new Plant(5, 5, 250, 250, (float)11.1, (float)2.50, false),
		new Plant(6, 6, 350, 350, (float)44.4, (float)7.89, false)};
		Color colour = new Color(10, 20, 30, 1);
		boolean filter = true;
		
		Species testSpecies = new Species(speciesID, commonName, latinName, colour);
			testSpecies.setMinHeight(minHeight);
			testSpecies.setMaxHeight(maxHeight);
			testSpecies.setRatio(avgRatio);
			testSpecies.setNumPlants(numPlants);
			testSpecies.setCanopyPlants(canopy);
			testSpecies.setUnderPlants(undergrowth);
			testSpecies.setFilter(filter);
			
			String testCommonName = testSpecies.getCommon();
				assertEquals(testCommonName,commonName);
			String testLatinName = testSpecies.getLatin();
				assertEquals(testLatinName,latinName);
			int testSpeciesID = testSpecies.getSpeciesID();
				assertEquals(testSpeciesID,speciesID);
			float testMinHeight = testSpecies.getMinHeight();  //assertEquals deprecated for float
				assertEquals((int)testMinHeight,(int)minHeight);			
			float testMaxHeight = testSpecies.getMaxHeight();  //assertEquals deprecated for float
				assertEquals((int)testMaxHeight,(int)maxHeight);		
			float testAvgRatio = testSpecies.getAvgRatio();  //assertEquals deprecated for float
				assertEquals((int)testAvgRatio,(int)avgRatio);		
			int testNumPlants = testSpecies.getNumPlants();
				assertEquals(testNumPlants,numPlants);
			Plant[] testCanopyPlants = testSpecies.getCanopyPlants(); //assertEquals deprecated for objects
				if (testCanopyPlants == canopy)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			Plant[] testUnderPlants = testSpecies.getUnderPlants(); //assertEquals deprecated for objects
				if (testUnderPlants == undergrowth)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);					
			Color testColour = testSpecies.getColour();
				assertEquals(testColour, colour);
			boolean testFilter = testSpecies.getFilter();
				assertEquals(testFilter, filter);	
			
			String newCommonName = new String("new common name");
			String newLatinName = new  String("new latin name");
			int newSpeciesID = 2;
			float newMinHeight = 15;
			float newMaxHeight = 50;
			float newAvgRatio = (float)15.0;
			int newNumPlants = 543;
			Color newColour = new Color(15, 25, 35, 1);
			boolean newFilter = false;

			testSpecies.setCommon(newCommonName);
			testSpecies.setLatin(newLatinName);
			testSpecies.setSpeciesID(newSpeciesID);
			testSpecies.setMinHeight(newMinHeight);
			testSpecies.setMaxHeight(newMaxHeight);
			testSpecies.setRatio(newAvgRatio);
			testSpecies.setNumPlants(newNumPlants);
			testSpecies.setColour(newColour);
			testSpecies.setFilter(newFilter);

			String testNewCommonName = testSpecies.getCommon();
				assertEquals(testNewCommonName,newCommonName);
			String testNewLatinName = testSpecies.getLatin();
				assertEquals(testNewLatinName,newLatinName);
			int testNewSpeciesID = testSpecies.getSpeciesID();
				assertEquals(testNewSpeciesID,newSpeciesID);
			float testNewMinHeight = testSpecies.getMinHeight();  //assertEquals deprecated for float
				assertEquals((int)testNewMinHeight,(int)newMinHeight);			
			float testNewMaxHeight = testSpecies.getMaxHeight();  //assertEquals deprecated for float
				assertEquals((int)testNewMaxHeight,(int)newMaxHeight);		
			float testNewAvgRatio = testSpecies.getAvgRatio();  //assertEquals deprecated for float
				assertEquals((int)testNewAvgRatio,(int)newAvgRatio);		
			int testNewNumPlants = testSpecies.getNumPlants();
				assertEquals(testNewNumPlants,newNumPlants);		
			Color testNewColour = testSpecies.getColour();
				assertEquals(testNewColour, newColour);
			boolean testNewFilter = testSpecies.getFilter();
				assertEquals(testNewFilter, newFilter);

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
			if (testMetric1 == true){
				testSpeedKPH = windTest.getSpeedKPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedKPH,(int)(testSpeed));
			}else{
				testSpeedMPH = windTest.getSpeedMPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedMPH,(int)(testSpeed));				
			}

			windTest.toggleMetric(); //Should be alternating the boolean value
			boolean testMetric2 = windTest.getMetric();
				assertEquals(testMetric1,!testMetric2);
			if (testMetric2 == true){
				testSpeedKPH = windTest.getSpeedKPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedKPH,(int)(testSpeed));
			}else{
				testSpeedMPH = windTest.getSpeedMPH();
				double testSpeed = windTest.getSpeed();
				assertEquals((int)testSpeedMPH,(int)(testSpeed));				
			}
	}
}
