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
import java.awt.*;

public class miniMap extends JPanel{

	private ImagePanel image;
	private BufferedImage terrain;
	private BufferedImage canopy;
	//private BufferedImage undergrowth;
	private BufferedImage box;
	
	private int tlx, tly, boxdimX, boxdimY;


	public miniMap(BufferedImage img, BufferedImage layer1, BufferedImage layer0){
		//reset constructor
		//canopy = layer1;
		//undergrowth = layer0;
		//terrain = img;
		this.tlx = 0;
		this.tly = 0;
		//this.boxdimX = Terrain.getDimX();
		//this.boxdimY = Terrain.getDimY();
		
		//resize minimap
		miniature();
	}

	public miniMap(ImagePanel image){
		this.image = image;
	}

	public void setZone(int tlx, int tly, int dimX, int dimY){
		this.tlx = tlx;
		this.tly = tly;
		this.boxdimX = dimX;
		this.boxdimY = dimY;
		repaint();
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
			graphics2d.drawImage(box, 0,0, null);
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

		BufferedImage smallbox = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = outElv.createGraphics();
		//Graphics2D g2 = outUnder.createGraphics();
		Graphics2D g2 =	outCanopy.createGraphics();
		Graphics2D g3 = smallbox.createGraphics();
		g3.setStroke(new BasicStroke(3.0f));
		g3.setColor(Color.RED);
		
		g3.drawRect((int)Math.floor(tlx/1024.0f*200), (int)Math.floor(tly/1024.0f*200), (int)Math.floor(boxdimX/1024.0f*200), (int)Math.floor(boxdimY/1024.0f*200));
		//g3.drawRect(tlx/1024*200, tly/1024*200, boxdimX/1024*200, boxdimY/1024*200);

		g.drawImage(tempElv,0,0,null);
		//g2.drawImage(tempUnder,0,0,null);
		g2.drawImage(tempCanopy,0,0,null);
		

		g.dispose();
		//g2.dispose();
		g2.dispose();

		this.terrain=outElv;
		//undergrowth=outUnder;
		this.canopy=outCanopy;
		this.box = smallbox;

	}

}