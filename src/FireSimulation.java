public class FireSimulation {

    private double speedMultiplier;
    private String colour;
    private int frameX, frameY;

    public FireSimulation() {
        speedMultiplier = 0.0;
        colour = "Red"; // Maybe have a secondary color to represent heat (i.e. orange, red or use RGB
                        // range)
        this.frameX = 0;
        this.frameY = 0;
    }

    public FireSimulation(int frameWidth, int frameHeight) {
        speedMultiplier = 0.0;
        colour = "Red"; // Maybe have a secondary color to represent heat (i.e. orange, red or use RGB
                        // range)
        this.frameX = frameX;
        this.frameY = frameY;
    }

    // Mutator Methods:
    public void setSpeedMultiplier(double newMult) {
        speedMultiplier = newMult;
    }

    public void setColour(String newColour) {
        colour = newColour;
    }

/*

    ///// Fire simulation logic /////

    if xcoord or ycoord not within terrain bounds:
      return;

    else:
      if coordinates fall within a plant's radius:
        for all plants in coordinate:
          burnt[plantX][plantY] = plant radius * plant height

          wind speed*

          if (direction <= 45):
            floodFill(xcoord+1, ycoord, 1);
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord, ycoord+1, 1);
            floodFill(xcoord+1, ycoord, 1);

          if else (direction <= 90):
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord+1, ycoord, 1);
            floodFill(xcoord-1, ycoord, 1);
            floodFill(xcoord, ycoord+1, 1);

          if else (direction <= 135):
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord-1, ycoord, 1);
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord+1, ycoord, 1);

          if else (direction <= 180):
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord-1, ycoord, 1);
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord+1, ycoord, 1);

          if else (direction <= 225):
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord-1, ycoord, 1);
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord+1, ycoord, 1);

          if else (direction <= 270):
            floodFill(xcoord, ycoord+1, 1);
            floodFill(xcoord-1, ycoord, 1);
            floodFill(xcoord+1, ycoord, 1);
            floodFill(xcoord, ycoord-1, 1);

          if else (direction <= 315):
            floodFill(xcoord, ycoord+1, 1);
            floodFill(xcoord+1, ycoord, 1);
            floodFill(xcoord-1, ycoord, 1);
            floodFill(xcoord, ycoord-1, 1);

          if else (direction < 360):
            floodFill(xcoord+1, ycoord, 1);
            floodFill(xcoord, ycoord-1, 1);
            floodFill(xcoord, ycoord+1, 1);
            floodFill(xcoord+1, ycoord, 1);

          burnt[previousX][previousY] -= 10; //reduce the previous plant's fuel/heat

      else:
        return;

/*
    public void floodFill(int xcoord, int ycoord, int previous, int locations[][])
    {
      if( xcoord < 0 || xcoord > frameX || ycoord < 0 || ycoord > frameY )
      {
        return;
      }

      if( PlantLayer.getPlant(xcoord, ycoord) != null)
      {
        for (int x = 0; x < frameX; x++)
        {
          for (int y = 0; y < frameY; y++)
          {
            if (locations[x][y] == 1)
            {
              burnt[xcoord][ycoord] = 1;
              floodFill(xcoord, ycoord-1, 1);
              floodFill(xcoord-1, ycoord, 1);
              floodFill(xcoord, ycoord+1, 1);
              floodFill(xcoord+1, ycoord, 1);
            }
          }
        }
      }
      else
      {
        return;
      }
    }
*/
}
