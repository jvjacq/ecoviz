/*
* File: PlantLayer.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.1
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 18/08/2021
* Status: In progress
*/
public class PlantLayer {

    //ID 0 = no PlantType
    //ID -1 = burnt?
    private int[][] idLocations;
    private int[][] burnt;

    public PlantLayer() {
        idLocations = null;
        burnt = null;
    }

    // Methods:
    public void removePlant(int dimx, int dimy) {
      idLocations[dimx][dimy] = 0;
    }

    // Mutator methods:
    public void setLayer(int[][] newLocations) {
      idLocations = newLocations;
    }

    // Accessor methods:
    public PlantType getPlant(int dimx, int dimy) {
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
