/*
* File: imgPanel.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Status: In progress
*/
//References:
//Zoom functionality : credit to @Thanasis - StackOverflow for the algorithm

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

public class ImagePanel extends JPanel{

	private int dimY, dimX;
	private BufferedImage terrain;
	private BufferedImage canopy;
	private BufferedImage undergrowth;
	private BufferedImage fire;
	//
	private BufferedImage zoomTerrain;
	private BufferedImage zoomPlants;

	private	double zoomMultiplier;
	private double prevZoomMultiplier;
	private boolean zoom;

	private boolean dragger,released;
	private double xOffset,yOffset;
	private int xDiff,yDiff;
	private Point startPoint;

	private boolean showCanopy;
	private boolean showUnderGrowth;

	private int topleftx,toplefty,newDimX, newDimY;
	//private float scale;

	private int circles;

	public ImagePanel(){
		this.zoomMultiplier = 1;
		this.prevZoomMultiplier = 1;
		this.showCanopy=true;
		this.showUnderGrowth=true;
		this.circles = 0;
		this.xOffset = 0;
		this.yOffset = 0;
	}
	/*public float getScale(){
		return this.scale;
	}*/

	public int getStartX(){
		return this.startPoint.x;
	}

	public int getStartY(){
		return this.startPoint.y;
	}

	public double getZoomMult(){
		return this.zoomMultiplier;
	}

	public BufferedImage getFire(){
		return this.fire;
	}

	public void setFire(BufferedImage fire){
		this.fire = fire;
	}

	public BufferedImage getTerrain(){
		return this.terrain;
	}

	public BufferedImage getCanopy(){
		return this.canopy;
	}

	public BufferedImage getUndergrowth(){
		return this.undergrowth;
	}

	public int getTLX(){
		return this.topleftx;
	}

	public int getTLY(){
		return this.toplefty;
	}

	public int getNewDimX(){
		return this.newDimX;
	}

	public int getNewDimY(){
		return this.newDimY;
	}

	/*public void setScale(float f){
		this.scale = f;
	}*/

	public void setStartPoint(Point p){
		this.startPoint = p;
	}

	public void setXDiff(int x){
		this.xDiff = x;
	}

	public void setYDiff(int y){
		this.yDiff = y;
	}

	public void setDragger(boolean b){
		this.dragger = b;
	}

	public void setReleased(boolean b){
		this.released = b;
	}

	public void setZoom(boolean b){
		this.zoom = b;
	}

	public void setZoomMult(double multiplier){
		this.zoomMultiplier = multiplier;
	}

	

	//========================================================================
	//      Create the greyscale top-down view
	//========================================================================
	public void deriveImg(Terrain terrain){
		//
		circles = 0;
		//
		int dimX = Terrain.getBaseX();
		int dimY = Terrain.getBaseY();
		int scale = 1024/dimX;

		BufferedImage img = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
		//BufferedImage img = new BufferedImage((int)dimX/zoomMultiplier,(int)dimY/zoomMultiplier,BufferedImage.TYPE_INT_ARGB);
		double maxh = -10000.0f;
		double minh = 10000.0f;
		double[][] elevations = terrain.getElevations();
		//determine the range of heights
		for (int y = 0; y <dimY;y++){
			for (int x = 0; x < dimX; x++){
				double h = elevations[y][x];
				if (h>maxh){maxh = h;}
				if (h<minh){minh = h;}  //Can be cut out and done in read loop - optimization
			}
		}

		//find normalized height value
		for (int y = 0; y <dimY;y++){
			for (int x = 0; x < dimX; x++){
				float val = (float) ((elevations[y][x]-minh)/(maxh-minh));
				Color col = new Color(val, val, val, 1.0f);
				img.setRGB(x, y, col.getRGB());
			}
		}
		AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
		AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		//BufferedImage scaled = new BufferedImage(Math.round(dimX*scale),Math.round(dimY*scale),BufferedImage.TYPE_INT_ARGB);
		BufferedImage scaled = new BufferedImage(Terrain.getDimX(),Terrain.getDimY(),BufferedImage.TYPE_INT_ARGB);
		scaled = ato.filter(img, scaled);
		this.terrain = scaled;
		this.zoomTerrain = scaled;
	}

