public class FireSimulation {

    private double speedMultiplier;
    private String colour;

    public FireSimulation() {
        speedMultiplier = 0.0;
        colour = "Red"; // Maybe have a secondary color to represent heat (i.e. orange, red or use RGB
                        // range)
    }

    // Mutator Methods:
    public void setSpeedMultiplier(double newMult) {
        speedMultiplier = newMult;
    }

    public void setColour(String newColour) {
        colour = newColour;
    }

/*
    public void floodFill(int xcoord, int ycoord, int previous, int locations[][])
    {
      if( xcoord < 0 || xcoord > terrainWidth || ycoord < 0 || ycoord > terrainHeight )
      {
        return;
      }

      for (int x = 0; x < terrainWidth; x++)
      {
        for (int y = 0; y < terrainHeight; y++)
        {
          if (locations[x][y] == 1)
          {
            burnt[xcoord][ycoord] = 1;
            floodfill(xcoord, ycoord-1, 1);
            floodfill(xcoord-1, ycoord, 1);
            floodfill(xcoord, ycoord+1, 1);
            floodfill(xcoord+1, ycoord, 1);
          }
        }
      }
    }
*/

}
