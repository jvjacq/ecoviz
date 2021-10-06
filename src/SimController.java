/*
* File: SimController.java
* MVC: Main runner/driver
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
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
        //Initialise view
        Gui gui = new Gui();
        //Initialise model
        Terrain terrain = new Terrain();
        PlantLayer undergrowth = new PlantLayer();
        PlantLayer canopy = new PlantLayer();
        //Intitialise controller
        Controller controller = new Controller(gui, terrain, undergrowth, canopy);
        //Begin sim
        controller.initController();
    }
}

