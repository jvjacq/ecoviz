/*
* File: Simulation.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.1
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 25/08/2021
* Status: In progress
*/

import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;

//Simulation.java - Main Class

public class Simulation {

    private static Gui gui;
    private static Terrain terrain;
    //new
    private static PlantLayer[] plantlayers;
    private static int numSpecies;

    public static void main(String[] args) throws FileNotFoundException {
        //terrain object
        terrain = new Terrain(); //Use terrain to access data
        terrain.readElevation(); 
        terrain.deriveImg();

        int frameX=terrain.getDimX();   //Consider making theses static?
        int frameY=terrain.getDimY();
        SwingUtilities.invokeLater(() -> new Gui(frameX,frameY,terrain)); //in case we use threads

        //plantlayer objects
        PlantLayer canopy = new PlantLayer("src/data/S2000-2000-512_canopy.pdb", frameX, frameY);
        PlantLayer undergrowth = new PlantLayer("src/data/S2000-2000-512_canopy.pdb", frameX, frameY);
        
        plantlayers = new PlantLayer[2];
        plantlayers[0] = undergrowth;
        plantlayers[1] = canopy;

        //gui = new Gui(terrain);
        System.out.println("GUI is showing...");
    }

}
