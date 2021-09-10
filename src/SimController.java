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

public class SimController {
    //public static int frameX, frameY;
    
    //private static Gui gui;
    //private static imgPanel
    private static Terrain terrain;
    private static FileController filecontroller;

    public static void main(String[] args) throws FileNotFoundException {

        filecontroller = new FileController();
        terrain = new Terrain();
        
        filecontroller.readElevation(terrain, "src/data/S4500-4500-1024.elv");
        filecontroller.readSpecies("src/data/S4500-4500-1024.spc.txt");

        PlantLayer canopy = new PlantLayer();
        PlantLayer undergrowth = new PlantLayer();

        filecontroller.readLayer(canopy, "src/data/S4500-4500-1024_canopy.pdb");
        filecontroller.readLayer(undergrowth, "src/data/S4500-4500-1024_undergrowth.pdb");
        

        //terrain object
         //Use terrain to access data
        //terrain.readElevation(); 
        

        //plantlayer objects
        //PlantLayer canopy = new PlantLayer("src/data/S6000-6000-256_canopy.pdb", frameX, frameY);
        //PlantLayer undergrowth = new PlantLayer("src/data/S6000-6000-256_undergrowth.pdb", frameX, frameY);
        //PlantLayer canopy = new PlantLayer("src/data/S2000-2000-512_canopy.pdb", frameX, frameY);
        //PlantLayer undergrowth = new PlantLayer("src/data/S2000-2000-512_undergrowth.pdb", frameX, frameY);
        //PlantLayer canopy = new PlantLayer("src/data/S4500-4500-1024_canopy.pdb", frameX, frameY);
        //PlantLayer undergrowth = new PlantLayer("src/data/S4500-4500-1024_undergrowth.pdb", frameX, frameY);
        
        
        //terrain.deriveImg();
        //canopy.deriveImg();
        //undergrowth.deriveImg();

        SwingUtilities.invokeLater(() -> new Gui(terrain,canopy,undergrowth)); //in case we use threads
        //gui = new Gui(terrain);
        System.out.println("GUI is showing..."); 
    }

}
