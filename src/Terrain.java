import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Create 2D Greyscale enviro
public class Terrain {
    private double[][] elv;
    private double gridSpacing = 0.0;
    private double latitude = 0.0;
    private int[][] canopy;
    private int[][] undergrowth;
    private int[][] species;

    public Terrain(){

    }

    public void readElevation() throws FileNotFoundException{
        String fileName = "src/data/S6000-6000-256.elv";
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        int dimX = Integer.parseInt(scanner.next());
        int dimY = Integer.parseInt(scanner.next());
        gridSpacing = Float.parseFloat(scanner.next());
        latitude = Float.parseFloat(scanner.next());

        elv = new double[dimY][dimX];

        while (scanner.hasNext()){
            for (int y=0; y<dimY;y++){
                for (int x=0; x<dimX;x++){
                    elv[y][x] = Double.parseDouble(scanner.next());
                }
            }
        }
        System.out.println("Elevation File has been read...");
        scanner.close();
    }
    public void readCanopy(){
        String file = "S6000-6000-256_canopy.pdb";
    }
    public void readUndergrowth(){
        String file = "S6000-6000-256_undergrowth.pdb";
    }
    public void readSpecies(){
        String file = "S6000-6000-256.spc.txt";
    }
}