/*
* File: FileController.java
* MVC: Controller
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

import java.io.*;
import java.util.*;
import java.awt.Color;

public class FileController implements Runnable{

    //Records total planst per pdb file
    private int totalPlants;
    //Used to scale all images up to 1024x1024 pixels
    private float scale;
    //Records max height and max radius across all plants
    private static float MAXHeight, MAXRadius;

    private int totalSpecies;
    private static float gridSpacing;

    //Variables to be used by a Thread passed a Filcontroller class
    private String threadFile;
    private PlantLayer threadLayer;
    private boolean threadCanopy;

    //Predefined colour list, to allow for contrast/remove clashing colours
    private Color[] colourSpread = {            
        new Color(61,153,153,127),
        new Color(91,229,229,127),
        new Color(107,153,61,127),
        new Color(160,229,91,127),
        new Color(107,61,153,127),
        new Color(160,91,229,127),
        new Color(153,130,61,127),
        new Color(229,195,91,127),
        new Color(61,153,84,127),
        new Color(91,229,126,127),
        new Color(61,84,153,127),
        new Color(91,126,229,127),
        new Color(153,61,130,127),
        new Color(229,91,195,127),
        new Color(153,95,61,127),
        new Color(229,143,91,127),
        new Color(141,153,61,127),
        new Color(212,229,91,127),
        new Color(72,153,61,127),
        new Color(109,229,91,127),
        new Color(61,153,118,127),
        new Color(91,229,177,127),
        new Color(61,118,153,127),
        new Color(91,177,229,127),
        new Color(72,61,153,127),
        new Color(109,91,229,127),
        new Color(141,61,153,127),
        new Color(212,91,229,127),
        new Color(153,61,95,127),
        new Color(229,91,143,127),
        new Color(153,78,61,127)};
    
        //Default constructor
    public FileController(){
        MAXHeight = 0;
        MAXRadius = 0;
    }

    //Constructor used when Thread created (When class to be passed to a Thread)
    public FileController(String filename, PlantLayer layer, boolean canopy){
        threadFile = filename;
        threadLayer = layer;
        threadCanopy = canopy;
        MAXHeight = 0;
        MAXRadius = 0;       
    }

    //Validates that the correct files have been chosen, based on their extensions.
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
        
        totalPlants = 0;
        PlantLayer.setPlantList();
        
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int baseX = Integer.parseInt(scanner.next());
        int baseY = Integer.parseInt(scanner.next());
        //Records original dimensions of images (in pixels, not metres)
        Terrain.setBaseX(baseX);
        Terrain.setBaseY(baseY);
        
        //Sets new dimensions to 1024x1024 (scale up smaller images)
        scale = 1024/baseX;
        int dimX = Math.round(baseX * scale);
        int dimY = Math.round(baseY * scale);
        
        //Dimensions to be used for every image from now on
        Terrain.setDimX(dimX);
        Terrain.setDimY(dimY);

        gridSpacing = Float.parseFloat(scanner.next());
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

        System.out.println("Elevation File has been read.");
        scanner.close();
    }

    //========================================================================
    //      Read in Plant layer
    //========================================================================
    public void readLayer(PlantLayer layer, String filename, boolean bCanopy) throws FileNotFoundException{
        //bCanopy represents layer - True:canopy, False:Undergrowth
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        scale = 1024/Terrain.getBaseX();
        
        int numSpecies = filein.nextInt();
        System.out.println(numSpecies);
        layer.setNumSpecies(numSpecies);
        //Set location ids array based on dimensions
        layer.setLocations(Terrain.getDimX(), Terrain.getDimY());
        Species[] list = PlantLayer.getAllSpecies();

        for (int i=0;i<numSpecies;++i){
            //Species average details:
            int speciesID =  filein.nextInt();
            float minHeight = Float.parseFloat( filein.next() );
            float maxHeight = Float.parseFloat(filein.next() );
            float avgRatio = Float.parseFloat(filein.next() );
            int numPlants = filein.nextInt();

            totalPlants += numPlants;
            compareAndSetMaxHeight(maxHeight);
            
            //Averages species details across both plant layers (canopy/undergrowth files)
            if((list[speciesID].getMinHeight() == -1) || (list[speciesID].getMinHeight() > minHeight)) list[speciesID].setMinHeight(minHeight);                            
            if((list[speciesID].getMaxHeight() == -1) || (list[speciesID].getMaxHeight() < maxHeight)) list[speciesID].setMaxHeight(maxHeight);            
            list[speciesID].setRatio((avgRatio + list[speciesID].getAvgRatio()) /2) ;
            list[speciesID].setNumPlants(list[speciesID].getNumPlants() + numPlants);
            
            //Create new empty plant list
            Plant[] plantlist = new Plant[numPlants];

            //Loop for each new plant - extract information, store it in a new plant object and then add plant object to list
            for (int id = 0; id < numPlants;++id){
                float height, canopy;
                int xpos, ypos, zpos;
                xpos = (int)Math.round(Float.parseFloat(filein.next())/gridSpacing*scale);
                ypos = (int)Math.round(Float.parseFloat(filein.next())/gridSpacing*scale);
                zpos = Math.round(Float.parseFloat(filein.next()));  //Intentionally unused
                zpos = 1*zpos; //irrelevant, just removed VSCode warning
                height = Float.parseFloat(filein.next());
                canopy = Float.parseFloat(filein.next())*scale;

                Plant plant = new Plant(speciesID, id, xpos, ypos, height, canopy, bCanopy);
                plantlist[id] = plant;
                PlantLayer.addPlant(plant);
                
                if(( !(xpos > Terrain.getDimX()-1) ) & ( !(ypos > Terrain.getDimY()-1) ) )
                    layer.setPlantAtLocation(xpos, ypos, speciesID, id);

                compareAndSetMaxRadius(canopy);
            }
            //Set species plantlist based on which layer being read (canopy: true/ undergrowth: false)
            if(bCanopy) list[speciesID].setCanopyPlants(plantlist);
            else list[speciesID].setUnderPlants(plantlist);
        }
        System.out.println("Plant database file read in successfully: " + totalPlants + " plants.");
        filein.close();
    }

    //========================================================================
    //      Read in Species
    //========================================================================
    public int readSpecies(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        //Quick initial run over file to determine size of specieslist
        totalSpecies = 0;
        while(filein.hasNextLine()){
            ++totalSpecies;
            filein.nextLine();
        }
        filein.close();

        Species[] specieslist = new Species[totalSpecies];
        filein = new Scanner(file);

        for(int l = 0; l < totalSpecies; ++l){
            //Parse line - separating species common name and latin name
            String[] names = new String[2];
            int id = filein.nextInt();
            String line = filein.nextLine();
            int comma = line.indexOf("“");
            line = line.substring(comma+1);
            comma = line.indexOf("”");
            names[0] = line.substring(0, comma);
            line = line.substring(comma+3);
            names[1] = line.substring(0,line.length()-2);	  
            Color col = colourSpread[id];

            //New species object added to overall specieslist
            Species species = new Species(id, names[0], names[1], col);
            specieslist[id] = species;
        }
        filein.close();
        PlantLayer.setAllSpecies(specieslist); //Static species list referenced by all classes after this point
        System.out.println("Species file processed.");

        //Returns total number of species to be used in creating filter checkboxes etc.
        return totalSpecies;
    }

    //Accessor methods
    public static float getMaxHeight(){
        return FileController.MAXHeight;
    }

    public static float getMaxRadius(){
        return FileController.MAXRadius;
    }

    public int getTotalSpecies(){
        return this.totalSpecies;
    }

    //Used to compare previous max height to new one and update if necessary
    synchronized public static void compareAndSetMaxHeight(float f){
        if(MAXHeight < f) FileController.MAXHeight = f;
    }

    //Used to compare previous max radius to new one and update if necessary
    synchronized public static void compareAndSetMaxRadius(float f){
        if(MAXRadius < f) FileController.MAXRadius = f;
    }

    //==================================================================
    //  Overrriden methods
    //==================================================================

    //Run method for use in concurrently loading the two pdb files (one read in a separate FileController thread)
    @Override
    public void run() {
        try{
            readLayer(this.threadLayer, this.threadFile, this.threadCanopy);
        }catch(FileNotFoundException e){
            System.out.println("Thread read failed.");
        }finally{
            System.out.println("Reading thread terminated.");
        }
    }
}
