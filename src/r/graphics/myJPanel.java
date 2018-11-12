package r.graphics;

import javax.swing.JPanel;

public class myJPanel extends JPanel {

	public myJPanel() {
		super();
	}
	
	public void request_paint ()
	{
		paint(getGraphics());
	}
}
