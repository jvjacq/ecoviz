/*
* File: Simulation.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Version 1.2
* Created: +++++++++++ Owen insert date here +++++++
* Last edited: 26/08/2021
* Status: In progress
*/

import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarculaLaf;

public class SimController {

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }catch (Exception e){
            e.printStackTrace();
        }
        Gui gui = new Gui();
        Terrain terrain = new Terrain();
        PlantLayer undergrowth = new PlantLayer();
        PlantLayer canopy = new PlantLayer();
        Controller controller = new Controller(gui, terrain, undergrowth, canopy);
        controller.initController();
    }
}

