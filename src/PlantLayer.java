/*
* File: PlantLayer.java
* MVC: Model
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

import java.util.ArrayList;

public class PlantLayer{

    //Array stores ids (speciesID, plantID) at the plants x,y coordinates
    private int[][][] idLocations;
    //Static list. Contains all plants in both undergrowth & canopy combined and sorted
    private static ArrayList<Plant> plantlist;
    //Static list. Contains all species in both undergrowth & canopy combined and sorted
    private static Species[] allSpecies;
    private int numSpecies; //per layer
    
    public PlantLayer(){
      //
    }

    //Mutator Methods:
    public void setNumSpecies(int num){
        this.numSpecies = num;
    }

    public void setPlantAtLocation(int x, int y, int speciesid, int plantid){
        this.idLocations[x][y][0] = speciesid;
        this.idLocations[x][y][1] = plantid;
    }

    //Initialise idLocations
    public void setLocations(int dimx, int dimy){
      idLocations = new int[dimx][dimy][2];
    }

    //And id of -1 specifies a removed plant
    public void removePlant(int dimx, int dimy) {
      idLocations[dimx][dimy][0] = 0;
      idLocations[dimx][dimy][1] = -1;
    }

    //Initialisation methods
    public static void setPlantList() {
      PlantLayer.plantlist = new ArrayList<Plant>();
    }

    public static void setAllSpecies(Species[] list) {
      PlantLayer.allSpecies = list;
    }

    //Add species to static list
    public static void addSpecies(Species s){
      PlantLayer.allSpecies[s.getSpeciesID()] = s;
    }

    //Add plant to static list (thread safe)
    synchronized public static void addPlant(Plant plant){
      PlantLayer.plantlist.add(plant);
    }

    // Accessor Methods
    public int[][][] getLocations(){
        return this.idLocations;
    }

    public int getNumSpecies(){
      return this.numSpecies;
    }

    public int[] getPlantAtLocation(int x, int y){
      return this.idLocations[x][y];
    }

    public static ArrayList<Plant> getPlantList() {
      return PlantLayer.plantlist;
    }

    public static Species[] getAllSpecies(){
      return PlantLayer.allSpecies;
    }

    public static Species getSpeciesAtID(int id){
      return PlantLayer.allSpecies[id];
    }

}
