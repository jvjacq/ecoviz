import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics2D;

import java.awt.Graphics;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.AlphaComposite;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class RenderPanel extends JPanel {

    private BufferedImage img;

    public RenderPanel() {

    }

    // ========================================================================
    // Overide Paint Component of the Panel:
    // ========================================================================
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2d = (Graphics2D) g;
        if (img != null) {

            graphics2d.drawImage(img, 0, 0, null);
        }
    }

    public void setFrame(BufferedImage b){
        img = b;
        repaint();
    }
}
