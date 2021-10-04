/*
* File: FileController.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.0
* Created: 09/09/2021
* Last edited: 09/09/2021
* Status: In progress
* MVC - Controller
*/

import java.io.*;
import java.util.*;
import java.awt.Color;

public class FileController implements Runnable{

    private int totalPlants;
    private float scale;
    private static float MAXHeight, MAXRadius;
    private int totalSpecies;

    private String threadFile;
    private PlantLayer threadLayer;
    private boolean threadCanopy;
    private Color[] colourSpread = {            
        new Color(61,153,153),
        new Color(91,229,229),
        new Color(107,153,61),
        new Color(160,229,91),
        new Color(107,61,153),
        new Color(160,91,229),
        new Color(153,130,61),
        new Color(229,195,91),
        new Color(61,153,84),
        new Color(91,229,126),
        new Color(61,84,153),
        new Color(91,126,229),
        new Color(153,61,130),
        new Color(229,91,195),
        new Color(153,95,61),
        new Color(229,143,91),
        new Color(141,153,61),
        new Color(212,229,91),
        new Color(72,153,61),
        new Color(109,229,91),
        new Color(61,153,118),
        new Color(91,229,177),
        new Color(61,118,153),
        new Color(91,177,229),
        new Color(72,61,153),
        new Color(109,91,229),
        new Color(141,61,153),
        new Color(212,91,229),
        new Color(153,61,95),
        new Color(229,91,143),
        new Color(153,78,61)};
        //new Color(193,0,32),
        //new Color(127,24,13),
        //new Color(129,112,102),
        //new Color(206,162,98),
        //new Color(166,189,215),
        //new Color(241,58,19),
    /*private Color[] colourSpread = {            
        new Color(255,179,0),
        new Color(128,62,117),
        new Color(255,104,0),
        
        
        
        new Color(0,125,125),
        new Color(246,118,142),
        new Color(0,83,138),
        new Color(255,122,92),
        new Color(83,55,122),
        new Color(255,142,0),
        new Color(179,40,81),
        new Color(244,200,0),
        
        new Color(147,170,0),
        new Color(89,51,21),
        new Color(255,179,0),
        new Color(128,62,117),
        new Color(255,104,0),
        
        
        
        new Color(0,125,125),
        new Color(246,118,142),
        new Color(0,83,138),
        new Color(255,122,92),
        new Color(83,55,122),
        new Color(255,142,0),
        new Color(179,40,81),
        new Color(244,200,0),
        
        new Color(147,170,0),
        new Color(89,51,21)
        };*/
    public static float getMaxHeight(){
        return FileController.MAXHeight;
    }

    public static float getMaxRadius(){
        return FileController.MAXRadius;
    }

    public int getTotalSpecies(){
        return this.totalSpecies;
    }

    synchronized public static void compareAndSetMaxHeight(float f){
        if(MAXHeight < f) FileController.MAXHeight = f;
    }

    synchronized public static void compareAndSetMaxRadius(float f){
        if(MAXRadius < f) FileController.MAXRadius = f;
    }

    public FileController(){
        MAXHeight = 0;
        MAXRadius = 0;
    }

    public FileController(String filename, PlantLayer layer, boolean canopy){
        threadFile = filename;
        threadLayer = layer;
        threadCanopy = canopy;
        MAXHeight = 0;
        MAXRadius = 0;
        //new Color(153,61,61),
        //new Color(229,91,91),
        
    }

    public boolean validateFiles(File[] list, String[] filenames){
        boolean elv = false, spc = false, undergrowth = false, canopy = false;
        for(File file: list){
            if (file.toString().contains(".elv")){
                filenames[0] = file.toString();
                elv = true;
              }
              if (file.toString().contains(".spc.txt")){
                filenames[1] = file.toString();
                spc = true;
              }
              if (file.toString().contains("undergrowth.pdb")){
                filenames[2] = file.toString();
                undergrowth = true;
              }
              if (file.toString().contains("canopy.pdb")){
                filenames[3] = file.toString();
                canopy = true;
              }
        }
        return elv & spc & undergrowth & canopy;
    }

    //========================================================================
    //      Read in the elevation values
    //========================================================================
    public void readElevation(Terrain terrain, String filename) throws FileNotFoundException{
        //
        totalPlants = 0;
        PlantLayer.setPlantList();
        //
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int baseX = Integer.parseInt(scanner.next());
        int baseY = Integer.parseInt(scanner.next());
        Terrain.setBaseX(baseX);
        Terrain.setBaseY(baseY);
        //
        scale = 1024/baseX;
        int dimX = Math.round(baseX * scale);
        int dimY = Math.round(baseY * scale);
        //
        Terrain.setDimX(dimX);
        Terrain.setDimY(dimY);

        float gridSpacing = Float.parseFloat(scanner.next());
        float latitude = Float.parseFloat(scanner.next());
        double[][] elevations = new double[dimY][dimX];

        while (scanner.hasNext()){
            for (int y=0; y<baseY;y++){
                for (int x=0; x<baseX;x++){
                    elevations[y][x] = Double.parseDouble(scanner.next());
                }
            }
        }

        terrain.setGridSpacing(gridSpacing);
        terrain.setLatitude(latitude);
        terrain.setElevations(elevations);

        System.out.println("Elevation File has been read...");
        scanner.close();
    }

