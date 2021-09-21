//Controller
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FireController {
    static long startTime;

    private static int width; 
    private static int height; 
    
    private static AtomicBoolean inFlow = new AtomicBoolean(false);
    private static Fire fireData;
    
    private static int initLimit;

    private static FireThread mainThread;
    private static FireThread seg1;

    private static FireThread seg2;

    private static FireThread seg3;

    private static FireThread seg4;


    public FireController(int width, int height, PlantLayer undergrowth,PlantLayer canopy){
        FireController.width=width;
        FireController.height=height;
        
        //Generate Fire Data:
        fireData = new Fire(width,height);
        startTime = 0;
    }

    public static void setupGUI(int frameX, int frameY, Fire fireData) {

  	JFrame frame = new JFrame("Waterflow");
  	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	frame.getContentPane().setLayout(new BorderLayout());

    JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));

		mainThread = new FireThread(fireData, (frameX*frameY));
		mainThread.setPreferredSize(new Dimension(frameX,frameY));
		g.add(mainThread);

		mainThread.addMouseListener(new ClickListener(mainThread));
		JPanel b = new JPanel();
	  b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		JLabel counterL = new JLabel(mainThread.getCounter());
		JButton resetB = new JButton("Reset");
		JButton pauseB = new JButton("Pause");
		JButton playB = new JButton("Play");
		JButton endB = new JButton("End");

		/*Timer refresh = new Timer(100, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				counterL.setText(mainThread.getCounter());
			}
		});*/

		resetB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainThread.resetCounter();
				counterL.setText(mainThread.getCounter());
				mainThread.resetFire();
				inFlow.set(false);
				mainThread.flowStop();
				seg1.flowStop();
				seg2.flowStop();
				seg3.flowStop();
				seg4.flowStop();
				mainThread.resetCounter();
				counterL.setText(mainThread.getCounter());
			}
		});

		pauseB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				inFlow.set(false);
				mainThread.flowStop();
				seg1.flowStop();
				seg2.flowStop();
				seg3.flowStop();
				seg4.flowStop();
				counterL.setText(mainThread.getCounter());
			}
		});

		playB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//refresh.start();
				inFlow.set(true);
				mainThread.flowStart();
				seg1.flowStart();
				seg2.flowStart();
				seg3.flowStart();
				seg4.flowStart();
				counterL.setText(mainThread.getCounter());
			}
		});

		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainThread.flowStop();
				seg1.flowStop();
				seg2.flowStop();
				seg3.flowStop();
				seg4.flowStop();
				frame.dispose();
				System.exit(0);
			}
		});

		/*Timer init = new Timer(50, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (initLimit < 1){
					mainThread.clickFire(10, 10);
					playB.doClick();
					initLimit++;
					resetB.doClick();
				}
			}
		});*/

		g.add(b);
		b.add(resetB);
		b.add(pauseB);
		b.add(playB);
		b.add(endB);
		b.add(counterL);

		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
  	frame.setLocationRelativeTo(null);	// center window on screen
  	frame.add(g);				// add contents to window
		frame.setContentPane(g);
		frame.setVisible(true);
		Thread fpt = new Thread(mainThread);
		fpt.start();
		initLimit = 0;
		//init.start();

		}


    public void runSimulation(){   //Emulates Flow main method
        //Split Grid into 4 (4 Threads will be operating):
        
        SwingUtilities.invokeLater(()->setupGUI(width, height, fireData));

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
