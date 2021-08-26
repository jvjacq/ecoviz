/*
* File: Simulation.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.2
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 26/08/2021
* Status: In progress
*/

import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;

//Simulation.java - Main Class

public class Simulation {
    public static int frameX, frameY;
    
    private static Gui gui;
    private static Terrain terrain;
    
    private static PlantLayer[] plantlayers;
    private static int numSpecies;

    public static void main(String[] args) throws FileNotFoundException {
        //terrain object
        terrain = new Terrain(); //Use terrain to access data
        terrain.readElevation(); 
        terrain.deriveImg();

        frameX=terrain.getDimX();   //Consider making theses static?
        frameY=terrain.getDimY();
        
        //Species objects
        PlantLayer.readSpecies();
        //plantlayer objects
        //PlantLayer canopy = new PlantLayer("src/data/S6000-6000-256_canopy.pdb", frameX, frameY);
        //PlantLayer undergrowth = new PlantLayer("src/data/S6000-6000-256_undergrowth.pdb", frameX, frameY);
        PlantLayer canopy = new PlantLayer("src/data/S2000-2000-512_canopy.pdb", frameX, frameY);
        PlantLayer undergrowth = new PlantLayer("src/data/S2000-2000-512_undergrowth.pdb", frameX, frameY);
        //PlantLayer canopy = new PlantLayer("src/data/S4500-4500-1024_canopy.pdb", frameX, frameY);
        //PlantLayer undergrowth = new PlantLayer("src/data/S4500-4500-1024_undergrowth.pdb", frameX, frameY);
        
        plantlayers = new PlantLayer[2];
        plantlayers[0] = undergrowth;
        plantlayers[1] = canopy;

        canopy.deriveImg();
        undergrowth.deriveImg();

        SwingUtilities.invokeLater(() -> new Gui(frameX,frameY,terrain,canopy,undergrowth)); //in case we use threads
        //gui = new Gui(terrain);
        System.out.println("GUI is showing...");
    }

}