    //========================================================================
    //      Read in Plant layer
    //========================================================================
    public void readLayer(PlantLayer layer, String filename, boolean bCanopy) throws FileNotFoundException{
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        scale = 1024/Terrain.getBaseX();
        
        int numSpecies = filein.nextInt();
        System.out.println(numSpecies);
        layer.setNumSpecies(numSpecies);
        //set location array based on dimensions
        layer.setLocations(Terrain.getDimX(), Terrain.getDimY());
        Species[] list = PlantLayer.getAllSpecies();

        for (int i=0;i<numSpecies;++i){
            //Species average Details:
            int speciesID =  filein.nextInt();
            float minHeight = Float.parseFloat( filein.next() );
            float maxHeight = Float.parseFloat(filein.next() );
            float avgRatio = Float.parseFloat(filein.next() );
            int numPlants = filein.nextInt();
            //
            totalPlants += numPlants;
            compareAndSetMaxHeight(maxHeight);
            //
            //Species species = new Species(speciesID, minHeight, maxHeight, avgRatio, numPlants);
            if((list[speciesID].getMinHeight() == -1) || (list[speciesID].getMinHeight() > minHeight)) list[speciesID].setMinHeight(minHeight);                            
            if((list[speciesID].getMaxHeight() == -1) || (list[speciesID].getMaxHeight() < maxHeight)) list[speciesID].setMaxHeight(maxHeight);            
            list[speciesID].setRatio((avgRatio + list[speciesID].getAvgRatio()) /2) ;
            list[speciesID].setNumPlants(list[speciesID].getNumPlants() + numPlants);
            //species.setColour(Species.getCOLOURS()[speciesID]);
            //
            Plant[] plantlist = new Plant[numPlants];

            for (int id = 0; id < numPlants;++id){
                float height, canopy;
                int xpos, ypos, zpos;
                xpos = (int)Math.round(Float.parseFloat(filein.next())/0.9144*scale);
                ypos = (int)Math.round(Float.parseFloat(filein.next())/0.9144*scale);
                zpos = Math.round(Float.parseFloat(filein.next()));  //Intentionally unused
                height = Float.parseFloat(filein.next());
                canopy = Float.parseFloat(filein.next())*scale;

                Plant plant = new Plant(speciesID, id, xpos, ypos, height, canopy, bCanopy);
                //plant.setLayer(bCanopy);
                plantlist[id] = plant;
                //
                PlantLayer.addPlant(plant);
                //
                if(( !(xpos > Terrain.getDimX()-1) ) & ( !(ypos > Terrain.getDimY()-1) ) )
                    layer.setPlantAtLocation(xpos, ypos, speciesID, id);
                //
                compareAndSetMaxRadius(canopy);
            }
            if(bCanopy) list[speciesID].setCanopyPlants(plantlist);
            else list[speciesID].setUnderPlants(plantlist);
            //list[i] = species;
        }
        //PlantLayer.setSpeciesList(list);
        System.out.println("Plant database file read in successfully " + totalPlants);
        filein.close();
    }

    //========================================================================
    //      Read in Species
    //========================================================================
    public int readSpecies(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        totalSpecies = 0;
        while(filein.hasNextLine()){
            ++totalSpecies;
            filein.nextLine();
        }
        filein.close();

        //String[][] specieslist = new String[totalSpecies][2];
        Species[] specieslist = new Species[totalSpecies];
        //int[] colourlist = new int[totalSpecies];

        filein = new Scanner(file);
        for(int l = 0; l < totalSpecies; ++l){
            String[] names = new String[2];
            int id = filein.nextInt();
            String line = filein.nextLine();
            int comma = line.indexOf("“");
            line = line.substring(comma+1);
            comma = line.indexOf("”");
            names[0] = line.substring(0, comma);
            line = line.substring(comma+3);
            names[1] = line.substring(0,line.length()-2);
            //Random r = new Random();		  
            Color col = colourSpread[id];
            //new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 0.5f);
            Species species = new Species(id, names[0], names[1], col);
            //colourlist[id] = col.getRGB();
            specieslist[id] = species;
        }
        filein.close();

        //Species.setColourList(colourlist);
        PlantLayer.setAllSpecies(specieslist);
        System.out.println("Species file processed.");

        return totalSpecies;
    }

    @Override
    public void run() {
        try{
            readLayer(this.threadLayer, this.threadFile, this.threadCanopy);
        }catch(FileNotFoundException e){
            System.out.println("Thread read failed.");
        }finally{
            System.out.println("Thread terminated.");
        }
    }
}
