import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

public class Fire {
    private int dimX;
	private int dimY;
	private int [][] fireGrid;
	private PlantLayer[] plantLayers;
	BufferedImage fireImage;

	Fire (int dimX, int dimY, PlantLayer[] plantLayers) {
        this.dimX=dimX;
        this.dimY=dimY;
        this.plantLayers=plantLayers;
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
                    fireGrid[x+i][y+j] = 1; //1 means there is fire here
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

    public void moveFire(){

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
