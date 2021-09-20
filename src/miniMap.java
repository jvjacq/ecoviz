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

public class miniMap extends JPanel{

	private ImagePanel image;
	private BufferedImage terrain;
	private BufferedImage canopy;
	private BufferedImage undergrowth;


	public miniMap(BufferedImage img, BufferedImage layer1, BufferedImage layer0){
		canopy = layer1;
		undergrowth = layer0;
		terrain = img;
		
		//resize minimap
		miniature();
	}

	public miniMap(ImagePanel image){
		this.image = image;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		terrain = image.getTerrain();
		//undergrowth = image.getUndergrowth();
		canopy = image.getCanopy();
		miniature();

		Graphics2D graphics2d = (Graphics2D) g;
		if (terrain != null) {
			graphics2d.drawImage(terrain, 0,0,null);
			//graphics2d.drawImage(undergrowth, 0,0,null);
			graphics2d.drawImage(canopy, 0,0,null);
		}
	}

	//Thanks @Ocracoke for the algorithm here:
	public void miniature(){

		//Elevation
		Image tempElv = terrain.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		BufferedImage outElv = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		/*Undergrowth
		Image tempUnder = undergrowth.getScaledInstance(200,200,Image.SCALE_SMOOTH);
		BufferedImage outUnder = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);*/

		//Canopy
		Image tempCanopy = canopy.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		BufferedImage outCanopy = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = outElv.createGraphics();
		//Graphics2D g2 = outUnder.createGraphics();
		Graphics2D g3 =	outCanopy.createGraphics();

		g.drawImage(tempElv,0,0,null);
		//g2.drawImage(tempUnder,0,0,null);
		g3.drawImage(tempCanopy,0,0,null);
		

		g.dispose();
		//g2.dispose();
		g3.dispose();

		terrain=outElv;
		//undergrowth=outUnder;
		canopy=outCanopy;

	}

}