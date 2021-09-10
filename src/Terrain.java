/*
* File: Terrain.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 2.0
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 09/09/2021
* Status: In progress
*/

import java.awt.image.BufferedImage;
import java.awt.Color;

public class Terrain {
    private double[][] elevations;
    private static int dimX;
    private static int dimY;
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

    public double[][] getElevations(){
        return this.elevations;
    }

    public double getGridSpacing(){
        return this.gridSpacing;
    }

    public double getLattitude(){
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

//========================================================================
//      Create the greyscale top-down view
//========================================================================
    public BufferedImage deriveImg(){
        BufferedImage img = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
        double maxh = -10000.0f;
        double minh = 10000.0f;

        //determine the range of heights
        for (int y = 0; y <dimY;y++){
            for (int x = 0; x < dimX; x++){
                double h = elevations[y][x];
                if (h>maxh){maxh = h;}
                if (h<minh){minh = h;}  //Can be cut out and done in read loop - optimization
            }
        }

        //find normalized height value
        for (int y = 0; y <dimY;y++){
            for (int x = 0; x < dimX; x++){
                float val = (float) ((elevations[y][x]-minh)/(maxh-minh));
                Color col = new Color(val, val, val, 1.0f);
                img.setRGB(x, y, col.getRGB());
            }
        }
        return img;
    }

}