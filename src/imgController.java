import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Point;
import java.awt.MouseInfo;

public class imgController implements MouseWheelListener, MouseListener, MouseMotionListener{
    private ImagePanel image;
    private Gui gui;

    public imgController(ImagePanel img, Gui gui){
        this.image = img;
        this.gui = gui;
    }

    @Override
	public void mouseDragged(MouseEvent e) {
		Point cursor = e.getLocationOnScreen();
		image.setXDiff(cursor.x - image.getStartX());
		image.setYDiff(cursor.y - image.getStartY());

		image.setDragger(true);
		image.repaint();	
	}

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point click = e.getPoint();
        int col = image.getCanopy().getRGB(click.x, click.y);
        int[] speciesColours = Species.getCOLOURS();
        String[][] specieslist = Species.getSPECIES();
        for(int idx = 0; idx < speciesColours.length; ++idx){
            if(speciesColours[idx] == col){
                gui.setSpeciesDetails("Common name:\n" + specieslist[idx][0] + "\nLatin name:\n" + specieslist[idx][1]);
                break;
            }
        }

        
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
	public void mousePressed(MouseEvent e) {
        image.setReleased(false);
        image.setStartPoint( MouseInfo.getPointerInfo().getLocation() );
		
	}

    @Override
	public void mouseReleased(MouseEvent e) {
		image.setReleased(true);
		image.repaint();
		
	}

    @Override
	public void mouseWheelMoved (MouseWheelEvent e){
		image.setZoom(true);
        double multiplier = image.getZoomMult();
		if (e.getWheelRotation() < 0) {	// Zoom in
			multiplier *=1.1;	//Adjust for smoothness
            image.setZoomMult(multiplier);
			image.repaint();
		}
		if (e.getWheelRotation() > 0) {	// Zoom out
			multiplier /=1.1;	//Adjust for smoothness
            image.setZoomMult(multiplier);
			image.repaint();
		}
	}

}