public class PlantLayer{

    //ID 0 = no PlantType
    //ID -1 = burnt?
    private int[][][] idLocations;
    //private int[][] burnt;
    private Species[] specieslist;
    private int numSpecies; //per layer
    
    public PlantLayer(){
      //Nothing yet
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

    public void setLocations(int dimx, int dimy){
      idLocations = new int[dimx][dimy][2];
    }

    public void removePlant(int dimx, int dimy) {
      idLocations[dimx][dimy][0] = 0;
      idLocations[dimx][dimy][1] = -1;  //Burnt would be double -1?
    }

    // Mutator methods:
    public void setLayer(int[][][] newLocations) {
      idLocations = newLocations;
    }

    // Accessor methods:
    public Species getPlant(int dimx, int dimy) {
       // return idLocations[dimx][dimy];
       return null;
    }

}
