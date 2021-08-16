import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Create 2D Greyscale enviro
public class Terrain {
    private double[][] elv;
    private double gridSpacing = 0.0;
    private double latitude = 0.0;
    private double[][] canopy;
    private double[][] undergrowth;
    private ArrayList<PlantType> underPlants = new ArrayList<PlantType>();

    private ArrayList<PlantType> canopyPlants = new ArrayList<PlantType>();
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

    public void readCanopy() throws FileNotFoundException{
        String fileName = "S6000-6000-256_canopy.pdb";
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        int numSpecies = Integer.parseInt(scanner.next()); 
        
        for (int i=0;i<numSpecies;i++){
            //Plant Details:
            int id =  Integer.parseInt(scanner.next()); 
            float minHeight = Float.parseFloat(scanner.next());
            float maxHeight = Float.parseFloat(scanner.next());
            float avgRatio = Float.parseFloat(scanner.next());
            int numPlants = Integer.parseInt(scanner.next()); 
            PlantType species = new PlantType(id, minHeight, maxHeight, avgRatio, numPlants);  //Create PlantType object
            canopy = new double[numPlants][4];
            for (int y = 0; y < numPlants;y++){
                for (int x = 0; x<5; x++){
                    canopy[y][x] = Float.parseFloat(scanner.next());    //Store 5 data val for each plant in the type.
                }
            }
            species.setData(canopy);    //Set data
            canopyPlants.add(species);
            

        }
        scanner.close();
    }

    public void readUndergrowth() throws FileNotFoundException{
        String fileName = "S6000-6000-256_undergrowth.pdb";
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        int numSpecies = Integer.parseInt(scanner.next()); 
        
        for (int i=0;i<numSpecies;i++){
            //Plant Details:
            int id =  Integer.parseInt(scanner.next()); 
            float minHeight = Float.parseFloat(scanner.next());
            float maxHeight = Float.parseFloat(scanner.next());
            float avgRatio = Float.parseFloat(scanner.next());
            int numPlants = Integer.parseInt(scanner.next()); 
            PlantType species = new PlantType(id, minHeight, maxHeight, avgRatio, numPlants);  //Create PlantType object
            undergrowth = new double[numPlants][4];
            for (int y = 0; y < numPlants;y++){
                for (int x = 0; x<5; x++){
                    canopy[y][x] = Float.parseFloat(scanner.next());    //Store 5 data val for each plant in the type.
                }
            }
            species.setData(undergrowth);    //Set data
            underPlants.add(species);
        }
        scanner.close();
    }
    public void readSpecies(){
        String file = "S6000-6000-256.spc.txt";
    }
}