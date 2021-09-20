import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JPanel;

public class FirePanel extends JPanel implements Runnable {
    private AtomicBoolean flowing = new AtomicBoolean(false);
    private Fire fire;
    private int segmentLow;
    private int segmentHigh;
    
    public FirePanel(Fire fire, int segmentLow, int segmentHigh) {
		this.fire = fire;
        this.segmentHigh = segmentHigh;
        this.segmentLow = segmentLow;
	}

    public synchronized void simulate(int segmentLow, int segmentHigh) {
        // check each grid point to see if it has fire
        // if it has fire then check all neighbours
        // if it can move, then move it, otherwise go to next position
    
        
        }

    @Override
    public void run() {
        while (true) {
            if (flowing.get() == true){

            }
        }        
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
