import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

import java.awt.BorderLayout;

public class Gui {

    public Gui() {

        JFrame frame = new JFrame("EcoViz");
        JPanel panel = new JPanel();
        // JMenuBar mb = new JMenuBar();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pack and Show
        frame.pack();
        frame.setVisible(true);

    }
}
