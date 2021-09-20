import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.Math;

public class Fire {
    private int dimX;
	private int dimY;
	private int [][] fireGrid;
	private int [][] plantGrid;
	BufferedImage fireImage;

	Fire (int dimX, int dimY, int[][] plantGrid) {
        this.dimX=dimX;
        this.dimY=dimY;
        this.plantGrid=plantGrid;   //Every location with a plant is represented with a 1... otherwise 0 if none
        fireGrid = new int[dimX][dimY];
    }

    public boolean isFire(int x, int y){
        boolean firePresent = false;
        if (fireGrid[x][y]==1){
            firePresent = true;
        }

        return firePresent;
    }

    public void addFire(int x,int y){
        //Add Fire To grid
        for (int i = -3;i<4;i++){
            for (int j = -3;j<4;j++){
                if (x+i > 0 && x+i < dimX-2 && y+j > 0 && y+j < dimY-2) {
                    fireGrid[x+i][y+j] = 1; //1 means there is fire here    //2 means there is ash there
                    }
            }
        }
    }

    public void clearGrid(){
        for (int x = 0;x<dimX;x++){
            for (int y = 0;y<dimY;y++){
                fireGrid[x][y] = 0;
            }
        }
    }

    public void moveFire(int x, int y){
        double rand = Math.random() * 100;
        
        fireGrid[x][y]=2;   //ASH or Burnt (Darker red - For nice aesthetic)

        if (plantGrid[x-1][y]==1){  //West ←
            //100% chance of fire spread
            fireGrid[x-1][y]=1; //FIRE
        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x-1][y]=1; //FIRE
        }

        if (plantGrid[x+1][y]==1){  //East →
            //100% chance of fire spread
            fireGrid[x+1][y]=1; //FIRE

        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x+1][y]=1; //FIRE
        }

        if (plantGrid[x+1][y+1]==1){  //North East 🡥
            //100% chance of fire spread
            fireGrid[x+1][y+1]=1; //FIRE
        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x+1][y+1]=1; //FIRE
        }

        if (plantGrid[x][y+1]==1){  //North ↑
            //100% chance of fire spread
            fireGrid[x][y+1]=1; //FIRE
        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x][y+1]=1; //FIRE
        }

        if (plantGrid[x-1][y+1]==1){  //North West 🡤
            //100% chance of fire spread
            fireGrid[x-1][y+1]=1; //FIRE
        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x-1][y+1]=1; //FIRE
        }

        if (plantGrid[x-1][y-1]==1){  //South ↓
            //100% chance of fire spread
            fireGrid[x-1][y]=1; //FIRE
        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x-1][y-1]=1; //FIRE
        }

        if (plantGrid[x+1][y-1]==1){  //South East 🡦
            //100% chance of fire spread
            fireGrid[x+1][y-1]=1; //FIRE
        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x+1][y-1]=1; //FIRE
        }

        if (plantGrid[x-1][y-1]==1){  //South West 🡧
            //100% chance of fire spread
            fireGrid[x-1][y-1]=1; //FIRE
        } else if (rand < 20) {
            //20% chance of fire spread
            fireGrid[x-1][y-1]=1; //FIRE
        }
    }

    public BufferedImage getImage(){
        return fireImage;
}

    public void deriveFireImage(){
        fireImage = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fireImage.createGraphics();
        g2d.setColor(Color.RED);

        for (int x = 0; x < dimX; x++){
            for (int y = 0; y < dimY; y++){
                if (fireGrid[x][y] != 0){
                    g2d.fillRect(x,y,1,1);
                }
            }
        }

    }

}
