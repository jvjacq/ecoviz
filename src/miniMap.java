import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.MouseInfo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

public class miniMap extends JPanel{

    private BufferedImage img;
    private BufferedImage uImg;
    private BufferedImage cImg;
    private int posX,posY;

    public miniMap(BufferedImage img, BufferedImage layer1, BufferedImage layer0) {
    }


	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (img != null) {

		graphics2d.drawImage(img, 0, 0, null);
		graphics2d.drawImage(uImg, 0, 0, null);
		graphics2d.drawImage(cImg, 0, 0, null);
        if ((posX!=0) && (posY!=0))
            graphics2d.drawOval(posX,posY,50,50);
		}
	}
    

    //Mutator Methods:
    //Sets the position to draw the users position on the map
    //Then repaints()
    public void setPostion(int posX, int posY){
        this.posX=posX;
        this.posY=posY;
        //Position and then repaint... something like this
        repaint();
    }
}
