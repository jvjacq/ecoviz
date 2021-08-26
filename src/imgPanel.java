/*
* File: imgPanel.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Status: In progress
*/
//References:
//Zoom functionality : credit to @Thanasis - StackOverflow for the algorithm

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

public class imgPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener{

	private Terrain land;
	private Graphics2D graph;
	private Graphics graphics;

	private BufferedImage img;

	private	double zoomMultiplier = 1;
	private double prevZoomMultiplier = 1;
	private boolean zoom;

	private boolean zoomer,dragger,released;
	private double xOffset,yOffset = 0;
	private int xDiff,yDiff;
	private Point startPoint;

	public imgPanel(BufferedImage img){
		this.img=img;
		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (img != null) {

		if (zoom) {
			AffineTransform affine = new AffineTransform();

			double xRelative = MouseInfo.getPointerInfo().getLocation().getX()-getLocationOnScreen().getX();
			double yRelative = MouseInfo.getPointerInfo().getLocation().getY()-getLocationOnScreen().getY();

			double divident = zoomMultiplier/prevZoomMultiplier;

			xOffset = divident * xOffset + (1-divident)*xRelative;
			yOffset = divident * yOffset + (1-divident)*yRelative;

			affine.translate(xOffset,yOffset);
			affine.scale(zoomMultiplier, zoomMultiplier);
			prevZoomMultiplier = zoomMultiplier;
			graphics2d.transform(affine);
			zoom = false;
		} 
		
		if (dragger){
			AffineTransform affine = new AffineTransform();
			affine.translate(xOffset+xDiff,yOffset+yDiff);
			affine.scale(zoomMultiplier,zoomMultiplier);
			graphics2d.transform(affine);

			if (released){
				xOffset += xDiff;
				yOffset += yDiff;
				dragger = false;
			}

		}
		
		graphics2d.drawImage(img, 0, 0, null);

		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point cursor = e.getLocationOnScreen();
		xDiff = cursor.x - startPoint.x;
		yDiff = cursor.y - startPoint.y;

		dragger = true;
		repaint();

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		released= true;
		repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		released = false;
		startPoint= MouseInfo.getPointerInfo().getLocation();
		
	}

	@Override
	public void mouseWheelMoved (MouseWheelEvent e){
		zoom = true;

		if (e.getWheelRotation() < 0) {	// Zoom in
			zoomMultiplier *=1.1;	//Adjust for smoothness
			System.out.println("Zooming in");
			repaint();
		}
		if (e.getWheelRotation() > 0) {	// Zoom out
			zoomMultiplier /=1.1;	//Adjust for smoothness
			System.out.println("Zooming out");

			repaint();
		}



	}

	

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	


}