/*
* File: Plant.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.0
* Created: 25/08/2021
* Last edited: 25/08/2021
* Status: In progress
*/
public class Plant {
    
    private int speciesID;
    private int id;
    private double height;
    private double canopy;
    private boolean burnt;

    public Plant(int speciesID, int plantID, float height, float canopy){
        this.speciesID = speciesID;
        id = plantID;
        this.height = height;
        this.canopy = canopy;
        burnt = false;
    }

    //Accessor methods
    public int getSpeciesID(){
        return speciesID;
    }

    public int getID(){
        return id;
    }

    public double getHeight(){
        return height;
    }

    public double getCanopy(){
        return canopy;
    }

    //Mutator methdos
    public void toggleburn(){
        burnt = !burnt;
    }

}
