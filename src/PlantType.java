public class PlantType {
    private String commonName;
    private String latinName;
    private int id;

    public PlantType(String cName, String lName, int identity) {
        commonName = cName;
        latinName = lName;
        id = identity;
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
