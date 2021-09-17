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
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import java.io.File;

public class SimController {
    private static Terrain terrain;
    private static FileController fileController;
    private static FileLoader fileLoader;
    private static File[] files;

    public static void main(String[] args) throws FileNotFoundException {

        try{
        UIManager.setLookAndFeel(new FlatDarculaLaf());
    }catch (Exception e){
            e.printStackTrace();
        }
        fileLoader = new FileLoader();
        fileController = new FileController();
        fileLoader.loadFiles();
        //fileLoader.loader();
        files = fileLoader.getFiles();
        terrain = new Terrain();
        fileController.readElevation(terrain, files[0].toString());
        fileController.readSpecies(files[1].toString());
        PlantLayer canopy = new PlantLayer();
        PlantLayer undergrowth = new PlantLayer();
        fileController.readLayer(canopy, files[2].toString());
        fileController.readLayer(undergrowth, files[3].toString());
        SwingUtilities.invokeLater(() -> new Gui(terrain,canopy,undergrowth));
        System.out.println("GUI is showing...");
    }
}

