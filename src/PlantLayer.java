/*
* File: PlantLayer.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.3
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 26/08/2021
* Status: In progress
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.Math;

public class PlantLayer{

    //ID 0 = no PlantType
    //ID -1 = burnt?
    private int[][][] idLocations;
    //private int[][] burnt;
    private static Species[] specieslist;
    private int numSpecies; //per layer

    //private BufferedImage img;
    // Constructors:
    
    public PlantLayer(){
      idLocations = new int[Terrain.getDimX()][Terrain.getDimY()][2];
    }

    //Mutator Methods:
    public void setNumSpecies(int num){
        this.numSpecies = num;
    }

    public void setPlantAtLocation(int x, int y, int speciesid, int plantid){
        this.idLocations[y][x][0] = speciesid;
        this.idLocations[y][x][1] = plantid;
    }

    public static void setSpeciesList(Species[] list){
      PlantLayer.specieslist = list;
    }

    // Accessor Methods
    public int[][][] getLocations(){
        return this.idLocations;
    }

    public int getNumSpecies(){
      return this.numSpecies;
    }

    public int[] getPlantAtLocation(int x, int y){
      return this.idLocations[y][x];
    }
    
    public static Species[] getSpeciesList(){
      return PlantLayer.specieslist;
    }

    // Methods:

    //========================================================================
    //      Create the colourful circles
    //========================================================================
    public void deriveImg(){
      int dimx = Terrain.getDimX();
      int dimy = Terrain.getDimY();

      BufferedImage img = new BufferedImage(dimx,dimy,BufferedImage.TYPE_INT_ARGB);
      Graphics2D imgGraphics = img.createGraphics();

      imgGraphics.setComposite(AlphaComposite.Clear);
      imgGraphics.fillRect(0, 0, dimx, dimy);

      imgGraphics.setComposite(AlphaComposite.Src);
      for (Species s: specieslist){
        Random r = new Random();
        imgGraphics.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
        for(Plant p: s.getPlants()){
          imgGraphics.fillOval(p.getX(),p.getY(),(int)p.getCanopy()*2,(int)p.getCanopy()*2);
        }
      }
      
    }

    public void removePlant(int dimx, int dimy) {
      idLocations[dimx][dimy][0] = 0;
      idLocations[dimx][dimy][1] = -1;  //Burnt would be double -1?
    }

    // Mutator methods:
    public void setLayer(int[][][] newLocations) {
      idLocations = newLocations;
    }

    // Accessor methods:
    public Species getPlant(int dimx, int dimy) {
       // return idLocations[dimx][dimy];
       return null;
    }

    public void getLayer() {
      //return idLocations;
      
    }

    /*public int[][] getBurnt(){
      return burnt;
    }

    public BufferedImage getImg(){
      return img;
    }*/

}
