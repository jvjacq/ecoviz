//Model
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.ArrayList;

public class Fire {
    private int dimX;
	private int dimY;
	private int [][] fireGrid;
	private int [][] plantGrid;
    private int[] traversal;
	private BufferedImage fireImage;
    private ArrayList<Integer> permute;	// permuted list of integers in range [0, dimx*dimy)


	public Fire (int dimX, int dimY, int[][] plantGrid) {
        this.dimX=dimX;
        this.dimY=dimY;

        traversal = new int[dimX*dimY];

        this.plantGrid=plantGrid;   //Every location with a plant is represented with a 1... otherwise 0 if none

        fireGrid = new int[dimX][dimY];
        genPermute();   // create randomly permuted list of indices for traversal 
    }

   // public void setDimX(int dimX){this.dimX=dimX;}
   // public void setDimY(int dimY){this.dimY=dimY;}
   // public void setCanopy(PlantLayer canopy){this.canopy=canopy;}
   // public void setUnder(PlantLayer undergrowth){this.undergrowth=undergrowth;}

   public void simulate(int segmentLow, int segmentHigh) {
    // check each grid point to see if it has fire
    // if it has fire then check all neighbours
    // if it can move, then move it, otherwise go to next position

    for (int i = segmentLow;i<segmentHigh;i++){
        getPermute(i,traversal);

        //Move fire if can:
        if (isFire(traversal[0],traversal[1]) == true){
            moveFire(traversal[0],traversal[1]);
            
        }
    }
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
        deriveFireImage();
    }


    public void clearGrid(){
        for (int x = 0;x<dimX;x++){
            for (int y = 0;y<dimY;y++){
                fireGrid[x][y] = 0;
            }
        }
        System.out.println("All Fire Cleared/Extinguished");
    }

    public void moveFire(int x, int y){
        double rand = Math.random() * 100;
        double chance = 15; //Chance of moving if not a plant (i.e. ground/ash) (Lower has better performance)
        fireGrid[x][y]=2;   //ASH cant move anymore
        if (x>0 || y>0 || x<Terrain.getDimX() || y<Terrain.getDimY()){
            try{
            if (plantGrid[x-1][y]==1 && fireGrid[x-1][y]!=2){  //West ←
                //100% chance of fire spread
                fireGrid[x-1][y]=1; //FIRE
            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x-1][y]=1; //FIRE
            }

            if (plantGrid[x+1][y]==1 && fireGrid[x+1][y]!=2){  //East →
                //100% chance of fire spread
                fireGrid[x+1][y]=1; //FIRE

            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x+1][y]=1; //FIRE
            }

            if (plantGrid[x+1][y+1]==1 && fireGrid[x+1][y+1]!=2){  //North East 🡥
                //100% chance of fire spread
                fireGrid[x+1][y+1]=1; //FIRE

            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x+1][y+1]=1; //FIRE
            }

            if (plantGrid[x][y+1]==1 && fireGrid[x][y+1]!=2){  //North ↑
                //100% chance of fire spread
                fireGrid[x][y+1]=1; //FIRE

            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x][y+1]=1; //FIRE
            }

            if (plantGrid[x-1][y+1]==1 && fireGrid[x-1][y+1]!=2){  //North West 🡤
                //100% chance of fire spread
                fireGrid[x-1][y+1]=1; //FIRE

            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x-1][y+1]=1; //FIRE
            }

            if (plantGrid[x-1][y-1]==1 && fireGrid[x-1][y-1]!=2){  //South ↓
                //100% chance of fire spread
                fireGrid[x-1][y-1]=1; //FIRE

            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x-1][y-1]=1; //FIRE
            }

            if (plantGrid[x+1][y-1]==1 && fireGrid[x+1][y-1]!=2){  //South East 🡦
                //100% chance of fire spread
                fireGrid[x+1][y-1]=1; //FIRE

            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x+1][y-1]=1; //FIRE
            }

            if (plantGrid[x-1][y-1]==1 && fireGrid[x-1][y-1]!=2){  //South West 🡧
                //100% chance of fire spread
                fireGrid[x-1][y-1]=1; //FIRE

            } else if (rand < chance) {
                //20% chance of fire spread
                fireGrid[x-1][y-1]=1; //FIRE
            }
        }catch (Exception e){

        }

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
                if (fireGrid[x][y] == 1){
                    g2d.fillRect(x,y,1,1);
                } else if(fireGrid[x][y]==2){
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillRect(x,y,1,1);
                    g2d.setColor(Color.RED);

                }
            }
        }

    }

    public int getDim(){
        return dimX*dimY;
    }

    // generate a permuted list of linear index positions to allow a random traversal over the terrain:
    public void genPermute() {
		permute = new ArrayList<Integer>();
		for(int idx = 0; idx < getDim(); idx++)
			permute.add(idx);
		java.util.Collections.shuffle(permute);
	}

    // find permuted 2D location from a linear index in the range [0, dimx*dimy)
    public void getPermute(int i, int[] loc){
        locate(permute.get(i),loc);
    }

    // convert linear position into 2D location in grid
    public void locate(int pos, int[] index){
        index[0] = (int) pos/dimY;
        index[1] = (int) pos % dimY;
    }

}
