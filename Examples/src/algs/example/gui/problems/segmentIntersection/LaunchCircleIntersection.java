package algs.example.gui.problems.segmentIntersection;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Application to launch when considering intersecting circles.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LaunchCircleIntersection {
	public static void main (String []args) {
		final IntersectingCirclesGUI mf = new IntersectingCirclesGUI();
		mf.addWindowListener(new WindowAdapter() {

			public void windowClosed(WindowEvent e) {	
				mf.setVisible(false);
				System.exit(0);
			}

			public void windowClosing(WindowEvent e) {
				mf.setVisible(false);
				System.exit(0);
			}
		});
		
		mf.setVisible(true);
	}
}
