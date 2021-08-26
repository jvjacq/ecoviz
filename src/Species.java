/*
* Previously: PlantLayer.java
* File: Species.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.3
* Created: ++++++++Owen insert date++++++++
* Last edited: 26/08/2021
* Status: In progress
*/

public class Species {
    private String commonName;
    private String latinName;
    private int id;
    private float minHeight;
    private float maxHeight;
    private float avgRadius;
    private int numPlants;
    private Plant[] list;

    public Species(int id, float minH, float maxH, float rad, int numP) {
        
        this.id = id;
        minHeight = minH;
        maxHeight = maxH;
        avgRadius = rad;
        numPlants = numP;
        list = new Plant[numP];
        
    } 
    /*public Species(int id, String cname, String lname) {
        
        this.id = id;
        commonName = cname;
        latinName = lname;
        minHeight = 0.0f;
        maxHeight = 0.0f;
        avgRadius = 0.0f;
        numPlants = 0;
        
    } 
    //New idea im working on

    public void create(float minH, float maxH, float rad, int numP){
        minHeight = minH;
        maxHeight = maxH;
        avgRadius = rad;
        numPlants = numP;
    }*/

    
    //Mutator Methods:
    public void addPlant(Plant plant){
        list[plant.getID()] = plant;
    }

    // Accessor Methods:
    public int getID() {
        return id;
    }

    public String getDesc() {
        String out = "Common Name: " + commonName + "/n";
        out += "Common Name: " + commonName + "/n";
        out += "Latin Name: " + latinName + "/n";

        return out;
    }

    public Plant[] getPlants(){
        return list;
    }
}
