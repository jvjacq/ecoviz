import java.io.FileNotFoundException;

//Simulation.java - Main Class

public class Simulation {

    private static Gui gui;
    private static Terrain terrain;

    public static void main(String[] args) throws FileNotFoundException {
        gui = new Gui();
        System.out.println("GUI is showing...");
        terrain = new Terrain(); //Use terrain to access data
        terrain.readElevation();    

    }
}
