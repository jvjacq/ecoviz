/*
* File: Firebreak.java
* MVC: Model
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

import java.util.ArrayList;

public class Firebreak{

    //Static list of all firebreaks (local to Firebreak Model class)
    private static ArrayList<Firebreak> breaklist;
    //Region of x,y pairs covered by firebreak
    private ArrayList<int[]> region;
    //Max/min x and y values of region to improve search speed
    private int minX, maxX, minY, maxY;

    //List of layers (canopy - true/ undergrowth - false) that each int[] in below 'ids' corresponds to
    private ArrayList<Boolean> layers;
    //List id's ([0] - speciesID, [1] = plantID) for each plant part of firebreak
    private ArrayList<int[]> ids;

    public Firebreak(){
        //Default values
        if(breaklist == null) breaklist = new ArrayList<Firebreak>();
        region = new ArrayList<int[]>();
        ids = new ArrayList<int[]>();
        layers = new ArrayList<Boolean>();
        maxX = -1; minX = -1; minY = -1; maxY = -1;
    }

    //Accessor methods
    public ArrayList<int[]> getIDList(){
        return this.ids;
    }

    public ArrayList<Boolean> getLayers(){
        return this.layers;
    }

    public static ArrayList<Firebreak> getBreakList(){
        return Firebreak.breaklist;
    }

    //Add x and y pair to region covered by firebreak
    public void addToRegion(int x, int y){
        int[] pair = new int[2];
        if(x > maxX) maxX = x;
        if(x < minX || minX == -1) minX = x;
        if(y > maxY) maxY = y;
        if(y < minY || minY == -1) minY = y;
        pair[0] = x;
        pair[1] = y;
        if(!region.contains(pair)){
            region.add(pair);
        }
    }

    //Add a full firebreak to class's static list
    public static void addCompleted(Firebreak fb){
        breaklist.add(fb);
    }

    //Remove last-added firebreak from static list
    public static void removeLatest(){
        breaklist.remove(breaklist.size()-1);
    }

    //Method to check if a given plant falls within the firebreak region
    //If so, add plant ids and layer to list
    public boolean inFirebreak(Plant p){
        if((p.getX() > maxX) || (p.getX() < minX) || (p.getY() > maxY) || (p.getY() < minY)) return false; 
        for(int i = 0; i < region.size(); ++i){
            if((p.getX() == region.get(i)[0]) && (p.getY() == region.get(i)[1])){
                int[] pair = {p.getSpeciesID(), p.getID()};
                ids.add(pair);
                layers.add(p.getLayer());
                return true;
            }
        }
        return false;
    }
}
