public class Species {
    private String commonName;
    private String latinName;
    private int id;
    private float minHeight;
    private float maxHeight;
    private float avgRadius;
    private int numPlants;
    private Plant[] list;


    public Species(int id, String cname, String lname) {
        
        this.id = id;
        commonName = cname;
        latinName = lname;
        minHeight = 0.0f;
        maxHeight = 0.0f;
        avgRadius = 0.0f;
        numPlants = 0;
        
    }

    public void create(float minH, float maxH, float rad, int numP){
        minHeight = minH;
        maxHeight = maxH;
        avgRadius = rad;
        numPlants = numP;
    }

    
    //Mutator Methods:

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
