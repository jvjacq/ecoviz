import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;

public class FirePanel extends JPanel implements Runnable {
    private AtomicBoolean flowing = new AtomicBoolean(false);
    private AtomicInteger stepCounter = new AtomicInteger(0);

    private Fire fire;
    private int segmentLow;
    private int segmentHigh;
    private int[] traversal;
    
    public FirePanel(Fire fire, int segmentLow, int segmentHigh) {
		this.fire = fire;
        this.segmentHigh = segmentHigh;
        this.segmentLow = segmentLow;
	}

    @Override
    public void run() {
        while (true) {
            if (flowing.get() == true){
                simulate(segmentLow, segmentHigh);
                stepCounter.set(stepCounter.get() + 1);
                repaint();
            }
        }        
    }

    public synchronized void simulate(int segmentLow, int segmentHigh) {
        // check each grid point to see if it has fire
        // if it has fire then check all neighbours
        // if it can move, then move it, otherwise go to next position

        for (int i = segmentLow;i<segmentHigh;i++){
            fire.getPermute(i,traversal);

            //Move fire if can:
            if (fire.isFire(traversal[0],traversal[1]) == true){
                fire.moveFire(traversal[0],traversal[1]);
                repaint();
            }
        }
    }
    public synchronized void resetCounter() {
		stepCounter.set(0);
	}
	public synchronized void incCounter() {
		stepCounter.set(stepCounter.get() + 1);
	}
	public synchronized String getCounter() {
		return " " + Integer.toString(Math.round(stepCounter.get()/(256*16)));
	}
    public void clickFire(int x, int y){
        fire.addFire(x,y);
        repaint();
    }
    public synchronized void resetFire() {
        fire.clearGrid();
    }
    public synchronized void flowStop(){
        flowing.set(false);
    }
    public synchronized void flowStart() {
		flowing.set(true);
	}
    
}
