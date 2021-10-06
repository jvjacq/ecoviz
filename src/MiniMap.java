/*
* File: MiniMap.java
* MVC: View
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.*;

public class MiniMap extends JPanel{
	private ImagePanel image;
	private BufferedImage terrain;
	private BufferedImage canopy; //Canopy here refers to all plants, as in ImagePanel
	private BufferedImage box;	//Red box indicating position
	
	//Dimensions/Position of red border/box
	private int tlx, tly, boxdimX, boxdimY;

	//Constructed from companion ImagePanel
	public MiniMap(ImagePanel image){
		this.image = image;
	}

	//Set dimension - topleft (x,y) and width, height (dimX, dimY) of red position guide/box
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
		//Fetch new images from image panel
		terrain = image.getTerrain();
		canopy = image.getCanopy();
		//Miniaturize images
		miniature();

		//Paint
		Graphics2D graphics2d = (Graphics2D) g;
		if (terrain != null) {
			graphics2d.drawImage(terrain, 0,0,null);
			graphics2d.drawImage(canopy, 0,0,null);
			graphics2d.drawImage(box, 0,0, null);
		}
	}

	//Thanks @Ocracoke for the algorithm here:
	public void miniature(){

		//Elevation - scale down larger image from ImagePanel
		Image tempElv = terrain.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		BufferedImage outElv = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		//Plants - scale down larger image from ImagePanel
		Image tempCanopy = canopy.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		BufferedImage outCanopy = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		//Red rectangle
		BufferedImage smallbox = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = outElv.createGraphics();
		Graphics2D g2 =	outCanopy.createGraphics();
		Graphics2D g3 = smallbox.createGraphics();

		//Create and draw red box on separate buffered image
		g3.setStroke(new BasicStroke(3.0f));
		g3.setColor(Color.RED);		
		g3.drawRect((int)Math.floor(tlx/1024.0f*200), (int)Math.floor(tly/1024.0f*200), (int)Math.floor(boxdimX/1024.0f*200), (int)Math.floor(boxdimY/1024.0f*200));
		
		g.drawImage(tempElv,0,0,null);
		g2.drawImage(tempCanopy,0,0,null);
		
		//Cleanup
		g.dispose();
		g2.dispose();
		g3.dispose();

		this.terrain=outElv;
		this.canopy=outCanopy;
		this.box = smallbox;

	}

}
