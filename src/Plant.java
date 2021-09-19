/*
* File: Plant.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 2.0
* Created: 25/08/2021
* Last edited: 09/09/2021
* Status: In progress
* MVC - Model
*/


public class Plant implements Comparable<Plant>{
    
    private int speciesid;
    private int plantid;
    private int posx;
    private int posy;
    private double height;
    private double canopyRadius;
    private boolean filter;

    public Plant(int sid, int pid, int x, int y, float height, float rad){
        this.speciesid = sid;
        this.plantid = pid;
        this.posx = x;
        this.posy = y;
        this.height = height;
        this.canopyRadius = rad;
        this.filter = false;
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

    //Mutator methods
    public void toggleFilter(){
        this.filter = !(this.filter);
    }

    public void setPosX(int X){
        this.posx = X;
    }

    public void setPosY(int Y){
        this.posy = Y;
    }

    @Override
    public int compareTo(Plant other) {
        if(this.height == other.getHeight()) return 1;
        else return -1;
    }
}
