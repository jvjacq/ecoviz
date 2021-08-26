/*
* File: PlantLayer.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.2
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 26/08/2021
* Status: In progress
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
public class PlantLayer {

    //ID 0 = no PlantType
    //ID -1 = burnt?
    private int[][][] idLocations;
    private int[][] burnt;
    //new
    private static Species[] specieslist;
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

  //========================================================================
  //      Read in Plant layer
  //========================================================================
  public void readLayer(String filename) throws FileNotFoundException{
    File file = new File(filename);
    Scanner filein = new Scanner(file);

    numSpecies = filein.nextInt();//Integer.parseInt(filein.next()); 

    for (int i=0;i<numSpecies;++i){
        //Species average Details:
        int speciesID =  filein.nextInt();
        //System.out.println(speciesID); 
        float minHeight = Float.parseFloat(filein.next());
        float maxHeight = Float.parseFloat(filein.next());
        float avgRatio = Float.parseFloat(filein.next());
        int numPlants = filein.nextInt();
        Species species = new Species(speciesID,minHeight, maxHeight, avgRatio, numPlants);
        //specieslist[id].create(minHeight, maxHeight, avgRatio, numPlants);  //Add values to already existing species objects
        for (int y = 0; y < numPlants;++y){
          float height, canopy;
          int xpos, ypos, zpos; 
          xpos = Math.round(Float.parseFloat(filein.next()));
          ypos = Math.round(Float.parseFloat(filein.next()));
          zpos = Math.round(Float.parseFloat(filein.next()));  //Intentionally unused
          height = Float.parseFloat(filein.next());
          canopy = Float.parseFloat(filein.next());
          species.addPlant(new Plant(speciesID,y,height,canopy));  //Store 5 data val for each plant in the type.
          //
          PlantLayer.specieslist[speciesID] = species;
          //
          idLocations[xpos][ypos][0] = speciesID;
          idLocations[xpos][ypos][1] = speciesID;
        }
    }
    System.out.println("One .pdb file read in successfully");
    filein.close();
  }

    //========================================================================
    //      Read in Species
    //========================================================================
    public static void readSpecies() throws FileNotFoundException{
      File file = new File("src/data/S2000-2000-512.spc.txt");
      Scanner filein = new Scanner(file);
      int totalSpecies = -1;
      while(filein.hasNextLine()){
        ++totalSpecies;
        System.out.println(totalSpecies);
        System.out.println(filein.nextLine());
      }
      if(totalSpecies >= 0){ PlantLayer.specieslist = new Species[totalSpecies+1]; }
      filein.close();

      filein = new Scanner(file);
      String[] names = new String[2];
      for(int l = 0; l < totalSpecies; ++l){
        names = filein.nextLine().split("\" \"");
        //
        //missing
        //
      } //Should be counting down - does not read them into Species objects yet
      filein.close();
      System.out.println("Species file processed.");
    }

    // Methods:
    public void removePlant(int dimx, int dimy) {
      idLocations[dimx][dimy][0] = 0;
      idLocations[dimx][dimy][1] = -1;  //Burnt would be double -1?
    }

    // Mutator methods:
    public void setLayer(int[][][] newLocations) {
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
