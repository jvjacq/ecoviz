/*
* File: Plant.java
* MVC: Model
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

public class Plant implements Comparable<Plant>{
    
    private int speciesid;
    private int plantid;
    private int posx;
    private int posy;
    private double height;
    private double canopyRadius;
    
    //Filter variables to show/hide plant
    private boolean filter; //Set if inside filter radius
    private boolean layer;  //Set if in hidden PlantLayer
    private boolean fHeight, fCanopy; //Set if in correct height/radius ranges respectively
    private int inFirebreak; //Set +1 for each firebreak plant is in

    public Plant(int sid, int pid, int x, int y, float height, float rad, boolean canopy){
        //Default values
        this.speciesid = sid;
        this.plantid = pid;
        this.posx = x;
        this.posy = y;
        this.height = height;
        this.canopyRadius = rad;
        this.layer = canopy; //True if Plant in canopy, else false if in undergrowth (consistent throughout code)
        this.filter = true;
        this.fHeight = true;
        this.fCanopy = true;
        this.inFirebreak = 0;
    }

    //Accessor methods
    public int getSpeciesID(){
        return this.speciesid;
    }

    public int getID(){
        return this.plantid;
    }

    public int getX(){
        return this.posx;
    }

    public int getY(){
        return this.posy;
    }

    public double getHeight(){
        return this.height;
    }

    public double getCanopy(){
        return this.canopyRadius;
    }

    public boolean getFilter(){
        return this.filter;
    }

    public boolean getLayer(){
        return this.layer;
    }

    public boolean getHeightFlag(){
        return this.fHeight;
    }

    public boolean getCanopyFlag(){
        return this.fCanopy;
    }

    public int isInFireBreak(){
        return this.inFirebreak;
    }

    //Mutator methods
    public void setFilter(boolean b){
        this.filter = b;
    }

    public void setPosX(int X){
        this.posx = X;
    }

    public void setPosY(int Y){
        this.posy = Y;
    }

    public void setLayer(boolean b){
        this.layer = b;
    }

    public void setHeightFlag(boolean b){
        this.fHeight = b;
    }

    public void setCanopyFlag(boolean b){
        this.fCanopy = b;
    }

    public void incFirebreak(){
        this.inFirebreak++;
    }

    public void decFirebreak(){
        this.inFirebreak--;
    }

    //Compare method for sorting array of Plants according to Height
    @Override
    public int compareTo(Plant other) {
        if(this.height > other.getHeight()) return 1;
        else if(this.height < other.getHeight()) return -1;
        else return 0;
    }
}
