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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

public class imgPanel extends JPanel implements MouseWheelListener{

	private Terrain land;
	private Graphics2D graph;
	private Graphics graphics;

	private BufferedImage img;

	private	double zoomMultiplier = 1;
	private double prevZoomMultiplier = 1;
	private boolean zoom;

	public imgPanel(BufferedImage img){
		this.img=img;
		addMouseWheelListener(this);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (img != null) {

		if (zoom) {
			AffineTransform affine = new AffineTransform();
			affine.scale(zoomMultiplier, zoomMultiplier);
			prevZoomMultiplier = zoomMultiplier;
			graphics2d.transform(affine);
			zoom = false;
		}
		
		g.drawImage(img, 0, 0, null);

		}
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


}