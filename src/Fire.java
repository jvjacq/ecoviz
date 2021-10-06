/*
* File: Fire.java
* MVC: Model
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.lang.Math;
import java.util.ArrayList;

public class Fire {
    //Dimensions of all grids
    private int dimX;
    private int dimY;
    //Wind direction 1 - 8 representing (North, North East, etc.)
    private int windDirection;
    private float windForce;    
    private int[][] fireGrid,plantGrid,burntPlants;
    private int[][][] underPlants, canopyPlants;
    private int[] traversal;
    private BufferedImage fireImage, burntImage;
    private ArrayList<Integer> permute; // permuted list of integers in range [0, dimx*dimy)
    
    private boolean showPath;
    private boolean showBurnt;
    private Species[] specieslist;
    private Color ashColor, burntColor, igniteColor;
    private double chance, windChance;

    // Paramaterised Constructor
    public Fire(int dimX, int dimY, int[][][] plantGrid1, int[][][] plantGrid2) {

        // Passed Parameters
        this.dimX = dimX;
        this.dimY = dimY;
        this.underPlants = plantGrid1;
        this.canopyPlants = plantGrid2;

        // Global Variables - default values
        chance = 33; // Chance of fire moving on ground
        windChance = 50; // Chance of wind influencing fire spread 
        windDirection = 1;  //North
        windForce = 0; // Fraction of total possible speed
        specieslist = PlantLayer.getAllSpecies();
        ashColor = new Color(235, 137, 52);
        burntColor = new Color(194, 34, 2);
        igniteColor = new Color(252, 169, 25);
        showPath = true;
        showBurnt = true;
        traversal = new int[dimX * dimY];
        fireGrid = new int[dimX][dimY];
        plantGrid = new int[dimX][dimY];

        burntPlants = new int[dimX][dimY];

        // Create randomly permuted list of indices for traversal
        genPermute();
        //Generate grid for fire to check/spread over
        genGrid();
    }

    // Populates a grid of the trees (represented as 1s) on the map (plantGrid)
    public void genGrid() {
        for (int x = 0; x < dimX; x++) {
            for (int y = 0; y < dimY; y++) {
                if (canopyPlants[x][y][1] > -1) {
                    try {
                        int specId = canopyPlants[x][y][0]; // Species ID
                        int plantID = canopyPlants[x][y][1]; // Plant ID

                        Plant[] uPlants = specieslist[specId].getCanopyPlants();
                        double rad = uPlants[plantID].getCanopy();

                        double temp = Math.round(rad);
                        int boundary = (int) temp + 1;

                        for (int j = y - boundary; j < (y + boundary + 1); j++) {
                            for (int i = x - boundary; i < (x + boundary + 1); i++) {
                                if (j < Terrain.getDimY() && j > 0 && i < Terrain.getDimX() && i > 0) {
                                    double dist = Math.sqrt(Math.pow((x - i), 2) + Math.pow((y - j), 2));
                                    if (dist <= rad) {
                                        plantGrid[i][j] = 1;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        System.out.println("Plants Populated for Fire Sim");
    }

    // Performs a single iteration of fire movement
    public void simulate(int segmentLow, int segmentHigh) {
        for (int i = segmentLow; i < segmentHigh; i++) {
            getPermute(i, traversal);

            // Move fire where possible:
            if (isFire(traversal[0], traversal[1]) == true) {
                moveFire(traversal[0], traversal[1]);

            }
        }
    }

    // Returns True/False if fire is present at location (x,y)
    public boolean isFire(int x, int y) {
        boolean firePresent = false;
        if (fireGrid[x][y] == 1) {
            firePresent = true;
        }
        return firePresent;
    }

    // Add fire from user input
    public void addFire(int x, int y, double rad) {
        // Add Fire To grid
        try {           
            double temp = Math.round(rad);
            int boundary = (int) temp + 1;

            for (int j = y - boundary; j < (y + boundary + 1); j++) {
                for (int i = x - boundary; i < (x + boundary + 1); i++) {
                    if (j < Terrain.getDimY() && j > 0 && i < Terrain.getDimX() && i > 0) {
                        double dist = Math.sqrt(Math.pow((x - i), 2) + Math.pow((y - j), 2));
                        if (dist <= rad) {
                            fireGrid[i][j] = 1;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        deriveFireImage();
    }

    //Reset the simulation - all grid values 0
    public void clearGrid() {
        for (int x = 0; x < dimX; x++) {
            for (int y = 0; y < dimY; y++) {
                fireGrid[x][y] = 0;
                burntPlants[x][y] = 0;
                plantGrid[x][y] = 0;
            }
        }
        System.out.println("Reset Simulation");
    }

    // Moves Fire and sets Fire to plants/ground, based on set chance and wind
    public void moveFire(int x, int y) {
        double rand = Math.random() * 100;
        fireGrid[x][y] = 2; // ASH cant move anymore
        if (x > 0 || y > 0 || x < Terrain.getDimX() || y < Terrain.getDimY()) {
            try {
                if (plantGrid[x][y - 1] == 1 && fireGrid[x][y - 1] != 2) { // North
                    // 100% chance of fire spread
                    plantGrid[x][y - 1] = 2;
                    fireGrid[x][y - 1] = 1; // FIRE

                } else if (fireGrid[x][y - 1] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 1 && windForce > 0){ 
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x][y - 1] = 1; // FIRE
                    }
                }

                if (plantGrid[x + 1][y - 1] == 1 && fireGrid[x + 1][y - 1] != 2) { // North East
                    // 100% chance of fire spread
                    plantGrid[x + 1][y - 1] = 2;
                    fireGrid[x + 1][y - 1] = 1; // FIRE

                } else if (fireGrid[x + 1][y - 1] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 2 && windForce > 0){
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x + 1][y - 1] = 1; // FIRE
                    }
                }

                if (plantGrid[x + 1][y] == 1 && fireGrid[x + 1][y] != 2) { // East
                    // 100% chance of fire spread
                    plantGrid[x + 1][y] = 2;
                    fireGrid[x + 1][y] = 1;

                } else if (fireGrid[x + 1][y] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 3 && windForce > 0){
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x + 1][y] = 1;
                    }
                }

                if (plantGrid[x + 1][y + 1] == 1 && fireGrid[x + 1][y + 1] != 2) { // South East
                    // 100% chance of fire spread
                    plantGrid[x + 1][y + 1] = 2;
                    fireGrid[x + 1][y + 1] = 1;

                } else if (fireGrid[x + 1][y + 1] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 4 && windForce > 0){
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x + 1][y + 1] = 1;
                    }
                }

                if (plantGrid[x][y + 1] == 1 && fireGrid[x][y + 1] != 2) { // South
                    plantGrid[x][y + 1] = 2;
                    fireGrid[x][y + 1] = 1; // FIRE

                } else if (fireGrid[x][y + 1] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 5 && windForce > 0){
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x][y + 1] = 1; // FIRE
                    }
                }

                if (plantGrid[x - 1][y + 1] == 1 && fireGrid[x - 1][y + 1] != 2) { // South West
                    // 100% chance of fire spread
                    plantGrid[x - 1][y + 1] = 2;
                    fireGrid[x - 1][y + 1] = 1; // FIRE

                } else if (fireGrid[x - 1][y + 1] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 6 && windForce > 0){
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x - 1][y + 1] = 1; // FIRE
                    }
                }

                if (plantGrid[x - 1][y] == 1 && fireGrid[x - 1][y] != 2) { // West
                    // 100% chance of fire spread
                    plantGrid[x - 1][y] = 2; // FIRE
                    fireGrid[x - 1][y] = 1; // FIRE

                } else if (fireGrid[x - 1][y] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 7 && windForce > 0){
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x - 1][y] = 1; // FIRE
                    }
                }

                if (plantGrid[x - 1][y - 1] == 1 && fireGrid[x - 1][y - 1] != 2) { // North West
                    // 100% chance of fire spread
                    plantGrid[x - 1][y - 1] = 2;
                    fireGrid[x - 1][y - 1] = 1; // FIRE

                } else if (fireGrid[x - 1][y - 1] != 2) {
                    // 20% chance of fire spread + wind force
                    if (windDirection == 8 && windForce > 0){
                        windChance = chance + chance*windForce;
                    if (rand < windChance) fireGrid[x - 1][y - 1] = 1; // FIRE
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    // Draw graphics on the bufferedImages
    public void deriveFireImage() {
        fireImage = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_ARGB);

        if (showPath) {
            for (int x = 0; x < dimX; x++) {
                for (int y = 0; y < dimY; y++) {
                    if (fireGrid[x][y] == 1) {
                        fireImage.setRGB(x, y, igniteColor.getRGB());
                    } else if (fireGrid[x][y] == 2 && plantGrid[x][y] == 0) {
                        fireImage.setRGB(x, y, ashColor.getRGB());
                    }
                }
            }
        }

        burntImage = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_ARGB);

        if (showBurnt) {
            for (int x = 0; x < dimX; x++) {
                for (int y = 0; y < dimY; y++) {
                    if (plantGrid[x][y] == 2) {
                        fireImage.setRGB(x, y, burntColor.getRGB());
                    }
                }
            }
        }
    }

    // Generate a permuted list of linear index positions to allow a random
    // traversal over the terrain
    public void genPermute() {
        permute = new ArrayList<Integer>();
        for (int idx = 0; idx < getDim(); idx++)
            permute.add(idx);
        java.util.Collections.shuffle(permute);
    }

    // Find permuted 2D location from a linear index in the range [0, dimx*dimy)
    public void getPermute(int i, int[] loc) {
        locate(permute.get(i), loc);
    }

    // Convert linear position into 2D location in grid
    public void locate(int pos, int[] index) {
        index[0] = (int) pos / dimY;
        index[1] = (int) pos % dimY;
    }

    // Return Fire Spread Image
    public BufferedImage getImage() {
        return fireImage;
    }

    // Return Burnt Tree Image
    public BufferedImage getBurntImage() {
        return burntImage;
    }

    // Return Dimensions
    public int getDim() {
        return dimX * dimY;
    }

    //Mutator methods
    public void setShowPath(Boolean b) {
        showPath = b;
    }

    public void setShowBurnt(Boolean b) {
        showBurnt = b;
    }

    public void setWindDirection(int windDirection){
        this.windDirection = windDirection;
    }

    public void setWindForce(int windSpeed, int windMax){
        this.windForce = (float)(windSpeed / windMax);
    }

    public void removePlant(int speciesid, int plantid){
        try{
            int x = specieslist[speciesid].getCanopyPlants()[plantid].getX();
            int y = specieslist[speciesid].getCanopyPlants()[plantid].getY();
            canopyPlants[x][y][1] = -1;
        }catch(Exception e){
            //
        }
    }

    public void restorePlant(int speciesid, int plantid){
        try{
            int x = specieslist[speciesid].getCanopyPlants()[plantid].getX();
            int y = specieslist[speciesid].getCanopyPlants()[plantid].getY();
            canopyPlants[x][y][1] = plantid;
        }catch(Exception e){
            //
        }
    }
}
