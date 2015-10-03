package algs.example.gui.problems.segmentIntersection;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Application to launch when considering intersecting line segments.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class LaunchSegmentIntersection {
	public static void main (String []args) {
		final IntersectingSegmentsGUI mf = new IntersectingSegmentsGUI();
		mf.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				mf.setVisible(false);
				System.exit(0);
			}
		});
		
		mf.setVisible(true);
	}
}
