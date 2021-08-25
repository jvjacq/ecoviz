public class Plant {
    
    private int speciesID;
    private int id;
    private double height;
    private double canopy;
    private boolean burnt;

    public Plant(int speciesID){
        this.speciesID = speciesID;
        id = 0;
        height = 0;
        canopy = 0;
        burnt = false;
    }

    //Accessor methods
    public int getSpeciesID(){
        return speciesID;
    }

    public int getID(){
        return id;
    }

    public double getHeight(){
        return height;
    }

    public double getCanopy(){
        return canopy;
    }

    public void toggleburn(){
        burnt = !burnt;
    }

}
