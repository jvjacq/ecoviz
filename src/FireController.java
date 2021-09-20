import java.util.concurrent.atomic.AtomicBoolean;

public class FireController {
    private int width; 
    private int height; 
    private PlantLayer undergrowth;
    private PlantLayer canopy;
    private AtomicBoolean inFlow = new AtomicBoolean(false);


    public FireController(int width, int height, PlantLayer undergrowth,PlantLayer canopy){
        this.width=width;
        this.height=height;
        this.undergrowth = undergrowth;
        this.canopy = canopy;
    }

    public void startSimulation(){
        //Generate Fire Data:
        Fire fireData = new Fire(width,height,createPlantGrid());

        //Split Grid into 4 (4 Threads will be operating):
        int gridSize = width*height;

        int segment1Low = 0;
        int segment1High = Math.round(gridSize / 4 * 1);
		
        int segment2Low = segment1High;
		int segment2High = Math.round(gridSize / 4 * 2);

		int segment3Low = segment2High;
		int segment3High = Math.round(gridSize / 4 * 2);
		
        int segment4Low = segment3High;
		int segment4High = Math.round(gridSize / 4 * 2);

        //Create 4 Panels for threads to work on seperately:
        FirePanel seg1 = new FirePanel( fireData, segment1Low, segment1High); //Gonna add another class soon
        FirePanel seg2 = new FirePanel( fireData, segment2Low, segment2High); //Gonna add another class soon
        FirePanel seg3 = new FirePanel( fireData, segment3Low, segment3High); //Gonna add another class soon
        FirePanel seg4 = new FirePanel( fireData, segment4Low, segment4High); //Gonna add another class soon

        //Create the Threads:
        inFlow.set(true);

        while (inFlow.get()){
            Thread t1 = new Thread(seg1);
            Thread t2 = new Thread(seg2);
            Thread t3 = new Thread(seg3);
            Thread t4 = new Thread(seg4);

            t1.start();
            t2.start();
            t3.start();
            t4.start();

            try{
                t1.join();
                }
                catch(InterruptedException e){}
                try{
                t2.join();
                }
                catch(InterruptedException e){}
                try{
                t3.join();
                }
                catch(InterruptedException e){}
                try{
                t4.join();
                }
                catch(InterruptedException e){}

        }

    }



}
