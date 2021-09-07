/*
* File: imgPanel.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Status: In progress
*/
//References:
//Zoom functionality : credit to @Thanasis - StackOverflow for the algorithm

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;

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
	private BufferedImage cimg;
	private BufferedImage uimg;

	private BufferedImage miniMap;

	public miniMap(BufferedImage img, BufferedImage layer1, BufferedImage layer0){
		this.img=img;
		cimg = layer1;
		uimg = layer0;

		miniMap = img;

		//resize minimap
		miniature();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (img != null) {

		
		
		graphics2d.drawImage(miniMap, 0,0,null);
		graphics2d.drawImage(uimg, 0,0,null);
		graphics2d.drawImage(cimg, 0,0,null);


		}
	}

	//Thanks Ocracoke for the algorithm here:
	public void miniature(){
		Image temp = miniMap.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		BufferedImage out = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = out.createGraphics();
		g.drawImage(temp,0,0,null);
		g.dispose();

		miniMap=out;

	}

	


}