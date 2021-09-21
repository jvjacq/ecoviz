import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickListener extends MouseAdapter {

	private FireThread panel;

	public ClickListener(FireThread panel) {
		super();
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
		panel.clickFire(e.getX(), e.getY());
	}

}
