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

    private String threadFile;
    private PlantLayer threadLayer;
    private boolean threadCanopy;

    public FileController(){
        //
    }

    public FileController(String filename, PlantLayer layer, boolean canopy){
        threadFile = filename;
        threadLayer = layer;
        threadCanopy = canopy;
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

        int totalSpecies = 0;
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
            Random r = new Random();		  
            Color col = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 0.5f);
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
