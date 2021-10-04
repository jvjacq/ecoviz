/*
* Previously: PlantType.java
* File: Species.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 2.0
* Created: ++++++++Owen insert date++++++++
* Last edited: 09/09/2021
* Status: In progress
* MVC - Model
*/
import java.awt.Color;

public class Species {

    //private static String[][] SPECIES;
    //private static int[] COLOURS;
    private String commonName;
    private String latinName;
    private int speciesid;
    private float minHeight;
    private float maxHeight;
    private float avgRatio;
    private int numPlants;
    private Plant[] canopy;
    private Plant[] undergrowth;
    private Color colour;
    private Color prevColour;
    private boolean filter;


    public Species(int id, String common, String latin, Color colour){
        this.speciesid = id;
        this.commonName = common;
        this.latinName = latin;
        this.colour = colour;
        this.minHeight = -1;
        this.maxHeight = -1;
        this.avgRatio = 0;
        this.numPlants = 0;
        this.filter = true;
    }

    /*public Species(int id, float minH, float maxH, float avg, int num){
        this.speciesid = id;
        this.minHeight = minH;
        this.maxHeight = maxH;
        this.avgRatio = avg;
        this.numPlants = num;
        //this.commonName = Species.SPECIES[id][0];
        //this.latinName = Species.SPECIES[id][1];
    }*/

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

    /*public static void setSpeciesList(String[][] list){
        Species.SPECIES = list;
    }

    public static void setColourList(int[] list){
        Species.COLOURS = list;
    }*/

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

    /*public static String[][] getSPECIES(){
        return Species.SPECIES;
    }

    public static int[] getCOLOURS(){
        return Species.COLOURS;
    }*/

    @Override
    public String toString() {
        //Way to access tallest/shortest?
        return "Common name:\n" + this.commonName + "\nLatin name:\n" + this.latinName + "\nShortest plant:\n" + this.minHeight + "\nTallest plant:\n" + this.maxHeight + "\nAvg. Ration of Canopy/Height:\n" + this.avgRatio + "\nTotal number of individuals:\n" + this.numPlants;
    }
}
