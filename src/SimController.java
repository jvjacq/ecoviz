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
        
        filecontroller.readElevation(terrain, "src/data/S2000-2000-512.elv");
        filecontroller.readSpecies("src/data/S2000-2000-512.spc.txt");

        PlantLayer canopy = new PlantLayer();
        PlantLayer undergrowth = new PlantLayer();

        filecontroller.readLayer(canopy, "src/data/S2000-2000-512_canopy.pdb");
        filecontroller.readLayer(undergrowth, "src/data/S2000-2000-512_undergrowth.pdb");

        SwingUtilities.invokeLater(() -> new Gui(terrain,canopy,undergrowth)); //in case we use threads
        System.out.println("GUI is showing..."); 
    }

}
