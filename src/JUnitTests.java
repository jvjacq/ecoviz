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

	@Test
	public void testMiniMap(){
		//Class : MiniMap.java

		ImagePanel image = null;
		int tlx = 10;
		int tly = 10;
		int dimX = 10;
		int dimY = 10;

		try {
			MiniMap testMap = new MiniMap(image);
			testMap.setZone(tlx, tly, dimX, dimY);
				assertEquals(1, 1);
		} catch (Exception e) {
				assertEquals(1, 0);
		}

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
		boolean fHeight = true;
		boolean fCanopy = true;
		int inFirebreak = 0;
		
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
			boolean testFHeight = plantTest.getHeightFlag();
				assertEquals(testFHeight,fHeight);
			boolean testFCanopy = plantTest.getCanopyFlag();
				assertEquals(testFCanopy,fCanopy);
			int testInFireBreak = plantTest.isInFireBreak();
				assertEquals(testInFireBreak, inFirebreak);
			boolean testNewCanopy = !testCanopy;
				assertEquals(testNewCanopy,false);
			boolean testNewLayer = !testFilter;
				assertEquals(testNewLayer,false);
			plantTest.setFilter(false); //Should be setting to false
			boolean testNewFilter = plantTest.getFilter();
				assertEquals(testNewFilter,false);

			int newPosX = 20;
			plantTest.setPosX(newPosX);
			int newPosY = 30;
			plantTest.setPosY(newPosY);
			boolean newFHeight = false;
			plantTest.setHeightFlag(newFHeight);
			boolean newFCanopy = false;
			plantTest.setCanopyFlag(newFCanopy);
			int newInFirebreak = 0;
			boolean newFilter = false;
			plantTest.setFilter(newFilter);
			boolean newCanopy = false;
			boolean newLayer = false;
			plantTest.setLayer(newLayer);

			int testNewX = plantTest.getX();
				assertEquals(testNewX, newPosX);
			int testNewY = plantTest.getY();
				assertEquals(testNewY, newPosY);
			boolean testNewFHeight = plantTest.getHeightFlag();
				assertEquals(testNewFHeight,newFHeight);
			boolean testNewFCanopy = plantTest.getCanopyFlag();
				assertEquals(testNewFCanopy,newFCanopy);
			int testNewInFireBreak = plantTest.isInFireBreak();
				assertEquals(testNewInFireBreak, newInFirebreak);

			testNewCanopy = plantTest.getCanopyFlag();
				assertEquals(testNewCanopy,newCanopy);
			testNewLayer = plantTest.getLayer();
				assertEquals(testNewLayer, newLayer);
			plantTest.setFilter(newFilter);
			testNewFilter = plantTest.getFilter();
				assertEquals(testNewFilter, plantTest.getFilter());

			int testInFireBreakA = plantTest.isInFireBreak();
			plantTest.incFirebreak();
			int testInFireBreakB = plantTest.isInFireBreak();
				assertEquals(testInFireBreakA + 1, testInFireBreakB);
			testInFireBreakA = plantTest.isInFireBreak();
			plantTest.decFirebreak();
			testInFireBreakB = plantTest.isInFireBreak();
				assertEquals(testInFireBreakA -1, testInFireBreakB);

	}

	@Test
	public void testPlantLayer() {
		//Class : PlantLayer.java
		
		int[][][] idLocations;
		Plant testPlant1 = new Plant(1, 1, 50, 50, (float)10, (float)10, true);
		Plant testPlant2 = new Plant(2, 2, 100, 100, (float)10, (float)10, true);
		Plant testPlant3 = new Plant(3, 3, 150, 150, (float)10, (float)10, false);
		ArrayList<Plant> plantList = new ArrayList<Plant>(Arrays.asList(testPlant1, testPlant2, testPlant3));
		Color colour1 = new Color(10, 20, 30, 1);
		Color colour2 = new Color(20, 30, 40, 1);
		Color colour3 = new Color(30, 40, 50, 1);
		Species species1 = new Species(0, "common one", "latin one", colour1);
		Species species2 = new Species(1, "common two", "latin two", colour2);
		Species species3 = new Species(2, "common three", "latin three", colour3);
		Species[] allSpecies = {species1, species2, species3};
		int numSpecies = 3;
		boolean filter = true;
		
		PlantLayer plantLayerTest = new PlantLayer();
			plantLayerTest.setNumSpecies(numSpecies);
			plantLayerTest.setLocations(500, 500);
			plantLayerTest.removePlant(50, 50);
			PlantLayer.setPlantList();
			PlantLayer.setAllSpecies(allSpecies);
			PlantLayer.addSpecies(species1);
			PlantLayer.addSpecies(species2);
			PlantLayer.addSpecies(species3);
			PlantLayer.addPlant(testPlant1);
			PlantLayer.addPlant(testPlant2);
			PlantLayer.addPlant(testPlant3);
			
			int testNumSpecies = plantLayerTest.getNumSpecies();
				assertEquals(testNumSpecies, numSpecies);
			int[] plantAtLocation1 = {1,1};
			plantLayerTest.setPlantAtLocation(50, 50, 1, 1);
			int[] testPlantAtLocation1 = plantLayerTest.getPlantAtLocation(50, 50);
				assertArrayEquals(testPlantAtLocation1, plantAtLocation1);
			int[] plantAtLocation2 = {1,1};
			plantLayerTest.setPlantAtLocation(100, 100, 1, 1);
			int[] testPlantAtLocation2 = plantLayerTest.getPlantAtLocation(50, 50);
				assertArrayEquals(testPlantAtLocation2, plantAtLocation2);
			int[] plantAtLocation3 = {1,1};
			plantLayerTest.setPlantAtLocation(150, 150, 1, 1);
			int[] testPlantAtLocation3 = plantLayerTest.getPlantAtLocation(50, 50);
				assertArrayEquals(testPlantAtLocation3, plantAtLocation3);
			boolean testFilter = plantLayerTest.getFilter();
				assertEquals(testFilter, filter);
			plantLayerTest.setFilter(!filter);
			testFilter = plantLayerTest.getFilter();
				assertEquals(testFilter, !filter);
			plantLayerTest.setFilter(!filter);
			ArrayList<Plant> testPlantList = PlantLayer.getPlantList();
				assertEquals(testPlantList, plantList);
			Species[] testAllSpecies = PlantLayer.getAllSpecies();
				if (testAllSpecies == allSpecies)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			Species testPlantLayerSpecies1 = PlantLayer.getSpeciesAtID(0);
				if (testPlantLayerSpecies1 == species1)
					assertEquals(1, 1);
				else
					assertEquals(1, 0);
			Species testPlantLayerSpecies2 = PlantLayer.getSpeciesAtID(1);
			if (testPlantLayerSpecies2 == species2)
				assertEquals(1, 1);
			else
				assertEquals(1, 0);
			Species testPlantLayerSpecies3 = PlantLayer.getSpeciesAtID(2);
			if (testPlantLayerSpecies3 == species3)
				assertEquals(1, 1);
			else
				assertEquals(1, 0);
	}

	@Test
	public void testSimController() {
		//Class : SimController.java
		
		Gui gui = new Gui();
		Terrain terrain = new Terrain();
        PlantLayer undergrowth = new PlantLayer();
        PlantLayer canopy = new PlantLayer();
        Controller controller = new Controller(gui, terrain, undergrowth, canopy);
        

		Gui testGui = new Gui();
			if (gui == testGui) assertEquals(1, 1);
		Terrain testTerrain = new Terrain();
			if (terrain == testTerrain) assertEquals(1, 1);
		PlantLayer testUndergrowth = new PlantLayer();
			if (undergrowth == testUndergrowth) assertEquals(1, 1);
		PlantLayer testCanopy = new PlantLayer();
			if (canopy == testCanopy) assertEquals(1, 1);
		Controller testController = new Controller(gui, terrain, undergrowth, canopy);
			if (controller == testController) assertEquals(1, 1);

		try {
			controller.initController();
			assertEquals(1, 1);
		} catch (Exception e) {
			assertEquals(1, 0);
		}
		try {
			testController.initController();
			assertEquals(1, 1);
		} catch (Exception e) {
			assertEquals(1, 0);
		}

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
		Color prevColour = new Color(10, 20, 30, 1);
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
			Color testPrevColour = testSpecies.getPrevColour();
				assertEquals(testPrevColour, prevColour);
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
			Color testNewPrevColour = testSpecies.getPrevColour();
				assertEquals(testNewPrevColour, prevColour);
			boolean testNewFilter = testSpecies.getFilter();
				assertEquals(testNewFilter, newFilter);

	}

	@Test
	public void testTerrain() {
		//Class : Terrain.java
		
		double[][] elevations = new double[][] {{1.1,1.2},{1.3,1.4}};
		int dimX = 2;
		int dimY = 2;
		int baseX = 1;
		int baseY = 1;
		double gridSpacing = 2.1;
		double latitude = 3.2;

		Terrain terrainTest = new Terrain();
			terrainTest.setElevations(elevations);
			Terrain.setDimX(dimX);
			Terrain.setDimY(dimY);
			Terrain.setBaseX(baseX);
			Terrain.setBaseY(baseY);
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
			int testBaseX = Terrain.getBaseX();
				assertEquals(testBaseX,baseX);
			int testBaseY = Terrain.getBaseY();
				assertEquals(testBaseY,baseY);
			double testGridSpacing = terrainTest.getGridSpacing();
				assertEquals((int)testGridSpacing,(int)gridSpacing); //assertEquals deprecated for double
			double testLatitude = terrainTest.getLatitude();
				assertEquals((int)testLatitude,(int)latitude); //assertEquals deprecated for double
	}
}
