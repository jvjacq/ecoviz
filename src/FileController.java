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

public class FileController {

    //========================================================================
    //      Read in the elevation values
    //========================================================================
    public void readElevation(Terrain terrain, String filename) throws FileNotFoundException{
        //String fileName = "src/data/S6000-6000-256.elv";
        //String fileName = "src/data/S2000-2000-512.elv";
        //String fileName = "src/data/S4500-4500-1024.elv";

        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        int dimX = Integer.parseInt(scanner.next());
        int dimY = Integer.parseInt(scanner.next());
        Terrain.setDimX(dimX);
        Terrain.setDimY(dimY);
        float gridSpacing = Float.parseFloat(scanner.next());
        float latitude = Float.parseFloat(scanner.next());

        double[][] elevations = new double[dimY][dimX];

        while (scanner.hasNext()){
            for (int y=0; y<dimY;y++){
                for (int x=0; x<dimX;x++){
                    elevations[y][x] = Double.parseDouble(scanner.next());
                }
            }
        }
        //
        terrain.setGridSpacing(gridSpacing);
        terrain.setLatitude(latitude);
        terrain.setElevations(elevations);
        //
        System.out.println("Elevation File has been read...");
        scanner.close();
    }

    //========================================================================
    //      Read in Plant layer
    //========================================================================
    public void readLayer(PlantLayer layer, String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        int numSpecies = filein.nextInt();//Integer.parseInt(filein.next()); 
        layer.setNumSpecies(numSpecies);

        Species[] list = PlantLayer.getSpeciesList();
        //set location array based on dimensions

        for (int i=0;i<numSpecies;++i){
            //Species average Details:
            int speciesID =  filein.nextInt();            
            list[speciesID].setSpeciesID(speciesID);
            list[speciesID].setMinHeight( Float.parseFloat( filein.next() ));
            list[speciesID].setMaxHeight( Float.parseFloat(filein.next() ));
            list[speciesID].setRatio( Float.parseFloat(filein.next() ));
            int numPlants = filein.nextInt();
            list[speciesID].setNumPlants(numPlants);

            Plant[] plantlist = new Plant[numPlants];

            for (int id = 0; id < numPlants;++id){
                float height, canopy;
                int xpos, ypos, zpos; 
                xpos = Math.round(Float.parseFloat(filein.next()));
                ypos = Math.round(Float.parseFloat(filein.next()));
                zpos = Math.round(Float.parseFloat(filein.next()));  //Intentionally unused
                height = Float.parseFloat(filein.next());
                canopy = Float.parseFloat(filein.next());
                
                plantlist[id] = new Plant(speciesID, id, xpos, ypos, height, canopy);
                
                layer.setPlantAtLocation(xpos, ypos, speciesID, id);
            }
            list[speciesID].setPlantList(plantlist);
        }

        System.out.println("Plant database file read in successfully");
        filein.close();
    }

    //========================================================================
    //      Read in Species
    //========================================================================
    public void readSpecies(String filename) throws FileNotFoundException{
        //File file = new File("src/data/S6000-6000-256.spc.txt");
        //File file = new File("src/data/S2000-2000-512.spc.txt");
        //File file = new File("src/data/S4500-4500-1024.spc.txt");
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        int totalSpecies = 0;
        while(filein.hasNextLine()){
            ++totalSpecies;
            filein.nextLine();
        }
        //System.out.println(totalSpecies);
        PlantLayer.setSpeciesList(new Species[totalSpecies]);
        filein.close();

        Species[] specieslist = PlantLayer.getSpeciesList();

        filein = new Scanner(file);
        for(int l = 0; l < totalSpecies; ++l){
            int id = filein.nextInt();
            //System.out.println(filein.nextLine());
            String[] names = filein.nextLine().split(" ");
            //System.out.println(names);
            specieslist[id] = new Species(names[0], names[1]);
        }
        filein.close();
        System.out.println("Species file processed.");
        System.out.println(PlantLayer.getSpeciesList());
    }
}
