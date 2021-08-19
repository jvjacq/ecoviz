import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;

//Simulation.java - Main Class

public class Simulation {

    private static Gui gui;
    private static Terrain terrain;

    public static void main(String[] args) throws FileNotFoundException {
        terrain = new Terrain(); //Use terrain to access data
        terrain.readElevation(); 
        terrain.deriveImg();

        int frameX=terrain.getDimX();
        int frameY=terrain.getDimY();
        SwingUtilities.invokeLater(() -> new Gui(frameX,frameY,terrain)); //in case we use threads


        //gui = new Gui(terrain);
        System.out.println("GUI is showing...");
           

    }
}
