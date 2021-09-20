import java.util.ArrayList;

public class PlantLayer{

    //ID 0 = no PlantType
    //ID -1 = burnt?
    private int[][][] idLocations;
    //private int[][] burnt;
    private Species[] specieslist;
    private static ArrayList<Plant> plantlist;
    private int numSpecies; //per layer
    private boolean filter;
    
    public PlantLayer(){
      this.filter = true;
    }

    //Mutator Methods:
    public void setNumSpecies(int num){
        this.numSpecies = num;
    }

    public void setPlantAtLocation(int x, int y, int speciesid, int plantid){
        this.idLocations[y][x][0] = speciesid;
        this.idLocations[y][x][1] = plantid;
    }

    public void setSpeciesList(Species[] list){
      this.specieslist = list;
    }

    public void setLayer(int[][][] newLocations) {
      idLocations = newLocations;
    }

    public void setFilter(boolean filter) {
      this.filter = filter;
    }

    public void setLocations(int dimx, int dimy){
      idLocations = new int[dimx][dimy][2];
    }

    public void removePlant(int dimx, int dimy) {
      idLocations[dimx][dimy][0] = 0;
      idLocations[dimx][dimy][1] = -1;  //Burnt would be double -1?
    }

    public static void setPlantList() {
      PlantLayer.plantlist = new ArrayList<Plant>();
    }

    public static void addPlant(Plant plant){
      PlantLayer.plantlist.add(plant);
    }

    // Accessor Methods
    public int[][][] getLocations(){
        return this.idLocations;
    }

    public int getNumSpecies(){
      return this.numSpecies;
    }

    public int[] getPlantAtLocation(int x, int y){
      return this.idLocations[y][x];
    }
    
    public Species[] getSpeciesList(){
      return this.specieslist;
    }

    public Species getPlant(int dimx, int dimy) {
       // return idLocations[dimx][dimy];
       return null;
    }

    public boolean getFilter() {
      return this.filter;
   }

   public static ArrayList<Plant> getPlantList() {
    return PlantLayer.plantlist;
 }

}
