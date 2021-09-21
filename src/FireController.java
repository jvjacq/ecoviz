//Controller
import java.util.concurrent.atomic.AtomicBoolean;

public class FireController {
    static long startTime;

    private static int width; 
    private static int height; 
    
    private AtomicBoolean inFlow = new AtomicBoolean(false);
    private static Fire fireData;
    
    private int initLimit;

    private static FireThread mainThread;
    private FireThread seg1,seg2,seg3,seg4;


    public FireController(int width, int height, PlantLayer undergrowth,PlantLayer canopy){
        this.width=width;
        this.height=height;
        
        //Generate Fire Data:
        fireData = new Fire(width,height);
        startTime = 0;
    }

    public static void setup(){
        mainThread = new FireThread(fireData, (width*height));

        Thread fpt = new Thread(mainThread);
		fpt.start();
    }

    public void runSimulation(){   //Emulates Flow main method
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
        seg1 = new FireThread( fireData, segment1Low, segment1High, (width*height));
        seg2 = new FireThread( fireData, segment2Low, segment2High, (width*height));
        seg3 = new FireThread( fireData, segment3Low, segment3High, (width*height));
        seg4 = new FireThread( fireData, segment4Low, segment4High, (width*height));
        
        //Create the Threads:
        inFlow.set(true);
        
       // while (inFlow.get()){
            
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

   // }
	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}

	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f;
	}

////////
public Fire getFire(){
    return fireData;
}

}
