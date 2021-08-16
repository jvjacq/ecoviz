public class PlantType {
    private String commonName;
    private String latinName;
    private int id;
    private float minHeight;
    private float maxHeight;
    private float avgRadius;
    private int numPlants;
    private double[][] canopy;


    public PlantType(int identity, float minH, float maxH, float rad, int num) {
        
        id = identity;
        minHeight = minH;
        maxHeight = maxH;
        avgRadius = rad;
        numPlants = num;
    }

    //Mutator Methods:
    public void setData(double[][] newCanopy){
        canopy = newCanopy; //Assigns canopy data
    }

    // Accessor Methods:
    public int getID() {
        return id;
    }

    public String getDesc() {
        String out = "Common Name: " + commonName + "/n";
        out += "Common Name: " + commonName + "/n";
        out += "Latin Name: " + latinName + "/n";

        return out;
    }
}
