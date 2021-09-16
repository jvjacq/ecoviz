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

public class FileController {

    //========================================================================
    //      Read in the elevation values
    //========================================================================
    public void readElevation(Terrain terrain, String filename) throws FileNotFoundException{
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

        terrain.setGridSpacing(gridSpacing);
        terrain.setLatitude(latitude);
        terrain.setElevations(elevations);

        System.out.println("Elevation File has been read...");
        scanner.close();
    }

    //========================================================================
    //      Read in Plant layer
    //========================================================================
    public void readLayer(PlantLayer layer, String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        int numSpecies = filein.nextInt();
        layer.setNumSpecies(numSpecies);

        Species[] list = new Species[numSpecies];
        //set location array based on dimensions

        for (int i=0;i<numSpecies;++i){
            //Species average Details:
            int speciesID =  filein.nextInt();
            float minHeight = Float.parseFloat( filein.next() );
            float maxHeight = Float.parseFloat(filein.next() );
            float avgRatio = Float.parseFloat(filein.next() );
            int numPlants = filein.nextInt();
            Species species = new Species(speciesID, minHeight, maxHeight, avgRatio, numPlants);
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
            species.setPlantList(plantlist);
            list[i] = species;
        }
        layer.setSpeciesList(list);
        System.out.println("Plant database file read in successfully");
        filein.close();
    }

    //========================================================================
    //      Read in Species
    //========================================================================
    public void readSpecies(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner filein = new Scanner(file);

        int totalSpecies = 0;
        while(filein.hasNextLine()){
            ++totalSpecies;
            filein.nextLine();
        }
        filein.close();

        String[][] specieslist = new String[totalSpecies][2];
        int[] colourlist = new int[totalSpecies];

        filein = new Scanner(file);
        for(int l = 0; l < totalSpecies; ++l){
            int id = filein.nextInt();
            String line = filein.nextLine();
            int comma = line.indexOf("“");
            line = line.substring(comma+1);
            comma = line.indexOf("”");
            specieslist[id][0] = line.substring(0, comma);
            line = line.substring(comma+3);
            specieslist[id][1] = line.substring(0,line.length()-2);
            Random r = new Random();		  
            Color col = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
            colourlist[id] = col.getRGB();
        }
        filein.close();

        Species.setColourList(colourlist);
        Species.setSpeciesList(specieslist);
        System.out.println("Species file processed.");
    }
}
