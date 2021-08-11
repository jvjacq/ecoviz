import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;

public class Gui {

    private int width;
    private int height;

    public Gui(int frameX, int frameY) {
        width = frameX;
        height = frameY;
    }

    public void setupGUI() {
        JFrame frame = new JFrame("EcoViz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        JMenuBar mb = new JMenuBar();
    }
}