	//========================================================================
    //      Create the colourful circles
    //========================================================================
    public void derivePlants(){
		//int dimx = Math.round(Terrain.getDimX()*scale);
		//int dimy = Math.round(Terrain.getDimY()*scale);
		dimX = Math.round(Terrain.getDimX());
		dimY = Math.round(Terrain.getDimY());
  
		BufferedImage img = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = img.createGraphics();
  
		imgGraphics.setComposite(AlphaComposite.Clear);
		imgGraphics.fillRect(0,0, dimX, dimY);
		
		imgGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		//
		//imgGraphics.setColor(new Color(0,0,0,1.0f));
		//imgGraphics.fillOval(0,0,10,10);
		//
		Species[] specieslist = PlantLayer.getAllSpecies();
		//int[] colourlist = Species.getCOLOURS();
		//System.out.println(this.showCanopy + " " + this.showUnderGrowth);
		for(Plant p: PlantLayer.getPlantList()){
			//imgGraphics.setColor(new Color(colourlist[p.getSpeciesID()], true));
			imgGraphics.setColor(specieslist[p.getSpeciesID()].getColour());
			//++circles;
			//imgGraphics.fillOval(Math.round(p.getX()*scale),Math.round(p.getY()*scale),(int)(Math.round(p.getCanopy())*2*scale),(int)(Math.round(p.getCanopy())*2*scale));
			//System.out.println("Plant before print: " + p.getX() + " " + p.getY());
			if((p.getFilter()) && (specieslist[p.getSpeciesID()].getFilter()) && ((this.showCanopy && p.getLayer()) | (this.showUnderGrowth && !p.getLayer()))){
				imgGraphics.fillOval(p.getX()-(int)p.getCanopy(),p.getY()-(int)p.getCanopy(),(int)p.getCanopy()*2,(int)p.getCanopy()*2);
			}
		}
		canopy = img;
		this.zoomPlants = img;
		//
		//System.out.println(circles);      
	}

