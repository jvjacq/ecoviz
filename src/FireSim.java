import java.awt.image.BufferedImage;

public class FireSim {
    private BufferedImage[] save_States;    //Can cycle through to 'render' frame by frame animation
    private boolean running = true;

    public void runSimulation(){
        while(running){
            //Save Current State
            CaptureState();

            //Light Burning Neighbours... or Extinguish if there aren't any
            Plant[] neighPlants = findNeighbours();
            if (neighPlants!=null){
                for (Plant p: neighPlants){
                    //Set each neigbour alight
                    
                }
            }else{break;}


        }


    }

    public Plant[] findNeighbours(){
        Plant[] neighbours = null;
        //Populate array with overlapping/'touching' neighbours

        return neighbours;
    }

    public void burn(Plant p){
        //Set Plant to burning state
        //Set Plant to red
    }


    public void CaptureState(){
        //Captures current state and adds to save_States
    }
}
