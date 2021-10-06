/*
* File: Species.java
* MVC: Model
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

import java.awt.Color;

public class Species {
    private String commonName;
    private String latinName;
    private int speciesid;
    private float minHeight;
    private float maxHeight;
    private float avgRatio;
    private int numPlants;
    //Plant list specific to each species (indexed by plantID)
    private Plant[] canopy;
    private Plant[] undergrowth;
    //Colour of species as displayed on Viz (can be changed)
    private Color colour;
    //Stores previous colour in case a colour revert is required (i.e species selection)
    private Color prevColour;
    //Set if species filter checkbox is ticked/currently selected
    private boolean filter;


    public Species(int id, String common, String latin, Color colour){
        //Default values/parameters
        this.speciesid = id;
        this.commonName = common;
        this.latinName = latin;
        this.colour = colour;
        this.prevColour = colour;
        this.minHeight = -1;
        this.maxHeight = -1;
        this.avgRatio = 0;
        this.numPlants = 0;
        this.filter = true;
    }

    //Mutator Methods:
    public void setCommon(String name){
        this.commonName = name;
    }

    public void setLatin(String name){
        this.latinName = name;
    }

    public void setSpeciesID(int id){
        this.speciesid = id;
    }

    public void setMinHeight(float minH){
        this.minHeight = minH;
    }

    public void setMaxHeight(float maxH){
        this.maxHeight = maxH;
    }

    public void setRatio(float avg){
        this.avgRatio = avg;
    }

    public void setNumPlants(int num){
        this.numPlants = num;
    }

    public void setCanopyPlants(Plant[] list){
        this.canopy = list;
    }

    public void setUnderPlants(Plant[] list){
        this.undergrowth = list;
    }

    public void setColour(Color col){
        this.colour = col;
    }

    public void setPrevColour(Color col){
        this.prevColour = col;
    }

    public void setFilter(boolean b){
        this.filter = b;
    }

    // Accessor Methods:
    public String getCommon(){
        return this.commonName;
    }

    public String getLatin(){
        return this.latinName;
    }

    public int getSpeciesID() {
        return this.speciesid;
    }

    public float getMinHeight() {
        return this.minHeight;
    }

    public float getMaxHeight() {
        return this.maxHeight;
    }

    public float getAvgRatio() {
        return this.avgRatio;
    }

    public int getNumPlants() {
        return this.numPlants;
    }

    public Plant[] getCanopyPlants(){
        return this.canopy;
    }

    public Plant[] getUnderPlants(){
        return this.undergrowth;
    }

    public Color getColour(){
        return this.colour;
    }

    public Color getPrevColour(){
        return this.prevColour;
    }

    public boolean getFilter(){
        return this.filter;
    }
}
