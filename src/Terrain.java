/*
* File: Terrain.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 2.0
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 09/09/2021
* Status: In progress
*/

public class Terrain {
    private double[][] elevations;
    private static int dimX;
    private static int dimY;
    private static int baseX;
    private static int baseY;
    private double gridSpacing = 0.0;
    private double latitude = 0.0;
    //private BufferedImage img;

    public Terrain(){
        //Do something
    }
    
    //Accessor Methods:
    public static int getDimX(){
        return Terrain.dimX;
    }

    public static int getDimY(){
        return Terrain.dimY;
    }

    public static int getBaseX(){
        return Terrain.baseX;
    }

    public static int getBaseY(){
        return Terrain.baseY;
    }

    public double[][] getElevations(){
        return this.elevations;
    }

    public double getGridSpacing(){
        return this.gridSpacing;
    }

    public double getLatitude(){
        return this.latitude;
    }
    
    //Mutator methods
    public void setElevations(double[][] elevations){
        this.elevations = elevations;
    }

    public void setGridSpacing(float spacing){
        this.gridSpacing = spacing;
    }

    public void setLatitude(float lat){
        this.latitude = lat;
    }

    public static void setDimX(int X){
        Terrain.dimX = X;
    }

    public static void setDimY(int Y){       
        Terrain.dimY = Y;
    }

    public static void setBaseX(int X){
        Terrain.baseX = X;
    }

    public static void setBaseY(int Y){       
        Terrain.baseY = Y;
    }

}
