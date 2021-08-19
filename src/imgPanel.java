
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;

public class imgPanel extends JPanel {
	private Terrain land;
	private Graphics2D graph;
	private Graphics graphics;

	public imgPanel(Terrain l){
		land=l;
	}

	

	@Override 
	protected void paintComponent(Graphics g){
		//BufferedImage 
		graphics = g;
		super.paintComponent(g);

		//Draw landscape in greyscale:
		if (land.getImg() != null) {

			g.drawImage(land.getImg(), 0, 0, null);
		
		}
	}
}