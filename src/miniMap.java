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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;


public class miniMap extends JPanel{

	private BufferedImage eImg;
	private BufferedImage cImg;
	private BufferedImage uImg;


	public miniMap(BufferedImage img, BufferedImage layer1, BufferedImage layer0){
		cImg = layer1;
		uImg = layer0;

		eImg = img;

		//resize minimap
		miniature();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (eImg != null) {

		graphics2d.drawImage(eImg, 0,0,null);
		graphics2d.drawImage(uImg, 0,0,null);
		graphics2d.drawImage(cImg, 0,0,null);
		}
	}

	//Thanks @Ocracoke for the algorithm here:
	public void miniature(){

		//Elevation
		Image tempElv = eImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		BufferedImage outElv = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		//Canopy
		Image tempCanopy = cImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		BufferedImage outCanopy = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		//Undergrowth
		Image tempUnder = uImg.getScaledInstance(200,200,Image.SCALE_SMOOTH);
		BufferedImage outUnder = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = outElv.createGraphics();
		Graphics2D g2 = outCanopy.createGraphics();
		Graphics2D g3 =	outUnder.createGraphics();

		g.setClip(new Ellipse2D.Float(20,20,160,160));
		g.drawImage(tempElv,0,0,null);
		g2.setClip(new Ellipse2D.Float(20,20,160,160));
		g2.drawImage(tempCanopy,0,0,null);
		g3.setClip(new Ellipse2D.Float(20,20,160,160));
		g3.drawImage(tempUnder,0,0,null);

		g.dispose();
		g2.dispose();
		g3.dispose();

		eImg=outElv;
		uImg=outUnder;
		cImg=outCanopy;

	}

}