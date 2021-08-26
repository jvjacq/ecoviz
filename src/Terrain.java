/*
* File: Terrain.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.2
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 25/08/2021
* Status: In progress
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.Color;


//Create 2D Greyscale enviro
public class Terrain {
    private double[][] elv;
    private int dimX,dimY;
    private double gridSpacing = 0.0;
    private double latitude = 0.0;
    private BufferedImage img;

    public Terrain(){

    }
//Accessor Methods:
    public int getDimX(){
        return dimX;
    }
    public int getDimY(){
        return dimY;
    }
    public BufferedImage getImg(){
        return img;
    }

//========================================================================
//      Read in the elevation values
//========================================================================
    public void readElevation() throws FileNotFoundException{
        String fileName = "src/data/S2000-2000-512.elv";
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        dimX = Integer.parseInt(scanner.next());
        dimY = Integer.parseInt(scanner.next());
        gridSpacing = Float.parseFloat(scanner.next());
        latitude = Float.parseFloat(scanner.next());

        elv = new double[dimY][dimX];

        while (scanner.hasNext()){
            for (int y=0; y<dimY;y++){
                for (int x=0; x<dimX;x++){
                    elv[y][x] = Double.parseDouble(scanner.next());
                }
            }
        }
        System.out.println("Elevation File has been read...");
        scanner.close();
    }

//========================================================================
//      Create the greyscale top-down view
//========================================================================
    public void deriveImg(){
        img = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
        double maxh = -10000.0f;
        double minh = 10000.0f;

        //determine the range of heights
        for (int y = 0; y <dimY;y++){
            for (int x = 0; x < dimX; x++){
                double h = elv[y][x];
                if (h>maxh){maxh = h;}
                if (h<minh){minh = h;}
            }
        }

        //find normalized height value
        for (int y = 0; y <dimY;y++){
            for (int x = 0; x < dimX; x++){
                float val = (float) ((elv[y][x]-minh)/(maxh-minh));
                Color col = new Color(val, val, val, 1.0f);
                img.setRGB(x, y, col.getRGB());
            }
        }
    }

}