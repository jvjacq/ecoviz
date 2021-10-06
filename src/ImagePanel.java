/*
* File: ImgPanel.java
* MVC: View
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

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

	//Dimensions of image panel itself
	private int dimY, dimX;

	//All images produced/ displayed in layers
	private BufferedImage terrain;
	private BufferedImage canopy;
	private BufferedImage undergrowth;
	private BufferedImage zoomTerrain;
	private BufferedImage zoomPlants;
	private BufferedImage fire,burnt;
	private BufferedImage firebreakImg;
	private BufferedImage details;	

	//Variables for new dimension calculation
	private boolean dragger,released;
	private double xOffset,yOffset;
	private double xDiff,yDiff;
	private Point startPoint;
	private int topleftx,toplefty,newDimX, newDimY;
	private	double zoomMultiplier;
	private double prevZoomMultiplier;
	private boolean zoom;

	//Variables used to filter plants/Selective painting
	private boolean showCanopy;
	private boolean showUnderGrowth;	
	private float maxHeight, minHeight, maxRadius, minRadius;
	private int[] plantsInView;
	private int selectRad, selectX, selectY;
	private Color speciesColour;

	public ImagePanel(){
		//Default values
		this.zoomMultiplier = 1;
		this.prevZoomMultiplier = 1;
		this.showCanopy=true;
		this.showUnderGrowth=true;
		this.xOffset = 0;
		this.yOffset = 0;
		this.selectRad = -1;
		this.canopy = null;
		this.speciesColour = Color.BLACK;
	}

	public void reset(){
		this.zoomMultiplier = 1;
		this.prevZoomMultiplier = 1;
		this.showCanopy=true;
		this.showUnderGrowth=true;
		this.xOffset = 0;
		this.yOffset = 0;
		this.topleftx = 0;
		this.toplefty = 0;
		this.selectRad = -1;
		this.canopy = null;
		this.speciesColour = Color.BLACK;
	}

	//========================================================================
	//      Create the greyscale top-down view (initial)
	//========================================================================
	public void deriveImg(Terrain terrain){
		int dimX = Terrain.getBaseX();
		int dimY = Terrain.getBaseY();
		int scale = 1024/dimX;

		BufferedImage img = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
		double maxh = -10000.0f;
		double minh = 10000.0f;
		double[][] elevations = terrain.getElevations();
		//Determine the range of heights
		for (int y = 0; y <dimY;y++){
			for (int x = 0; x < dimX; x++){
				double h = elevations[y][x];
				if (h>maxh){maxh = h;}
				if (h<minh){minh = h;}
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
		BufferedImage scaled = new BufferedImage(Terrain.getDimX(),Terrain.getDimY(),BufferedImage.TYPE_INT_ARGB);
		scaled = ato.filter(img, scaled);
		this.terrain = scaled;
		this.zoomTerrain = scaled;
	}
	//Above method as used in water simulation (2nd year) assignment - adapted

	//========================================================================
    //      Overide Paint Component of the Panel:
    //========================================================================
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (terrain != null) {

			graphics2d.drawImage(zoomTerrain, 0, 0, null);
			graphics2d.drawImage(zoomPlants, 0, 0, null);
			graphics2d.drawImage(fire, 0, 0, null);
			graphics2d.drawImage(burnt, 0, 0, null);
			if(details != null) graphics2d.drawImage(details,0,0,null);
			if(firebreakImg != null) graphics2d.drawImage(firebreakImg,0,0,null);
		}		
	}

	//Compute new offsets, topleft coordinates and zoom dimensions (of next image to be rendered)
	public void calculateView(){
		//Computes offset if user currently dragging
		if (dragger){
			xOffset -= 0.05*xDiff;
			yOffset -= 0.05*yDiff;
			if(xOffset < 0) xOffset = 0;
			if(xOffset > dimX - newDimX) xOffset = dimX-newDimX;
			if(yOffset < 0) yOffset = 0;
			if(yOffset > dimY - newDimY) yOffset = dimY-newDimY;
			if (released){
				dragger = false;
			}
			

		}
		//Computes dimensions based on new zoom multiplier and current mouse point
		if (zoom) {
			double scale = 1/zoomMultiplier;
			double oldscale = 1/prevZoomMultiplier;
			double scalechange = scale - oldscale;
			double xRelative = MouseInfo.getPointerInfo().getLocation().getX()-getLocationOnScreen().getX();
			double yRelative = MouseInfo.getPointerInfo().getLocation().getY()-getLocationOnScreen().getY();
			xRelative *= oldscale; 
			yRelative *= oldscale;
			xRelative += xOffset; 
			yRelative += yOffset;
			newDimX = (int)Math.floor(dimX*scale);
			newDimY = (int)Math.floor(dimY*scale);
			xOffset += Math.round(-1 * (xRelative * scalechange));
			yOffset += Math.round(-1 * (yRelative * scalechange));
			
			topleftx = (int)Math.max(xOffset,0.0f);
			toplefty = (int)Math.max(yOffset, 0.0f);
			if(topleftx + newDimX > dimX) topleftx = dimX - newDimX;
			if(toplefty + newDimY > dimY) toplefty = dimY - newDimY;

			prevZoomMultiplier = zoomMultiplier;

			//Fully zoomed out
			if(zoomMultiplier == 1){
				zoom = false;
				xOffset = 0.0;
				yOffset = 0.0;
			}
		}
	}

	//Method to rederive the terrain and plant images, based on any filters/zooms
	public void deriveImage(){
		if(zoomMultiplier == 1.0 && prevZoomMultiplier == 1.0){
			dimX = Math.round(Terrain.getDimX());
			dimY = Math.round(Terrain.getDimY());
			zoomPlants = zoomPlants(0,0,dimX,dimY);
			if(canopy == null) canopy = zoomPlants;	
		}else{
			if(zoomMultiplier < 35.0){
				//Scale terrain, using bilinear interpolation
				zoomTerrain = terrain.getSubimage(topleftx, toplefty, newDimX, newDimY);
				AffineTransform at = AffineTransform.getScaleInstance(zoomMultiplier, zoomMultiplier);
				AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
				BufferedImage scaledT = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
				scaledT = ato.filter(zoomTerrain, scaledT);
				zoomTerrain = scaledT;
			}
			//Rerender plant image
			zoomPlants = zoomPlants(topleftx, toplefty, newDimX, newDimY);	
		}	
	}

	//Return zoomed-in/out image, with re-rendered plants
	public BufferedImage zoomPlants(int tlx, int tly, int newX, int newY){
		setPlantsInView(this.plantsInView.length);
		Species[] specieslist = PlantLayer.getAllSpecies();
		BufferedImage img = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = img.createGraphics();

		for(Plant p: PlantLayer.getPlantList()){
			//Filters for hieght and canopy radius
			if((p.getHeight() > maxHeight) || (p.getHeight() < minHeight)){
				p.setHeightFlag(false);
			}else p.setHeightFlag(true);
			if((p.getCanopy() > maxRadius) || (p.getCanopy() < minRadius)){
				p.setCanopyFlag(false);
			}else p.setCanopyFlag(true);
			if(!p.getHeightFlag() || !p.getCanopyFlag()){
				continue;
			}
			//Filters for select/ surrounding radius
			if(insideSelected(p)){
				p.setFilter(true);
			}else p.setFilter(false);

			//Filters for species removed, layer filtering and firebreak (in that order)
			if((p.getFilter()) && (specieslist[p.getSpeciesID()].getFilter()) && ((this.showCanopy && p.getLayer()) || (this.showUnderGrowth && !p.getLayer())) && (p.isInFireBreak()==0)){
				int x = p.getX();
				int y = p.getY();
				double rad = p.getCanopy();
				//Check if plant in current, zoomed in, view
				if(plantInRect(x,y,rad,tlx,tly,newX,newY)){
					plantsInView[p.getSpeciesID()] += 1;
					imgGraphics.setColor(specieslist[p.getSpeciesID()].getColour());
					int newx = (int)Math.round((x)*zoomMultiplier- tlx*zoomMultiplier) ;
					int newy = (int)Math.round((y)*zoomMultiplier- tly*zoomMultiplier) ;
					int iRad = (int)Math.round(rad*zoomMultiplier);
					//Circle drawing method
					for(int j = newy - iRad; j < (newy+iRad+1); ++j){
						for(int i = newx - iRad; i < (newx+iRad+1); ++i){
							if (j < dimX && j > 0 && i < dimY && i > 0) {
								double dist = Math.sqrt(Math.pow((newx - i), 2) + Math.pow((newy - j), 2));
								if (dist <= iRad-1){
									//Paints using colourMixer method, on either terrain below/ or plant below
									if(img.getRGB(i, j) != 0) img.setRGB(i, j, colorMixer(img.getRGB(i,j),specieslist[p.getSpeciesID()].getColour().getRGB()));
									else img.setRGB(i, j, colorMixer(zoomTerrain.getRGB(i,j),specieslist[p.getSpeciesID()].getColour().getRGB()));
								}
							}

						}
					}
				}
			}
		}
		return img;
	}

	//Check if plant is in give rectangular view
	public boolean plantInRect(int x, int y, double rad, int tlx, int tly, int w, int h){
		if((x + rad > tlx) && (x - rad < tlx+w)){
			if((y + rad > tly) && (y - rad < tly+h)){
				return true;
				
			}
		}
		return false;
	}

	//Display black overlay for selected plant
	public void displayPlant(Plant plant, int radius){
		details = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = details.createGraphics();
		imgGraphics.setComposite(AlphaComposite.Clear);
		imgGraphics.fillRect(0,0, dimX, dimY);
		imgGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		if(plant == null){
			selectRad = -1;
			selectX = -1;
			selectY = -1;
			return;
		}
		imgGraphics.setColor(speciesColour);
		int x = plant.getX();
		int y = plant.getY();
		double rad = plant.getCanopy();
		int newx = (int)Math.round((x-rad)*zoomMultiplier- topleftx*zoomMultiplier) ;
		int newy = (int)Math.round((y-rad)*zoomMultiplier- toplefty*zoomMultiplier) ;
		imgGraphics.fillOval(newx,newy,(int)Math.round(rad*2*zoomMultiplier),(int)Math.round(rad*2*zoomMultiplier));
		this.selectRad = radius;
		this.selectX = x;
		this.selectY = y;
		if(radius != -1){
			//Display black ring to indicate 'select radius'
			imgGraphics.setColor(Color.BLACK);
			imgGraphics.drawOval((int)Math.round((x-radius-topleftx)*zoomMultiplier), (int)Math.round((y-radius-toplefty)*zoomMultiplier), (int)Math.round(radius*2*zoomMultiplier), (int)Math.round(radius*2*zoomMultiplier));
		}
	}

	//Draw red brush around users mouse
	public void drawFirebreak(Firebreak fb){
		if(firebreakImg == null) firebreakImg = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = firebreakImg.createGraphics();
		g.setColor(Color.RED);
		int x = startPoint.x-(int)getLocationOnScreen().getX();
		int y = startPoint.y-(int)getLocationOnScreen().getY();
		int strokesize = 20;
		if(!released){			
			if(!dragger){
				//Circle drawing method
				for (int j = y - strokesize - 1; j < (y + strokesize + 1); j++) {
					for (int i = x - strokesize; i < (x + strokesize + 1); i++) {
						if (j < Terrain.getDimY() && j > 0 && i < Terrain.getDimX() && i > 0) {
							double dist = Math.sqrt(Math.pow((x - i), 2) + Math.pow((y - j), 2));
							if (dist <= strokesize) {
								firebreakImg.setRGB(i,j,(255 << 24) + (255 << 16));
								//Add selected circular region to firebreak instance
								fb.addToRegion(i, j);
							}
						}
					}
				}
			}else{
				//Dargging - must update offsets
				x += (int)xDiff;
				y += (int)yDiff;
				//Circle drawing method
				for (int j = y - strokesize - 1; j < (y + strokesize + 1); j++) {
					for (int i = x - strokesize; i < (x + strokesize + 1); i++) {
						if (j < Terrain.getDimY() && j > 0 && i < Terrain.getDimX() && i > 0) {
							double dist = Math.sqrt(Math.pow((x - i), 2) + Math.pow((y - j), 2));
							if (dist <= strokesize) {
								firebreakImg.setRGB(i,j,(255 << 24) + (255 << 16));
								//Add selected circular region to firebreak instance
								fb.addToRegion(i, j);
							}
						}
					}
				}
			}
		}else{
			dragger = false;
			for(Plant p: PlantLayer.getPlantList()){
				if(fb.inFirebreak(p)) p.incFirebreak();
			}
			//Add completed firebreak to list
			Firebreak.addCompleted(fb);
			firebreakImg = null;
		}
	}

	//Check if plant inside currently selected radius
	public boolean insideSelected(Plant p){
		if(selectRad == -1) return true;
		return (Math.pow((p.getX() - selectX),2) + Math.pow((p.getY()-selectY),2) <= Math.pow(selectRad,2));
	}

	//Resets/clears details buffered image
	public void resetDetails(){
		details = new BufferedImage(dimX,dimY,BufferedImage.TYPE_INT_ARGB);
	}

	//Export current image as png
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

	//Booleans to filter the layers independently
	public void setShowCanopy(boolean b){
		showCanopy=b;
	}
	public void setShowUnderGrowth(boolean b){
		showUnderGrowth=b;
	}

	//Set heights and canopy radius range for displaying plants
	public void setFilterLimits(float minH, float maxH, float minR, float maxR){
		this.minHeight = minH;
		this.maxHeight = maxH;
		this.minRadius = minR;
		this.maxRadius = maxR;
	}

	//Mix 2 rgb int colours with alpha values
	public int colorMixer(int col1, int col2){
		return (255 << 24) + ((((col1 & 0x00FF0000) >>17) + ((col2 & 0x00FF0000) >>17)) << 16) + ((((col1 & 0x0000FF00) >>9) + ((col2 & 0x0000FF00) >>9)) << 8) + (((col1 & 0x000000FF) >>1) + ((col2 & 0x000000FF) >>1));
	}

	//Accessor methods
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

	public void setFireRender(BufferedImage fire){
		this.fire = fire;
		repaint();
	}

	public void setBurntRender(BufferedImage burnt){
		this.burnt = burnt;
		repaint();
	}

	public void setBurnt(BufferedImage burnt){
		this.burnt = burnt;
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

	public boolean getDragger(){
		return this.dragger;
	}

	public int[] getPlantsInView(){
		return this.plantsInView;
	}

	public boolean getShowCanopy(){
		return this.showCanopy;
	}

	public boolean getShowUndergrowth(){
		return this.showUnderGrowth;
	}

	//Mutator methods
	public void setStartPoint(Point p){
		this.startPoint = p;
	}

	public void setXDiff(double x){
		this.xDiff = x;
	}

	public void setYDiff(double y){
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

	public void setPrevZoomMult(double multiplier){
		this.prevZoomMultiplier = multiplier;
	}

	public void setPlantsInView(int size){
		this.plantsInView = new int[size];
		for(int i = 0; i < plantsInView.length; ++i){
			plantsInView[i] = 0;
		} 
	}

	public void setSpeciesColor(Color c){
		this.speciesColour = c;
	}

}