	//========================================================================
    //      Overide Paint Component of the Panel:
    //========================================================================
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (terrain != null) {
		if (dragger){
			/*AffineTransform affine = new AffineTransform();
			affine.translate(xOffset+xDiff,yOffset+yDiff);
			affine.scale(zoomMultiplier,zoomMultiplier);
			graphics2d.transform(affine);*/

			if (released){
				xOffset += xDiff;
				yOffset += yDiff;
				//zoomPlants
				dragger = false;
			}

		}
		if (zoom) {
			//AffineTransform affine = new AffineTransform();
			double scale = 1/zoomMultiplier;
			double oldscale = 1/prevZoomMultiplier;
			double scalechange = scale - oldscale;
			double xRelative = MouseInfo.getPointerInfo().getLocation().getX()-getLocationOnScreen().getX();
			double yRelative = MouseInfo.getPointerInfo().getLocation().getY()-getLocationOnScreen().getY();
			xRelative *= oldscale; 
			yRelative *= oldscale;
			xRelative += xOffset; 
			yRelative += yOffset;
			//int newDimX = (int)Math.floor(dimX/zoomMultiplier);
			//int newDimY = (int)Math.floor(dimY/zoomMultiplier);
			newDimX = (int)Math.floor(dimX*scale);
			newDimY = (int)Math.floor(dimY*scale);
			/*double centerx;
			if(xRelative > dimX/2) centerx = dimX - Math.max(Math.abs(xRelative - dimX), newDimX/2);
			else centerx = Math.max(xRelative, newDimX/2.0f + xOffset);
			double centery;
			if(yRelative > dimY/2) centery = dimY - Math.max(Math.abs(yRelative - dimY), newDimY/2);
			else centery = Math.max(yRelative, newDimY/2.0f + yOffset);
			//System.out.println(centerx + " " + centery);
			int topleftx = (int)Math.floor(centerx - newDimX/2.0f);
			int toplefty = (int)Math.floor(centery - newDimY/2.0f);*/
			xOffset += Math.round(-1 * (xRelative * scalechange));
			yOffset += Math.round(-1 * (yRelative * scalechange));
			//System.out.println(xOffset + " " + yOffset);
			topleftx = (int)Math.max(xOffset,0.0f);
			toplefty = (int)Math.max(yOffset, 0.0f);
			if(topleftx + newDimX > dimX) topleftx = dimX - newDimX;
			if(toplefty + newDimY > dimY) toplefty = dimY - newDimY;
			
			//System.out.println(topleftx + " " + toplefty + " " + newDimX + " " + newDimY);
			zoomTerrain = terrain.getSubimage(topleftx, toplefty, newDimX, newDimY);
			//zoomPlants = canopy.getSubimage(topleftx, toplefty, newDimX, newDimY);
			AffineTransform at = AffineTransform.getScaleInstance(zoomMultiplier, zoomMultiplier);
			AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			BufferedImage scaledT = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
			//BufferedImage scaledP = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
			scaledT = ato.filter(zoomTerrain, scaledT);
			//scaledP = ato.filter(zoomPlants, scaledP);
			zoomTerrain = scaledT;
			//zoomPlants = scaledP;
			/*if(Math.floor(Math.log(zoomMultiplier)/Math.log(1.1f) % 2) == 0.0f){
				zoomPlants = zoomPlants(topleftx, toplefty, newDimX, newDimY);
			}else{
				zoomPlants = canopy.getSubimage(topleftx, toplefty, newDimX, newDimY);
				BufferedImage scaledP = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
				scaledP = ato.filter(zoomPlants, scaledP);
				zoomPlants = scaledP;	
			}*/
			zoomPlants = zoomPlants(topleftx, toplefty, newDimX, newDimY);
			prevZoomMultiplier = zoomMultiplier;

			/*System.out.println(centerx + " " + centery);
			double divident = zoomMultiplier/prevZoomMultiplier;

			xOffset = divident * xOffset + (1-divident)*xRelative;
			yOffset = divident * yOffset + (1-divident)*yRelative;

			affine.translate(xOffset,yOffset);
			affine.scale(zoomMultiplier, zoomMultiplier);
			prevZoomMultiplier = zoomMultiplier;
			graphics2d.transform(affine);*/
			if(zoomMultiplier == 1){
				zoom = false;
				xOffset = 0.0;
				yOffset = 0.0;
			}
		} 
		//else derivePlants()
		
		graphics2d.drawImage(zoomTerrain, 0, 0, null);
		//if (showUnderGrowth){graphics2d.drawImage(undergrowth, 0, 0, null);}
		//if (showCanopy){graphics2d.drawImage(canopy, 0, 0, null);}
		graphics2d.drawImage(zoomPlants, 0, 0, null);
		//graphics2d.drawImage(fire, 0, 0, null);
		}
	}

	public BufferedImage zoomPlants(int tlx, int tly, int newX, int newY){
		Species[] specieslist = PlantLayer.getAllSpecies();
		BufferedImage img = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = img.createGraphics();

		for(Plant p: PlantLayer.getPlantList()){
			imgGraphics.setColor(specieslist[p.getSpeciesID()].getColour());
			if((p.getFilter()) && (specieslist[p.getSpeciesID()].getFilter()) && ((this.showCanopy && p.getLayer()) | (this.showUnderGrowth && !p.getLayer()))){
				int x = p.getX();
				int y = p.getY();
				double rad = p.getCanopy();
				if(plantInRect(x,y,rad,tlx,tly,newX,newY)){
					int newx = (int)Math.round((x-rad)*zoomMultiplier- tlx*zoomMultiplier) ;
					int newy = (int)Math.round((y-rad)*zoomMultiplier- tly*zoomMultiplier) ;
					imgGraphics.fillOval(newx,newy,(int)Math.round(rad*2*zoomMultiplier),(int)Math.round(rad*2*zoomMultiplier));
				}
			}
		}
		return img;
	}

	public boolean plantInRect(int x, int y, double rad, int tlx, int tly, int w, int h){
		if((x + rad > tlx) && (x - rad < tlx+w)){
			if((y + rad > tly) && (y - rad < tly+h)){
				//System.out.println("1");
				return true;
				
			}
		}
		return false;
	}

	public void exportImage(String nm){
		BufferedImage img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		paintAll(g);
		try{
			ImageIO.write(img,"png", new File("exports/"+nm+".png"));
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void setShowCanopy(boolean b){
		showCanopy=b;
		repaint();
	}
	public void setShowUnderGrowth(boolean b){
		showUnderGrowth=b;
		repaint();
	}

}