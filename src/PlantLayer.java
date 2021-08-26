/*
* File: PlantLayer.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.2
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 25/08/2021
* Status: In progress
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
public class PlantLayer {

    //private double[][] canopy;
    //private double[][] undergrowth;
    //private ArrayList<Species> underPlants = new ArrayList<Species>();
    //private ArrayList<Species> canopyPlants = new ArrayList<Species>();
    //ID 0 = no PlantType
    //ID -1 = burnt?
    private int[][][] idLocations;
    private int[][] burnt;
    //new
    //private static Species[] specieslist;
    private int numSpecies; //per layer

    public PlantLayer() {
        idLocations = null;
        burnt = null;
    }

    public PlantLayer(String filename, int width, int height) throws FileNotFoundException{
      idLocations = new int[width][height][2]; //May need to be swapped, but for squares doesnt matter
      //Not implemented yet
      burnt = null;
      readLayer(filename);
  }

  public void readLayer(String filename) throws FileNotFoundException{
    File file = new File(filename);
    Scanner filein = new Scanner(file);

    numSpecies = filein.nextInt();//Integer.parseInt(filein.next()); 

    for (int i=0;i<numSpecies;++i){
        //Species average Details:
        int speciesID =  filein.nextInt(); 
        float minHeight = filein.nextFloat();
        float maxHeight = filein.nextFloat();
        float avgRatio = filein.nextFloat();
        int numPlants = filein.nextInt();
        Species species = new Species(speciesID,minHeight, maxHeight, avgRatio, numPlants);
        //specieslist[id].create(minHeight, maxHeight, avgRatio, numPlants);  //Add values to already existing species objects
        for (int y = 0; y < numPlants;++y){
          float height, canopy;
          int xpos, ypos, zpos; 
          xpos = Math.round(filein.nextFloat());
          ypos = Math.round(filein.nextFloat());
          zpos = Math.round(filein.nextFloat());  //Intentionally unused
          height = filein.nextFloat();
          canopy = filein.nextFloat();
          species.addPlant(new Plant(speciesID,y,height,canopy));  //Store 5 data val for each plant in the type.
          idLocations[xpos][ypos][0] = speciesID;
          idLocations[xpos][ypos][1] = speciesID;
        }
    }
    System.out.println("File read in successfully");
    filein.close();
  }

    /*========================================================================
    //      Read in Canopy
    //========================================================================
    public void readCanopy() throws FileNotFoundException{
      String fileName = "S2000-2000-512_canopy.pdb";
      File file = new File(fileName);
      Scanner scanner = new Scanner(file);

      int numSpecies = Integer.parseInt(scanner.next()); 
      
      for (int i=0;i<numSpecies;i++){
          //Plant Details:
          int id =  Integer.parseInt(scanner.next()); 
          float minHeight = Float.parseFloat(scanner.next());
          float maxHeight = Float.parseFloat(scanner.next());
          float avgRatio = Float.parseFloat(scanner.next());
          int numPlants = Integer.parseInt(scanner.next()); 
          Species species = new Species(id, minHeight, maxHeight, avgRatio, numPlants);  //Create PlantType object
          canopy = new double[numPlants][4];
          for (int y = 0; y < numPlants;y++){
              for (int x = 0; x<5; x++){
                  canopy[y][x] = Float.parseFloat(scanner.next());    //Store 5 data val for each plant in the type.
              }
          }
          species.setData(canopy);    //Set data
          canopyPlants.add(species);
          

      }
      scanner.close();
    }

    //========================================================================
    //      Read in Undergrowth
    //========================================================================
    public void readUndergrowth() throws FileNotFoundException{
      String fileName = "S2000-2000-512_undergrowth.pdb";
      File file = new File(fileName);
      Scanner scanner = new Scanner(file);

      int numSpecies = Integer.parseInt(scanner.next()); 
      
      for (int i=0;i<numSpecies;i++){
          //Plant Details:
          int id =  Integer.parseInt(scanner.next()); 
          float minHeight = Float.parseFloat(scanner.next());
          float maxHeight = Float.parseFloat(scanner.next());
          float avgRatio = Float.parseFloat(scanner.next());
          int numPlants = Integer.parseInt(scanner.next()); 
          Species species = new Species(id, minHeight, maxHeight, avgRatio, numPlants);  //Create PlantType object
          undergrowth = new double[numPlants][4];
          for (int y = 0; y < numPlants;y++){
              for (int x = 0; x<5; x++){
                  canopy[y][x] = Float.parseFloat(scanner.next());    //Store 5 data val for each plant in the type.
              }
          }
          species.setData(undergrowth);    //Set data
          underPlants.add(species);
      }
      scanner.close();
    }

    //========================================================================
    //      Read in Species
    //========================================================================
    public void readSpecies(){
      String file = "S2000-2000-512.spc.txt";
    }*/

    // Methods:
    public void removePlant(int dimx, int dimy) {
      idLocations[dimx][dimy] = 0;
    }

    // Mutator methods:
    public void setLayer(int[][] newLocations) {
      idLocations = newLocations;
    }

    // Accessor methods:
    public Species getPlant(int dimx, int dimy) {
       // return idLocations[dimx][dimy];
       return null;
    }

    public void getLayer() {
      //return idLocations;
      
    }

    public int[][] getBurnt(){
      return burnt;
    }

}
